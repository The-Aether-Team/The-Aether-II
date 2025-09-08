package com.aetherteam.aetherii.item.equipment.weapons;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.item.equipment.AetherIINeoItemAbilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;

public class TieredSpearItem extends Item {
    public static final ResourceLocation BASE_STAB_RADIUS_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "base_stab_radius");
    public static final ResourceLocation BASE_STAB_DISTANCE_ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "base_stab_distance");

    public TieredSpearItem(Item.Properties properties) {
        super(properties);
    }

    public static Item.Properties applyWeaponProperties(Item.Properties properties, ToolMaterial toolMaterial, float damage, float speed, List<ItemAttributeModifiers.Entry> specialDamage) {
        return properties.durability(toolMaterial.durability()).repairable(toolMaterial.repairItems()).enchantable(toolMaterial.enchantmentValue())
                .component(DataComponents.TOOL, new Tool(List.of(), 1.0F, 2))
                .attributes(createAttributes(toolMaterial, damage, speed, specialDamage));
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
                .add(AetherIIAttributes.STAB_RADIUS, new AttributeModifier(BASE_STAB_RADIUS_ID, 1.5, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(AetherIIAttributes.STAB_DISTANCE, new AttributeModifier(BASE_STAB_DISTANCE_ID, 5.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility toolAction) {
        return AetherIINeoItemAbilities.DEFAULT_SPEAR_ACTIONS.contains(toolAction);
    }
}
