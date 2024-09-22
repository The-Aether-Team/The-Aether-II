package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIISoundEvents {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, AetherII.MODID);

    // Blocks
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_AETHER_PORTAL_AMBIENT = register("block.aether_portal.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_AETHER_PORTAL_TRAVEL = register("block.aether_portal.travel");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_AETHER_PORTAL_TRIGGER = register("block.aether_portal.trigger");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_BLUE_AERCLOUD_BOUNCE = register("block.blue_aercloud.bounce");

    // Items
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_AMBROSIUM_SHARD = register("item.ambrosium_shard.use");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_SWET_BALL_USE = register("item.swet_ball.use");

    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_MUSIC_DISC_AETHER_TUNE = register("item.music_disc.aether_tune");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_MUSIC_DISC_ASCENDING_DAWN = register("item.music_disc.ascending_dawn");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_MUSIC_DISC_AERWHALE = register("item.music_disc.aerwhale");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_MUSIC_DISC_APPROACHES = register("item.music_disc.approaches");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_MUSIC_DISC_DEMISE = register("item.music_disc.demise");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_RECORDING_892 = register("item.music_disc.recording_892");

    // Player
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_SLASH_DAMAGE_CORRECT = register("entity.player.damage.slash.correct");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_IMPACT_DAMAGE_CORRECT = register("entity.player.damage.impact.correct");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_PIERCE_DAMAGE_CORRECT = register("entity.player.damage.pierce.correct");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_SLASH_DAMAGE_INCORRECT = register("entity.player.damage.slash.incorrect");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_IMPACT_DAMAGE_INCORRECT = register("entity.player.damage.impact.incorrect");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_PIERCE_DAMAGE_INCORRECT = register("entity.player.damage.pierce.incorrect");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_ATTACK_SHOCK = register("entity.player.attack.shock");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_ATTACK_STAB = register("entity.player.attack.stab");

    // Entities
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

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SHEEPUFF_AMBIENT = register("entity.sheepuff.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SHEEPUFF_DEATH = register("entity.sheepuff.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SHEEPUFF_HURT = register("entity.sheepuff.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SHEEPUFF_SHEAR = register("entity.sheepuff.shear");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SHEEPUFF_STEP = register("entity.sheepuff.step");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AERBUNNY_DEATH = register("entity.aerbunny.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AERBUNNY_HURT = register("entity.aerbunny.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AERBUNNY_LIFT = register("entity.aerbunny.lift");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TAEGORE_AMBIENT = register("entity.taegore.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TAEGORE_DEATH = register("entity.taegore.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TAEGORE_HURT = register("entity.taegore.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TAEGORE_STEP = register("entity.taegore.step");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_BURRUKAI_AMBIENT = register("entity.burrukai.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_BURRUKAI_DEATH = register("entity.burrukai.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_BURRUKAI_HURT = register("entity.burrukai.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_BURRUKAI_STEP = register("entity.burrukai.step");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_KIRRID_AMBIENT = register("entity.kirrid.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_KIRRID_DEATH = register("entity.kirrid.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_KIRRID_HURT = register("entity.kirrid.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_KIRRID_STEP = register("entity.kirrid.step");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_AMBIENT = register("entity.moa.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_DEATH = register("entity.moa.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_HURT = register("entity.moa.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_SADDLE = register("entity.moa.saddle");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_STEP = register("entity.moa.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_FLAP = register("entity.moa.flap");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_EGG = register("entity.moa.egg");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SKYROOT_LIZARD_AMBIENT = register("entity.skyroot_lizard.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SKYROOT_LIZARD_DEATH = register("entity.skyroot_lizard.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SKYROOT_LIZARD_HURT = register("entity.skyroot_lizard.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SKYROOT_LIZARD_STEP = register("entity.skyroot_lizard.step");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_ZEPHYR_SHOOT = register("entity.zephyr.shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_ZEPHYR_AMBIENT = register("entity.zephyr.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_ZEPHYR_DEATH = register("entity.zephyr.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_ZEPHYR_HURT = register("entity.zephyr.hurt");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TEMPEST_SHOOT = register("entity.tempest.shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TEMPEST_AMBIENT = register("entity.tempest.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TEMPEST_DEATH = register("entity.tempest.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TEMPEST_HURT = register("entity.tempest.hurt");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_COCKATRICE_SHOOT = register("entity.cockatrice.shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_COCKATRICE_AMBIENT = register("entity.cockatrice.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_COCKATRICE_DEATH = register("entity.cockatrice.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_COCKATRICE_HURT = register("entity.cockatrice.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_COCKATRICE_STEP = register("entity.cockatrice.step");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SWET_ATTACK = register("entity.swet.attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SWET_DEATH = register("entity.swet.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SWET_HURT = register("entity.swet.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SWET_JUMP = register("entity.swet.jump");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SWET_SQUISH = register("entity.swet.squish");


    // Music
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_AETHER = register("music.aether");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_AETHER_NIGHT = register("music.aether_night");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_AETHER_SUNRISE = register("music.aether_sunrise");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_AETHER_SUNSET = register("music.aether_sunset");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_AETHER_AMBIENCE = register("music.aether_ambience");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String location) {
        return SOUNDS.register(location, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, location)));
    }
}