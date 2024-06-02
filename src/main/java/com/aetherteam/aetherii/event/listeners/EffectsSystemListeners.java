package com.aetherteam.aetherii.event.listeners;

import com.aetherteam.aetherii.event.hooks.EffectsSystemHooks;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public class EffectsSystemListeners {
    public static void listen(IEventBus bus) {
        bus.addListener(EffectsSystemListeners::livingTick);
    }

    public static void livingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        EffectsSystemHooks.tickEffectsSystemAttachment(livingEntity);
    }
}
