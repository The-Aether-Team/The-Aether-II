package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.AetherII;
import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Rarity;

import java.util.List;

public final class DataComponents {
    public static final DataComponentType<Integer> MAX_DAMAGE = component("max_damage", Codec.INT);
    public static final DataComponentType<Tool> TOOL = component("tool", Tool.CODEC);
    public static final DataComponentType<ItemAttributeModifiers> ATTRIBUTE_MODIFIERS = component("attribute_modifiers", ItemAttributeModifiers.CODEC);
    public static final DataComponentType<Rarity> RARITY = component("rarity", Codec.STRING.xmap(Rarity::valueOf, Rarity::name));
    public static final DataComponentType<ChargedProjectiles> CHARGED_PROJECTILES = component("charged_projectiles", Codec.unit(ChargedProjectiles.EMPTY));
    public static final DataComponentType<Object> BLOCKS_ATTACKS = unit("blocks_attacks");
    public static final DataComponentType<SoundEvent> BREAK_SOUND = unit("break_sound");
    public static final DataComponentType<Component> CUSTOM_NAME = component("custom_name", com.aetherteam.aetherii.util.ComponentSerialization.CODEC);
    public static final DataComponentType<Component> ITEM_NAME = CUSTOM_NAME;
    public static final DataComponentType<Integer> DAMAGE = component("damage", Codec.INT);
    public static final DataComponentType<Object> LORE = unit("lore");
    public static final DataComponentType<ResourceLocation> ITEM_MODEL = component("item_model", ResourceLocation.CODEC);
    public static final DataComponentType<Boolean> ENCHANTMENT_GLINT_OVERRIDE = component("enchantment_glint_override", Codec.BOOL);
    public static final DataComponentType<Object> BUNDLE_CONTENTS = unit("bundle_contents");
    public static final DataComponentType<Consumable> CONSUMABLE = component("consumable", Consumable.CODEC);

    private DataComponents() {
    }

    private static <T> DataComponentType<T> component(String name, Codec<T> codec) {
        return new DataComponentType<>(new ResourceLocation(AetherII.MODID, name), codec);
    }

    @SuppressWarnings("unchecked")
    private static <T> DataComponentType<T> unit(String name) {
        return new DataComponentType<>(new ResourceLocation(AetherII.MODID, name), (Codec<T>) Codec.unit(new Object()));
    }
}
