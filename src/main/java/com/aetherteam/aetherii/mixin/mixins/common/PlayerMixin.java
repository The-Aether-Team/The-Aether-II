package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.AetherIIToolActions;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(Player.class)
public class PlayerMixin { //todo sounds, particles, and stats
    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"), method = "attack(Lnet/minecraft/world/entity/Entity;)V")
    private ItemStack getItemInHand(ItemStack original, @Share("canSpearStab") LocalBooleanRef canSpearStab, @Share("canHammerShock") LocalBooleanRef canHammerShock) {
        canSpearStab.set(original.canPerformAction(AetherIIToolActions.SPEAR_STAB));
        canHammerShock.set(original.canPerformAction(AetherIIToolActions.HAMMER_SHOCK));
        return original;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setLastHurtMob(Lnet/minecraft/world/entity/Entity;)V", shift = At.Shift.BEFORE), method = "attack(Lnet/minecraft/world/entity/Entity;)V")
    private void attack(Entity target, CallbackInfo ci, @Share("canSpearStab") LocalBooleanRef canSpearStab, @Share("canHammerShock") LocalBooleanRef canHammerShock) {
        Player player = (Player) (Object) this;
        stabBehavior(player, target, canSpearStab.get());
        shockBehavior(player, target, canHammerShock.get());
    }

    private static void stabBehavior(Player player, Entity target, boolean canSpearStab) {
        if (canSpearStab) {
            Iterator<LivingEntity> iterator = player.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(0.25, 0.25, 4)).iterator();

            stab:
            while(true) {
                LivingEntity livingentity;
                double entityReachSq;
                do {
                    do {
                        do {
                            do {
                                if (!iterator.hasNext()) {
                                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);//todo
                                    stabAttack(player);
                                    break stab;
                                }

                                livingentity = iterator.next();
                                entityReachSq = Mth.square(player.getEntityReach());
                            } while(livingentity == player);
                        } while(livingentity == target);
                    } while(player.isAlliedTo(livingentity));
                } while(livingentity instanceof ArmorStand && ((ArmorStand) livingentity).isMarker());

                if (player.distanceToSqr(livingentity) < entityReachSq) {
                    livingentity.knockback(0.1, Mth.sin(player.getYRot() * 0.0175F), -Mth.cos(player.getYRot() * 0.0175F));
                    livingentity.hurt(player.damageSources().playerAttack(player), 2.0F);
                }
            }
        }
    }

    private static void shockBehavior(Player player, Entity target, boolean canHammerShock) {
        if (canHammerShock) {
            Iterator<LivingEntity> iterator = player.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(1.0, 0.25, 3)).iterator();

            shock:
            while(true) {
                LivingEntity livingentity;
                double entityReachSq;
                do {
                    do {
                        do {
                            do {
                                if (!iterator.hasNext()) {
                                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);//todo
                                    shockAttack(player);
                                    break shock;
                                }

                                livingentity = iterator.next();
                                entityReachSq = Mth.square(player.getEntityReach());
                            } while(livingentity == player);
                        } while(livingentity == target);
                    } while(player.isAlliedTo(livingentity));
                } while(livingentity instanceof ArmorStand && ((ArmorStand) livingentity).isMarker());

                if (player.distanceToSqr(livingentity) < entityReachSq) {
                    livingentity.knockback(1.2, Mth.sin(player.getYRot() * 0.0175F), -Mth.cos(player.getYRot() * 0.0175F)); //todo knockback direction
                    livingentity.hurt(player.damageSources().playerAttack(player), 0.5F);
                }
            }
        }
    }

    private static void stabAttack(Player player) {
        double d0 = -Mth.sin(player.getYRot() * 0.0175F);
        double d1 = Mth.cos(player.getYRot() * 0.0175F);
        if (player.level() instanceof ServerLevel) {
            ((ServerLevel) player.level()).sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX() + d0, player.getY(0.5), player.getZ() + d1, 0, d0, 0.0, d1, 0.0);
        }
    }

    private static void shockAttack(Player player) {
        double d0 = -Mth.sin(player.getYRot() * 0.0175F);
        double d1 = Mth.cos(player.getYRot() * 0.0175F);
        if (player.level() instanceof ServerLevel) {
            ((ServerLevel) player.level()).sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX() + d0, player.getY(0.5), player.getZ() + d1, 0, d0, 0.0, d1, 0.0);
        }
    }
}
