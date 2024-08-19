package com.aetherteam.aetherii.item.equipment.armor;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class AetherIIArmorMaterials { //todo sounds
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, AetherII.MODID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> TAEGORE_HIDE = ARMOR_MATERIALS.register("taegore_hide", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 2);
        map.put(ArmorItem.Type.CHESTPLATE, 3);
        map.put(ArmorItem.Type.HELMET, 1);
    }), 15, SoundEvents.ARMOR_EQUIP_GENERIC, () -> Ingredient.of(AetherIITags.Items.TAEGORE_HIDE_REPAIRING), List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "taegore_hide")), new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "taegore_hide"), "_dyed", true)), 0.0F, 0.0F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> BURRUKAI_PELT = ARMOR_MATERIALS.register("burrukai_pelt", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 4);
        map.put(ArmorItem.Type.CHESTPLATE, 5);
        map.put(ArmorItem.Type.HELMET, 2);
    }), 15, SoundEvents.ARMOR_EQUIP_GENERIC, () -> Ingredient.of(AetherIITags.Items.BURRUKAI_PELT_REPAIRING), List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "burrukai_pelt")), new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "burrukai_pelt"), "_dyed", true)), 0.0F, 0.0F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ZANITE = ARMOR_MATERIALS.register("zanite", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.LEGGINGS, 5);
        map.put(ArmorItem.Type.CHESTPLATE, 6);
        map.put(ArmorItem.Type.HELMET, 2);
    }), 9, SoundEvents.ARMOR_EQUIP_GENERIC, () -> Ingredient.of(AetherIITags.Items.ZANITE_REPAIRING), List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "zanite"))), 0.0F, 0.0F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ARKENIUM = ARMOR_MATERIALS.register("arkenium", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.LEGGINGS, 5);
        map.put(ArmorItem.Type.CHESTPLATE, 6);
        map.put(ArmorItem.Type.HELMET, 2);
    }), 10, SoundEvents.ARMOR_EQUIP_GENERIC, () -> Ingredient.of(AetherIITags.Items.ARKENIUM_REPAIRING), List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "arkenium"))), 1.0F, 0.0F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> GRAVITITE = ARMOR_MATERIALS.register("gravitite", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
    }), 10, SoundEvents.ARMOR_EQUIP_GENERIC, () -> Ingredient.of(AetherIITags.Items.GRAVITITE_REPAIRING), List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "gravitite"))), 2.0F, 0.0F));
}
