package com.aetherteam.aetherii.event.listeners;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.AetherIIArmorMaterials;
import com.aetherteam.aetherii.item.EquipmentUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class ItemAttributeListener { //todo add to items
    private static final ResourceLocation BURRUKAI_PELT_KNOCKBACK_RESISTANCE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "armor_set.ability.burrukai_pelt.knockback_resistance");
    private static final ResourceLocation ZANITE_ATTACK_SPEED = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "armor_set.ability.zanite.attack_speed");
    private static final ResourceLocation ZANITE_MINING_SPEED = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "armor_set.ability.zanite.mining_speed");
    private static final ResourceLocation ZANITE_MOVEMENT_SPEED = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "armor_set.ability.zanite.movement_speed");

    public static void listen(IEventBus bus) {
        bus.addListener(ItemAttributeListener::onPlayerUpdate);
    }

    public static void onPlayerUpdate(PlayerTickEvent.Pre event) { //todo looping system.
        Player player = event.getEntity();
        AttributeInstance knockbackResistanceAttribute = player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        AttributeInstance attackSpeedAttribute = player.getAttribute(Attributes.ATTACK_SPEED);
        AttributeInstance miningSpeedAttribute = player.getAttribute(Attributes.MINING_EFFICIENCY);
        AttributeInstance movementSpeedAttribute = player.getAttribute(Attributes.MOVEMENT_EFFICIENCY);

        if (EquipmentUtil.hasArmorAbility(player, AetherIIArmorMaterials.BURRUKAI_PELT)) { //todo stun resistance
            if (knockbackResistanceAttribute != null && !knockbackResistanceAttribute.hasModifier(BURRUKAI_PELT_KNOCKBACK_RESISTANCE)) {
                knockbackResistanceAttribute.addTransientModifier(new AttributeModifier(BURRUKAI_PELT_KNOCKBACK_RESISTANCE, 0.2, AttributeModifier.Operation.ADD_VALUE));
            }
        } else {
            if (knockbackResistanceAttribute != null && knockbackResistanceAttribute.hasModifier(BURRUKAI_PELT_KNOCKBACK_RESISTANCE)) {
                knockbackResistanceAttribute.removeModifier(BURRUKAI_PELT_KNOCKBACK_RESISTANCE);
            }
        }

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
