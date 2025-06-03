package com.aetherteam.aetherii.item.equipment.armor.abilities;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public interface BurrukaiPlateArmor {
    ResourceLocation BURRUKAI_PLATE_KNOCKBACK_RESISTANCE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "armor_set.ability.burrukai_plate.knockback_resistance");
    ResourceLocation BURRUKAI_PLATE_STUN_RESISTANCE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "armor_set.ability.burrukai_plate.stun_resistance");

    static void updatePlayerAttributes(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        AttributeInstance knockbackResistanceAttribute = player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        AttributeInstance stunResistanceAttribute = player.getAttribute(AetherIIAttributes.STUN_EFFECT_RESISTANCE);

        if (EquipmentUtil.hasArmorAbility(player, AetherIITags.Items.BURRUKAI_PLATE_ARMOR)) {
            if (knockbackResistanceAttribute != null && !knockbackResistanceAttribute.hasModifier(BURRUKAI_PLATE_KNOCKBACK_RESISTANCE)) {
                knockbackResistanceAttribute.addTransientModifier(new AttributeModifier(BURRUKAI_PLATE_KNOCKBACK_RESISTANCE, 0.2, AttributeModifier.Operation.ADD_VALUE));
            }
            if (stunResistanceAttribute != null && !stunResistanceAttribute.hasModifier(BURRUKAI_PLATE_STUN_RESISTANCE)) {
                stunResistanceAttribute.addTransientModifier(new AttributeModifier(BURRUKAI_PLATE_STUN_RESISTANCE, 0.5, AttributeModifier.Operation.ADD_VALUE));
            }
        } else {
            if (knockbackResistanceAttribute != null && knockbackResistanceAttribute.hasModifier(BURRUKAI_PLATE_KNOCKBACK_RESISTANCE)) {
                knockbackResistanceAttribute.removeModifier(BURRUKAI_PLATE_KNOCKBACK_RESISTANCE);
            }
            if (stunResistanceAttribute != null && stunResistanceAttribute.hasModifier(BURRUKAI_PLATE_STUN_RESISTANCE)) {
                stunResistanceAttribute.removeModifier(BURRUKAI_PLATE_STUN_RESISTANCE);
            }
        }
    }
}
