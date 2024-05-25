package com.aetherteam.aetherii.event.listeners.attachment;

import com.aetherteam.aetherii.event.hooks.AttachmentHooks;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public class AetherIIPlayerListener {
    /**
     * @see com.aetherteam.aetherii.AetherII#eventSetup(IEventBus)
     */
    public static void listen(IEventBus bus) {
        bus.addListener(AetherIIPlayerListener::onPlayerUpdate);
    }

    /**
     * @see com.aetherteam.aetherii.event.hooks.AttachmentHooks.AetherIIPlayerHooks#update(LivingEntity)
     */
    public static void onPlayerUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        AttachmentHooks.AetherIIPlayerHooks.update(livingEntity);
    }
}