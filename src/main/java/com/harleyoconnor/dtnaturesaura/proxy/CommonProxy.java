package com.harleyoconnor.dtnaturesaura.proxy;

import com.harleyoconnor.dtnaturesaura.dropcreators.DropCreatorGoldenLeaves;
import com.harleyoconnor.dtnaturesaura.effects.DynamicLeavesDecayEffect;
import com.harleyoconnor.dtnaturesaura.events.BrilliantFiberClickEvent;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.MinecraftForge;

import java.util.Arrays;

public class CommonProxy {
	
	public void preInit() {
	}
	
	public void init() {
		// Register tree ritual recipe for Ancient Acorn.
//		new TreeRitualRecipe(new ResourceLocation(DTNaturesAura.MOD_ID, "ancientseed"), getIngredient(Blocks.OAK_SAPLING), new ItemStack(ModContent.itemAncientSeed), 200, getIngredient(Species.REGISTRY.getValue(new ResourceLocation("dynamictrees", "oak")).getSeedStack(1).getItem()), getIngredient(Blocks.YELLOW_FLOWER), getIngredient(Blocks.RED_FLOWER), getIngredient(Items.WHEAT_SEEDS), getIngredient(Items.REEDS), getIngredient(ModItems.GOLD_LEAF)).register();

		// Register brilliant fiber click event for dynamic leaves.
		MinecraftForge.EVENT_BUS.register(new BrilliantFiberClickEvent());

		// Add drain spot effect for decaying dynamic leaves.
		NaturesAuraAPI.DRAIN_SPOT_EFFECTS.put(DynamicLeavesDecayEffect.NAME, DynamicLeavesDecayEffect::new);

		// Create drop creator for golden leaves.
		DropCreatorGoldenLeaves dropCreator = new DropCreatorGoldenLeaves();

		// Register golden leaf drop creator to global species.
//		if (ModConfig.GOLD_LEAF_NEEDS_OAK) {
//			TreeRegistry.registerDropCreator(new ResourceLocation(ModConstants.MODID, "oak"), dropCreator);
//			TreeRegistry.registerDropCreator(new ResourceLocation(ModConstants.MODID, "apple"), dropCreator);
//			TreeRegistry.registerDropCreator(new ResourceLocation(ModConstants.MODID, "oakswamp"), dropCreator);
//		} else TreeRegistry.registerDropCreator(null, dropCreator);
	}
	
	public void postInit() {
	}

//	private static Ingredient getIngredient(Block... blocks) {
//		return getIngredient(Arrays.stream(blocks).map(ItemStack::new).toArray(ItemStack[]::new));
//	}

//	private static Ingredient getIngredient(Item... items) {
//		return getIngredient(Arrays.stream(items).map(ItemStack::new).toArray(ItemStack[]::new));
//	}

//	private static Ingredient getIngredient(ItemStack... stacks) {
//	}

}
