package com.harleyoconnor.dtnaturesaura.block;

import com.ferreusveritas.dynamictrees.blocks.leaves.DynamicLeavesBlock;
import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;
import com.harleyoconnor.dtnaturesaura.AddonRegistries;
import com.harleyoconnor.dtnaturesaura.util.LootTableHelper;
import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.blocks.BlockGoldenLeaves;
import de.ellpeck.naturesaura.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColors;
import net.minecraft.world.server.ServerWorld;
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
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(STAGE);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos,
                                  PlayerEntity player) {
        return new ItemStack(ModBlocks.GOLDEN_LEAVES.asItem());
    }

    @Override
    public List<ItemStack> onSheared(@Nullable PlayerEntity player, ItemStack item, World world, BlockPos pos,
                                     int fortune) {
        return new ArrayList<>(
                Collections.singletonList(this.getProperties(world.getBlockState(pos)).getPrimitiveLeavesItemStack()));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.getValue(STAGE) == HIGHEST_STAGE && rand.nextFloat() >= 0.75F) {
            NaturesAuraAPI.instance().spawnMagicParticle(
                    pos.getX() + rand.nextFloat(),
                    pos.getY() + rand.nextFloat(),
                    pos.getZ() + rand.nextFloat(),
                    0F, 0F, 0F,
                    0xF2FF00, 0.5F + rand.nextFloat(), 50, 0F, false, true);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        super.randomTick(state, worldIn, pos, random);
        if (!worldIn.isClientSide) {
            int stage = state.getValue(STAGE);
            if (stage < HIGHEST_STAGE) {
                worldIn.setBlockAndUpdate(pos, state.setValue(STAGE, stage + 1));
            }

            if (stage > 1) {
                BlockPos offset = pos.relative(Direction.getRandom(random));
                if (worldIn.isLoaded(offset)) {
                    convert(worldIn, offset);
                }
            }
        }
    }

    public static IBlockColor getBlockColour() {
        return (state, worldIn, pos, tintIndex) ->
                getColour(state, worldIn, pos, false);
    }

    public static int getColour(@Nullable BlockState state, @Nullable IBlockDisplayReader worldIn,
                                @Nullable BlockPos pos, final boolean ratioOverride) {
        final int color = 0xF2FF00;
        if (state != null && worldIn != null && pos != null) {
            final int foliage = BiomeColors.getAverageFoliageColor(worldIn, pos);
            return Helper.blendColors(color, foliage,
                    ratioOverride ? 1 : state.getValue(STAGE) / (float) HIGHEST_STAGE);
        } else {
            return color;
        }
    }

    public static boolean convert(World world, BlockPos pos) {
        final BlockState state = world.getBlockState(pos);

        if (!(state.getBlock() instanceof DynamicLeavesBlock) ||
                state.getBlock() instanceof DynamicAncientLeavesBlock ||
                state.getBlock() instanceof DynamicGoldenLeavesBlock) {
            return false;
        }

        final LeavesProperties goldenLeavesProperties = AddonRegistries.FAMILY_GOLDEN_LEAVES_MAP
                .get(((DynamicLeavesBlock) state.getBlock()).getFamily(state, world, pos));

        if (goldenLeavesProperties == null) {
            return false;
        }

        if (!world.isClientSide) {
            goldenLeavesProperties.getDynamicLeavesBlock().ifPresent(goldenLeaves ->
                    world.setBlockAndUpdate(pos, goldenLeaves.defaultBlockState()
                            .setValue(DISTANCE, state.hasProperty(DISTANCE) ? state.getValue(DISTANCE) : 1)
                            .setValue(PERSISTENT, state.hasProperty(PERSISTENT) ? state.getValue(PERSISTENT) : false))
            );
        }

        return true;
    }

}