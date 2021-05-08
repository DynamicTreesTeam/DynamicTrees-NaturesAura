package com.harleyoconnor.dtnaturesaura.proxy;

//@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
		super.preInit();
	}
	
	@Override
	public void init() {
		super.init();
//		registerColorHandlers();
	}
	
	@Override public void postInit() {
		super.postInit();
	}
	
//	public void registerColorHandlers() {
//		for (BlockDynamicLeaves leaves : ModContent.leaves) {
//			ModelHelper.regColorHandler(leaves, (state, worldIn, pos, tintIndex) -> {
//				if (state.getBlock() instanceof DynamicAncientLeavesBlock) return 0xE55B97;
//				else if (state.getBlock() instanceof BlockDynamicLeavesGolden) {
//					int color = 0xF2FF00;
//					if (state != null && worldIn != null && pos != null) {
//						int foliage = BiomeColorHelper.getFoliageColorAtPos(worldIn, pos);
//						return Helper.blendColors(color, foliage, state.getValue(BlockDynamicLeavesGolden.STAGE) / (float) BlockDynamicLeavesGolden.HIGHEST_STAGE);
//					} else {
//						return color;
//					}
//				}
//
//				return 0x000000;
//			});
//		}
//	}
}
