package com.harleyoconnor.dynamictreesnaturesaura.dropcreators;

import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreator;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.harleyoconnor.dynamictreesnaturesaura.DynamicTreesNaturesAura;
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

public class DropCreatorGoldLeaf extends DropCreator {

    private final ItemStack goldLeaves;

    public DropCreatorGoldLeaf() {
        super(new ResourceLocation(DynamicTreesNaturesAura.MODID, ModItems.GOLD_LEAF.getRegistryName().getResourceDomain()));
        this.goldLeaves = new ItemStack(ModItems.GOLD_LEAF, 1, 0);
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

        IBlockState state = access.getBlockState(leafPos);
        BlockDynamicLeavesGolden.addDrops(dropList, access, leafPos, state, fortune);

        return dropList;
    }

}
