package com.aetherteam.aetherii.item.equipment.armor.abilities;

import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;

public interface SentryArmor {
    static void playerFall(LivingFallEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity.getItemBySlot(EquipmentSlot.FEET).is(AetherIITags.Items.SENTRY_ARMOR)) {
            event.setDamageMultiplier(event.getDamageMultiplier() * 0.25F);
            if (livingEntity.fallDistance < 8) {
                event.setDistance(0);
            }
        }
    }
}
