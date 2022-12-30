package com.harleyoconnor.dtnaturesaura.util;

import de.ellpeck.naturesaura.blocks.BlockGoldenLeaves;
import de.ellpeck.naturesaura.items.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

/**
 * @author Harley O'Connor
 */
public final class LootTableHelper {

    public static LootTable.Builder createGoldenLeavesTable(Block leavesBlock, LootContextParamSet parameterSet) {
        return LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(
                        LootItem.lootTableItem(ModItems.GOLD_LEAF)
                                .when(ExplosionCondition.survivesExplosion())
                                .when(
                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(leavesBlock).setProperties(
                                                StatePropertiesPredicate.Builder.properties()
                                                        .hasProperty(BlockGoldenLeaves.STAGE,
                                                                BlockGoldenLeaves.HIGHEST_STAGE)
                                        )
                                )
                ).when(LootItemRandomChanceCondition.randomChance(0.75F))
        ).setParamSet(parameterSet);
    }

    public static class LootTableHooks extends BlockLoot {

        public static LootTable.Builder createSilkTouchOnlyTable(Block block) {
            return BlockLoot.createSilkTouchOnlyTable(block);
        }

    }

}
