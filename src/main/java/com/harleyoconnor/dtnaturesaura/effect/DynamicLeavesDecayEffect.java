package com.harleyoconnor.dtnaturesaura.effect;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.block.leaves.DynamicLeavesBlock;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.block.leaves.SolidLeavesProperties;
import com.harleyoconnor.dtnaturesaura.AddonConfig;
import com.harleyoconnor.dtnaturesaura.AddonRegistries;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;

public class DynamicLeavesDecayEffect implements IDrainSpotEffect {

    public static final ResourceLocation NAME = new ResourceLocation(NaturesAura.MOD_ID, "dynamic_leaves_decay");

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

    @Override
    @SuppressWarnings("deprecation")
    public void update(Level level, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot) {
        if (!this.calcValues(level, pos, spot)) {
            return;
        }

        for (int i = this.amount / 2 + level.random.nextInt(this.amount / 2); i >= 0; i--) {
            final BlockPos grassPos = new BlockPos(
                    pos.getX() + level.random.nextGaussian() * this.dist,
                    pos.getY() + level.random.nextGaussian() * this.dist,
                    pos.getZ() + level.random.nextGaussian() * this.dist
            );

            if (grassPos.distSqr(pos) > this.dist * this.dist || !level.hasChunkAt(grassPos)) {
                continue;
            }

            final BlockState state = level.getBlockState(grassPos);
            final Block block = state.getBlock();

            final DynamicLeavesBlock leaves = TreeHelper.getLeaves(block);

            if (leaves == null) {
                continue;
            }

            final LeavesProperties properties = leaves.getProperties(state);

            // Solid leaves properties or any added to the leaves decay blacklist
            // shouldn't be able to decay.
            if (properties instanceof SolidLeavesProperties ||
                    AddonConfig.LEAVES_DECAY_BLACKLIST.get().stream()
                            .map(ResourceLocation::tryParse)
                            .anyMatch(properties.getRegistryName()::equals)
            ) {
                continue;
            }

            // Try to get the decayed leaves block and set it.
            LeavesProperties.REGISTRY
                    .get(AddonRegistries.DECAYED)
                    .getDynamicLeavesBlock()
                    .map(Block::defaultBlockState)
                    .ifPresent(newState -> level.setBlockAndUpdate(grassPos, newState));
        }
    }

    @Override
    public boolean appliesHere(LevelChunk chunk, IAuraChunk auraChunk, IAuraType type) {
        return ModConfig.instance.grassDieEffect.get() && type.isSimilar(NaturesAuraAPI.TYPE_OVERWORLD);
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }

}