package com.aetherteam.aetherii.item.equipment.armor.abilities;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public interface SentryArmor {
    ResourceLocation SENTRY_FALL_DAMAGE_SUPPRESSION = new ResourceLocation(AetherII.MODID, "armor.ability.sentry.fall_damage_suppression");

    static void playerFall(LivingFallEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity.getItemBySlot(EquipmentSlot.FEET).is(AetherIIItems.SENTRY_BOOTS.get())) {
            if (event.getDistance() < 8) {
                event.setDistance(0);
            } else {
                event.setDamageMultiplier(event.getDamageMultiplier() * 0.25F);
            }
        }
    }
}
