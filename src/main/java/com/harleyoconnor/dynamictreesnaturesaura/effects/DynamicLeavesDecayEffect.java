package com.harleyoconnor.dynamictreesnaturesaura.effects;

import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.harleyoconnor.dynamictreesnaturesaura.ModContent;
import com.harleyoconnor.dynamictreesnaturesaura.blocks.BlockDynamicLeavesDecayed;
import com.harleyoconnor.dynamictreesnaturesaura.util.SpeciesUtils;
import de.ellpeck.naturesaura.ModConfig;
import de.ellpeck.naturesaura.NaturesAura;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.chunk.IDrainSpotEffect;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import de.ellpeck.naturesaura.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import static com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves.HYDRO;

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
    public int isActiveHere(EntityPlayer player, Chunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot) {
        if (!this.calcValues(player.world, pos, spot))
            return -1;
        if (player.getDistanceSq(pos) > this.dist * this.dist)
            return -1;
        return 1;
    }

    @Override
    public ItemStack getDisplayIcon() {
        return new ItemStack(ModBlocks.DECAYED_LEAVES);
    }

    @Override
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
                IBlockState state = world.getBlockState(grassPos);
                Block block = state.getBlock();

                IBlockState newState = null;
                if (!(block instanceof BlockDynamicLeavesDecayed) && block instanceof BlockDynamicLeaves) {
                    final Species commonSpeices = SpeciesUtils.getSpeciesFromLeaveState(state);
                    if (commonSpeices != Species.NULLSPECIES)
                        newState = ModContent.decayedLeavesVariants.get(commonSpeices).getDynamicLeavesState().withProperty(HYDRO, state.getValue(HYDRO));
                }

                if (newState != null)
                    world.setBlockState(grassPos, newState);
            }
        }
    }

    @Override
    public boolean appliesHere(Chunk chunk, IAuraChunk auraChunk, IAuraType type) {
        return ModConfig.enabledFeatures.grassDieEffect && type.isSimilar(NaturesAuraAPI.TYPE_OVERWORLD);
    }

    @Override
    public ResourceLocation getName() {
        return NAME;
    }

}