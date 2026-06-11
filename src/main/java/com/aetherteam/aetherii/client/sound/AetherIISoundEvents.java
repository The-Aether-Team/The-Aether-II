package com.aetherteam.aetherii.client.sound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
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

    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_FERROSITE_BREAK = register("block.ferrosite.break");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_FERROSITE_STEP = register("block.ferrosite.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_FERROSITE_PLACE = register("block.ferrosite.place");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_FERROSITE_HIT = register("block.ferrosite.hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_FERROSITE_FALL = register("block.ferrosite.fall");

    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_MOA_EGG_CRACK = register("block.moa_egg.crack");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_MOA_EGG_HATCH = register("block.moa_egg.hatch");

    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_BUSH_RUSTLE = register("block.bush.rustle");

    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_GEL_SLIDE = register("block.gel.slide");

    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_HESTVEIL_IGNITE = register("block.hestveil.ignite");

    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_HOLYSTONE_FURNACE_CRACKLE = register("block.holystone_furnace.crackle");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_HOLYSTONE_SMOKER_SMOKE = register("block.holystone_smoker.smoke");

    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_ARKENIUM_FORGE_USE = register("block.arkenium_forge.use");

    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_ALKAHEST_PURIFIER_OPEN = register("block.alkahest_purifier.open");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_ALKAHEST_PURIFIER_CLOSE = register("block.alkahest_purifier.close");

    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_GROUND_TRAP_TRIGGER = register("block.ground_trap.trigger");

    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_GUARDIAN_DONATION_BOX_INSERT = register("block.guardian_donation_box.insert");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_GUARDIAN_DONATION_BOX_INSERT_FAIL = register("block.guardian_donation_box.inser_fail");

    public static final DeferredHolder<SoundEvent, SoundEvent> WATER_EVAPORATE = register("block.water.evaporate");

    // Items
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_AMBROSIUM_SHARD = register("item.ambrosium_shard.use");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_SWET_GEL_USE = register("item.swet_ball.use");

    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_SCATTERGLASS_VIAL_FILL = register("item.scatterglass_vial.fill");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_SCATTERGLASS_VIAL_EMPTY = register("item.scatterglass_vial.empty");

    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARKENIUM_CANISTER_FILL = register("item.arkenium_canister.fill");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARKENIUM_CANISTER_EMPTY = register("item.arkenium_canister.empty");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARKENIUM_CANISTER_FILL_ALKAHEST = register("item.arkenium_canister.fill_alkahest");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARKENIUM_CANISTER_EMPTY_ALKAHEST = register("item.arkenium_canister.empty_alkahest");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARKENIUM_CANISTER_FILL_HESTVEIL = register("item.arkenium_canister.fill_hestveil");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARKENIUM_CANISTER_EMPTY_HESTVEIL = register("item.arkenium_canister.empty_hestveil");

    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_SHIFTING_GLASS_USE = register("item.shifting_glass.use");

    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_AERBUNNY_BELL_RING = register("item.aerbunny_bell.ring");

    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_HAMMER_OF_DEMOLITION_SHOOT = register("item.hammer_of_demolition.shoot");

    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ENGRAVED_DISC_ASCENDING_DAWN = register("item.engraved_disc.ascending_dawn");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ENGRAVED_DISC_AERWHALE = register("item.engraved_disc.aerwhale");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ENGRAVED_DISC_APPROACHES = register("item.engraved_disc.approaches");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ENGRAVED_DISC_DEMISE = register("item.engraved_disc.demise");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ENGRAVED_DISC_CHINCHILLA = register("item.engraved_disc.chinchilla");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ENGRAVED_DISC_HIGH = register("item.engraved_disc.high");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ENGRAVED_DISC_REVOLUTIONS = register("item.engraved_disc.revolutions");

    // Armor
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARMOR_EQUIP_BEAST_PELT = register("item.armor.equip_beast_pelt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARMOR_EQUIP_BURRUKAI_PLATE = register("item.armor.equip_burrukai_plate");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARMOR_EQUIP_ZANITE = register("item.armor.zanite");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARMOR_EQUIP_ARKENIUM = register("item.armor.arkenium");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARMOR_EQUIP_GRAVITITE = register("item.armor.gravitite");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARMOR_EQUIP_SENTRY = register("item.armor.sentry");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ARMOR_EQUIP_NEPTUNE = register("item.armor.neptune");

    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_ACCESSORY_EQUIP_GENERIC = register("item.accessory.equip_generic");

    // Player
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_SLASH_DAMAGE_CORRECT = register("entity.player.damage.slash.correct");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_IMPACT_DAMAGE_CORRECT = register("entity.player.damage.impact.correct");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_PIERCE_DAMAGE_CORRECT = register("entity.player.damage.pierce.correct");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_SLASH_DAMAGE_INCORRECT = register("entity.player.damage.slash.incorrect");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_IMPACT_DAMAGE_INCORRECT = register("entity.player.damage.impact.incorrect");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_PIERCE_DAMAGE_INCORRECT = register("entity.player.damage.pierce.incorrect");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_ATTACK_SWEEP = register("entity.player.attack.sweep");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_ATTACK_SHOCK = register("entity.player.attack.shock");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_ATTACK_STAB = register("entity.player.attack.stab");

    // Entities
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_PHYG_AMBIENT = register("entity.phyg.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_PHYG_DEATH = register("entity.phyg.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_PHYG_HURT = register("entity.phyg.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_PHYG_STEP = register("entity.phyg.step");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_FLYING_COW_AMBIENT = register("entity.flying_cow.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_FLYING_COW_DEATH = register("entity.flying_cow.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_FLYING_COW_HURT = register("entity.flying_cow.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_FLYING_COW_MILK = register("entity.flying_cow.milk");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_FLYING_COW_STEP = register("entity.flying_cow.step");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SHEEPUFF_AMBIENT = register("entity.sheepuff.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SHEEPUFF_DEATH = register("entity.sheepuff.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SHEEPUFF_HURT = register("entity.sheepuff.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SHEEPUFF_SHEAR = register("entity.sheepuff.shear");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SHEEPUFF_STEP = register("entity.sheepuff.step");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AERBUNNY_AMBIENT = register("entity.aerbunny.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AERBUNNY_DEATH = register("entity.aerbunny.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AERBUNNY_HURT = register("entity.aerbunny.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AERBUNNY_HOP = register("entity.aerbunny.hop");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AERBUNNY_LIFT = register("entity.aerbunny.lift");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AERBUNNY_LAND = register("entity.aerbunny.land");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AERWHALE_AMBIENT = register("entity.aerwhale.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AERWHALE_DEATH = register("entity.aerwhale.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AERWHALE_HURT = register("entity.aerwhale.hurt");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TAEGORE_AMBIENT = register("entity.taegore.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TAEGORE_DEATH = register("entity.taegore.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TAEGORE_HURT = register("entity.taegore.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TAEGORE_STEP = register("entity.taegore.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TAEGORE_SEARCHING = register("entity.taegore.searching");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TAEGORE_DIGGING = register("entity.taegore.digging");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TAEGORE_DIGGING_STOP = register("entity.taegore.digging_stop");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_TAEGORE_DROP_SEED = register("entity.taegore.drop_seed");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_BURRUKAI_AMBIENT = register("entity.burrukai.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_BURRUKAI_DEATH = register("entity.burrukai.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_BURRUKAI_HURT = register("entity.burrukai.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_BURRUKAI_STEP = register("entity.burrukai.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_BURRUKAI_RAM_IMPACT = register("entity.burrukai.ram_impact");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_KIRRID_AMBIENT = register("entity.kirrid.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_KIRRID_DEATH = register("entity.kirrid.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_KIRRID_HURT = register("entity.kirrid.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_KIRRID_STEP = register("entity.kirrid.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_KIRRID_JUMP = register("entity.kirrid.jump");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_KIRRID_RAM_IMPACT = register("entity.kirrid.ram_impact");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_AMBIENT = register("entity.moa.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_DEATH = register("entity.moa.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_HURT = register("entity.moa.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_SADDLE = register("entity.moa.saddle");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_STEP = register("entity.moa.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_FLAP = register("entity.moa.flap");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MOA_EGG = register("entity.moa.egg");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_PRISMALLARD_AMBIENT = register("entity.prismallard.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_PRISMALLARD_DEATH = register("entity.prismallard.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_PRISMALLARD_HURT = register("entity.prismallard.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_PRISMALLARD_STEP = register("entity.prismallard.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_PRISMALLARD_FLAP = register("entity.prismallard.flap");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_PRISMALLARD_EGG = register("entity.prismallard.egg");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SKYROOT_LIZARD_AMBIENT = register("entity.skyroot_lizard.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SKYROOT_LIZARD_DEATH = register("entity.skyroot_lizard.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SKYROOT_LIZARD_HURT = register("entity.skyroot_lizard.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SKYROOT_LIZARD_STEP = register("entity.skyroot_lizard.step");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AECHOR_PLANT_SHOOT = register("entity.aechor_plant.shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AECHOR_PLANT_DEATH = register("entity.aechor_plant.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_AECHOR_PLANT_HURT = register("entity.aechor_plant.hurt");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_CARRION_SPROUT_TRAP = register("entity.carrion_sprout.trap");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_CARRION_SPROUT_DEATH = register("entity.carrion_sprout.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_CARRION_SPROUT_HURT = register("entity.carrion_sprout.hurt");

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
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SWET_LEECH = register("entity.swet.leech");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SKEPHID_SHOOT = register("entity.skephid.shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SKEPHID_AMBIENT = register("entity.skephid.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SKEPHID_DEATH = register("entity.skephid.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SKEPHID_HURT = register("entity.skephid.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SKEPHID_STEP = register("entity.skephid.step");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_ARKENIUM_TALUTON_ATTACK = register("entity.arkenium_taluton.attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_ARKENIUM_TALUTON_AMBIENT = register("entity.arkenium_taluton.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_ARKENIUM_TALUTON_DEATH = register("entity.arkenium_taluton.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_ARKENIUM_TALUTON_HURT = register("entity.arkenium_taluton.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_ARKENIUM_TALUTON_STEP = register("entity.arkenium_taluton.step");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_GRAVITITE_TALUTON_SHOOT = register("entity.gravitite_taluton.shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_GRAVITITE_TALUTON_AMBIENT = register("entity.gravitite_taluton.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_GRAVITITE_TALUTON_DEATH = register("entity.gravitite_taluton.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_GRAVITITE_TALUTON_HURT = register("entity.gravitite_taluton.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_GRAVITITE_TALUTON_STEP = register("entity.gravitite_taluton.step");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MIMIC_ATTACK = register("entity.mimic.attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MIMIC_DEATH = register("entity.mimic.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MIMIC_HURT = register("entity.mimic.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_MIMIC_KILL = register("entity.mimic.kill");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_DETONATION_SENTRY_DEATH = register("entity.detonation_sentry.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_DETONATION_SENTRY_HURT = register("entity.detonation_sentry.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_DETONATION_SENTRY_JUMP = register("entity.detonation_sentry.jump");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_DETONATION_SENTRY_SQUISH = register("entity.detonation_sentry.squish");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_DETONATION_SENTRY_AMBIENT = register("entity.detonation_sentry.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_DETONATION_SENTRY_BEEP = register("entity.detonation_sentry.beep");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SENTRY_GOLEM_HURT = register("entity.sentry_golem.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SENTRY_GOLEM_DEATH = register("entity.sentry_golem.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SENTRY_GOLEM_SAY = register("entity.sentry_golem.say");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SENTRY_GOLEM_THROW_BOMB = register("entity.sentry_golem.throw_bomb");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SLIDER_AWAKEN = register("entity.slider.awaken");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SLIDER_AMBIENT = register("entity.slider.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SLIDER_COLLIDE = register("entity.slider.collide");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SLIDER_MOVE = register("entity.slider.move");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SLIDER_HURT = register("entity.slider.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SLIDER_DEATH = register("entity.slider.death");



    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_BLIGHTED_BURN = register("entity.blighted.burn");

    // Projectiles
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_PRISMALLARD_EGG_THROW = register("entity.prismallard_egg.throw");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_ARCTIC_SNOWBALL_THROW = register("entity.arctic_snowball.throw");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_ROCK_THROW = register("entity.rock.throw");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_SKYROOT_PINECONE_THROW = register("entity.skyroot_pinecone.throw");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_LASSO_THROW = register("entity.lasso.throw");

    // Miscellaneous
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_ELECTRIC_FIELD_CREATE = register("entity.electric_field.ambient");

    // UI
    public static final DeferredHolder<SoundEvent, SoundEvent> UI_TOAST_AETHER_GENERAL = register("ui.toast.aether_general");
    public static final DeferredHolder<SoundEvent, SoundEvent> UI_TOAST_AETHER_SLIDER = register("ui.toast.aether_slider");

    public static final DeferredHolder<SoundEvent, SoundEvent> UI_ARTISANS_BENCH_SELECT_RECIPE = register("ui.artisans_bench.select_recipe");
    public static final DeferredHolder<SoundEvent, SoundEvent> UI_ARTISANS_BENCH_TAKE_RESULT = register("ui.artisans_bench.take_result");

    // Music
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_AETHER = register("music.aether");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_AETHER_NIGHT = register("music.aether_night");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_AETHER_SUNRISE = register("music.aether_sunrise");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_AETHER_SUNSET = register("music.aether_sunset");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_AETHER_CAVES = register("music.aether_caves");

    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_MENU = register("music.menu");

    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_BOSS_SLIDER = register("music.boss.slider");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String location) {
        return SOUNDS.register(location, () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(AetherII.MODID, location)));
    }
}
