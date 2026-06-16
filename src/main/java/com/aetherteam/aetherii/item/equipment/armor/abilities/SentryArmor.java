package com.aetherteam.aetherii.item.equipment.armor.abilities;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;

public interface SentryArmor {
    Identifier SENTRY_FALL_DAMAGE_SUPPRESSION = Identifier.fromNamespaceAndPath(AetherII.MODID, "armor.ability.sentry.fall_damage_suppression");

    static void playerFall(LivingFallEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity.getItemBySlot(EquipmentSlot.FEET).is(AetherIIItems.SENTRY_BOOTS)) {
            if (livingEntity.fallDistance < 8) {
                event.setDistance(0);
            }
        }
    }
}
