package com.harleyoconnor.dtnaturesaura.block;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.block.leaves.DynamicLeavesBlock;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.harleyoconnor.dtnaturesaura.util.LootTableHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.LootTable;

/**
 * @author Harley O'Connor
 */
public final class AncientLeavesProperties extends LeavesProperties {

    public static final TypedRegistry.EntryType<LeavesProperties> TYPE =
            TypedRegistry.newType(AncientLeavesProperties::new);

    public AncientLeavesProperties(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected DynamicLeavesBlock createDynamicLeaves(BlockBehaviour.Properties properties) {
        return new DynamicAncientLeavesBlock(this, properties);
    }

    @Override
    public boolean shouldGenerateBlockDrops() {
        return getPrimitiveLeavesBlock().isPresent();
    }

    @Override
    public LootTable.Builder createBlockDrops() {
        return LootTableHelper.LootTableHooks.createSilkTouchOnlyTable(primitiveLeaves.getBlock());
    }

    @Override
    public boolean shouldGenerateDrops() {
        return false;
    }
}
