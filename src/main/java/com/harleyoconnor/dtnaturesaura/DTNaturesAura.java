package com.harleyoconnor.dtnaturesaura;

import com.ferreusveritas.dynamictrees.api.registry.RegistryHandler;
import com.harleyoconnor.dtnaturesaura.effects.DynamicLeavesDecayEffect;
import com.harleyoconnor.dtnaturesaura.events.BrilliantFiberClickEvent;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DTNaturesAura.MOD_ID)
public class DTNaturesAura {

	public static final String MOD_ID = "dtnaturesaura";

	public DTNaturesAura() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

		RegistryHandler.setup(MOD_ID);
	}

	private void setup (final FMLCommonSetupEvent event) {
		// Register brilliant fiber click event for dynamic leaves.
//		MinecraftForge.EVENT_BUS.register(new BrilliantFiberClickEvent());

		// Add drain spot effect for decaying dynamic leaves.
		NaturesAuraAPI.DRAIN_SPOT_EFFECTS.put(DynamicLeavesDecayEffect.NAME, DynamicLeavesDecayEffect::new);
	}

	public static ResourceLocation resLoc(final String path) {
		return new ResourceLocation(MOD_ID, path);
	}

}
