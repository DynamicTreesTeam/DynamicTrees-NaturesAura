package com.harleyoconnor.dtnaturesaura.effect;

import com.ferreusveritas.dynamictrees.block.leaves.DynamicLeavesBlock;
import de.ellpeck.naturesaura.ModConfig;
import de.ellpeck.naturesaura.NaturesAura;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.chunk.IDrainSpotEffect;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import de.ellpeck.naturesaura.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;

/**
 * Replacement for {@link de.ellpeck.naturesaura.chunk.effect.GrassDieEffect} so that it doesn't convert dynamic leaves
 * to non-dynamic decayed leaves.
 *
 * @author Harley O'Connor
 */
public class GrassDieEffect implements IDrainSpotEffect {

    public static final ResourceLocation NAME = new ResourceLocation(NaturesAura.MOD_ID, "grass_die");

    private int amount;
    private int dist;

    private boolean calcValues(Level level, BlockPos pos, Integer spot) {
        if (spot < 0) {
            int aura = IAuraChunk.getAuraInArea(level, pos, 50);
            if (aura < 0) {
                this.amount = Math.min(300,
                        Mth.ceil(Math.abs(aura) / 100000F / IAuraChunk.getSpotAmountInArea(level, pos, 50)));
                if (this.amount > 1) {
                    this.dist = Mth.clamp(Math.abs(aura) / 75000, 5, 75);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ActiveType isActiveHere(Player player, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot) {
        if (!this.calcValues(player.level, pos, spot)) {
            return ActiveType.INACTIVE;
        }
        if (player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) > this.dist * this.dist) {
            return ActiveType.INACTIVE;
        }
        return ActiveType.ACTIVE;
    }

    @Override
    public ItemStack getDisplayIcon() {
        return new ItemStack(ModBlocks.DECAYED_LEAVES);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void update(Level level, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot) {
        if (!this.calcValues(level, pos, spot)) {
            return;
        }

        for (int i = this.amount / 2 + level.random.nextInt(this.amount / 2); i >= 0; i--) {
            BlockPos grassPos = new BlockPos(
                    pos.getX() + level.random.nextGaussian() * this.dist,
                    pos.getY() + level.random.nextGaussian() * this.dist,
                    pos.getZ() + level.random.nextGaussian() * this.dist
            );
            if (grassPos.distSqr(pos) <= this.dist * this.dist && level.hasChunkAt(grassPos)) {
                BlockState state = level.getBlockState(grassPos);
                Block block = state.getBlock();

                BlockState newState = null;
                if (block instanceof LeavesBlock && !(block instanceof DynamicLeavesBlock)) {
                    newState = ModBlocks.DECAYED_LEAVES.defaultBlockState();
                } else if (block instanceof GrassBlock) {
                    newState = Blocks.COARSE_DIRT.defaultBlockState();
                } else if (block instanceof BushBlock) {
                    newState = Blocks.AIR.defaultBlockState();
                } else if (block == ModBlocks.NETHER_GRASS) {
                    newState = Blocks.NETHERRACK.defaultBlockState();
                }
                if (newState != null) {
                    level.setBlockAndUpdate(grassPos, newState);
                }
            }
        }
    }

    @Override
    public boolean appliesHere(LevelChunk chunk, IAuraChunk auraChunk, IAuraType type) {
        return ModConfig.instance.grassDieEffect.get() &&
                (type.isSimilar(NaturesAuraAPI.TYPE_OVERWORLD) || type.isSimilar(NaturesAuraAPI.TYPE_NETHER));
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }
}
