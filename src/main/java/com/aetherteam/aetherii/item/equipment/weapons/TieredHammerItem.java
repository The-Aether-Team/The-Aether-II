package com.aetherteam.aetherii.item.equipment.weapons;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.item.equipment.AetherIINeoItemAbilities;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.Tags;

import java.util.List;

public class TieredHammerItem extends Item {
    public static final Identifier BASE_SHOCK_RANGE_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "base_shock_range");

    public TieredHammerItem(Item.Properties properties) {
        super(properties);
    }

    public static Item.Properties applyWeaponProperties(Item.Properties properties, ToolMaterial toolMaterial, float damage, float speed, List<ItemAttributeModifiers.Entry> specialDamage) {
        return properties.durability(toolMaterial.durability()).repairable(toolMaterial.repairItems()).enchantable(toolMaterial.enchantmentValue())
                .component(DataComponents.TOOL, new Tool(List.of(
                        Tool.Rule.overrideSpeed(BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK).getOrThrow(Tags.Blocks.GLASS_BLOCKS), 15.0F),
                        Tool.Rule.overrideSpeed(BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK).getOrThrow(Tags.Blocks.GLASS_PANES), 15.0F)
                ), 1.0F, 2, false))
                .attributes(createAttributes(toolMaterial, damage, speed, specialDamage))
                .component(DataComponents.WEAPON, new Weapon(1));
    }

    public static ItemAttributeModifiers createAttributes(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        return createAttributes(toolMaterial, (float) attackDamage, attackSpeed);
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
                .add(AetherIIAttributes.SHOCK_RANGE, new AttributeModifier(BASE_SHOCK_RANGE_ID, 2.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }
    @Override
    public void hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
    }

    @Override
    public void postHurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility toolAction) {
        return AetherIINeoItemAbilities.DEFAULT_HAMMER_ACTIONS.contains(toolAction);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return false;
    }
}
