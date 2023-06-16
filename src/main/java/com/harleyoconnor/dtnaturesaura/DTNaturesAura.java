package com.harleyoconnor.dtnaturesaura;

import com.ferreusveritas.dynamictrees.api.GatherDataHelper;
import com.ferreusveritas.dynamictrees.api.registry.RegistryHandler;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import com.harleyoconnor.dtnaturesaura.effect.DynamicLeavesDecayEffect;
import com.harleyoconnor.dtnaturesaura.effect.GrassDieEffect;
import com.harleyoconnor.dtnaturesaura.event.BrilliantFiberClickEventHandler;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
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
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::gatherData);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AddonConfig.COMMON_CONFIG);

        RegistryHandler.setup(MOD_ID);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Register brilliant fiber click event for dynamic leaves.
        MinecraftForge.EVENT_BUS.register(new BrilliantFiberClickEventHandler());

        // Overwrite grass die effect so that it doesn't convert dynamic leaves.
        NaturesAuraAPI.DRAIN_SPOT_EFFECTS.put(GrassDieEffect.NAME, GrassDieEffect::new);

        // Add drain spot effect for decaying dynamic leaves.
        NaturesAuraAPI.DRAIN_SPOT_EFFECTS.put(DynamicLeavesDecayEffect.NAME, DynamicLeavesDecayEffect::new);

        // Register decayed leaves properties as valid leaves for any tree.
        final LeavesProperties decayedProperties = LeavesProperties.REGISTRY.get(AddonRegistries.DECAYED);
        Species.REGISTRY.getAll().forEach(species -> species.addValidLeafBlocks(decayedProperties));
    }

    public void gatherData(final GatherDataEvent event) {
        GatherDataHelper.gatherAllData(MOD_ID, event);
    }

    public static ResourceLocation location(final String path) {
        return new ResourceLocation(MOD_ID, path);
    }

}
