package com.harleyoconnor.dtnaturesaura.block;

import com.ferreusveritas.dynamictrees.blocks.leaves.DynamicLeavesBlock;
import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.blocks.ModBlocks;
import de.ellpeck.naturesaura.blocks.tiles.TileEntityAncientLeaves;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public final class DynamicAncientLeavesBlock extends DynamicLeavesBlock {

    public DynamicAncientLeavesBlock(LeavesProperties leavesProperties, Properties properties) {
        super(leavesProperties, properties);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityAncientLeaves();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        super.animateTick(stateIn, worldIn, pos, rand);
        if (rand.nextFloat() >= 0.95F && !worldIn.getBlockState(pos.below()).isSolidRender(worldIn, pos)) {
            TileEntity tile = worldIn.getBlockEntity(pos);
            if (tile instanceof TileEntityAncientLeaves) {
                if (((TileEntityAncientLeaves) tile).getAuraContainer().getStoredAura() > 0) {
                    NaturesAuraAPI.instance().spawnMagicParticle(
                            pos.getX() + rand.nextDouble(), pos.getY(), pos.getZ() + rand.nextDouble(),
                            0F, 0F, 0F, 0xCC4780,
                            rand.nextFloat() * 2F + 0.5F,
                            rand.nextInt(50) + 75,
                            rand.nextFloat() * 0.02F + 0.002F, true, true);

                }
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        super.randomTick(state, worldIn, pos, random);
        if (!worldIn.isClientSide) {
            TileEntity tile = worldIn.getBlockEntity(pos);
            if (tile instanceof TileEntityAncientLeaves) {
                if (((TileEntityAncientLeaves) tile).getAuraContainer().getStoredAura() <= 0) {
                    worldIn.setBlockAndUpdate(pos, ModBlocks.DECAYED_LEAVES.defaultBlockState());
                }
            }
        }
    }

}
