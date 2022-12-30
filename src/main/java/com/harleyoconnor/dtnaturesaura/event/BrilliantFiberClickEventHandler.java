package com.harleyoconnor.dtnaturesaura.event;

import com.ferreusveritas.dynamictrees.block.leaves.DynamicLeavesBlock;
import com.harleyoconnor.dtnaturesaura.block.DynamicGoldenLeavesBlock;
import de.ellpeck.naturesaura.items.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class BrilliantFiberClickEventHandler {

    @SubscribeEvent
    public void onClick(PlayerInteractEvent.RightClickBlock event) {
        final ItemStack stack = event.getItemStack();
        final BlockState state = event.getWorld().getBlockState(event.getPos());

        if (!stack.getItem().equals(ModItems.GOLD_FIBER) || !(state.getBlock() instanceof DynamicLeavesBlock)) {
            return;
        }

        event.setCanceled(true);

        if (!DynamicGoldenLeavesBlock.convert(event.getWorld(), event.getPos())) {
            return;
        }

        event.setResult(Event.Result.ALLOW);
        event.getPlayer().swing(event.getHand());

        if (!event.getWorld().isClientSide && !event.getPlayer().isCreative()) {
            stack.shrink(1);
        }
    }

}
