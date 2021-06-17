package com.harleyoconnor.dtnaturesaura.blocks;

import com.ferreusveritas.dynamictrees.blocks.leaves.DynamicLeavesBlock;
import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.util.BlockStates;
import de.ellpeck.naturesaura.blocks.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DynamicDecayedLeavesBlock extends DynamicLeavesBlock {

    public DynamicDecayedLeavesBlock(LeavesProperties leavesProperties, Properties properties) {
        super(leavesProperties, properties);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return new ItemStack(ModBlocks.DECAYED_LEAVES.asItem());
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        if (!world.isClientSide) {
            world.setBlockAndUpdate(pos, BlockStates.AIR);
        }
    }

    @Override
    public List<ItemStack> getDrops(@Nullable PlayerEntity player, ItemStack item, World world, BlockPos pos, int fortune) {
        return Collections.emptyList();
    }

}