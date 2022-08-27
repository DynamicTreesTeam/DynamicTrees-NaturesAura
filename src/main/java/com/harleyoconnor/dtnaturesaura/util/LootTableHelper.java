package com.harleyoconnor.dtnaturesaura.util;

import de.ellpeck.naturesaura.blocks.BlockGoldenLeaves;
import de.ellpeck.naturesaura.items.ModItems;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.conditions.SurvivesExplosion;

/**
 * @author Harley O'Connor
 */
public final class LootTableHelper {

    public static LootTable.Builder createGoldenLeavesTable(Block leavesBlock, LootParameterSet parameterSet) {
        return LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(
                        ItemLootEntry.lootTableItem(ModItems.GOLD_LEAF)
                                .when(SurvivesExplosion.survivesExplosion())
                                .when(
                                        BlockStateProperty.hasBlockStateProperties(leavesBlock).setProperties(
                                                StatePropertiesPredicate.Builder.properties()
                                                        .hasProperty(BlockGoldenLeaves.STAGE,
                                                                BlockGoldenLeaves.HIGHEST_STAGE)
                                        )
                                )
                ).when(RandomChance.randomChance(0.75F))
        ).setParamSet(parameterSet);
    }

    public static class LootTableHooks extends BlockLootTables {

        public static LootTable.Builder createSilkTouchOnlyTable(Block block) {
            return BlockLootTables.createSilkTouchOnlyTable(block);
        }

    }

}
