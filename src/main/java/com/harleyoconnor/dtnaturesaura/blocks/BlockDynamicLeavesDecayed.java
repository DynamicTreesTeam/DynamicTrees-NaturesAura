package com.harleyoconnor.dtnaturesaura.blocks;

import com.ferreusveritas.dynamictrees.blocks.leaves.DynamicLeavesBlock;
import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;

public class BlockDynamicLeavesDecayed extends DynamicLeavesBlock {

    public BlockDynamicLeavesDecayed(LeavesProperties leavesProperties, Properties properties) {
        super(leavesProperties, properties);
    }

//    public BlockDynamicLeavesDecayed(String speciesName) {
//        this.setRegistryName(DTNaturesAura.MOD_ID, "leaves_decayed_" + speciesName);
//        this.setUnlocalizedName("leaves_decayed");
//    }
//
//    @Override
//    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
//        if (!worldIn.isRemote) {
//            worldIn.setBlockToAir(pos);
//        }
//    }
//
//    @Override
//    public int branchSupport(IBlockState blockState, IBlockAccess blockAccess, BlockBranch branch, BlockPos pos, EnumFacing dir, int radius) {
//        return radius == 1 ? BlockBranch.setSupport(0, 1) : 0;
//    }
//
//    @Override
//    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
//        return Items.AIR;
//    }

}