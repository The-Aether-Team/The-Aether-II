package com.aetherteam.aetherii.item.equipment.armor.abilities;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.AbilityBehaviorAttachment;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.LivingEntityAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.TickEvent;

public interface GravititeArmor {
    ResourceLocation GRAVITITE_FALL_DAMAGE_SUPPRESSION = new ResourceLocation(AetherII.MODID, "armor_set.ability.gravitite.fall_damage_suppression");

    static void updatePlayerAttributes(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
    }

    static void playerFall(LivingFallEvent event) { //todo
        LivingEntity livingEntity = event.getEntity();
        if (EquipmentUtil.hasArmorAbility(livingEntity, AetherIITags.Items.GRAVITITE_ARMOR)) {
            if (livingEntity.fallDistance < 8) {
                event.setDistance(0);
            }
        }
    }

    static void playerUpdate(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
        Player player = event.player;
        AbilityBehaviorAttachment attachment = AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR);
        boolean isFluid = player.isInWater() /*|| player.isInFluidType()*/;
        if (isFluid) {
            player.noJumpDelay = 6;
        }

        if (attachment.isGravititeJumpUsed()) {
            if (isFluid || player.onGround()) {
                attachment.setGravititeJumpUsed(false);
            }
        }

        if (!player.onGround() && !isFluid && player.jumping && player.noJumpDelay == 0 && EquipmentUtil.hasArmorAbility(player, AetherIITags.Items.GRAVITITE_ARMOR) && !attachment.isGravititeJumpUsed()) {
            float f = ((LivingEntityAccessor) player).callGetJumpPower() * 1.25F;
            if (!(f <= 1.0E-5F)) {
                Vec3 vec3 = player.getDeltaMovement();
                player.setDeltaMovement(vec3.x, f, vec3.z);
                if (player.isSprinting()) {
                    float f1 = player.getYRot() * (float) (Math.PI / 180.0);
                    player.addDeltaMovement(new Vec3((double) (-Mth.sin(f1)) * 0.2, 0.0, (double) Mth.cos(f1) * 0.2));
                }
                player.hasImpulse = true;
            }
            player.noJumpDelay = 10;
            attachment.setGravititeJumpUsed(true);
        }
    }
}
