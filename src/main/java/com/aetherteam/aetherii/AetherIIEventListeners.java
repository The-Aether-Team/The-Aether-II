package com.aetherteam.aetherii;

import com.aetherteam.aetherii.event.hooks.AttachmentHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class AetherIIEventListeners {
    public static void listen(IEventBus bus) {
        // Player
        bus.addListener(AetherIIEventListeners::onPlayerLogin);
        bus.addListener(AetherIIEventListeners::onPlayerLogout);
        bus.addListener(AetherIIEventListeners::onPlayerRespawn);
        bus.addListener(AetherIIEventListeners::onPlayerPostTick);
        bus.addListener(AetherIIEventListeners::onPlayerRightClickBlock);
        bus.addListener(AetherIIEventListeners::onPlayerEntityInteractSpecific);

        // LivingEntity
        bus.addListener(AetherIIEventListeners::onLivingPostTick);
    }

    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();

        AttachmentHooks.playerLogin(player);
    }

    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();

        AttachmentHooks.playerLogout(player);
    }

    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();

        AttachmentHooks.playerRespawn(player);
    }

    public static void onPlayerPostTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        AttachmentHooks.playerPostTickUpdate(player);
    }

    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        InteractionHand interactionHand = event.getHand();
        ItemStack itemStack = event.getItemStack();
        BlockPos blockPos = event.getPos();
        Direction directionFace = event.getFace();
        LogicalSide side = event.getSide();


    }

    public static void onPlayerEntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        InteractionHand interactionHand = event.getHand();
        ItemStack itemStack = event.getItemStack();
        BlockPos localPos = event.getPos();
        Entity targetEntity = event.getTarget();
        LogicalSide side = event.getSide();


    }

    public static void onLivingPostTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();

        AttachmentHooks.livingPostTickUpdate(entity);
    }
}
