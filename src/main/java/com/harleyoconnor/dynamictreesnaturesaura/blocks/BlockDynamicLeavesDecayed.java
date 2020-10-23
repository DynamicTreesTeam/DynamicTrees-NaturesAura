package com.harleyoconnor.dynamictreesnaturesaura.blocks;

import com.ferreusveritas.dynamictrees.blocks.BlockBranch;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.harleyoconnor.dynamictreesnaturesaura.DynamicTreesNaturesAura;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockDynamicLeavesDecayed extends BlockDynamicLeaves {

    public BlockDynamicLeavesDecayed(String speciesName) {
        this.setRegistryName(DynamicTreesNaturesAura.MODID, "leaves_decayed_" + speciesName);
        this.setUnlocalizedName("leaves_decayed");
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        if (!worldIn.isRemote) {
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public int branchSupport(IBlockState blockState, IBlockAccess blockAccess, BlockBranch branch, BlockPos pos, EnumFacing dir, int radius) {
        return radius == 1 ? BlockBranch.setSupport(0, 1) : 0;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.AIR;
    }

}