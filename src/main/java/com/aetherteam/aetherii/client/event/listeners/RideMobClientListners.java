package com.aetherteam.aetherii.client.event.listeners;

import com.aetherteam.aetherii.client.event.hooks.RideMobClientHooks;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;

public class RideMobClientListners {
    /**
     * @see com.aetherteam.aetherii.client.AetherIIClient#eventSetup(IEventBus)
     */
    public static void listen(IEventBus bus) {
        bus.addListener(RideMobClientListners::onMove);
    }

    /**
     * @see com.aetherteam.aetherii.client.event.hooks.RideMobClientHooks#movementInput(Player, Input)
     */
    public static void onMove(MovementInputUpdateEvent event) {
        Player player = event.getEntity();
        Input input = event.getInput();
        RideMobClientHooks.movementInput(player, input);
    }
}
