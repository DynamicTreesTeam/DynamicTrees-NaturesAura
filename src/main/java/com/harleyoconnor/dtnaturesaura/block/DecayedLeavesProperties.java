package com.harleyoconnor.dtnaturesaura.block;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.block.branch.BranchBlock;
import com.ferreusveritas.dynamictrees.block.leaves.DynamicLeavesBlock;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.harleyoconnor.dtnaturesaura.util.LootTableHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;

/**
 * @author Harley O'Connor
 */
public final class DecayedLeavesProperties extends LeavesProperties {

    public static final TypedRegistry.EntryType<LeavesProperties> TYPE =
            TypedRegistry.newType(DecayedLeavesProperties::new);

    public DecayedLeavesProperties(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected DynamicLeavesBlock createDynamicLeaves(BlockBehaviour.Properties properties) {
        return new DynamicDecayedLeavesBlock(this, properties);
    }

    @Override
    public int getRadiusForConnection(BlockState state, BlockGetter level, BlockPos pos, BranchBlock from, Direction side, int fromRadius) {
        final int twigRadius = from.getFamily().getPrimaryThickness();
        return fromRadius == twigRadius || this.connectAnyRadius ? twigRadius : 0;
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
