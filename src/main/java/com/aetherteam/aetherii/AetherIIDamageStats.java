package com.aetherteam.aetherii;

import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.List;

public class AetherIIDamageStats {
    public static final List<ItemAttributeModifiers.Entry> SKYROOT_SHORTSWORD = List.of(slashDamageModifer(3.0));
    public static final List<ItemAttributeModifiers.Entry> SKYROOT_HAMMER = List.of(impactDamageModifer(3.0));
    public static final List<ItemAttributeModifiers.Entry> SKYROOT_SPEAR = List.of(pierceDamageModifer(3.0));
    public static final List<ItemAttributeModifiers.Entry> HOLYSTONE_SHORTSWORD = List.of(slashDamageModifer(4.0));
    public static final List<ItemAttributeModifiers.Entry> HOLYSTONE_HAMMER = List.of(impactDamageModifer(4.0));
    public static final List<ItemAttributeModifiers.Entry> HOLYSTONE_SPEAR = List.of(pierceDamageModifer(4.0));
    public static final List<ItemAttributeModifiers.Entry> ZANITE_SHORTSWORD = List.of(slashDamageModifer(5.0));
    public static final List<ItemAttributeModifiers.Entry> ZANITE_HAMMER = List.of(impactDamageModifer(5.0));
    public static final List<ItemAttributeModifiers.Entry> ZANITE_SPEAR = List.of(pierceDamageModifer(5.0));
    public static final List<ItemAttributeModifiers.Entry> ARKENIUM_SHORTSWORD = List.of(slashDamageModifer(5.0));
    public static final List<ItemAttributeModifiers.Entry> ARKENIUM_HAMMER = List.of(impactDamageModifer(5.0));
    public static final List<ItemAttributeModifiers.Entry> ARKENIUM_SPEAR = List.of(pierceDamageModifer(5.0));
    public static final List<ItemAttributeModifiers.Entry> GRAVITITE_SHORTSWORD = List.of(slashDamageModifer(6.0));
    public static final List<ItemAttributeModifiers.Entry> GRAVITITE_HAMMER = List.of(impactDamageModifer(6.0));
    public static final List<ItemAttributeModifiers.Entry> GRAVITITE_SPEAR = List.of(pierceDamageModifer(6.0));

    public static final AttributeSupplier.Builder AERBUNNY = new AttributeSupplier.Builder()
            .add(AetherIIAttributes.SLASH_RESISTANCE, 0.0)
            .add(AetherIIAttributes.IMPACT_RESISTANCE, 2.0)
            .add(AetherIIAttributes.PIERCE_RESISTANCE, -2.0);
    public static final AttributeSupplier.Builder HIGHFIELDS_TAEGORE = new AttributeSupplier.Builder()
            .add(AetherIIAttributes.SLASH_RESISTANCE, -2.0)
            .add(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .add(AetherIIAttributes.PIERCE_RESISTANCE, 0.0);
    public static final AttributeSupplier.Builder MAGNETIC_TAEGORE = new AttributeSupplier.Builder()
            .add(AetherIIAttributes.SLASH_RESISTANCE, -2.0)
            .add(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .add(AetherIIAttributes.PIERCE_RESISTANCE, 0.0);
    public static final AttributeSupplier.Builder ARCTIC_TAEGORE = new AttributeSupplier.Builder()
            .add(AetherIIAttributes.SLASH_RESISTANCE, -2.0)
            .add(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .add(AetherIIAttributes.PIERCE_RESISTANCE, 0.0);
    public static final AttributeSupplier.Builder HIGHFIELDS_BURRUKAI = new AttributeSupplier.Builder()
            .add(AetherIIAttributes.SLASH_RESISTANCE, 2.0)
            .add(AetherIIAttributes.IMPACT_RESISTANCE, -2.0)
            .add(AetherIIAttributes.PIERCE_RESISTANCE, 0.0);
    public static final AttributeSupplier.Builder MAGNETIC_BURRUKAI = new AttributeSupplier.Builder()
            .add(AetherIIAttributes.SLASH_RESISTANCE, 2.0)
            .add(AetherIIAttributes.IMPACT_RESISTANCE, -2.0)
            .add(AetherIIAttributes.PIERCE_RESISTANCE, 0.0);
    public static final AttributeSupplier.Builder ARCTIC_BURRUKAI = new AttributeSupplier.Builder()
            .add(AetherIIAttributes.SLASH_RESISTANCE, 2.0)
            .add(AetherIIAttributes.IMPACT_RESISTANCE, -2.0)
            .add(AetherIIAttributes.PIERCE_RESISTANCE, 0.0);
    public static final AttributeSupplier.Builder HIGHFIELDS_KIRRID = new AttributeSupplier.Builder()
            .add(AetherIIAttributes.SLASH_RESISTANCE, 0.0)
            .add(AetherIIAttributes.IMPACT_RESISTANCE, 2.0)
            .add(AetherIIAttributes.PIERCE_RESISTANCE, -2.0);
    public static final AttributeSupplier.Builder MAGNETIC_KIRRID = new AttributeSupplier.Builder()
            .add(AetherIIAttributes.SLASH_RESISTANCE, 0.0)
            .add(AetherIIAttributes.IMPACT_RESISTANCE, 2.0)
            .add(AetherIIAttributes.PIERCE_RESISTANCE, -2.0);
    public static final AttributeSupplier.Builder ARCTIC_KIRRID = new AttributeSupplier.Builder()
            .add(AetherIIAttributes.SLASH_RESISTANCE, 0.0)
            .add(AetherIIAttributes.IMPACT_RESISTANCE, 2.0)
            .add(AetherIIAttributes.PIERCE_RESISTANCE, -2.0);
    public static final AttributeSupplier.Builder MOA = new AttributeSupplier.Builder()
            .add(AetherIIAttributes.SLASH_RESISTANCE, 2.0)
            .add(AetherIIAttributes.IMPACT_RESISTANCE, 2.0)
            .add(AetherIIAttributes.PIERCE_RESISTANCE, -2.0);
    public static final AttributeSupplier.Builder AECHOR_PLANT = new AttributeSupplier.Builder()
            .add(AetherIIAttributes.SLASH_RESISTANCE, -2.0)
            .add(AetherIIAttributes.IMPACT_RESISTANCE, 2.0)
            .add(AetherIIAttributes.PIERCE_RESISTANCE, 0.0);
    public static final AttributeSupplier.Builder ZEPHYR = new AttributeSupplier.Builder()
            .add(AetherIIAttributes.SLASH_RESISTANCE, -2.0)
            .add(AetherIIAttributes.IMPACT_RESISTANCE, 2.0)
            .add(AetherIIAttributes.PIERCE_RESISTANCE, 0.0);
    public static final AttributeSupplier.Builder TEMPEST = new AttributeSupplier.Builder()
            .add(AetherIIAttributes.SLASH_RESISTANCE, 2.0)
            .add(AetherIIAttributes.IMPACT_RESISTANCE, 2.0)
            .add(AetherIIAttributes.PIERCE_RESISTANCE, -2.0);
    public static final AttributeSupplier.Builder COCKATRICE = new AttributeSupplier.Builder()
            .add(AetherIIAttributes.SLASH_RESISTANCE, -2.0)
            .add(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .add(AetherIIAttributes.PIERCE_RESISTANCE, 2.0);
    public static final AttributeSupplier.Builder SWET = new AttributeSupplier.Builder()
            .add(AetherIIAttributes.SLASH_RESISTANCE, 2.0)
            .add(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .add(AetherIIAttributes.PIERCE_RESISTANCE, -2.0);

    private static ItemAttributeModifiers.Entry slashDamageModifer(double amount) {
        return new ItemAttributeModifiers.Entry(AetherIIAttributes.SLASH_DAMAGE, new AttributeModifier(AetherIIItems.BASE_SLASH_DAMAGE_ID, amount, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
    }

    private static ItemAttributeModifiers.Entry impactDamageModifer(double amount) {
        return new ItemAttributeModifiers.Entry(AetherIIAttributes.IMPACT_DAMAGE, new AttributeModifier(AetherIIItems.BASE_IMPACT_DAMAGE_ID, amount, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
    }

    private static ItemAttributeModifiers.Entry pierceDamageModifer(double amount) {
        return new ItemAttributeModifiers.Entry(AetherIIAttributes.PIERCE_DAMAGE, new AttributeModifier(AetherIIItems.BASE_PIERCE_DAMAGE_ID, amount, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
    }

    public static ItemAttributeModifiers merge(ItemAttributeModifiers modifiers, List<ItemAttributeModifiers.Entry> add) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        for (ItemAttributeModifiers.Entry entry : add) {
            builder = builder.add(entry.attribute(), entry.modifier(), entry.slot());
        }
        for (ItemAttributeModifiers.Entry entry : modifiers.modifiers()) {
            builder = builder.add(entry.attribute(), entry.modifier(), entry.slot());
        }
        return builder.build();
    }

    public static AttributeSupplier.Builder merge(AttributeSupplier.Builder builder, AttributeSupplier.Builder add) {
        builder.combine(add);
        return builder;
    }
}
