package com.aetherteam.aetherii;

import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.List;

public class AetherIIStats {
    public static final List<ItemAttributeModifiers.Entry> SKYROOT_SHORTSWORD = List.of(slashDamageModifer(3.0));
    public static final List<ItemAttributeModifiers.Entry> SKYROOT_HAMMER = List.of(impactDamageModifer(3.0));
    public static final List<ItemAttributeModifiers.Entry> SKYROOT_SPEAR = List.of(pierceDamageModifer(3.0));
    public static final List<ItemAttributeModifiers.Entry> SKYROOT_CROSSBOW = List.of(rangedPierceDamageModifer(2.0));
    public static final List<ItemAttributeModifiers.Entry> HOLYSTONE_SHORTSWORD = List.of(slashDamageModifer(4.0));
    public static final List<ItemAttributeModifiers.Entry> HOLYSTONE_HAMMER = List.of(impactDamageModifer(4.0));
    public static final List<ItemAttributeModifiers.Entry> HOLYSTONE_SPEAR = List.of(pierceDamageModifer(4.0));
    public static final List<ItemAttributeModifiers.Entry> HOLYSTONE_CROSSBOW = List.of(rangedPierceDamageModifer(3.0));
    public static final List<ItemAttributeModifiers.Entry> ZANITE_SHORTSWORD = List.of(slashDamageModifer(5.0));
    public static final List<ItemAttributeModifiers.Entry> ZANITE_HAMMER = List.of(impactDamageModifer(5.0));
    public static final List<ItemAttributeModifiers.Entry> ZANITE_SPEAR = List.of(pierceDamageModifer(5.0));
    public static final List<ItemAttributeModifiers.Entry> ZANITE_CROSSBOW = List.of(rangedPierceDamageModifer(4.0));
    public static final List<ItemAttributeModifiers.Entry> ARKENIUM_SHORTSWORD = List.of(slashDamageModifer(5.0));
    public static final List<ItemAttributeModifiers.Entry> ARKENIUM_HAMMER = List.of(impactDamageModifer(5.0));
    public static final List<ItemAttributeModifiers.Entry> ARKENIUM_SPEAR = List.of(pierceDamageModifer(5.0));
    public static final List<ItemAttributeModifiers.Entry> ARKENIUM_CROSSBOW = List.of(pierceDamageModifer(2.0), rangedPierceDamageModifer(4.0));
    public static final List<ItemAttributeModifiers.Entry> GRAVITITE_SHORTSWORD = List.of(slashDamageModifer(6.0));
    public static final List<ItemAttributeModifiers.Entry> GRAVITITE_HAMMER = List.of(impactDamageModifer(6.0));
    public static final List<ItemAttributeModifiers.Entry> GRAVITITE_SPEAR = List.of(pierceDamageModifer(6.0));
    public static final List<ItemAttributeModifiers.Entry> GRAVITITE_CROSSBOW = List.of(rangedPierceDamageModifer(5.0));

    public static final ImmutableMap<Holder<Attribute>, Double> FLYING_COW = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 10.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> SHEEPUFF = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 8.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> PHYG = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 10.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> AERBUNNY = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 6.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 0.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 2.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, -2.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> HIGHFIELDS_TAEGORE = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 14.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, -2.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> MAGNETIC_TAEGORE = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 14.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, -2.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> ARCTIC_TAEGORE = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 14.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, -2.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> HIGHFIELDS_BURRUKAI = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 18.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 2.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, -2.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> MAGNETIC_BURRUKAI = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 18.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 2.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, -2.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> ARCTIC_BURRUKAI = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 18.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 2.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, -2.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> HIGHFIELDS_KIRRID = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 10.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 0.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 2.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, -2.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> MAGNETIC_KIRRID = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 10.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 0.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 2.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, -2.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> ARCTIC_KIRRID = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 10.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 0.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 2.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, -2.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> MOA = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 35.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 2.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 2.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, -2.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> SKYROOT_LIZARD = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 4.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> AECHOR_PLANT = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 15.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, -2.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 2.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> CARRION_SPROUT = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 8.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, -2.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 2.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> ZEPHYR = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 5.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, -2.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 2.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> TEMPEST = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 30.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 2.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 2.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, -2.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> COCKATRICE = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 25.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, -2.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 2.0)
            .put(AetherIIAttributes.VENOM_EFFECT_RESISTANCE, 1.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> SWET = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 12.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 2.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, -2.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> SKEPHID = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 10.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, -2.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, -2.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, -2.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> ARKENIUM_TALUTON = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 30.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 2.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, -2.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 2.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> GRAVITITE_TALUTON = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 20.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 2.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 2.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, -2.0)
            .build();

    private static ItemAttributeModifiers.Entry slashDamageModifer(double amount) {
        return new ItemAttributeModifiers.Entry(AetherIIAttributes.SLASH_DAMAGE, new AttributeModifier(AetherIIItems.BASE_SLASH_DAMAGE_ID, amount, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
    }

    private static ItemAttributeModifiers.Entry rangedSlashDamageModifer(double amount) {
        return new ItemAttributeModifiers.Entry(AetherIIAttributes.SLASH_RANGED_DAMAGE, new AttributeModifier(AetherIIItems.BASE_SLASH_RANGED_DAMAGE_ID, amount, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
    }

    private static ItemAttributeModifiers.Entry impactDamageModifer(double amount) {
        return new ItemAttributeModifiers.Entry(AetherIIAttributes.IMPACT_DAMAGE, new AttributeModifier(AetherIIItems.BASE_IMPACT_DAMAGE_ID, amount, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
    }

    private static ItemAttributeModifiers.Entry rangedImpactDamageModifer(double amount) {
        return new ItemAttributeModifiers.Entry(AetherIIAttributes.IMPACT_RANGED_DAMAGE, new AttributeModifier(AetherIIItems.BASE_IMPACT_RANGED_DAMAGE_ID, amount, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
    }

    private static ItemAttributeModifiers.Entry pierceDamageModifer(double amount) {
        return new ItemAttributeModifiers.Entry(AetherIIAttributes.PIERCE_DAMAGE, new AttributeModifier(AetherIIItems.BASE_PIERCE_DAMAGE_ID, amount, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
    }

    private static ItemAttributeModifiers.Entry rangedPierceDamageModifer(double amount) {
        return new ItemAttributeModifiers.Entry(AetherIIAttributes.PIERCE_RANGED_DAMAGE, new AttributeModifier(AetherIIItems.BASE_PIERCE_RANGED_DAMAGE_ID, amount, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
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

    public static AttributeSupplier.Builder merge(AttributeSupplier.Builder builder, ImmutableMap<Holder<Attribute>, Double> add) {
        add.forEach(builder::add);
        return builder;
    }
}