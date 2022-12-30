package com.harleyoconnor.dtnaturesaura.block;

import com.ferreusveritas.dynamictrees.block.leaves.DynamicLeavesBlock;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.harleyoconnor.dtnaturesaura.AddonRegistries;
import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.blocks.BlockGoldenLeaves;
import de.ellpeck.naturesaura.blocks.ModBlocks;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static de.ellpeck.naturesaura.blocks.BlockGoldenLeaves.HIGHEST_STAGE;

@SuppressWarnings("deprecation")
public final class DynamicGoldenLeavesBlock extends DynamicLeavesBlock {

    public static final IntegerProperty STAGE = BlockGoldenLeaves.STAGE;

    public DynamicGoldenLeavesBlock(LeavesProperties leavesProperties, Properties properties) {
        super(leavesProperties, properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(STAGE);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return new ItemStack(ModBlocks.GOLDEN_LEAVES.asItem());
    }

    @Override
    public List<ItemStack> onSheared(@Nullable Player player, ItemStack item, Level level, BlockPos pos, int fortune) {
        return new ArrayList<>(
                Collections.singletonList(this.getProperties(level.getBlockState(pos)).getPrimitiveLeavesItemStack()));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level level, BlockPos pos, Random rand) {
        if (state.getValue(STAGE) == HIGHEST_STAGE && rand.nextFloat() >= 0.75F) {
            NaturesAuraAPI.instance().spawnMagicParticle(
                    pos.getX() + rand.nextFloat(),
                    pos.getY() + rand.nextFloat(),
                    pos.getZ() + rand.nextFloat(),
                    0F, 0F, 0F,
                    0xF2FF00, 0.5F + rand.nextFloat(), 50, 0F, false, true);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        super.randomTick(state, level, pos, random);
        if (!level.isClientSide) {
            int stage = state.getValue(STAGE);
            if (stage < HIGHEST_STAGE) {
                level.setBlockAndUpdate(pos, state.setValue(STAGE, stage + 1));
            }

            if (stage > 1) {
                BlockPos offset = pos.relative(Direction.getRandom(random));
                if (level.isLoaded(offset)) {
                    convert(level, offset);
                }
            }
        }
    }

    public static BlockColor getBlockColour() {
        return (state, level, pos, tintIndex) ->
                getColour(state, level, pos, false);
    }

    public static int getColour(@Nullable BlockState state, @Nullable BlockAndTintGetter level,
                                @Nullable BlockPos pos, final boolean ratioOverride) {
        final int color = 0xF2FF00;
        if (state != null && level != null && pos != null) {
            final int foliage = BiomeColors.getAverageFoliageColor(level, pos);
            return Helper.blendColors(color, foliage,
                    ratioOverride ? 1 : state.getValue(STAGE) / (float) HIGHEST_STAGE);
        } else {
            return color;
        }
    }

    public static boolean convert(Level level, BlockPos pos) {
        final BlockState state = level.getBlockState(pos);

        if (!(state.getBlock() instanceof DynamicLeavesBlock) ||
                state.getBlock() instanceof DynamicAncientLeavesBlock ||
                state.getBlock() instanceof DynamicGoldenLeavesBlock) {
            return false;
        }

        final LeavesProperties goldenLeavesProperties = AddonRegistries.FAMILY_GOLDEN_LEAVES_MAP
                .get(((DynamicLeavesBlock) state.getBlock()).getFamily(state, level, pos));

        if (goldenLeavesProperties == null) {
            return false;
        }

        if (!level.isClientSide) {
            goldenLeavesProperties.getDynamicLeavesBlock().ifPresent(goldenLeaves ->
                    level.setBlockAndUpdate(pos, goldenLeaves.defaultBlockState()
                            .setValue(DISTANCE, state.hasProperty(DISTANCE) ? state.getValue(DISTANCE) : 1)
                            .setValue(PERSISTENT, state.hasProperty(PERSISTENT) ? state.getValue(PERSISTENT) : false))
            );
        }

        return true;
    }

}