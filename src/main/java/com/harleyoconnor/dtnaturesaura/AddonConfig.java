package com.harleyoconnor.dtnaturesaura;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Collections;
import java.util.List;

/**
 * @author Harley O'Connor
 */
public final class AddonConfig {

    public static final ForgeConfigSpec COMMON_CONFIG;

    public static final ForgeConfigSpec.ConfigValue<List<String>> LEAVES_DECAY_BLACKLIST;

    static {
        final ForgeConfigSpec.Builder commonBuilder = new ForgeConfigSpec.Builder();

        commonBuilder.push("Decay Settings").push("decay");
        LEAVES_DECAY_BLACKLIST = commonBuilder.comment("Any leaves properties registry names added to this will be ignored by the dynamic leaves decay effect. Note that any \"solid\" leaves (such as the nether fungus dynamic wart blocks) are automatically ignored.")
                .define("leavesDecayBlacklist", Collections.emptyList());
        commonBuilder.pop();

        COMMON_CONFIG = commonBuilder.build();
    }

}
