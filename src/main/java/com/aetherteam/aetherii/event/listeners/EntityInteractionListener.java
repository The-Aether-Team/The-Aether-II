package com.aetherteam.aetherii.event.listeners;

import com.aetherteam.aetherii.event.hooks.EntityInteractionHooks;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.Optional;

public class EntityInteractionListener {
    public static void listen(IEventBus bus) {
        bus.addListener(EntityInteractionListener::onInteractWithEntity);
    }

    public static void onInteractWithEntity(PlayerInteractEvent.EntityInteractSpecific event) {
        Entity targetEntity = event.getTarget();
        Player player = event.getEntity();
        InteractionHand interactionHand = event.getHand();
        EntityInteractionHooks.skyrootBucketMilking(targetEntity, player, interactionHand);
        Optional<InteractionResult> result = EntityInteractionHooks.pickupBucketable(targetEntity, player, interactionHand);
        result.ifPresent(event::setCancellationResult);
        event.setCanceled(result.isPresent());
    }
}
