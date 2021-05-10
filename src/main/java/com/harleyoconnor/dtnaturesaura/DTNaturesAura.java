package com.harleyoconnor.dtnaturesaura;

import com.ferreusveritas.dynamictrees.api.registry.RegistryHandler;
import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.harleyoconnor.dtnaturesaura.effects.DynamicLeavesDecayEffect;
import com.harleyoconnor.dtnaturesaura.events.BrilliantFiberClickEventHandler;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DTNaturesAura.MOD_ID)
public class DTNaturesAura {

	public static final String MOD_ID = "dtnaturesaura";

	public DTNaturesAura() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AddonConfig.COMMON_CONFIG);

		RegistryHandler.setup(MOD_ID);
	}

	private void setup (final FMLCommonSetupEvent event) {
		// Register brilliant fiber click event for dynamic leaves.
		MinecraftForge.EVENT_BUS.register(new BrilliantFiberClickEventHandler());

		// Add drain spot effect for decaying dynamic leaves.
		NaturesAuraAPI.DRAIN_SPOT_EFFECTS.put(DynamicLeavesDecayEffect.NAME, DynamicLeavesDecayEffect::new);

		// Register decayed leaves properties as valid leaves for any tree.
		final LeavesProperties decayedProperties = LeavesProperties.REGISTRY.get(resLoc("decayed"));
		Species.REGISTRY.getAll().forEach(species -> species.addValidLeafBlocks(decayedProperties));
	}

	public static ResourceLocation resLoc(final String path) {
		return new ResourceLocation(MOD_ID, path);
	}

}
