package com.aetherteam.aetherii.event;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class AetherIIEventDispatch {
    /**
     * @see EggLayEvent
     */
    public static EggLayEvent onLayEgg(Entity entity, SoundEvent sound, float volume, float pitch, ItemStack item) {
        EggLayEvent event = new EggLayEvent(entity, sound, volume, pitch, item);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }
}
