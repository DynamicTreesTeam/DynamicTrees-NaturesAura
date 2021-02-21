package com.harleyoconnor.dynamictreesnaturesaura.util;

import com.ferreusveritas.dynamictrees.ModConstants;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicSapling;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeBirch;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.ferreusveritas.dynamictrees.trees.TreeOak;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * @author Harley O'Connor
 */
public final class SpeciesUtils {

    private static final ResourceLocation leaves0 = new ResourceLocation(ModConstants.MODID, "leaves0");
    private static final ResourceLocation leaves1 = new ResourceLocation(ModConstants.MODID, "leaves1");

    public static Species getSpeciesFromLeaveState (final IBlockState state) {
        if (state.getBlock().getRegistryName() == null) return Species.NULLSPECIES;

        if (state.getBlock().getRegistryName().equals(leaves0)) {
            switch (state.getValue(BlockDynamicLeaves.TREE)) {
                case 0:
                    return findVanillaSpecies( "oak");
                case 1:
                    return findVanillaSpecies("spruce");
                case 2:
                    return findVanillaSpecies("birch");
                case 3:
                    return findVanillaSpecies("jungle");
            }
        } else if (state.getBlock().getRegistryName().equals(leaves1)) {
            switch (state.getValue(BlockDynamicLeaves.TREE)) {
                case 0:
                    return findVanillaSpecies("acacia");
                case 1:
                    return findVanillaSpecies("darkoak");
            }
        }

        return Species.NULLSPECIES;
    }

    public static ItemStack getVanillaSapling (final BlockDynamicSapling dynamicSapling, final TreeFamily family) {
        Item item = Item.getByNameOrId(family.getName().getResourcePath() + "_sapling");
        if (item == null) return ItemStack.EMPTY;
        return new ItemStack(item);
    }

    public static Species findVanillaSpecies (final String speciesName) {
        return TreeRegistry.findSpecies(new ResourceLocation(ModConstants.MODID, speciesName));
    }

    public static Species getCommonSpecies (final Species species) {
        return species.getFamily().getCommonSpecies();
    }

}
