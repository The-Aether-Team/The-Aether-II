package com.aetherteam.aetherii.item.components;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.Locale;

public enum EquipmentSlotGroup implements StringRepresentable {
    ANY,
    HAND,
    MAINHAND,
    OFFHAND,
    ARMOR,
    HEAD,
    CHEST,
    LEGS,
    FEET;

    public static final Codec<EquipmentSlotGroup> CODEC = StringRepresentable.fromEnum(EquipmentSlotGroup::values);

    public boolean test(EquipmentSlot slot) {
        return switch (this) {
            case ANY -> true;
            case HAND -> slot.getType() == EquipmentSlot.Type.HAND;
            case MAINHAND -> slot == EquipmentSlot.MAINHAND;
            case OFFHAND -> slot == EquipmentSlot.OFFHAND;
            case ARMOR -> slot.getType() == EquipmentSlot.Type.ARMOR;
            case HEAD -> slot == EquipmentSlot.HEAD;
            case CHEST -> slot == EquipmentSlot.CHEST;
            case LEGS -> slot == EquipmentSlot.LEGS;
            case FEET -> slot == EquipmentSlot.FEET;
        };
    }

    public boolean test(EquipmentSlotGroup group) {
        if (this == ANY || group == ANY || this == group) {
            return true;
        }
        return this == HAND && (group == MAINHAND || group == OFFHAND)
                || this == ARMOR && (group == HEAD || group == CHEST || group == LEGS || group == FEET);
    }

    public EquipmentSlot representativeSlot() {
        return switch (this) {
            case ANY, HAND, MAINHAND -> EquipmentSlot.MAINHAND;
            case OFFHAND -> EquipmentSlot.OFFHAND;
            case ARMOR, CHEST -> EquipmentSlot.CHEST;
            case HEAD -> EquipmentSlot.HEAD;
            case LEGS -> EquipmentSlot.LEGS;
            case FEET -> EquipmentSlot.FEET;
        };
    }

    public static EquipmentSlotGroup bySlot(EquipmentSlot slot) {
        return switch (slot) {
            case MAINHAND -> MAINHAND;
            case OFFHAND -> OFFHAND;
            case FEET -> FEET;
            case LEGS -> LEGS;
            case CHEST -> CHEST;
            case HEAD -> HEAD;
        };
    }

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
