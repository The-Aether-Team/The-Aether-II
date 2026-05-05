package com.aetherteam.aetherii.item.equipment.weapons;

import com.aetherteam.aetherii.item.equipment.AetherIINeoItemAbilities;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;

public class TieredSpearItem extends Item {
    public TieredSpearItem(Properties properties) {
        super(properties);
    }

    public static Item.Properties applyWeaponProperties(Item.Properties properties, ToolMaterial toolMaterial, float damage, float speed, float attackDuration, float damageMultiplier, float delay, float dismountTime, float dismountThreshold, float knockbackTime, float damageTime, List<ItemAttributeModifiers.Entry> specialDamage) {
        return properties.spear(toolMaterial, attackDuration, damageMultiplier, delay, dismountTime, dismountThreshold, knockbackTime, 5.1F, damageTime, 4.6F).attributes(createAttributes(toolMaterial, damage-3, speed, specialDamage));
    }

    public static ItemAttributeModifiers createAttributes(ToolMaterial toolMaterial, float attackDamage, float attackSpeed) {
        return createAttributes(toolMaterial, attackDamage, attackSpeed, List.of());
    }

    public static ItemAttributeModifiers createAttributes(ToolMaterial toolMaterial, float attackDamage, float attackSpeed, List<ItemAttributeModifiers.Entry> specialDamage) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        for (ItemAttributeModifiers.Entry entry : specialDamage) {
            builder.add(entry.attribute(), entry.modifier(), entry.slot());
        }
        return builder
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage + toolMaterial.attackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public boolean canPerformAction(ItemInstance stack, ItemAbility itemAbility) {
        return AetherIINeoItemAbilities.DEFAULT_SPEAR_ACTIONS.contains(itemAbility);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return false;
    }
}
