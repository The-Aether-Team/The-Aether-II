package com.aetherteam.aetherii.client.event.listeners;

import com.aetherteam.aetherii.client.event.hooks.AerbunnyMountClientHooks;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;

public class AerbunnyMountClientListners {
    /**
     * @see com.aetherteam.aetherii.client.AetherIIClient#eventSetup(IEventBus)
     */
    public static void listen(IEventBus bus) {
        bus.addListener(AerbunnyMountClientListners::onMove);
    }

    /**
     * @see AerbunnyMountClientHooks#movementInput(Player, Input)
     */
    public static void onMove(MovementInputUpdateEvent event) {
        Player player = event.getEntity();
        Input input = event.getInput();
        AerbunnyMountClientHooks.movementInput(player, input);
    }
}
