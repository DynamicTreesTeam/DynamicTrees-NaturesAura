package com.harleyoconnor.dtnaturesaura.events;

import com.ferreusveritas.dynamictrees.blocks.leaves.DynamicLeavesBlock;
import com.harleyoconnor.dtnaturesaura.blocks.BlockDynamicLeavesGolden;
import de.ellpeck.naturesaura.items.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class BrilliantFiberClickEvent {

    @SubscribeEvent
    public void onClick (PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();

        if (!stack.getItem().equals(ModItems.GOLD_FIBER)) return;

        BlockState state = event.getWorld().getBlockState(event.getPos());

        if (!(state.getBlock() instanceof DynamicLeavesBlock)) return;
        event.setCanceled(true);

//        if (!BlockDynamicLeavesGolden.convert(event.getWorld(), event.getPos())) return;
        event.setResult(Event.Result.ALLOW);
        event.getPlayer().swingArm(event.getHand());

        if (!event.getWorld().isRemote && !event.getPlayer().isCreative())
            stack.shrink(1);
    }

}
