package com.harleyoconnor.dtnaturesaura;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.api.treepacks.JsonApplierRegistryEvent;
import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreator;
import com.ferreusveritas.dynamictrees.trees.Family;
import com.google.common.collect.Maps;
import com.harleyoconnor.dtnaturesaura.blocks.AncientLeavesProperties;
import com.harleyoconnor.dtnaturesaura.blocks.DecayedLeavesProperties;
import com.harleyoconnor.dtnaturesaura.blocks.GoldenLeavesProperties;
import com.harleyoconnor.dtnaturesaura.dropcreators.GoldenLeavesDropCreator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;

import java.util.Map;

/**
 * @author Harley O'Connor
 */
@Mod.EventBusSubscriber(modid = DTNaturesAura.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class AddonRegistries {

    /** The {@link ResourceLocation} of ancient leaves properties (and their type). */
    public static final ResourceLocation ANCIENT = DTNaturesAura.resLoc("ancient");

    /** The {@link ResourceLocation} of golden leaves properties type. */
    public static final ResourceLocation GOLDEN = DTNaturesAura.resLoc("golden");

    /** The {@link ResourceLocation} of decayed leaves properties (and their type). */
    public static final ResourceLocation DECAYED = DTNaturesAura.resLoc("decayed");

    public static final Map<Family, LeavesProperties> FAMILY_GOLDEN_LEAVES_MAP = Maps.newHashMap();

    @SubscribeEvent
    public static void registerLeavesPropertyTypes(final TypeRegistryEvent<LeavesProperties> event) {
        event.registerType(ANCIENT, AncientLeavesProperties.TYPE);
        event.registerType(GOLDEN, GoldenLeavesProperties.TYPE);
        event.registerType(DECAYED, DecayedLeavesProperties.TYPE);
    }

    public static final DropCreator GOLDEN_LEAVES_DROP_CREATOR = new GoldenLeavesDropCreator();

    @SubscribeEvent
    public static void registerJsonAppliers(final JsonApplierRegistryEvent<LeavesProperties> event) {
        if (!event.isLoadApplier())
            return;

        event.getApplierList().register("family", GoldenLeavesProperties.class, ResourceLocation.class,
                (leavesProperties, resourceLocation) -> {
            final ResourceLocation registryName = TreeRegistry.processResLoc(resourceLocation);
            Family.REGISTRY.runOnNextLock(() -> Family.REGISTRY.get(registryName)
                    .ifValidElse(family -> {
                        leavesProperties.setFamily(family);
                        FAMILY_GOLDEN_LEAVES_MAP.put(family, leavesProperties);
                        family.getSpecies().forEach(species -> {
                            species.addDropCreator(GOLDEN_LEAVES_DROP_CREATOR);
                            species.addValidLeafBlocks(leavesProperties);
                        });
                    }, () -> LogManager.getLogger().error("Could not find family '" + registryName + "' to set as family for " + leavesProperties + "."))
            );
        });
    }

}
