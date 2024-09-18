package com.aetherteam.aetherii.item.equipment.armor.abilities;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import com.aetherteam.aetherii.item.equipment.armor.AetherIIArmorMaterials;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public interface BurrukaiPeltArmor {
    ResourceLocation BURRUKAI_PELT_KNOCKBACK_RESISTANCE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "armor_set.ability.burrukai_pelt.knockback_resistance");
    ResourceLocation BURRUKAI_PELT_STUN_RESISTANCE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "armor_set.ability.burrukai_pelt.stun_resistance");

    static void updatePlayerAttributes(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        AttributeInstance knockbackResistanceAttribute = player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        AttributeInstance stunResistanceAttribute = player.getAttribute(AetherIIAttributes.STUN_EFFECT_RESISTANCE);

        if (EquipmentUtil.hasArmorAbility(player, AetherIIArmorMaterials.BURRUKAI_PELT)) {
            if (knockbackResistanceAttribute != null && !knockbackResistanceAttribute.hasModifier(BURRUKAI_PELT_KNOCKBACK_RESISTANCE)) {
                knockbackResistanceAttribute.addTransientModifier(new AttributeModifier(BURRUKAI_PELT_KNOCKBACK_RESISTANCE, 0.2, AttributeModifier.Operation.ADD_VALUE));
            }
            if (stunResistanceAttribute != null && !stunResistanceAttribute.hasModifier(BURRUKAI_PELT_STUN_RESISTANCE)) {
                stunResistanceAttribute.addTransientModifier(new AttributeModifier(BURRUKAI_PELT_STUN_RESISTANCE, 0.5, AttributeModifier.Operation.ADD_VALUE));
            }
        } else {
            if (knockbackResistanceAttribute != null && knockbackResistanceAttribute.hasModifier(BURRUKAI_PELT_KNOCKBACK_RESISTANCE)) {
                knockbackResistanceAttribute.removeModifier(BURRUKAI_PELT_KNOCKBACK_RESISTANCE);
            }
            if (stunResistanceAttribute != null && stunResistanceAttribute.hasModifier(BURRUKAI_PELT_STUN_RESISTANCE)) {
                stunResistanceAttribute.removeModifier(BURRUKAI_PELT_STUN_RESISTANCE);
            }
        }
    }
}
