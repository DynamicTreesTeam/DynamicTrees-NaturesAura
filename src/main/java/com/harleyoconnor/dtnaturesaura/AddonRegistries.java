package com.harleyoconnor.dtnaturesaura;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.registry.RegistryEvent;
import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.api.treepacks.ApplierRegistryEvent;
import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreator;
import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreatorConfiguration;
import com.ferreusveritas.dynamictrees.trees.Family;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.harleyoconnor.dtnaturesaura.blocks.AncientLeavesProperties;
import com.harleyoconnor.dtnaturesaura.blocks.DecayedLeavesProperties;
import com.harleyoconnor.dtnaturesaura.blocks.GoldenLeavesProperties;
import com.harleyoconnor.dtnaturesaura.dropcreators.GoldenLeavesDropCreator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;

import java.util.LinkedList;
import java.util.List;
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

    public static final DropCreator GOLDEN_LEAVES_DROP_CREATOR = new GoldenLeavesDropCreator(DTNaturesAura.resLoc("golden_leaves"));
    private static final DropCreatorConfiguration DEFAULT_GOLDEN_LEAVES_DROP_CREATOR = GOLDEN_LEAVES_DROP_CREATOR.getDefaultConfiguration();

    @SubscribeEvent
    public static void registerDropCreator(final RegistryEvent<DropCreator> event) {
        event.getRegistry().register(GOLDEN_LEAVES_DROP_CREATOR);
    }

    @SubscribeEvent
    public static void addValidLeafBlocks(final ApplierRegistryEvent.Load<LeavesProperties, JsonElement> event) {
        event.getAppliers().register("family", GoldenLeavesProperties.class, ResourceLocation.class,
                (leavesProperties, resourceLocation) -> {
            final ResourceLocation registryName = TreeRegistry.processResLoc(resourceLocation);

            Family.REGISTRY.runOnNextLock(() -> Family.REGISTRY.get(registryName)
                    .ifValidElse(
                            family -> processFamily(leavesProperties, family),
                            () -> LogManager.getLogger().error("Could not find family '" + registryName +
                                    "' to set as family for " + leavesProperties + ".")
                    )
            );
        });
    }

    @SubscribeEvent
    public static void addDropCreators(final ApplierRegistryEvent.Reload<LeavesProperties, JsonElement> event) {
        final List<Species> speciesList = new LinkedList<>();

        event.getAppliers().register("family", GoldenLeavesProperties.class, ResourceLocation.class,
                (leavesProperties, resourceLocation) -> {
            final ResourceLocation registryName = TreeRegistry.processResLoc(resourceLocation);

            Family.REGISTRY.runOnNextLock(() ->
                    Family.REGISTRY.get(registryName).ifValid(family ->
                            speciesList.addAll(family.getSpecies())
                    )
            );

            // Add golden leaves drop creators.
            Species.REGISTRY.runOnNextLock(() ->
                    speciesList.forEach(species -> {
                        if (!species.getDropCreators().contains(DEFAULT_GOLDEN_LEAVES_DROP_CREATOR)) {
                            species.addDropCreators(DEFAULT_GOLDEN_LEAVES_DROP_CREATOR);
                        }
                    })
            );
        });
    }

    private static void processFamily(GoldenLeavesProperties leavesProperties, Family family) {
        leavesProperties.setFamily(family);
        FAMILY_GOLDEN_LEAVES_MAP.put(family, leavesProperties);
        family.getSpecies().forEach(species ->
                species.addValidLeafBlocks(leavesProperties));
    }

}
