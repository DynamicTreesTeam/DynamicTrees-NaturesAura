package com.harleyoconnor.dtnaturesaura.block;

import com.ferreusveritas.dynamictrees.block.leaves.DynamicLeavesBlock;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.blocks.ModBlocks;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityAncientLeaves;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public final class DynamicAncientLeavesBlock extends DynamicLeavesBlock implements EntityBlock {

    public DynamicAncientLeavesBlock(LeavesProperties leavesProperties, Properties properties) {
        super(leavesProperties, properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BlockEntityAncientLeaves(pos, state);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
        super.animateTick(state, level, pos, random);
        if (random.nextFloat() >= 0.95F && !level.getBlockState(pos.below()).isSolidRender(level, pos)) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof BlockEntityAncientLeaves) {
                if (((BlockEntityAncientLeaves) blockEntity).getAuraContainer().getStoredAura() > 0) {
                    NaturesAuraAPI.instance().spawnMagicParticle(
                            pos.getX() + random.nextDouble(), pos.getY(), pos.getZ() + random.nextDouble(),
                            0F, 0F, 0F, 0xCC4780,
                            random.nextFloat() * 2F + 0.5F,
                            random.nextInt(50) + 75,
                            random.nextFloat() * 0.02F + 0.002F, true, true);

                }
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        super.randomTick(state, level, pos, random);
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof BlockEntityAncientLeaves) {
                if (((BlockEntityAncientLeaves) blockEntity).getAuraContainer().getStoredAura() <= 0) {
                    level.setBlockAndUpdate(pos, ModBlocks.DECAYED_LEAVES.defaultBlockState());
                }
            }
        }
    }

}
