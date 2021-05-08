package com.harleyoconnor.dtnaturesaura;

import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DTNaturesAura.MOD_ID)
public class ModContent {

//	public static Item itemAncientSeed;
//
//	public static DynamicLeavesBlock ancientLeaves;
//	public static BlockSurfaceRoot ancientRoot;
//	public static ILeavesProperties ancientLeavesProperties, goldenLeavesProperties, decayedLeavesProperties;
//
//	public static final List<BlockDynamicLeaves> leaves = new ArrayList<>();
//	public static final ArrayList<TreeFamily> trees = new ArrayList<>();
//
//	public static final Map<Species, BlockDynamicLeaves> decayedLeavesVariants = new HashMap<>();
//	public static final Map<Species, BlockDynamicLeaves> goldenLeavesVariants = new HashMap<>();
//
//	@SubscribeEvent
//	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
//		IForgeRegistry<Block> registry = event.getRegistry();
//
//		final TreeOak oakTree = (TreeOak) Species.REGISTRY.getValue(new ResourceLocation(ModConstants.MODID, "oak")).getFamily();
//
//		if (ModConfig.GOLD_LEAF_NEEDS_OAK) {
//			final BlockDynamicLeaves goldenLeaves = new BlockDynamicLeavesGolden(oakTree.getCommonSpecies().getRegistryName().getResourcePath());
//
//			goldenLeavesProperties = setUpLeaves(ModBlocks.GOLDEN_LEAVES, TreeRegistry.findCellKit("deciduous"));
//			goldenLeavesProperties.setDynamicLeavesState(goldenLeaves.getDefaultState().withProperty(BlockDynamicLeaves.TREE, 0));
//			goldenLeavesProperties.setTree(oakTree);
//
//			goldenLeaves.setProperties(0, goldenLeavesProperties);
//
//			goldenLeavesVariants.put(oakTree.getCommonSpecies(), goldenLeaves);
//			registry.register(goldenLeaves);
//		}
//
//		final List<TreeFamily> registeredFamilies = new ArrayList<>();
//
//		for (Species species : Species.REGISTRY) {
//			// Only add leaves for each common species.
//			 if (!species.getFamily().getCommonSpecies().equals(species) || registeredFamilies.contains(species.getFamily()) || !(species.getFamily() instanceof TreeFamilyVanilla)) continue;
//
//			if (!ModConfig.GOLD_LEAF_NEEDS_OAK) {
//				final BlockDynamicLeaves goldenLeaves = new BlockDynamicLeavesGolden(species.getRegistryName().getResourcePath());
//				final ILeavesProperties goldLeavesProperties = setUpLeaves(ModBlocks.GOLDEN_LEAVES, species.getLeavesProperties().getCellKit());
//
//				goldLeavesProperties.setTree(species.getFamily());
//				goldLeavesProperties.setDynamicLeavesState(goldenLeaves.getDefaultState().withProperty(BlockDynamicLeaves.TREE, 0));
//				goldenLeaves.setProperties(0, goldLeavesProperties);
//
//				registry.register(goldenLeaves);
//				goldenLeavesVariants.put(species.getFamily().getCommonSpecies(), goldenLeaves);
//			}
//
//			final BlockDynamicLeaves decayedLeaves = new BlockDynamicLeavesDecayed(species.getRegistryName().getResourcePath());
//			final ILeavesProperties decayedLeavesProperties = setUpLeaves(ModBlocks.DECAYED_LEAVES, species.getLeavesProperties().getCellKit());
//
//			decayedLeavesProperties.setTree(species.getFamily());
//			decayedLeavesProperties.setDynamicLeavesState(decayedLeaves.getDefaultState().withProperty(BlockDynamicLeaves.TREE, 0));
//			decayedLeaves.setProperties(0, decayedLeavesProperties);
//
//			registry.register(decayedLeaves);
//			decayedLeavesVariants.put(species, decayedLeaves);
//
//			registeredFamilies.add(species.getFamily());
//		}
//
//		ancientLeaves = new DynamicAncientLeavesBlock();
//		registry.register(ancientLeaves);
//
//		ancientLeavesProperties = setUpLeaves(ModBlocks.ANCIENT_LEAVES, TreeRegistry.findCellKit("deciduous"));
//
//		ancientLeavesProperties.setDynamicLeavesState(ancientLeaves.getDefaultState().withProperty(BlockDynamicLeaves.TREE, 0));
//		ancientLeaves.setProperties(0, ancientLeavesProperties);
//
//		leaves.add(ancientLeaves);
//		goldenLeavesVariants.forEach((species, leavesBlock) -> leaves.add(leavesBlock));
//		decayedLeavesVariants.forEach((species, leavesBlock) -> leaves.add(leavesBlock));
//
//		TreeFamily ancientTree = new TreeAncient();
//		itemAncientSeed = ancientTree.getCommonSpecies().getSeedStack(1).getItem();
//
//		Collections.addAll(trees, ancientTree);
//
//		trees.forEach(tree -> tree.registerSpecies(Species.REGISTRY));
//		ArrayList<Block> treeBlocks = new ArrayList<>();
//		trees.forEach(tree -> tree.getRegisterableBlocks(treeBlocks));
//		treeBlocks.addAll(LeavesPaging.getLeavesMapForModId(DTNaturesAura.MODID).values());
//		registry.registerAll(treeBlocks.toArray(new Block[treeBlocks.size()]));
//	}
//
//	public static ILeavesProperties setUpLeaves (Block primitiveLeavesBlock, ICellKit cellKit){
//		return new LeavesProperties(
//				primitiveLeavesBlock.getDefaultState(),
//				new ItemStack(primitiveLeavesBlock, 1, 0),
//				cellKit) {
//			@Override public ItemStack getPrimitiveLeavesItemStack() {
//				return new ItemStack(primitiveLeavesBlock, 1, 0);
//			}
//
//			@Override
//			public int getLightRequirement() {
//				return 1;
//			}
//		};
//	}
//
//	@SubscribeEvent public static void registerItems(RegistryEvent.Register<Item> event) {
//		IForgeRegistry<Item> registry = event.getRegistry();
//
//		ArrayList<Item> treeItems = new ArrayList<>();
//		trees.forEach(tree -> tree.getRegisterableItems(treeItems));
//		registry.registerAll(treeItems.toArray(new Item[treeItems.size()]));
//	}
//
//	@SubscribeEvent
//	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
//		setUpSeedRecipes("ancient", new ItemStack(TreeAncient.saplingBlock, 1, 0));
//	}
//
//	public static void setUpSeedRecipes (String name, ItemStack treeSapling){
//		Species treeSpecies = TreeRegistry.findSpecies(new ResourceLocation(DTNaturesAura.MODID, name));
//		ItemStack treeSeed = treeSpecies.getSeedStack(1);
//		ItemStack treeTransformationPotion = ModItems.dendroPotion.setTargetTree(new ItemStack(ModItems.dendroPotion, 1, DendroPotionType.TRANSFORM.getIndex()), treeSpecies.getFamily());
//		BrewingRecipeRegistry.addRecipe(new ItemStack(ModItems.dendroPotion, 1, DendroPotionType.TRANSFORM.getIndex()), treeSeed, treeTransformationPotion);
//		ModRecipes.createDirtBucketExchangeRecipes(treeSapling, treeSeed, true);
//	}
//
//	@SideOnly(Side.CLIENT)
//	@SubscribeEvent
//	public static void registerModels(ModelRegistryEvent event) {
//		for (TreeFamily tree : trees) {
//			ModelHelper.regModel(tree.getDynamicBranch());
//			ModelHelper.regModel(tree.getCommonSpecies().getSeed());
//			ModelHelper.regModel(tree);
//		}
//		LeavesPaging.getLeavesMapForModId(DTNaturesAura.MODID).forEach((key, leaves) -> ModelLoader.setCustomStateMapper(leaves, new StateMap.Builder().ignore(BlockLeaves.DECAYABLE).build()));
//	}
}
