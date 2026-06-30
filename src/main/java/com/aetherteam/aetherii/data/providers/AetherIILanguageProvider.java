package com.aetherteam.aetherii.data.providers;

import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.nitrogen.data.providers.NitrogenLanguageProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public abstract class AetherIILanguageProvider extends NitrogenLanguageProvider {
    public AetherIILanguageProvider(PackOutput output, String id) {
        super(output, id);
    }

    public void addKeratinColor(Moa.KeratinColor color, String name) {
        this.addItemTooltip("moa_egg.keratin_color." + color.getSerializedName(), name);
    }

    public void addEyeColor(Moa.EyeColor color, String name) {
        this.addItemTooltip("moa_egg.eye_color." + color.getSerializedName(), name);
    }

    public void addFeatherColor(Moa.FeatherColor color, String name) {
        this.addItemTooltip("moa_egg.feather_color." + color.getSerializedName(), name);
    }

    public void addFeatherShape(Moa.FeatherShape shape, String name) {
        this.addItemTooltip("moa_egg.feather_shape." + shape.getSerializedName(), name);
    }

    public void addEffectDarts(Supplier<? extends Item> key, String effect, String name) {
        this.add(key.get().getDescriptionId() + ".effect." + effect, name);
    }

    public void addItem(RegistryObject<? extends Item> key, String name) {
        this.add(key.getId().toLanguageKey("item"), name);
    }

    public void addDamageTypeTooltip(String path, String name) {
        this.addItemTooltip("damage." + path, name);
    }

    public void addItemTooltip(String path, String name) {
        this.addTooltip("item." + path, name);
    }

    public void addTooltip(String path, String name) {
        this.add(this.id + ".tooltip." + path, name);
    }

    public void addConfig(String path, String name) {
        this.add(this.id + ".configuration." + path, name);
    }

    public void addServerConfig(String path, String name) {
        this.add("config." + this.id + ".server." + path, name);
    }

    public void addCommonConfig(String path, String name) {
        this.add("config." + this.id + ".common." + path, name);
    }

    public void addClientConfig(String path, String name) {
        this.add("config." + this.id + ".client." + path, name);
    }

    public void addAccessorySlot(String path, String name) {
        this.add("accessories.slot." + this.id + "." + path, name);
    }

    public void addAttribute(Attribute attribute, String name) {
        this.add(attribute.getDescriptionId(), name);
    }

    public void addMusic(String songName, String name) {
        this.add(this.id + ".music." + songName, name);
    }

    public void addJukeboxSong(String songName, String name) {
        this.add("jukebox_song." + this.id + "." + songName, name);
    }

    public void addBestiaryName(EntityType<?> entityType, String description) {
        this.add(this.id + ".guidebook_bestiary.name." + entityType.getDescriptionId(), description);
    }

    public void addBestiarySlotName(EntityType<?> entityType, String description) {
        this.add(this.id + ".guidebook_bestiary.slot_name." + entityType.getDescriptionId(), description);
    }

    public void addBestiarySlotSubtitle(EntityType<?> entityType, String description) {
        this.add(this.id + ".guidebook_bestiary.slot_subtitle." + entityType.getDescriptionId(), description);
    }

    public void addBestiaryDescription(EntityType<?> entityType, String description) {
        this.add(this.id + ".guidebook_bestiary.description." + entityType.getDescriptionId(), description);
    }

    public void addEffectsDescription(MobEffect effect, String description) {
        this.add(this.id + ".guidebook_effects.description." + effect.getDescriptionId(), description);
    }
}
