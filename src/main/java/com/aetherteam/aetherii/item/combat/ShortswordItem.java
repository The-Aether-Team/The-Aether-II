package com.aetherteam.aetherii.item.combat;

import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.aetherteam.aetherii.item.AetherIIToolActions;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.neoforged.neoforge.common.ToolAction;

import java.util.UUID;

public class ShortswordItem extends SwordItem {
    public static final UUID BASE_SLASH_RANGE_UUID = UUID.fromString("8682E787-5A67-4999-AE6E-9B8AE5FA9F04");

    public ShortswordItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Item.Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
            attributeBuilder.putAll(map);
            attributeBuilder.put(AetherIIAttributes.SLASH_RANGE.get(), new AttributeModifier(BASE_SLASH_RANGE_UUID, "Weapon modifier", 2.0, AttributeModifier.Operation.ADDITION));
            map = attributeBuilder.build();
        }
        return map;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return AetherIIToolActions.DEFAULT_SHORTSWORD_ACTIONS.contains(toolAction);
    }
}
