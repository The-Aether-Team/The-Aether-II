package com.aetherteam.aetherii.item.equipment.armor.abilities;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public interface SentryArmor {
    ResourceLocation SENTRY_FALL_DAMAGE_SUPPRESSION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "armor.ability.sentry.fall_damage_suppression");

    static void playerFall(LivingFallEvent event) { //todo
        LivingEntity livingEntity = event.getEntity();
        if (EquipmentUtil.hasArmorAbility(livingEntity, AetherIITags.Items.GRAVITITE_ARMOR)) {
            if (livingEntity.fallDistance < 8) {
                event.setDistance(0);
            }
        }
    }
}
