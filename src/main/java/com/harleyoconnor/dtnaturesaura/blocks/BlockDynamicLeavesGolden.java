package com.harleyoconnor.dtnaturesaura.blocks;

import com.ferreusveritas.dynamictrees.blocks.leaves.DynamicLeavesBlock;
import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;

public class BlockDynamicLeavesGolden extends DynamicLeavesBlock {

    public BlockDynamicLeavesGolden(LeavesProperties leavesProperties, Properties properties) {
        super(leavesProperties, properties);
    }

    //    public static final int HIGHEST_STAGE = 3;
//    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, HIGHEST_STAGE);
//
//    public BlockDynamicLeavesGolden(String speciesName) {
//        setRegistryName(DTNaturesAura.MODID, "leaves_golden_" + speciesName);
//        setUnlocalizedName("leaves_golden");
//    }
//
//    @Override
//    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
//        return MapColor.GOLD;
//    }
//
//    @Override
//    public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess blockAccess, BlockPos pos, int fortune) {
//        ArrayList<ItemStack> drops = new ArrayList<>();
//        addDrops(drops, blockAccess, pos, blockAccess.getBlockState(pos), fortune);
//        return drops;
//    }
//
//    @Override
//    public int branchSupport(IBlockState blockState, IBlockAccess blockAccess, BlockBranch branch, BlockPos pos, EnumFacing dir, int radius) {
//        return radius == 1 ? BlockBranch.setSupport(0, 1) : 0;
//    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
//        if (stateIn.getValue(STAGE) == HIGHEST_STAGE && rand.nextFloat() >= 0.75F)
//            NaturesAuraAPI.instance().spawnMagicParticle(
//                    pos.getX() + rand.nextFloat(),
//                    pos.getY() + rand.nextFloat(),
//                    pos.getZ() + rand.nextFloat(),
//                    0F, 0F, 0F,
//                    0xF2FF00, 0.5F + rand.nextFloat(), 50, 0F, false, true);
//    }
//
//    @Override
//    protected BlockStateContainer createBlockState() {
//        return new BlockStateContainer(this, new IProperty[] {HYDRO, TREE, DECAYABLE, STAGE});
//    }
//
//    @Override
//    public IBlockState getStateFromMeta(int meta) {
//        return this.getDefaultState().withProperty(TREE, (meta >> 2) & 3).withProperty(HYDRO, (meta & 3) + 1).withProperty(STAGE, meta & HIGHEST_STAGE);
//    }
//
//    @Override
//    public int getMetaFromState(IBlockState state) {
//        return (state.getValue(HYDRO) - 1) | (state.getValue(TREE) << 2) | state.getValue(STAGE);
//    }
//
//    @Override
//    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
//        super.updateTick(worldIn, pos, state, rand);
//
//        if (worldIn.isRemote) return;
//
//        int stage = state.getValue(STAGE);
//        if (stage < HIGHEST_STAGE) {
//            worldIn.setBlockState(pos, state.withProperty(STAGE, stage + 1));
//        }
//
//        if (stage > 1) {
//            BlockPos offset = pos.offset(EnumFacing.random(rand));
//            if (worldIn.isBlockLoaded(offset))
//                convert(worldIn, offset);
//        }
//    }
//
//    public static void addDrops (List<ItemStack> drops, World world, BlockPos pos, IBlockState state, int fortune) {
//        Random rand = world.random;
//        if (state.get(STAGE) < HIGHEST_STAGE) {
//            if (rand.nextFloat() >= 0.75F) {
//                drops.add(new ItemStack(ModItems.GOLD_FIBER));
//            }
//        } else if (rand.nextFloat() >= 0.25F) {
//            drops.add(new ItemStack(ModItems.GOLD_LEAF));
//        }
//    }
//
//    public static boolean convert(World world, BlockPos pos) {
//        final BlockState state = world.getBlockState(pos);
//        if (state.getBlock() instanceof DynamicLeavesBlock) {
////            if ((((DynamicLeavesBlock) state.getBlock()).getProperties(state).getFamily() instanceof TreeOak || !ModConfig.GOLD_LEAF_NEEDS_OAK) && !(state.getBlock() instanceof BlockDynamicLeavesGolden)) {
////                final Species commonSpecies = SpeciesUtils.getSpeciesFromLeaveState(state);
////                if (commonSpecies != Species.NULLSPECIES) {
////                    if (!world.isRemote)
////                        world.setBlockState(pos, ModContent.goldenLeavesVariants.get(commonSpecies).getDefaultState()
////                                .withProperty(DECAYABLE, state.getPropertyKeys().contains(DECAYABLE) ? state.getValue(DECAYABLE) : false)
////                                .withProperty(HYDRO, state.getValue(HYDRO)));
////                    return true;
////                }
////            }
//        }
//        return false;
//    }

}