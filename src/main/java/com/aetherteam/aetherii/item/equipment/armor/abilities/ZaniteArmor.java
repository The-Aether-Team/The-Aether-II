package com.aetherteam.aetherii.item.equipment.armor.abilities;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import com.aetherteam.aetherii.item.equipment.armor.AetherIIArmorMaterials;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public interface ZaniteArmor {
    ResourceLocation ZANITE_ATTACK_SPEED = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "armor_set.ability.zanite.attack_speed");
    ResourceLocation ZANITE_MINING_SPEED = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "armor_set.ability.zanite.mining_speed");
    ResourceLocation ZANITE_MOVEMENT_SPEED = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "armor_set.ability.zanite.movement_speed");

    static void updatePlayerAttributes(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        AttributeInstance attackSpeedAttribute = player.getAttribute(Attributes.ATTACK_SPEED);
        AttributeInstance miningSpeedAttribute = player.getAttribute(Attributes.MINING_EFFICIENCY);
        AttributeInstance movementSpeedAttribute = player.getAttribute(Attributes.MOVEMENT_EFFICIENCY);

        if (EquipmentUtil.hasArmorAbility(player, AetherIIArmorMaterials.ZANITE)) {
            if (attackSpeedAttribute != null && !attackSpeedAttribute.hasModifier(ZANITE_ATTACK_SPEED)) {
                attackSpeedAttribute.addTransientModifier(new AttributeModifier(ZANITE_ATTACK_SPEED, 2, AttributeModifier.Operation.ADD_VALUE));
            }
            if (miningSpeedAttribute != null && !miningSpeedAttribute.hasModifier(ZANITE_MINING_SPEED)) {
                miningSpeedAttribute.addTransientModifier(new AttributeModifier(ZANITE_MINING_SPEED, 2, AttributeModifier.Operation.ADD_VALUE));
            }
            if (movementSpeedAttribute != null && !movementSpeedAttribute.hasModifier(ZANITE_MOVEMENT_SPEED)) {
                movementSpeedAttribute.addTransientModifier(new AttributeModifier(ZANITE_MOVEMENT_SPEED, 2, AttributeModifier.Operation.ADD_VALUE));
            }
        } else {
            if (attackSpeedAttribute != null && attackSpeedAttribute.hasModifier(ZANITE_ATTACK_SPEED)) {
                attackSpeedAttribute.removeModifier(ZANITE_ATTACK_SPEED);
            }
            if (miningSpeedAttribute != null && miningSpeedAttribute.hasModifier(ZANITE_MINING_SPEED)) {
                miningSpeedAttribute.removeModifier(ZANITE_MINING_SPEED);
            }
            if (movementSpeedAttribute != null && movementSpeedAttribute.hasModifier(ZANITE_MOVEMENT_SPEED)) {
                movementSpeedAttribute.removeModifier(ZANITE_MOVEMENT_SPEED);
            }
        }
    }
}
