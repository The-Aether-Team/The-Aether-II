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

    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_MUSIC_DISC_AETHER_TUNE = register("item.music_disc.aether_tune");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_MUSIC_DISC_ASCENDING_DAWN = register("item.music_disc.ascending_dawn");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_MUSIC_DISC_AERWHALE = register("item.music_disc.aerwhale");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_MUSIC_DISC_APPROACHES = register("item.music_disc.approaches");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_MUSIC_DISC_DEMISE = register("item.music_disc.demise");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_RECORDING_892 = register("item.music_disc.recording_892");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_PHYG_AMBIENT = register("entity.phyg.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_PHYG_DEATH = register("entity.phyg.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_PHYG_HURT = register("entity.phyg.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_PHYG_SADDLE = register("entity.phyg.saddle");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_PHYG_STEP = register("entity.phyg.step");


    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_FLYING_COW_AMBIENT = register("entity.flying_cow.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_FLYING_COW_DEATH = register("entity.flying_cow.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_FLYING_COW_HURT = register("entity.flying_cow.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_FLYING_COW_SADDLE = register("entity.flying_cow.saddle");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_FLYING_COW_MILK = register("entity.flying_cow.milk");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_FLYING_COW_STEP = register("entity.flying_cow.step");


    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AERBUNNY_DEATH = register("entity.aerbunny.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AERBUNNY_HURT = register("entity.aerbunny.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AERBUNNY_LIFT = register("entity.aerbunny.lift");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_ZEPHYR_SHOOT = register("entity.zephyr.shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_ZEPHYR_AMBIENT = register("entity.zephyr.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_ZEPHYR_DEATH = register("entity.zephyr.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_ZEPHYR_HURT = register("entity.zephyr.hurt");


    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_HIGHFIELDS = register("music.highlands.highfields");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_MAGNETIC = register("music.highlands.magnetic");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_ARCTIC = register("music.highlands.arctic");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_IRRADIATED = register("music.highlands.irradiated");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_AERCLOUD_SEA = register("music.highlands.aercloud_sea");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String location) {
        return SOUNDS.register(location, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(AetherII.MODID, location)));
    }
}