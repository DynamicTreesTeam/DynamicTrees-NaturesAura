package com.harleyoconnor.dynamictreesnaturesaura.events;

import com.ferreusveritas.dynamictrees.api.treedata.ILeavesProperties;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.trees.TreeOak;
import com.harleyoconnor.dynamictreesnaturesaura.ModConfig;
import com.harleyoconnor.dynamictreesnaturesaura.blocks.BlockDynamicLeavesGolden;
import de.ellpeck.naturesaura.items.ModItems;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class BrilliantFiberClickEvent {

    @SubscribeEvent
    public void onClick (PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();

        if (!stack.getItem().equals(ModItems.GOLD_FIBER)) return;

        IBlockState state = event.getWorld().getBlockState(event.getPos());

        if (!(state.getBlock() instanceof BlockDynamicLeaves)) return;
        event.setCanceled(true);

        if (!BlockDynamicLeavesGolden.convert(event.getWorld(), event.getPos())) return;
        event.setResult(Event.Result.ALLOW);
        event.getEntityPlayer().swingArm(event.getHand());

        if (!event.getWorld().isRemote && !event.getEntityPlayer().isCreative()) stack.shrink(1);
    }

}
