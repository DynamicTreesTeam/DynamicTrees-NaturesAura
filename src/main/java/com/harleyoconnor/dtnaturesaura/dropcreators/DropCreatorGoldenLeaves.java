package com.harleyoconnor.dtnaturesaura.dropcreators;

import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreator;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.harleyoconnor.dtnaturesaura.DTNaturesAura;
import de.ellpeck.naturesaura.items.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class DropCreatorGoldenLeaves extends DropCreator {

//    private final boolean requiresOak;

    public DropCreatorGoldenLeaves() {
        super(new ResourceLocation(DTNaturesAura.MOD_ID, ModItems.GOLD_LEAF.getRegistryName().getNamespace()));
//        this.requiresOak = ModConfig.GOLD_LEAF_NEEDS_OAK;
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
//        if (!(world.getBlockState(leafPos).getBlock() instanceof BlockDynamicLeavesGolden))
//            return dropList;
//
//        if (!(species instanceof TreeOak.SpeciesOak || species instanceof TreeOak.SpeciesAppleOak || species instanceof TreeOak.SpeciesSwampOak) && this.requiresOak)
//            return dropList;

//        BlockDynamicLeavesGolden.addDrops(dropList, world, leafPos, world.getBlockState(leafPos), fortune);

        return dropList;
    }

}
