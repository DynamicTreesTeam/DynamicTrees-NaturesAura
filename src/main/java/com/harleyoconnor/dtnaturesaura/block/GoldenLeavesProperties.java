package com.harleyoconnor.dtnaturesaura.block;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.blocks.leaves.DynamicLeavesBlock;
import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.loot.DTLootParameterSets;
import com.harleyoconnor.dtnaturesaura.util.LootTableHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;

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
    protected DynamicLeavesBlock createDynamicLeaves(AbstractBlock.Properties properties) {
        return new DynamicGoldenLeavesBlock(this, properties);
    }

    @Override
    public int treeFallColorMultiplier(BlockState state, IBlockDisplayReader world, BlockPos pos) {
        return DynamicGoldenLeavesBlock.getColour(state, world, pos, true);
    }

    @Override
    public int foliageColorMultiplier(BlockState state, IBlockDisplayReader world, BlockPos pos) {
        return DynamicGoldenLeavesBlock.getBlockColour().getColor(state, world, pos, -1);
    }

    @Override
    public LootTable.Builder createBlockDrops() {
        return LootTableHelper.createGoldenLeavesTable(getDynamicLeavesState().getBlock(), LootParameterSets.BLOCK);
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
