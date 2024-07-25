package com.aetherteam.aetherii.event.listeners.attachment;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.EquipmentAbilitiesAttachment;
import com.aetherteam.aetherii.item.AetherIIArmorMaterials;
import com.aetherteam.aetherii.item.EquipmentUtil;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.LivingEntityAccessor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class EquipmentAbilitiesListener {
    public static void listen(IEventBus bus) {
        bus.addListener(EquipmentAbilitiesListener::onPlayerFall);
        bus.addListener(EquipmentAbilitiesListener::onPlayerUpdate);
    }

    public static void onPlayerFall(LivingFallEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (EquipmentUtil.hasArmorAbility(livingEntity, AetherIIArmorMaterials.GRAVITITE)) {
            event.setDamageMultiplier(event.getDamageMultiplier() * 0.3F);
        }
    }

    public static void onPlayerUpdate(PlayerTickEvent.Post event) { //todo move to a special armor class tick method called from an event listener for aether armors.
        Player player = event.getEntity();
        LivingEntityAccessor accessor = (LivingEntityAccessor) player;
        EquipmentAbilitiesAttachment attachment = player.getData(AetherIIDataAttachments.EQUIPMENT_ABILITIES);
        if (player.onGround() && attachment.isGravititeJumpUsed()) {
            attachment.setGravititeJumpUsed(false);
        }
        if (!player.onGround() && accessor.aether$isJumping() && accessor.aether$getNoJumpDelay() == 0 && EquipmentUtil.hasArmorAbility(player, AetherIIArmorMaterials.GRAVITITE) && !attachment.isGravititeJumpUsed()) {
            float f = accessor.callGetJumpPower() * 1.25F;
            if (!(f <= 1.0E-5F)) {
                Vec3 vec3 = player.getDeltaMovement();
                player.setDeltaMovement(vec3.x, f, vec3.z);
                if (player.isSprinting()) {
                    float f1 = player.getYRot() * (float) (Math.PI / 180.0);
                    player.addDeltaMovement(new Vec3((double) (-Mth.sin(f1)) * 0.2, 0.0, (double) Mth.cos(f1) * 0.2));
                }
                player.hasImpulse = true;
            }
            accessor.aether$setNoJumpDelay(10);
            attachment.setGravititeJumpUsed(true);
        }
    }
}
