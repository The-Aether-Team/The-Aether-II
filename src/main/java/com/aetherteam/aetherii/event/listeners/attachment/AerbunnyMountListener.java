package com.aetherteam.aetherii.event.listeners.attachment;

import com.aetherteam.aetherii.event.hooks.attachment.AerbunnyMountHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class AerbunnyMountListener {
    /**
     * @see com.aetherteam.aetherii.AetherII#eventSetup(IEventBus)
     */
    public static void listen(IEventBus bus) {
        bus.addListener(AerbunnyMountListener::onPlayerLogin);
        bus.addListener(AerbunnyMountListener::onPlayerLogout);
        bus.addListener(AerbunnyMountListener::onPlayerUpdate);
    }

    /**
     * @see DimensionTeleportationHooks#startInAether(Player)
     */
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        AerbunnyMountHooks.onPlayerLogin(player);
    }

    /**
     * @see DimensionTeleportationHooks#createPortal(Player, Level, BlockPos, Direction, ItemStack, InteractionHand)
     */
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        AerbunnyMountHooks.onPlayerLogout(player);
    }

    // GENERIC ATTACHMENT METHODS

    /**
     * @see DimensionTeleportationHooks#update(LivingEntity)
     */
    public static void onPlayerUpdate(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        AerbunnyMountHooks.onUpdate(player);
    }
}