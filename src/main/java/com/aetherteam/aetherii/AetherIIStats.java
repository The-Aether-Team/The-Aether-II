package com.aetherteam.aetherii;

import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.List;

public class AetherIIStats {
    public static final List<ItemAttributeModifiers.Entry> SKYROOT_SHORTSWORD = List.of(slashDamageModifer(3.0));
    public static final List<ItemAttributeModifiers.Entry> SKYROOT_HAMMER = List.of(impactDamageModifer(3.0));
    public static final List<ItemAttributeModifiers.Entry> SKYROOT_PIKE = List.of(pierceDamageModifer(3.0));
    public static final List<ItemAttributeModifiers.Entry> SKYROOT_CROSSBOW = List.of(rangedPierceDamageModifer(2.0));
    public static final List<ItemAttributeModifiers.Entry> HOLYSTONE_SHORTSWORD = List.of(slashDamageModifer(4.0));
    public static final List<ItemAttributeModifiers.Entry> HOLYSTONE_HAMMER = List.of(impactDamageModifer(4.0));
    public static final List<ItemAttributeModifiers.Entry> HOLYSTONE_PIKE = List.of(pierceDamageModifer(4.0));
    public static final List<ItemAttributeModifiers.Entry> HOLYSTONE_CROSSBOW = List.of(rangedPierceDamageModifer(3.0));
    public static final List<ItemAttributeModifiers.Entry> ZANITE_SHORTSWORD = List.of(slashDamageModifer(5.0));
    public static final List<ItemAttributeModifiers.Entry> ZANITE_HAMMER = List.of(impactDamageModifer(5.0));
    public static final List<ItemAttributeModifiers.Entry> ZANITE_PIKE = List.of(pierceDamageModifer(5.0));
    public static final List<ItemAttributeModifiers.Entry> ZANITE_CROSSBOW = List.of(rangedPierceDamageModifer(4.0));
    public static final List<ItemAttributeModifiers.Entry> ARKENIUM_SHORTSWORD = List.of(slashDamageModifer(5.0));
    public static final List<ItemAttributeModifiers.Entry> ARKENIUM_HAMMER = List.of(impactDamageModifer(5.0));
    public static final List<ItemAttributeModifiers.Entry> ARKENIUM_PIKE = List.of(pierceDamageModifer(5.0));
    public static final List<ItemAttributeModifiers.Entry> ARKENIUM_CROSSBOW = List.of(pierceDamageModifer(2.0), rangedPierceDamageModifer(4.0));
    public static final List<ItemAttributeModifiers.Entry> GRAVITITE_SHORTSWORD = List.of(slashDamageModifer(6.0));
    public static final List<ItemAttributeModifiers.Entry> GRAVITITE_HAMMER = List.of(impactDamageModifer(6.0));
    public static final List<ItemAttributeModifiers.Entry> GRAVITITE_PIKE = List.of(pierceDamageModifer(6.0));
    public static final List<ItemAttributeModifiers.Entry> GRAVITITE_CROSSBOW = List.of(rangedPierceDamageModifer(5.0));
    public static final List<ItemAttributeModifiers.Entry> HAMMER_OF_DEMOLITION = List.of(impactDamageModifer(5.0));

    public static final ItemAttributeModifiers.Entry CHARM_MINING_EFFICIENCY_BONUS = new ItemAttributeModifiers.Entry(
            Attributes.MINING_EFFICIENCY,
            new AttributeModifier(Identifier.fromNamespaceAndPath(AetherII.MODID, "charm.mining_efficiency"), 2.0, AttributeModifier.Operation.ADD_VALUE),
            EquipmentSlotGroup.MAINHAND);
    public static final ItemAttributeModifiers.Entry CHARM_BLOCK_INTERACTION_RANGE_BONUS = new ItemAttributeModifiers.Entry(
            Attributes.BLOCK_INTERACTION_RANGE,
            new AttributeModifier(Identifier.fromNamespaceAndPath(AetherII.MODID, "charm.block_interaction_range"), 1.0, AttributeModifier.Operation.ADD_VALUE),
            EquipmentSlotGroup.MAINHAND);

    public static final ItemAttributeModifiers.Entry CHARM_DAMAGE_BONUS = new ItemAttributeModifiers.Entry(
            Attributes.ATTACK_DAMAGE,
            new AttributeModifier(Identifier.fromNamespaceAndPath(AetherII.MODID, "charm.attack_damage"), 1.0, AttributeModifier.Operation.ADD_VALUE),
            EquipmentSlotGroup.MAINHAND);
    public static final ItemAttributeModifiers.Entry CHARM_ATTACK_SPEED_BONUS = new ItemAttributeModifiers.Entry(
            Attributes.ATTACK_SPEED,
            new AttributeModifier(Identifier.fromNamespaceAndPath(AetherII.MODID, "charm.attack_speed"), 0.15, AttributeModifier.Operation.ADD_VALUE),
            EquipmentSlotGroup.MAINHAND);
    public static final ItemAttributeModifiers.Entry CHARM_ATTACK_KNOCKBACK_BONUS = new ItemAttributeModifiers.Entry(
            Attributes.ATTACK_KNOCKBACK,
            new AttributeModifier(Identifier.fromNamespaceAndPath(AetherII.MODID, "charm.attack_knockback"), 0.5, AttributeModifier.Operation.ADD_VALUE),
            EquipmentSlotGroup.MAINHAND);

    public static final ItemAttributeModifiers.Entry CHARM_MAX_HEALTH_BONUS = new ItemAttributeModifiers.Entry(
            Attributes.MAX_HEALTH,
            new AttributeModifier(Identifier.fromNamespaceAndPath(AetherII.MODID, "charm.max_health"), 2.0, AttributeModifier.Operation.ADD_VALUE),
            EquipmentSlotGroup.ARMOR);
    public static final ItemAttributeModifiers.Entry CHARM_ARMOR_BONUS = new ItemAttributeModifiers.Entry(
            Attributes.ARMOR,
            new AttributeModifier(Identifier.fromNamespaceAndPath(AetherII.MODID, "charm.armor"), 2.0, AttributeModifier.Operation.ADD_VALUE),
            EquipmentSlotGroup.ARMOR);
    public static final ItemAttributeModifiers.Entry CHARM_ARMOR_TOUGHNESS_BONUS = new ItemAttributeModifiers.Entry(
            Attributes.ARMOR_TOUGHNESS,
            new AttributeModifier(Identifier.fromNamespaceAndPath(AetherII.MODID, "charm.armor_toughness"), 1.0, AttributeModifier.Operation.ADD_VALUE),
            EquipmentSlotGroup.ARMOR);
    public static final ItemAttributeModifiers.Entry CHARM_KNOCKBACK_RESISTANCE_BONUS = new ItemAttributeModifiers.Entry(
            Attributes.KNOCKBACK_RESISTANCE,
            new AttributeModifier(Identifier.fromNamespaceAndPath(AetherII.MODID, "charm.knockback_resistance"), 0.1, AttributeModifier.Operation.ADD_VALUE),
            EquipmentSlotGroup.ARMOR);
    public static final ItemAttributeModifiers.Entry CHARM_MOVEMENT_SPEED_BONUS = new ItemAttributeModifiers.Entry(
            Attributes.MOVEMENT_SPEED,
            new AttributeModifier(Identifier.fromNamespaceAndPath(AetherII.MODID, "charm.movement_speed"), 0.0025, AttributeModifier.Operation.ADD_VALUE),
            EquipmentSlotGroup.ARMOR);

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
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> AERWHALE = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 20.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> HIGHFIELDS_TAEGORE = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 18.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, -1.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> MAGNETIC_TAEGORE = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 18.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, -1.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> ARCTIC_TAEGORE = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 18.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, -1.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> HIGHFIELDS_BURRUKAI = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 26.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 0.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, -1.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> MAGNETIC_BURRUKAI = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 26.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 0.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, -1.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> ARCTIC_BURRUKAI = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 26.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 0.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, -1.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> HIGHFIELDS_KIRRID = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 16.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 0.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, -1.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> MAGNETIC_KIRRID = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 16.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 0.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, -1.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> ARCTIC_KIRRID = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 16.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 0.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, -1.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> MOA = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 35.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, -1.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .put(AetherIIAttributes.TOXIN_EFFECT_RESISTANCE, 1.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> PRISMALLARD = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 4.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 0.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> SKYROOT_LIZARD = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 4.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> GLITTERWING = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 2.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> SHROUDWING = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 2.0)
            .build();

    public static final ImmutableMap<Holder<Attribute>, Double> AECHOR_PLANT = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 10.0)
            .put(AetherIIAttributes.TOXIN_EFFECT_RESISTANCE, 1.0)
            .build();

    public static final ImmutableMap<Holder<Attribute>, Double> CARRION_SPROUT = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 8.0)
            .build();

    public static final ImmutableMap<Holder<Attribute>, Double> ZEPHYR = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 12.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 0.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, -1.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> TEMPEST = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 28.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 0.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, -1.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> COCKATRICE = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 32.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, -1.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .put(AetherIIAttributes.VENOM_EFFECT_RESISTANCE, 1.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> SWET = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 16.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 0.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, -1.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> SKEPHID = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 16.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 1.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, -1.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> ARKENIUM_TALUTON = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 34.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 1.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, -1.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> GRAVITITE_TALUTON = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 32.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 0.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 1.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, -1.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> MIMIC = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 32.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 1.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, -1.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> DETONATION_SENTRY = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 14.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 0.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, -1.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 1.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> SENTRY_GOLEM = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 35.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 1.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, -1.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> SLIDER = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 350.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, 0.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 0.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> BLADESHROOM_HUNTER = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 18.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, -1.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 2.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();
    public static final ImmutableMap<Holder<Attribute>, Double> SAWCAP_SLINGER = ImmutableMap.<Holder<Attribute>, Double>builder()
            .put(Attributes.MAX_HEALTH, 18.0)
            .put(AetherIIAttributes.SLASH_RESISTANCE, -1.0)
            .put(AetherIIAttributes.IMPACT_RESISTANCE, 2.0)
            .put(AetherIIAttributes.PIERCE_RESISTANCE, 0.0)
            .build();

    public static ItemAttributeModifiers.Entry baseDamageModifer(double amount) {
        return new ItemAttributeModifiers.Entry(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, amount, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
    }

    public static ItemAttributeModifiers.Entry slashDamageModifer(double amount) {
        return new ItemAttributeModifiers.Entry(AetherIIAttributes.SLASH_DAMAGE, new AttributeModifier(AetherIIItems.BASE_SLASH_DAMAGE_ID, amount, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
    }

    public static ItemAttributeModifiers.Entry rangedSlashDamageModifer(double amount) {
        return new ItemAttributeModifiers.Entry(AetherIIAttributes.SLASH_RANGED_DAMAGE, new AttributeModifier(AetherIIItems.BASE_SLASH_RANGED_DAMAGE_ID, amount, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
    }

    public static ItemAttributeModifiers.Entry impactDamageModifer(double amount) {
        return new ItemAttributeModifiers.Entry(AetherIIAttributes.IMPACT_DAMAGE, new AttributeModifier(AetherIIItems.BASE_IMPACT_DAMAGE_ID, amount, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
    }

    public static ItemAttributeModifiers.Entry rangedImpactDamageModifer(double amount) {
        return new ItemAttributeModifiers.Entry(AetherIIAttributes.IMPACT_RANGED_DAMAGE, new AttributeModifier(AetherIIItems.BASE_IMPACT_RANGED_DAMAGE_ID, amount, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
    }

    public static ItemAttributeModifiers.Entry pierceDamageModifer(double amount) {
        return new ItemAttributeModifiers.Entry(AetherIIAttributes.PIERCE_DAMAGE, new AttributeModifier(AetherIIItems.BASE_PIERCE_DAMAGE_ID, amount, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
    }

    public static ItemAttributeModifiers.Entry rangedPierceDamageModifer(double amount) {
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