package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIISoundEvents {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, AetherII.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_AETHER_PORTAL_AMBIENT = register("block.aether_portal.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_AETHER_PORTAL_TRAVEL = register("block.aether_portal.travel");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_AETHER_PORTAL_TRIGGER = register("block.aether_portal.trigger");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_BLUE_AERCLOUD_BOUNCE = register("block.blue_aercloud.bounce");

    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_HIGHFIELDS = register("music.highlands.highfields");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_MAGNETIC = register("music.highlands.magnetic");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_ARCTIC = register("music.highlands.arctic");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_IRRADIATED = register("music.highlands.irradiated");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_AERCLOUD_SEA = register("music.highlands.aercloud_sea");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String location) {
        return SOUNDS.register(location, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(AetherII.MODID, location)));
    }
}