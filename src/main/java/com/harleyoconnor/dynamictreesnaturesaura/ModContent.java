package com.harleyoconnor.dynamictreesnaturesaura;

import com.ferreusveritas.dynamictrees.ModConstants;
import com.ferreusveritas.dynamictrees.ModItems;
import com.ferreusveritas.dynamictrees.ModRecipes;
import com.ferreusveritas.dynamictrees.ModTrees;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.WorldGenRegistry.BiomeDataBasePopulatorRegistryEvent;
import com.ferreusveritas.dynamictrees.api.cells.ICellKit;
import com.ferreusveritas.dynamictrees.api.client.ModelHelper;
import com.ferreusveritas.dynamictrees.api.treedata.ILeavesProperties;
import com.ferreusveritas.dynamictrees.blocks.*;
import com.ferreusveritas.dynamictrees.items.DendroPotion.DendroPotionType;
import com.ferreusveritas.dynamictrees.items.Seed;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.ferreusveritas.dynamictrees.trees.TreeFamilyVanilla;
import com.ferreusveritas.dynamictrees.trees.TreeOak;
import com.harleyoconnor.dynamictreesnaturesaura.blocks.BlockDynamicLeavesDecayed;
import com.harleyoconnor.dynamictreesnaturesaura.blocks.BlockDynamicLeavesGolden;
import de.ellpeck.naturesaura.blocks.BlockDecayedLeaves;
import de.ellpeck.naturesaura.blocks.ModBlocks;
import com.harleyoconnor.dynamictreesnaturesaura.blocks.BlockDynamicLeavesAncient;
import com.harleyoconnor.dynamictreesnaturesaura.trees.TreeAncient;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.commons.lang3.ArrayUtils;
import scala.actors.threadpool.Arrays;

import java.util.*;

@Mod.EventBusSubscriber(modid = DynamicTreesNaturesAura.MODID)
public class ModContent {

	public static Item itemAncientSeed;

	public static BlockDynamicLeaves ancientLeaves;
	public static BlockSurfaceRoot ancientRoot;
	public static ILeavesProperties ancientLeavesProperties, goldenLeavesProperties, decayedLeavesProperties;

	public static final List<BlockDynamicLeaves> leaves = new ArrayList<>();
	public static final ArrayList<TreeFamily> trees = new ArrayList<>();

	public static final Map<Species, BlockDynamicLeaves> decayedLeavesVariants = new HashMap<>();
	public static final Map<Species, BlockDynamicLeaves> goldenLeavesVariants = new HashMap<>();

	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();

		final TreeOak oakTree = (TreeOak) Species.REGISTRY.getValue(new ResourceLocation(ModConstants.MODID, "oak")).getFamily();

		if (ModConfig.GOLD_LEAF_NEEDS_OAK) {
			final BlockDynamicLeaves goldenLeaves = new BlockDynamicLeavesGolden(oakTree.getCommonSpecies().getRegistryName().getResourcePath());

			goldenLeavesProperties = setUpLeaves(ModBlocks.GOLDEN_LEAVES, TreeRegistry.findCellKit("deciduous"));
			goldenLeavesProperties.setDynamicLeavesState(goldenLeaves.getDefaultState().withProperty(BlockDynamicLeaves.TREE, 0));
			goldenLeavesProperties.setTree(oakTree);

			goldenLeaves.setProperties(0, goldenLeavesProperties);

			goldenLeavesVariants.put(oakTree.getCommonSpecies(), goldenLeaves);
			registry.register(goldenLeaves);
		}

		final List<TreeFamily> registeredFamilies = new ArrayList<>();

		for (Species species : Species.REGISTRY) {
			// Only add leaves for each common species.
			 if (!species.getFamily().getCommonSpecies().equals(species) || registeredFamilies.contains(species.getFamily()) || !(species.getFamily() instanceof TreeFamilyVanilla)) continue;

			if (!ModConfig.GOLD_LEAF_NEEDS_OAK) {
				final BlockDynamicLeaves goldenLeaves = new BlockDynamicLeavesGolden(species.getRegistryName().getResourcePath());
				final ILeavesProperties goldLeavesProperties = setUpLeaves(ModBlocks.GOLDEN_LEAVES, species.getLeavesProperties().getCellKit());

				goldLeavesProperties.setTree(species.getFamily());
				goldLeavesProperties.setDynamicLeavesState(goldenLeaves.getDefaultState().withProperty(BlockDynamicLeaves.TREE, 0));
				goldenLeaves.setProperties(0, goldLeavesProperties);

				registry.register(goldenLeaves);
				goldenLeavesVariants.put(species.getFamily().getCommonSpecies(), goldenLeaves);
			}

			final BlockDynamicLeaves decayedLeaves = new BlockDynamicLeavesDecayed(species.getRegistryName().getResourcePath());
			final ILeavesProperties decayedLeavesProperties = setUpLeaves(ModBlocks.DECAYED_LEAVES, species.getLeavesProperties().getCellKit());

			decayedLeavesProperties.setTree(species.getFamily());
			decayedLeavesProperties.setDynamicLeavesState(decayedLeaves.getDefaultState().withProperty(BlockDynamicLeaves.TREE, 0));
			decayedLeaves.setProperties(0, decayedLeavesProperties);

			registry.register(decayedLeaves);
			decayedLeavesVariants.put(species, decayedLeaves);

			registeredFamilies.add(species.getFamily());
		}

		ancientLeaves = new BlockDynamicLeavesAncient();
		registry.register(ancientLeaves);

		ancientLeavesProperties = setUpLeaves(ModBlocks.ANCIENT_LEAVES, TreeRegistry.findCellKit("deciduous"));

		ancientLeavesProperties.setDynamicLeavesState(ancientLeaves.getDefaultState().withProperty(BlockDynamicLeaves.TREE, 0));
		ancientLeaves.setProperties(0, ancientLeavesProperties);

		leaves.add(ancientLeaves);
		goldenLeavesVariants.forEach((species, leavesBlock) -> leaves.add(leavesBlock));
		decayedLeavesVariants.forEach((species, leavesBlock) -> leaves.add(leavesBlock));

		TreeFamily ancientTree = new TreeAncient();
		itemAncientSeed = ancientTree.getCommonSpecies().getSeedStack(1).getItem();

		Collections.addAll(trees, ancientTree);

		trees.forEach(tree -> tree.registerSpecies(Species.REGISTRY));
		ArrayList<Block> treeBlocks = new ArrayList<>();
		trees.forEach(tree -> tree.getRegisterableBlocks(treeBlocks));
		treeBlocks.addAll(LeavesPaging.getLeavesMapForModId(DynamicTreesNaturesAura.MODID).values());
		registry.registerAll(treeBlocks.toArray(new Block[treeBlocks.size()]));
	}

	public static ILeavesProperties setUpLeaves (Block primitiveLeavesBlock, ICellKit cellKit){
		return new LeavesProperties(
				primitiveLeavesBlock.getDefaultState(),
				new ItemStack(primitiveLeavesBlock, 1, 0),
				cellKit) {
			@Override public ItemStack getPrimitiveLeavesItemStack() {
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
