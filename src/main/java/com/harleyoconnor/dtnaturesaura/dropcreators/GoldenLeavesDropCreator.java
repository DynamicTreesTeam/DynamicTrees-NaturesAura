package com.harleyoconnor.dtnaturesaura.dropcreators;

import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreator;
import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreatorConfiguration;
import com.ferreusveritas.dynamictrees.systems.dropcreators.context.DropContext;
import com.harleyoconnor.dtnaturesaura.blocks.DynamicGoldenLeavesBlock;
import de.ellpeck.naturesaura.items.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

import static de.ellpeck.naturesaura.blocks.BlockGoldenLeaves.HIGHEST_STAGE;

public class GoldenLeavesDropCreator extends DropCreator {

    public GoldenLeavesDropCreator(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected void registerProperties() {}

    @Override
    public void appendHarvestDrops(DropCreatorConfiguration configuration, DropContext context) {
        this.appendDrops(context.world(), context.pos(), context.random(), context.drops(), context.fortune());
    }

    @Override
    public void appendLeavesDrops(DropCreatorConfiguration configuration, DropContext context) {
        this.appendDrops(context.world(), context.pos(), context.random(), context.drops(), context.fortune());
    }

    private void appendDrops(World world, BlockPos leafPos, Random random, List<ItemStack> dropList, int fortune) {
        final BlockState state = world.getBlockState(leafPos);

        if (!(state.getBlock() instanceof DynamicGoldenLeavesBlock)) {
            return;
        }

        if (state.getValue(DynamicGoldenLeavesBlock.STAGE) < HIGHEST_STAGE) {
            if (random.nextFloat() >= 0.75F) {
                dropList.add(new ItemStack(ModItems.GOLD_FIBER));
            }
        } else if (random.nextFloat() >= 0.25F) {
            dropList.add(new ItemStack(ModItems.GOLD_LEAF));
        }
    }

}
