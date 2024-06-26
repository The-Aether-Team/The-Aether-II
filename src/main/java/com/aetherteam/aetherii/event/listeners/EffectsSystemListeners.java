package com.aetherteam.aetherii.event.listeners;

import com.aetherteam.aetherii.event.hooks.EffectsSystemHooks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class EffectsSystemListeners {
    public static void listen(IEventBus bus) {
        bus.addListener(EffectsSystemListeners::livingTick);
    }

    public static void livingTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            EffectsSystemHooks.tickEffectsSystemAttachment(livingEntity);
        }
    }
}
