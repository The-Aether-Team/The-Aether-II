package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.living.EffectsSystemAttachment;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class StunEffect extends MobEffect {
    public StunEffect() {
        super(MobEffectCategory.HARMFUL, 0xFBFFC2);
    }

    public static void onEntityPostTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(AetherIIMobEffects.STUN)) {
            EffectsSystemAttachment attachment = livingEntity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM);
            attachment.setMotionMultiplier(attachment.getMotionMultiplier().multiply(new Vec3(0.4, 1.0, 0.4)));
        }
    }

    public static void disableAttacks(AttackEntityEvent event) {
        Player entity = event.getEntity();
        if (entity.hasEffect(AetherIIMobEffects.STUN)) {
            event.setCanceled(true);
        }
    }

    public static void disableDamage(LivingIncomingDamageEvent event) {
        DamageSource damageSource = event.getSource();
        if (damageSource.isDirect() && damageSource.getDirectEntity() instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(AetherIIMobEffects.STUN)) {
                event.setCanceled(true);
            }
        }
    }

    public static void disableEntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        Player entity = event.getEntity();
        if (entity.hasEffect(AetherIIMobEffects.STUN)) {
            event.setCanceled(true);
        }
    }

    public static void disableEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player entity = event.getEntity();
        if (entity.hasEffect(AetherIIMobEffects.STUN)) {
            event.setCanceled(true);
        }
    }

    public static void disableRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player entity = event.getEntity();
        if (entity.hasEffect(AetherIIMobEffects.STUN)) {
            event.setCanceled(true);
        }
    }

    public static void disableRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player entity = event.getEntity();
        if (entity.hasEffect(AetherIIMobEffects.STUN)) {
            event.setCanceled(true);
        }
    }

    public static void disableLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Player entity = event.getEntity();
        if (entity.hasEffect(AetherIIMobEffects.STUN)) {
            event.setCanceled(true);
        }
    }
}
