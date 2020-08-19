package com.harleyoconnor.dynamictreesnaturesaura.proxy;

import com.ferreusveritas.dynamictrees.api.client.ModelHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.harleyoconnor.dynamictreesnaturesaura.ModContent;
import com.harleyoconnor.dynamictreesnaturesaura.blocks.BlockDynamicLeavesAncient;
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

				return 0x000000;
			});
		}
	}
}
