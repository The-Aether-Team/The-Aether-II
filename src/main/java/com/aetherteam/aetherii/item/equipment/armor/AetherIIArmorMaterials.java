package com.aetherteam.aetherii.item.equipment.armor;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.data.resources.registries.AetherIIEquipmentAssets;
import net.minecraft.Util;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.EnumMap;

public class AetherIIArmorMaterials { //todo sounds
    public static final ArmorMaterial BEAST_PELT = new ArmorMaterial(5, Util.make(new EnumMap<>(ArmorType.class), map -> {
        map.put(ArmorType.BOOTS, 1);
        map.put(ArmorType.LEGGINGS, 2);
        map.put(ArmorType.CHESTPLATE, 3);
        map.put(ArmorType.HELMET, 1);
    }), 15, AetherIISoundEvents.ITEM_ARMOR_EQUIP_BEAST_PELT, 0.0F, 0.0F, AetherIITags.Items.BEAST_PELT_REPAIRING, AetherIIEquipmentAssets.BEAST_PELT);
    public static final ArmorMaterial BURRUKAI_PLATE = new ArmorMaterial(5, Util.make(new EnumMap<>(ArmorType.class), map -> {
        map.put(ArmorType.BOOTS, 1);
        map.put(ArmorType.LEGGINGS, 4);
        map.put(ArmorType.CHESTPLATE, 5);
        map.put(ArmorType.HELMET, 2);
    }), 15, AetherIISoundEvents.ITEM_ARMOR_EQUIP_BURRUKAI_PLATE, 0.0F, 0.0F, AetherIITags.Items.BURRUKAI_PLATE_REPAIRING, AetherIIEquipmentAssets.BURRUKAI_PLATE);
    public static final ArmorMaterial ZANITE = new ArmorMaterial(15, Util.make(new EnumMap<>(ArmorType.class), map -> {
        map.put(ArmorType.BOOTS, 2);
        map.put(ArmorType.LEGGINGS, 5);
        map.put(ArmorType.CHESTPLATE, 6);
        map.put(ArmorType.HELMET, 2);
    }), 9, AetherIISoundEvents.ITEM_ARMOR_EQUIP_ZANITE, 0.0F, 0.0F, AetherIITags.Items.ZANITE_REPAIRING, AetherIIEquipmentAssets.ZANITE);
    public static final ArmorMaterial ARKENIUM = new ArmorMaterial(15, Util.make(new EnumMap<>(ArmorType.class), map -> {
        map.put(ArmorType.BOOTS, 2);
        map.put(ArmorType.LEGGINGS, 5);
        map.put(ArmorType.CHESTPLATE, 6);
        map.put(ArmorType.HELMET, 2);
    }), 10, AetherIISoundEvents.ITEM_ARMOR_EQUIP_ARKENIUM, 1.0F, 0.0F, AetherIITags.Items.ARKENIUM_REPAIRING, AetherIIEquipmentAssets.ARKENIUM);
    public static final ArmorMaterial GRAVITITE = new ArmorMaterial(33, Util.make(new EnumMap<>(ArmorType.class), map -> {
        map.put(ArmorType.BOOTS, 3);
        map.put(ArmorType.LEGGINGS, 6);
        map.put(ArmorType.CHESTPLATE, 8);
        map.put(ArmorType.HELMET, 3);
    }), 10, AetherIISoundEvents.ITEM_ARMOR_EQUIP_GRAVITITE, 2.0F, 0.0F, AetherIITags.Items.GRAVITITE_REPAIRING, AetherIIEquipmentAssets.GRAVITITE);

    public static final ArmorMaterial NEPTUNE = new ArmorMaterial(15, Util.make(new EnumMap<>(ArmorType.class), map -> {
        map.put(ArmorType.BOOTS, 2);
        map.put(ArmorType.LEGGINGS, 5);
        map.put(ArmorType.CHESTPLATE, 6);
        map.put(ArmorType.HELMET, 2);
    }), 10, AetherIISoundEvents.ITEM_ARMOR_EQUIP_NEPTUNE, 1.0F, 0.0F, AetherIITags.Items.NEPTUNE_REPAIRING, AetherIIEquipmentAssets.NEPTUNE);
}
