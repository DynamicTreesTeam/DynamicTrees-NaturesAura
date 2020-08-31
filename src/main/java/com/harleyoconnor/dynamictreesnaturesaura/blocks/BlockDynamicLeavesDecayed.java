package com.harleyoconnor.dynamictreesnaturesaura.blocks;

import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.harleyoconnor.dynamictreesnaturesaura.DynamicTreesNaturesAura;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockDynamicLeavesDecayed extends BlockDynamicLeaves {

    public BlockDynamicLeavesDecayed() {
        super();
        setRegistryName(DynamicTreesNaturesAura.MODID, "leaves_decayed");
        setUnlocalizedName("leaves_decayed");
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        if (!worldIn.isRemote) {
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.AIR;
    }

}