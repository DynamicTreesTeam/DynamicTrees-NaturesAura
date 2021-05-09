package com.harleyoconnor.dtnaturesaura.effects;

import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;
import com.harleyoconnor.dtnaturesaura.DTNaturesAura;
import de.ellpeck.naturesaura.ModConfig;
import de.ellpeck.naturesaura.NaturesAura;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.chunk.IDrainSpotEffect;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import de.ellpeck.naturesaura.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class DynamicLeavesDecayEffect implements IDrainSpotEffect {

    public static final ResourceLocation NAME = new ResourceLocation(NaturesAura.MOD_ID, "dynamic_leave_decay");

    private int amount;
    private int dist;

    private boolean calcValues(World world, BlockPos pos, Integer spot) {
        if (spot < 0) {
            int aura = IAuraChunk.getAuraInArea(world, pos, 50);
            if (aura < 0) {
                this.amount = Math.min(300, MathHelper.ceil(Math.abs(aura) / 100000F / IAuraChunk.getSpotAmountInArea(world, pos, 50)));
                if (this.amount > 1) {
                    this.dist = MathHelper.clamp(Math.abs(aura) / 75000, 5, 75);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ActiveType isActiveHere(PlayerEntity player, Chunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot) {
        if (!this.calcValues(player.world, pos, spot))
            return ActiveType.INACTIVE;
        if (player.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()) > this.dist * this.dist)
            return ActiveType.INACTIVE;
        return ActiveType.ACTIVE;
    }

    @Override
    public ItemStack getDisplayIcon() {
        return new ItemStack(ModBlocks.DECAYED_LEAVES);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void update(World world, Chunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot) {
        if (!this.calcValues(world, pos, spot))
            return;
        for (int i = this.amount / 2 + world.rand.nextInt(this.amount / 2); i >= 0; i--) {
            BlockPos grassPos = new BlockPos(
                    pos.getX() + world.rand.nextGaussian() * this.dist,
                    pos.getY() + world.rand.nextGaussian() * this.dist,
                    pos.getZ() + world.rand.nextGaussian() * this.dist
            );
            if (grassPos.distanceSq(pos) <= this.dist * this.dist && world.isBlockLoaded(grassPos)) {
                BlockState state = world.getBlockState(grassPos);
                Block block = state.getBlock();

                BlockState newState = null;
                if (block instanceof LeavesBlock) {
                    newState = LeavesProperties.REGISTRY.get(DTNaturesAura.resLoc("decayed_leaves"))
                            .getDynamicLeavesBlock()
                            .map(Block::getDefaultState)
                            .orElse(newState);
                }
                if (newState != null)
                    world.setBlockState(grassPos, newState);
            }
        }
    }

    @Override
    public boolean appliesHere(Chunk chunk, IAuraChunk auraChunk, IAuraType type) {
        return ModConfig.instance.grassDieEffect.get() && (type.isSimilar(NaturesAuraAPI.TYPE_OVERWORLD) || type.isSimilar(NaturesAuraAPI.TYPE_NETHER));
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }

}