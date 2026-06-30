package com.aetherteam.aetherii.item.equipment.armor.abilities;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.item.components.ItemAttributeModifiers;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;

public interface ZaniteArmor {
    ResourceLocation ZANITE_ATTACK_SPEED = new ResourceLocation(AetherII.MODID, "armor_set.ability.zanite.attack_speed");
    ResourceLocation ZANITE_MINING_SPEED = new ResourceLocation(AetherII.MODID, "armor_set.ability.zanite.mining_speed");
    ResourceLocation ZANITE_MOVEMENT_SPEED = new ResourceLocation(AetherII.MODID, "armor_set.ability.zanite.movement_speed");

    static void updatePlayerAttributes(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
        Player player = event.player;
        AttributeInstance attackSpeedAttribute = player.getAttribute(Attributes.ATTACK_SPEED);
        AttributeInstance miningSpeedAttribute = player.getAttribute(AetherIIAttributes.MINING_EFFICIENCY.get());
        AttributeInstance movementSpeedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (EquipmentUtil.hasArmorAbility(player, AetherIITags.Items.ZANITE_ARMOR)) {
            if (attackSpeedAttribute != null && !ItemAttributeModifiers.hasModifier(attackSpeedAttribute, ZANITE_ATTACK_SPEED)) {
                attackSpeedAttribute.addTransientModifier(ItemAttributeModifiers.modifier(ZANITE_ATTACK_SPEED, 0.15, AttributeModifier.Operation.ADDITION));
            }
            if (miningSpeedAttribute != null && !ItemAttributeModifiers.hasModifier(miningSpeedAttribute, ZANITE_MINING_SPEED)) {
                miningSpeedAttribute.addTransientModifier(ItemAttributeModifiers.modifier(ZANITE_MINING_SPEED, 2, AttributeModifier.Operation.ADDITION));
            }
            if (movementSpeedAttribute != null && !ItemAttributeModifiers.hasModifier(movementSpeedAttribute, ZANITE_MOVEMENT_SPEED)) {
                movementSpeedAttribute.addTransientModifier(ItemAttributeModifiers.modifier(ZANITE_MOVEMENT_SPEED, 0.05, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        } else {
            if (attackSpeedAttribute != null && ItemAttributeModifiers.hasModifier(attackSpeedAttribute, ZANITE_ATTACK_SPEED)) {
                ItemAttributeModifiers.removeModifier(attackSpeedAttribute, ZANITE_ATTACK_SPEED);
            }
            if (miningSpeedAttribute != null && ItemAttributeModifiers.hasModifier(miningSpeedAttribute, ZANITE_MINING_SPEED)) {
                ItemAttributeModifiers.removeModifier(miningSpeedAttribute, ZANITE_MINING_SPEED);
            }
            if (movementSpeedAttribute != null && ItemAttributeModifiers.hasModifier(movementSpeedAttribute, ZANITE_MOVEMENT_SPEED)) {
                ItemAttributeModifiers.removeModifier(movementSpeedAttribute, ZANITE_MOVEMENT_SPEED);
            }
        }
    }
}
