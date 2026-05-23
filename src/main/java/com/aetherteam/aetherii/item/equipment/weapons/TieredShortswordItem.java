package com.aetherteam.aetherii.item.equipment.weapons;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.item.equipment.AetherIINeoItemAbilities;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;

public class TieredShortswordItem extends Item {
    public static final Identifier BASE_SWEEP_RANGE_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "base_sweep_range");

    public TieredShortswordItem(Item.Properties properties) {
        super(properties);
    }

    public static Item.Properties applyWeaponProperties(Item.Properties properties, ToolMaterial toolMaterial, float damage, float speed, List<ItemAttributeModifiers.Entry> specialDamage) {
        return properties.durability(toolMaterial.durability()).repairable(toolMaterial.repairItems()).enchantable(toolMaterial.enchantmentValue())
                .component(DataComponents.TOOL, new Tool(List.of(
                        Tool.Rule.minesAndDrops(HolderSet.direct(Blocks.COBWEB.builtInRegistryHolder()), 15.0F),
                        Tool.Rule.overrideSpeed(BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK).getOrThrow(BlockTags.SWORD_INSTANTLY_MINES), Float.MAX_VALUE),
                        Tool.Rule.overrideSpeed(BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK).getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)
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
                .add(AetherIIAttributes.SWEEP_RANGE, new AttributeModifier(BASE_SWEEP_RANGE_ID, 2.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public boolean canPerformAction(ItemInstance stack, ItemAbility itemAbility) {
        return AetherIINeoItemAbilities.DEFAULT_SHORTSWORD_ACTIONS.contains(itemAbility);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return false;
    }
}
