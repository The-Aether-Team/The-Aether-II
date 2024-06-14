package com.aetherteam.aetherii.item;

import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum AetherIIArmorMaterials implements ArmorMaterial { //todo sounds
    TAEGORE_HIDE("taegore_hide", 5, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 2);
        map.put(ArmorItem.Type.CHESTPLATE, 3);
        map.put(ArmorItem.Type.HELMET, 1);
    }), 15, () -> SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F, () -> Ingredient.of(AetherIITags.Items.TAEGORE_HIDE_REPAIRING)),
    BURRUKAI_PELT("burrukai_pelt", 15, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 4);
        map.put(ArmorItem.Type.CHESTPLATE, 5);
        map.put(ArmorItem.Type.HELMET, 2);
    }), 15, () -> SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F, () -> Ingredient.of(AetherIITags.Items.BURRUKAI_PELT_REPAIRING)),
    ZANITE("zanite", 15, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.LEGGINGS, 5);
        map.put(ArmorItem.Type.CHESTPLATE, 6);
        map.put(ArmorItem.Type.HELMET, 2);
    }), 9, () -> SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F, () -> Ingredient.of(AetherIITags.Items.ZANITE_REPAIRING)),
    ARKENIUM("arkenium", 33, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
    }), 10, () -> SoundEvents.ARMOR_EQUIP_GENERIC, 2.0F, () -> Ingredient.of(AetherIITags.Items.ARKENIUM_REPAIRING)),
    GRAVITITE("gravitite", 33, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
    }), 10, () -> SoundEvents.ARMOR_EQUIP_GENERIC, 2.0F, () -> Ingredient.of(AetherIITags.Items.GRAVITITE_REPAIRING));

    private static final EnumMap<ArmorItem.Type, Integer> DURABILITY_MAP = Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 13);
        map.put(ArmorItem.Type.LEGGINGS, 15);
        map.put(ArmorItem.Type.CHESTPLATE, 16);
        map.put(ArmorItem.Type.HELMET, 11);
    });
    private final String name;
    private final int maxDamageFactor;
    private final EnumMap<ArmorItem.Type, Integer> protectionAmountMap;
    private final int enchantability;
    private final Supplier<SoundEvent> soundEvent;
    private final float toughness;
    private final Supplier<Ingredient> repairMaterial;

    AetherIIArmorMaterials(String name, int maxDamageFactor, EnumMap<ArmorItem.Type, Integer> protectionAmountMap, int enchantability, Supplier<SoundEvent> soundEvent, float toughness, Supplier<Ingredient> repairMaterial) {
        this.name = name;
        this.maxDamageFactor = maxDamageFactor;
        this.protectionAmountMap = protectionAmountMap;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return DURABILITY_MAP.get(type) * this.maxDamageFactor;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return this.protectionAmountMap.get(type);
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.soundEvent.get();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
