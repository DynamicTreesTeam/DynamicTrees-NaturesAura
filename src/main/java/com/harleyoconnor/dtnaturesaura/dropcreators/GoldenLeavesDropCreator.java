package com.harleyoconnor.dtnaturesaura.dropcreators;

import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreator;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.harleyoconnor.dtnaturesaura.DTNaturesAura;
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

    public GoldenLeavesDropCreator() {
        super(DTNaturesAura.resLoc("golden_leaves"));
    }

    @Override
    public List<ItemStack> getHarvestDrop(World world, Species species, BlockPos leafPos, Random random, List<ItemStack> dropList, int soilLife, int fortune) {
        return this.getDrops(world, species, leafPos, random, dropList, fortune);
    }

    @Override
    public List<ItemStack> getLeavesDrop(World world, Species species, BlockPos breakPos, Random random, List<ItemStack> dropList, int fortune) {
        return this.getDrops(world, species, breakPos, random, dropList, fortune);
    }

    private List<ItemStack> getDrops (World world, Species species, BlockPos leafPos, Random random, List<ItemStack> dropList, int fortune) {
        if (!(world.getBlockState(leafPos).getBlock() instanceof DynamicGoldenLeavesBlock))
            return dropList;

        final Random rand = world.random;
        final BlockState state = world.getBlockState(leafPos);

        if (state.getValue(DynamicGoldenLeavesBlock.STAGE) < HIGHEST_STAGE) {
            if (rand.nextFloat() >= 0.75F) {
                dropList.add(new ItemStack(ModItems.GOLD_FIBER));
            }
        } else if (rand.nextFloat() >= 0.25F) {
            dropList.add(new ItemStack(ModItems.GOLD_LEAF));
        }

        return dropList;
    }

}
