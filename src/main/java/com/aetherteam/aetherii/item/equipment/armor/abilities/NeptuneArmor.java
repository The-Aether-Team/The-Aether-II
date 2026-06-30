package com.aetherteam.aetherii.item.equipment.armor.abilities;


import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.item.components.ItemAttributeModifiers;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;

public interface NeptuneArmor {
    ResourceLocation NEPTUNE_SWIM_SPEED_BOOST = new ResourceLocation(AetherII.MODID, "armor_set.ability.neptune.swim_speed_boost");
    ResourceLocation NEPTUNE_OXYGEN_BONUS = new ResourceLocation(AetherII.MODID, "armor_set.ability.neptune.oxygen_bonus");

    static void updatePlayerAttributes(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
        Player player = event.player;
        AttributeInstance swimSpeedAttribute = player.getAttribute(ForgeMod.SWIM_SPEED.get());

        if (EquipmentUtil.hasArmorAbility(player, AetherIITags.Items.NEPTUNE_ARMOR)) {
            if (swimSpeedAttribute != null && !ItemAttributeModifiers.hasModifier(swimSpeedAttribute, NEPTUNE_SWIM_SPEED_BOOST)) {
                swimSpeedAttribute.addTransientModifier(ItemAttributeModifiers.modifier(NEPTUNE_SWIM_SPEED_BOOST, 2.0, AttributeModifier.Operation.ADDITION));
            }
        } else {
            if (swimSpeedAttribute != null && ItemAttributeModifiers.hasModifier(swimSpeedAttribute, NEPTUNE_SWIM_SPEED_BOOST)) {
                ItemAttributeModifiers.removeModifier(swimSpeedAttribute, NEPTUNE_SWIM_SPEED_BOOST);
            }
        }
    }
}
