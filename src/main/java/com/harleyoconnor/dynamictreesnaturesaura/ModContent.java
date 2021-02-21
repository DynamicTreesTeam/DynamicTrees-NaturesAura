package com.harleyoconnor.dynamictreesnaturesaura;

import com.ferreusveritas.dynamictrees.ModItems;
import com.ferreusveritas.dynamictrees.ModRecipes;
import com.ferreusveritas.dynamictrees.ModTrees;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.cells.ICellKit;
import com.ferreusveritas.dynamictrees.api.client.ModelHelper;
import com.ferreusveritas.dynamictrees.api.treedata.ILeavesProperties;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.blocks.BlockSurfaceRoot;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.ferreusveritas.dynamictrees.blocks.LeavesProperties;
import com.ferreusveritas.dynamictrees.items.DendroPotion.DendroPotionType;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.ferreusveritas.dynamictrees.trees.TreeFamilyVanilla;
import com.harleyoconnor.dynamictreesnaturesaura.blocks.BlockDynamicLeavesAncient;
import com.harleyoconnor.dynamictreesnaturesaura.blocks.BlockDynamicLeavesDecayed;
import com.harleyoconnor.dynamictreesnaturesaura.blocks.BlockDynamicLeavesGolden;
import com.harleyoconnor.dynamictreesnaturesaura.trees.TreeAncient;
import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.*;

@Mod.EventBusSubscriber(modid = DynamicTreesNaturesAura.MODID)
public class ModContent {

	public static Item itemAncientSeed;

	public static BlockDynamicLeaves ancientLeaves;
	public static BlockSurfaceRoot ancientRoot;
	public static ILeavesProperties ancientLeavesProperties;

	public static final List<BlockDynamicLeaves> leaves = new ArrayList<>();
	public static final ArrayList<TreeFamily> trees = new ArrayList<>();

	public static final Map<TreeFamily, ILeavesProperties> decayedLeavesVariants = new HashMap<>();
	public static final Map<TreeFamily, ILeavesProperties> goldenLeavesVariants = new HashMap<>();

	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();

		final TreeFamily oakTree = Objects.requireNonNull(Species.REGISTRY.getValue(new ResourceLocation(ModTrees.OAK))).getFamily();

		for (Species species : Species.REGISTRY) {
			final TreeFamily family = species.getFamily();
			final Species commonSpecies = family.getCommonSpecies();
			ILeavesProperties goldenLeavesProperties = goldenLeavesVariants.get(family);
			ILeavesProperties decayedLeavesProperties = decayedLeavesVariants.get(family);

			// Only add leaves for vanilla families (for now).
			if (!(family instanceof TreeFamilyVanilla) || commonSpecies.getRegistryName() == null) continue;

			if ((!ModConfig.GOLD_LEAF_NEEDS_OAK || family.equals(oakTree)) && goldenLeavesProperties == null) {
				final BlockDynamicLeaves goldenLeaves = new BlockDynamicLeavesGolden(commonSpecies.getRegistryName().getResourcePath());
				goldenLeavesProperties = setUpGoldenLeaves(ModBlocks.GOLDEN_LEAVES, commonSpecies.getLeavesProperties().getCellKit());

				goldenLeavesProperties.setTree(species.getFamily());
				// Set default as highest stage for tree falling animation.
				goldenLeavesProperties.setDynamicLeavesState(goldenLeaves.getDefaultState().withProperty(BlockDynamicLeaves.TREE, 0)
						.withProperty(BlockDynamicLeavesGolden.STAGE, BlockDynamicLeavesGolden.HIGHEST_STAGE));
				goldenLeaves.setProperties(0, goldenLeavesProperties);

				registry.register(goldenLeaves);
				goldenLeavesVariants.put(family, goldenLeavesProperties);
			}

			if (decayedLeavesProperties == null) {
				final BlockDynamicLeaves decayedDynamicLeaves = new BlockDynamicLeavesDecayed(commonSpecies.getRegistryName().getResourcePath());
				decayedLeavesProperties = setUpLeaves(ModBlocks.DECAYED_LEAVES, commonSpecies.getLeavesProperties().getCellKit());

				decayedLeavesProperties.setTree(species.getFamily());
				decayedLeavesProperties.setDynamicLeavesState(decayedDynamicLeaves.getDefaultState().withProperty(BlockDynamicLeaves.TREE, 0));
				decayedDynamicLeaves.setProperties(0, decayedLeavesProperties);

				registry.register(decayedDynamicLeaves);
				decayedLeavesVariants.put(family, decayedLeavesProperties);
			}

			species.addValidLeavesBlocks(goldenLeavesProperties, decayedLeavesProperties);
		}

		ancientLeaves = new BlockDynamicLeavesAncient();
		registry.register(ancientLeaves);

		ancientLeavesProperties = setUpLeaves(ModBlocks.ANCIENT_LEAVES, TreeRegistry.findCellKit("deciduous"));

		ancientLeavesProperties.setDynamicLeavesState(ancientLeaves.getDefaultState().withProperty(BlockDynamicLeaves.TREE, 0));
		ancientLeaves.setProperties(0, ancientLeavesProperties);

		leaves.add(ancientLeaves);

		goldenLeavesVariants.forEach((species, properties) -> leaves.add((BlockDynamicLeaves) properties.getDynamicLeavesState().getBlock()));
		decayedLeavesVariants.forEach((species, properties) -> leaves.add((BlockDynamicLeaves) properties.getDynamicLeavesState().getBlock()));

		TreeFamily ancientTree = new TreeAncient();
		itemAncientSeed = ancientTree.getCommonSpecies().getSeedStack(1).getItem();

		Collections.addAll(trees, ancientTree);

		trees.forEach(tree -> tree.registerSpecies(Species.REGISTRY));
		ArrayList<Block> treeBlocks = new ArrayList<>();
		trees.forEach(tree -> tree.getRegisterableBlocks(treeBlocks));
		treeBlocks.addAll(LeavesPaging.getLeavesMapForModId(DynamicTreesNaturesAura.MODID).values());
		registry.registerAll(treeBlocks.toArray(new Block[0]));
	}

	public static ILeavesProperties setUpLeaves (Block primitiveLeavesBlock, ICellKit cellKit){
		return new LeavesProperties(
				primitiveLeavesBlock.getDefaultState(),
				new ItemStack(primitiveLeavesBlock, 1, 0),
				cellKit) {
			@Override
			public ItemStack getPrimitiveLeavesItemStack() {
				return new ItemStack(primitiveLeavesBlock, 1, 0);
			}

			@Override
			public int getLightRequirement() {
				return 1;
			}
		};
	}

	public static ILeavesProperties setUpGoldenLeaves (Block primitiveLeavesBlock, ICellKit cellKit){
		return new LeavesProperties(
				primitiveLeavesBlock.getDefaultState(),
				new ItemStack(primitiveLeavesBlock, 1, 0),
				cellKit) {
			@Override
			public int foliageColorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos) {
				int color = 0xF2FF00;

				if (state != null && world != null && pos != null) {
					int foliage = BiomeColorHelper.getFoliageColorAtPos(world, pos);
					return Helper.blendColors(color, foliage, state.getValue(BlockDynamicLeavesGolden.STAGE) / (float) BlockDynamicLeavesGolden.HIGHEST_STAGE);
				}

				return color;
			}

			@Override
			public ItemStack getPrimitiveLeavesItemStack() {
				return new ItemStack(primitiveLeavesBlock, 1, 0);
			}

			@Override
			public int getLightRequirement() {
				return 1;
			}
		};
	}

	@SubscribeEvent public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();

		ArrayList<Item> treeItems = new ArrayList<>();
		trees.forEach(tree -> tree.getRegisterableItems(treeItems));
		registry.registerAll(treeItems.toArray(new Item[treeItems.size()]));
	}

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		setUpSeedRecipes("ancient", new ItemStack(TreeAncient.saplingBlock, 1, 0));
	}

	public static void setUpSeedRecipes (String name, ItemStack treeSapling){
		Species treeSpecies = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNaturesAura.MODID, name));
		ItemStack treeSeed = treeSpecies.getSeedStack(1);
		ItemStack treeTransformationPotion = ModItems.dendroPotion.setTargetTree(new ItemStack(ModItems.dendroPotion, 1, DendroPotionType.TRANSFORM.getIndex()), treeSpecies.getFamily());
		BrewingRecipeRegistry.addRecipe(new ItemStack(ModItems.dendroPotion, 1, DendroPotionType.TRANSFORM.getIndex()), treeSeed, treeTransformationPotion);
		ModRecipes.createDirtBucketExchangeRecipes(treeSapling, treeSeed, true);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		for (TreeFamily tree : trees) {
			ModelHelper.regModel(tree.getDynamicBranch());
			ModelHelper.regModel(tree.getCommonSpecies().getSeed());
			ModelHelper.regModel(tree);
		}
		LeavesPaging.getLeavesMapForModId(DynamicTreesNaturesAura.MODID).forEach((key, leaves) -> ModelLoader.setCustomStateMapper(leaves, new StateMap.Builder().ignore(BlockLeaves.DECAYABLE).build()));
	}
}
