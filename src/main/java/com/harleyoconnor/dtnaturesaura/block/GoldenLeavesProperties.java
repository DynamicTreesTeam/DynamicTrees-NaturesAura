package com.harleyoconnor.dtnaturesaura.block;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.block.leaves.DynamicLeavesBlock;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.loot.DTLootParameterSets;
import com.harleyoconnor.dtnaturesaura.util.LootTableHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

/**
 * @author Harley O'Connor
 */
public final class GoldenLeavesProperties extends LeavesProperties {

    public static final TypedRegistry.EntryType<LeavesProperties> TYPE =
            TypedRegistry.newType(GoldenLeavesProperties::new);

    public GoldenLeavesProperties(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected DynamicLeavesBlock createDynamicLeaves(BlockBehaviour.Properties properties) {
        return new DynamicGoldenLeavesBlock(this, properties);
    }

    @Override
    public int treeFallColorMultiplier(BlockState state, BlockAndTintGetter world, BlockPos pos) {
        return DynamicGoldenLeavesBlock.getColour(state, world, pos, true);
    }

    @Override
    public int foliageColorMultiplier(BlockState state, BlockAndTintGetter world, BlockPos pos) {
        return DynamicGoldenLeavesBlock.getBlockColour().getColor(state, world, pos, -1);
    }

    @Override
    public LootTable.Builder createBlockDrops() {
        return LootTableHelper.createGoldenLeavesTable(getDynamicLeavesState().getBlock(), LootContextParamSets.BLOCK);
    }

    @Override
    public boolean shouldGenerateDrops() {
        return getDynamicLeavesBlock().isPresent();
    }

    @Override
    public LootTable.Builder createDrops() {
        return LootTableHelper.createGoldenLeavesTable(getDynamicLeavesState().getBlock(), DTLootParameterSets.LEAVES);
    }

}
