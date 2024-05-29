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

    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_SLASH_DAMAGE_CORRECT = register("entity.player.damage.slash.correct");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_IMPACT_DAMAGE_CORRECT = register("entity.player.damage.impact.correct");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_PIERCE_DAMAGE_CORRECT = register("entity.player.damage.pierce.correct");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_SLASH_DAMAGE_INCORRECT = register("entity.player.damage.slash.incorrect");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_IMPACT_DAMAGE_INCORRECT = register("entity.player.damage.impact.incorrect");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_PIERCE_DAMAGE_INCORRECT = register("entity.player.damage.pierce.incorrect");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_ATTACK_SHOCK = register("entity.player.attack.shock");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_ATTACK_STAB = register("entity.player.attack.stab");

    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_HIGHFIELDS = register("music.highlands.highfields");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_MAGNETIC = register("music.highlands.magnetic");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_ARCTIC = register("music.highlands.arctic");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_IRRADIATED = register("music.highlands.irradiated");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_AERCLOUD_SEA = register("music.highlands.aercloud_sea");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String location) {
        return SOUNDS.register(location, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(AetherII.MODID, location)));
    }
}