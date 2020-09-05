package com.harleyoconnor.dynamictreesnaturesaura.blocks.multi;

import com.ferreusveritas.dynamictrees.blocks.BlockBranch;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicSapling;
import com.harleyoconnor.dynamictreesnaturesaura.DynamicTreesNaturesAura;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.multiblock.IMultiblock;
import de.ellpeck.naturesaura.api.multiblock.Matcher;
import de.ellpeck.naturesaura.blocks.ModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Multiblocks {

    public static final IMultiblock DYNAMIC_TREE_RITUAL = NaturesAuraAPI.instance().createMultiblock(
            new ResourceLocation(DynamicTreesNaturesAura.MODID, "tree_ritual_dynamic"),
            new String[][]{
                    {"    W    ", " W     W ", "   GGG   ", "  GG GG  ", "W G 0 G W", "  GG GG  ", "   GGG   ", " W     W ", "    W    "}},
            'W', new Matcher(ModBlocks.WOOD_STAND.getDefaultState(),
                    (world, start, offset, pos, state, c) -> world != null || state.getBlock() == ModBlocks.WOOD_STAND),
            'G', ModBlocks.GOLD_POWDER,
            '0', new Matcher(com.ferreusveritas.dynamictrees.ModBlocks.blockDynamicSapling.getDefaultState(),
                    (world, start, offset, pos, state, c) -> {
                        if (state.getBlock() instanceof BlockDynamicSapling || state.getBlock() instanceof BlockBranch)
                            return true;
                        // try-catch to prevent blocks that need to have been placed crashing here
                        try {
                            ItemStack stack = state.getBlock().getItem(world, pos, state);
                            return !stack.isEmpty() && NaturesAuraAPI.TREE_RITUAL_RECIPES.values().stream().anyMatch(recipe -> recipe.saplingType.apply(stack));
                        } catch (Exception e) {
                            return false;
                        }
                    }
            ),
            ' ', Matcher.wildcard());

}
