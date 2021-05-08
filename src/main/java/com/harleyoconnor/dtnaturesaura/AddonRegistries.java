package com.harleyoconnor.dtnaturesaura;

import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;
import com.harleyoconnor.dtnaturesaura.blocks.AncientLeavesProperties;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author Harley O'Connor
 */
@Mod.EventBusSubscriber(modid = DTNaturesAura.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class AddonRegistries {

    @SubscribeEvent
    public static void registerLeavesPropertyTypes(final TypeRegistryEvent<LeavesProperties> event) {
        event.registerType(DTNaturesAura.resLoc("ancient"), AncientLeavesProperties.TYPE);
    }

}
