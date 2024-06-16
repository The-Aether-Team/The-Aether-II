package com.aetherteam.aetherii.item.combat;

import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.UUID;

public class AetherIIShieldItem extends ShieldItem {
    public static final UUID BASE_SHIELD_STAMINA_REDUCTION_UUID = UUID.fromString("1A3BE7C0-7F1C-44EE-AA8C-AA959CB1C06C");

    private final Tier tier;
    private final int staminaReductionRate;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public AetherIIShieldItem(Tier tier, int staminaReductionRate, Properties properties) {
        super(properties.defaultDurability(tier.getUses()));
        this.tier = tier;
        this.staminaReductionRate = staminaReductionRate;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(AetherIIAttributes.SHIELD_STAMINA_REDUCTION.get(), new AttributeModifier(BASE_SHIELD_STAMINA_REDUCTION_UUID, "Shield modifier", this.staminaReductionRate, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    public Tier getTier() {
        return this.tier;
    }

    public int getStaminaReductionRate() {
        return this.staminaReductionRate;
    }

    @Override
    public int getEnchantmentValue() {
        return this.tier.getEnchantmentValue();
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairItem) {
        return this.tier.getRepairIngredient().test(repairItem);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment == Enchantments.BINDING_CURSE;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        return (equipmentSlot == EquipmentSlot.MAINHAND || equipmentSlot == EquipmentSlot.OFFHAND) ? this.defaultModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }
}
