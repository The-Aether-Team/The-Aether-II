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

public interface BurrukaiPlateArmor {
    ResourceLocation BURRUKAI_PLATE_KNOCKBACK_RESISTANCE = new ResourceLocation(AetherII.MODID, "armor_set.ability.burrukai_plate.knockback_resistance");
    ResourceLocation BURRUKAI_PLATE_STUN_RESISTANCE = new ResourceLocation(AetherII.MODID, "armor_set.ability.burrukai_plate.stun_resistance");

    static void updatePlayerAttributes(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
        Player player = event.player;
        AttributeInstance knockbackResistanceAttribute = player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        AttributeInstance stunResistanceAttribute = player.getAttribute(AetherIIAttributes.STUN_EFFECT_RESISTANCE.get());

        if (EquipmentUtil.hasArmorAbility(player, AetherIITags.Items.BURRUKAI_PLATE_ARMOR)) {
            if (knockbackResistanceAttribute != null && !ItemAttributeModifiers.hasModifier(knockbackResistanceAttribute, BURRUKAI_PLATE_KNOCKBACK_RESISTANCE)) {
                knockbackResistanceAttribute.addTransientModifier(ItemAttributeModifiers.modifier(BURRUKAI_PLATE_KNOCKBACK_RESISTANCE, 0.2, AttributeModifier.Operation.ADDITION));
            }
            if (stunResistanceAttribute != null && !ItemAttributeModifiers.hasModifier(stunResistanceAttribute, BURRUKAI_PLATE_STUN_RESISTANCE)) {
                stunResistanceAttribute.addTransientModifier(ItemAttributeModifiers.modifier(BURRUKAI_PLATE_STUN_RESISTANCE, 0.5, AttributeModifier.Operation.ADDITION));
            }
        } else {
            if (knockbackResistanceAttribute != null && ItemAttributeModifiers.hasModifier(knockbackResistanceAttribute, BURRUKAI_PLATE_KNOCKBACK_RESISTANCE)) {
                ItemAttributeModifiers.removeModifier(knockbackResistanceAttribute, BURRUKAI_PLATE_KNOCKBACK_RESISTANCE);
            }
            if (stunResistanceAttribute != null && ItemAttributeModifiers.hasModifier(stunResistanceAttribute, BURRUKAI_PLATE_STUN_RESISTANCE)) {
                ItemAttributeModifiers.removeModifier(stunResistanceAttribute, BURRUKAI_PLATE_STUN_RESISTANCE);
            }
        }
    }
}
