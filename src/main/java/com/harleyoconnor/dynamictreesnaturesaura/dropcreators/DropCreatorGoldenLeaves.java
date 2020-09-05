package com.harleyoconnor.dynamictreesnaturesaura.dropcreators;

import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreator;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeOak;
import com.harleyoconnor.dynamictreesnaturesaura.DynamicTreesNaturesAura;
import com.harleyoconnor.dynamictreesnaturesaura.ModConfig;
import com.harleyoconnor.dynamictreesnaturesaura.blocks.BlockDynamicLeavesGolden;
import de.ellpeck.naturesaura.items.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class DropCreatorGoldenLeaves extends DropCreator {

    private final boolean requiresOak;

    public DropCreatorGoldenLeaves() {
        super(new ResourceLocation(DynamicTreesNaturesAura.MODID, ModItems.GOLD_LEAF.getRegistryName().getResourceDomain()));
        this.requiresOak = ModConfig.GOLD_LEAF_NEEDS_OAK;
    }

    @Override
    public List<ItemStack> getHarvestDrop(World world, Species species, BlockPos leafPos, Random random, List<ItemStack> dropList, int soilLife, int fortune) {
        return this.getDrops(world, species, leafPos, random, dropList, fortune);
    }

    @Override
    public List<ItemStack> getLeavesDrop(IBlockAccess access, Species species, BlockPos breakPos, Random random, List<ItemStack> dropList, int fortune) {
        return this.getDrops(access, species, breakPos, random, dropList, fortune);
    }

    private List<ItemStack> getDrops (IBlockAccess access, Species species, BlockPos leafPos, Random random, List<ItemStack> dropList, int fortune) {
        if (!(access.getBlockState(leafPos).getBlock() instanceof BlockDynamicLeavesGolden)) return dropList;

        if (!(species instanceof TreeOak.SpeciesOak || species instanceof TreeOak.SpeciesAppleOak || species instanceof TreeOak.SpeciesSwampOak) && this.requiresOak) return dropList;

        IBlockState state = access.getBlockState(leafPos);
        BlockDynamicLeavesGolden.addDrops(dropList, access, leafPos, state, fortune);

        return dropList;
    }

}
