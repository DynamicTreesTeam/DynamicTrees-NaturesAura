package com.harleyoconnor.dynamictreesnaturesaura.proxy;

import com.ferreusveritas.dynamictrees.api.client.ModelHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.harleyoconnor.dynamictreesnaturesaura.ModContent;
import com.harleyoconnor.dynamictreesnaturesaura.blocks.BlockDynamicLeavesAncient;
import com.harleyoconnor.dynamictreesnaturesaura.blocks.BlockDynamicLeavesDecayed;
import com.harleyoconnor.dynamictreesnaturesaura.blocks.BlockDynamicLeavesGolden;
import de.ellpeck.naturesaura.Helper;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
		super.preInit();
	}
	
	@Override
	public void init() {
		super.init();
		registerColorHandlers();
	}
	
	@Override public void postInit() {
		super.postInit();
	}
	
	public void registerColorHandlers() {
		for (BlockDynamicLeaves leaves : ModContent.leaves) {
			ModelHelper.regColorHandler(leaves, (state, worldIn, pos, tintIndex) -> {
				if (state.getBlock() instanceof BlockDynamicLeavesAncient) return 0xE55B97;
				else if (state.getBlock() instanceof BlockDynamicLeavesGolden) {
					int color = 0xF2FF00;
					if (state != null && worldIn != null && pos != null) {
						int foliage = BiomeColorHelper.getFoliageColorAtPos(worldIn, pos);
						return Helper.blendColors(color, foliage, state.getValue(BlockDynamicLeavesGolden.STAGE) / (float) BlockDynamicLeavesGolden.HIGHEST_STAGE);
					} else {
						return color;
					}
				} else if (state.getBlock() instanceof BlockDynamicLeavesDecayed) return 0xFFFFFF;

				return 0x000000;
			});
		}
	}
}
