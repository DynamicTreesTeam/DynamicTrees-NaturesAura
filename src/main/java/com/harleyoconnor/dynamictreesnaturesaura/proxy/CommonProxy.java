package com.harleyoconnor.dynamictreesnaturesaura.proxy;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.harleyoconnor.dynamictreesnaturesaura.DynamicTreesNaturesAura;
import com.harleyoconnor.dynamictreesnaturesaura.ModContent;
import com.harleyoconnor.dynamictreesnaturesaura.events.BrilliantFiberClickEvent;
import de.ellpeck.naturesaura.NaturesAura;
import de.ellpeck.naturesaura.api.recipes.TreeRitualRecipe;
import de.ellpeck.naturesaura.api.recipes.ing.AmountIngredient;
import de.ellpeck.naturesaura.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

import java.util.Arrays;

public class CommonProxy {
	
	public void preInit() {
	}
	
	public void init() {
		new TreeRitualRecipe(new ResourceLocation(DynamicTreesNaturesAura.MODID, "ancientseed"), getIngredient(Blocks.SAPLING), new ItemStack(ModContent.itemAncientSeed), 200, getIngredient(Species.REGISTRY.getValue(new ResourceLocation("dynamictrees", "oak")).getSeedStack(1).getItem()), getIngredient(Blocks.YELLOW_FLOWER), getIngredient(Blocks.RED_FLOWER), getIngredient(Items.WHEAT_SEEDS), getIngredient(Items.REEDS), getIngredient(ModItems.GOLD_LEAF)).register();
		MinecraftForge.EVENT_BUS.register(new BrilliantFiberClickEvent());
	}
	
	public void postInit() {
	}

	private static Ingredient getIngredient(Block... blocks) {
		return getIngredient(Arrays.stream(blocks).map(ItemStack::new).toArray(ItemStack[]::new));
	}

	private static Ingredient getIngredient(Item... items) {
		return getIngredient(Arrays.stream(items).map(ItemStack::new).toArray(ItemStack[]::new));
	}

	private static Ingredient getIngredient(ItemStack... stacks) {
		return Ingredient.fromStacks(stacks);
	}

}
