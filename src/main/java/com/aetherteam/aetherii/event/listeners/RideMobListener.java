package com.aetherteam.aetherii.event.listeners;

import com.aetherteam.aetherii.event.hooks.PortalTeleportationHooks;
import com.aetherteam.aetherii.event.hooks.RideMobHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class RideMobListener {
    /**
     * @see com.aetherteam.aetherii.AetherII#eventSetup(IEventBus)
     */
    public static void listen(IEventBus bus) {
        bus.addListener(RideMobListener::onPlayerLogin);
        bus.addListener(RideMobListener::onPlayerLogout);
        bus.addListener(RideMobListener::onPlayerUpdate);
    }

    /**
     * @see PortalTeleportationHooks#startInAether(Player)
     */
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        RideMobHooks.onPlayerLogin(player);
    }

    /**
     * @see PortalTeleportationHooks#createPortal(Player, Level, BlockPos, Direction, ItemStack, InteractionHand)
     */
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        RideMobHooks.onPlayerLogout(player);
    }

    // GENERIC ATTACHMENT METHODS

    /**
     * @see PortalTeleportationHooks#update(LivingEntity)
     */
    public static void onPlayerUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player player) {
            RideMobHooks.onUpdate(player);
        }
    }
}