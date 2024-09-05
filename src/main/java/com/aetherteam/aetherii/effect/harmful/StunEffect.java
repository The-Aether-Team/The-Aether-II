package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.effect.AetherIIEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.EffectCure;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.Set;

public class StunEffect extends MobEffect {
    public StunEffect() {
        super(MobEffectCategory.HARMFUL, 16515010);
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
        cures.clear();
    }

    public static void disableAttacks(AttackEntityEvent event) {
        Player entity = event.getEntity();
        if (entity.hasEffect(AetherIIEffects.STUN)) {
            event.setCanceled(true);
        }
    }

    public static void disableDamage(LivingIncomingDamageEvent event) {
        DamageSource damageSource = event.getSource();
        if (damageSource.isDirect() && damageSource.getDirectEntity() instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(AetherIIEffects.STUN)) {
                event.setCanceled(true);
            }
        }
    }

    public static void disableEntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        Player entity = event.getEntity();
        if (entity.hasEffect(AetherIIEffects.STUN)) {
            event.setCanceled(true);
        }
    }

    public static void disableEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player entity = event.getEntity();
        if (entity.hasEffect(AetherIIEffects.STUN)) {
            event.setCanceled(true);
        }
    }

    public static void disableRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player entity = event.getEntity();
        if (entity.hasEffect(AetherIIEffects.STUN)) {
            event.setCanceled(true);
        }
    }

    public static void disableRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player entity = event.getEntity();
        if (entity.hasEffect(AetherIIEffects.STUN)) {
            event.setCanceled(true);
        }
    }

    public static void disableLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Player entity = event.getEntity();
        if (entity.hasEffect(AetherIIEffects.STUN)) {
            event.setCanceled(true);
        }
    }
}
