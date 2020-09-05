package com.harleyoconnor.dynamictreesnaturesaura;

import net.minecraftforge.common.config.Config;

@Config(modid = DynamicTreesNaturesAura.MODID)
public class ModConfig {

    @Config.Comment("In the vanilla mod, you can use a gold leaf on any kind of tree, however it doesn't make much sense as golden leaves have the oak leaves texture. So, in this mod, you can only use the gold leaf on oak trees, unless you set this to false.")
    @Config.Name("Gold Leaf Needs Oak")
    @Config.RequiresMcRestart()
    public static boolean GOLD_LEAF_NEEDS_OAK = true;

}
