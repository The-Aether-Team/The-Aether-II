package com.aetherteam.aetherii.item.equipment.armor.abilities;


import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public interface NeptuneArmor {
    ResourceLocation NEPTUNE_SWIM_SPEED_BOOST = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "armor_set.ability.neptune.swim_speed_boost");
    ResourceLocation NEPTUNE_OXYGEN_BONUS = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "armor_set.ability.neptune.oxygen_bonus");

    static void updatePlayerAttributes(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        AttributeInstance fallDamageMultiplierAttribute = player.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY);
        AttributeInstance oxygenBonusAttribute = player.getAttribute(Attributes.OXYGEN_BONUS);

        if (EquipmentUtil.hasArmorAbility(player, AetherIITags.Items.NEPTUNE_ARMOR)) {
            if (fallDamageMultiplierAttribute != null && !fallDamageMultiplierAttribute.hasModifier(NEPTUNE_SWIM_SPEED_BOOST)) {
                fallDamageMultiplierAttribute.addTransientModifier(new AttributeModifier(NEPTUNE_SWIM_SPEED_BOOST, 1.0, AttributeModifier.Operation.ADD_VALUE));
            }
            if (oxygenBonusAttribute != null && !oxygenBonusAttribute.hasModifier(NEPTUNE_OXYGEN_BONUS)) {
                oxygenBonusAttribute.addTransientModifier(new AttributeModifier(NEPTUNE_OXYGEN_BONUS, 1.0, AttributeModifier.Operation.ADD_VALUE));
            }
        } else {
            if (fallDamageMultiplierAttribute != null && fallDamageMultiplierAttribute.hasModifier(NEPTUNE_SWIM_SPEED_BOOST)) {
                fallDamageMultiplierAttribute.removeModifier(NEPTUNE_SWIM_SPEED_BOOST);
            }
            if (oxygenBonusAttribute != null && oxygenBonusAttribute.hasModifier(NEPTUNE_OXYGEN_BONUS)) {
                oxygenBonusAttribute.removeModifier(NEPTUNE_OXYGEN_BONUS);
            }
        }
    }
}