package com.aetherteam.aetherii.event.listeners;

import com.aetherteam.aetherii.event.hooks.BlockInteractionHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class BlockInteractionListener {
    public static void listen(IEventBus bus) {
        bus.addListener(BlockInteractionListener::rightClickBlock);
    }

    public static void rightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        ItemStack itemStack = event.getItemStack();
        InteractionHand hand = event.getHand();
        if (BlockInteractionHooks.snowlog(player, level, pos, itemStack, hand)) {
            event.setCanceled(true);
        }
    }
}
