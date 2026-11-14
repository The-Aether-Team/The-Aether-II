package com.aetherteam.aetherii.item.equipment.armor.abilities;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.AbilityBehaviorAttachment;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.LivingEntityAccessor;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public interface GravititeArmor {
    Identifier GRAVITITE_FALL_DAMAGE_SUPPRESSION = Identifier.fromNamespaceAndPath(AetherII.MODID, "armor_set.ability.gravitite.fall_damage_suppression");

    static void updatePlayerAttributes(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        AttributeInstance fallDamageMultiplierAttribute = player.getAttribute(Attributes.FALL_DAMAGE_MULTIPLIER);

        if (EquipmentUtil.hasArmorAbility(player, AetherIITags.Items.GRAVITITE_ARMOR)) {
            if (fallDamageMultiplierAttribute != null && !fallDamageMultiplierAttribute.hasModifier(GRAVITITE_FALL_DAMAGE_SUPPRESSION)) {
                fallDamageMultiplierAttribute.addTransientModifier(new AttributeModifier(GRAVITITE_FALL_DAMAGE_SUPPRESSION, -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            }
        } else {
            if (fallDamageMultiplierAttribute != null && fallDamageMultiplierAttribute.hasModifier(GRAVITITE_FALL_DAMAGE_SUPPRESSION)) {
                fallDamageMultiplierAttribute.removeModifier(GRAVITITE_FALL_DAMAGE_SUPPRESSION);
            }
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

    static void playerUpdate(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        LivingEntityAccessor accessor = (LivingEntityAccessor) player;
        AbilityBehaviorAttachment attachment = player.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR);
        boolean isFluid = player.isInWater() || player.isInFluidType();
        if (isFluid) {
            accessor.aether$setNoJumpDelay(6);
        }

        if (attachment.isGravititeJumpUsed()) {
            if (isFluid || player.onGround()) {
                attachment.setGravititeJumpUsed(false);
            }
        }

        if (!player.onGround() && !isFluid && accessor.aether$isJumping() && accessor.aether$getNoJumpDelay() == 0 && EquipmentUtil.hasArmorAbility(player, AetherIITags.Items.GRAVITITE_ARMOR) && !attachment.isGravititeJumpUsed()) {
            float f = accessor.callGetJumpPower() * 1.25F;
            if (!(f <= 1.0E-5F)) {
                Vec3 vec3 = player.getDeltaMovement();
                player.setDeltaMovement(vec3.x, f, vec3.z);
                if (player.isSprinting()) {
                    float f1 = player.getYRot() * (float) (Math.PI / 180.0);
                    player.addDeltaMovement(new Vec3((double) (-Mth.sin(f1)) * 0.2, 0.0, (double) Mth.cos(f1) * 0.2));
                }
                player.needsSync = true;
            }
            accessor.aether$setNoJumpDelay(10);
            attachment.setGravititeJumpUsed(true);
        }
    }
}
