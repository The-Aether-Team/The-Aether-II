package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.living.EffectsSystemAttachment;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class StunEffect extends MobEffect {
    public StunEffect() {
        super(MobEffectCategory.HARMFUL, 0xFBFFC2);
    }

    public static void onEntityPostTick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity.hasEffect(AetherIIMobEffects.STUN.get())) {
            EffectsSystemAttachment attachment = AetherIIDataAttachments.get(livingEntity, AetherIIDataAttachments.EFFECTS_SYSTEM);
            attachment.setMotionMultiplier(attachment.getMotionMultiplier().multiply(new Vec3(0.4, 1.0, 0.4)));
        }
    }

    public static void disableAttacks(AttackEntityEvent event) {
        Player entity = event.getEntity();
        if (entity.hasEffect(AetherIIMobEffects.STUN.get())) {
            event.setCanceled(true);
        }
    }

    public static void disableDamage(LivingAttackEvent event) {
        DamageSource damageSource = event.getSource();
        if (!damageSource.isIndirect() && damageSource.getDirectEntity() instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(AetherIIMobEffects.STUN.get())) {
                event.setCanceled(true);
            }
        }
    }

    public static void disableEntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        Player entity = event.getEntity();
        if (entity.hasEffect(AetherIIMobEffects.STUN.get())) {
            event.setCanceled(true);
        }
    }

    public static void disableEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player entity = event.getEntity();
        if (entity.hasEffect(AetherIIMobEffects.STUN.get())) {
            event.setCanceled(true);
        }
    }

    public static void disableRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player entity = event.getEntity();
        if (entity.hasEffect(AetherIIMobEffects.STUN.get())) {
            event.setCanceled(true);
        }
    }

    public static void disableRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player entity = event.getEntity();
        if (entity.hasEffect(AetherIIMobEffects.STUN.get())) {
            event.setCanceled(true);
        }
    }

    public static void disableLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Player entity = event.getEntity();
        if (entity.hasEffect(AetherIIMobEffects.STUN.get())) {
            event.setCanceled(true);
        }
    }
}
