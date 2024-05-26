package com.aetherteam.aetherii.mixin;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Iterator;

public class MixinHooks {
    public static void spearStabBehavior(Player player, Entity target, boolean canSpearStab) {
        if (canSpearStab) {
            Iterator<LivingEntity> iterator = player.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(4, 4, 4), (other) -> withinStabDistance(player, target, other, 0.5, 4.0)).iterator();

            stab:
            while(true) {
                LivingEntity other;
                do {
                    do {
                        do {
                            do {
                                if (!iterator.hasNext()) {
                                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);//todo
                                    stabAttack(player);
                                    break stab;
                                }
                                other = iterator.next();
                            } while(other == player);
                        } while(other == target);
                    } while(player.isAlliedTo(other));
                } while(other instanceof ArmorStand && ((ArmorStand) other).isMarker());

                other.knockback(0.1, Mth.sin(player.getYRot() * 0.0175F), -Mth.cos(player.getYRot() * 0.0175F));
                other.hurt(player.damageSources().playerAttack(player), 2.0F);
            }
        }
    }

    private static boolean withinStabDistance(Entity player, Entity target, Entity other, double radialBounds, double forwardBounds) {
        Vec3 playerPosition = player.position();
        Vec3 targetPosition = target.position();
        Vec3 otherPosition = other.position();

        Vec3 playerToTarget = targetPosition.subtract(playerPosition);
        Vec3 playerToOther = otherPosition.subtract(playerPosition);

        double betweenAngle = Math.acos((playerToTarget.dot(playerToOther)) / (playerToTarget.length() * playerToOther.length()));
        double radialDistance = playerToOther.length() * Math.sin(betweenAngle);
        double forwardDistance = playerToOther.length() * Math.cos(betweenAngle);

        return radialDistance <= radialBounds && forwardDistance <= forwardBounds;
    }

    private static void stabAttack(Player player) {
        double d0 = -Mth.sin(player.getYRot() * 0.0175F);
        double d1 = Mth.cos(player.getYRot() * 0.0175F);
        if (player.level() instanceof ServerLevel) {
            ((ServerLevel) player.level()).sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX() + d0, player.getY(0.5), player.getZ() + d1, 0, d0, 0.0, d1, 0.0);
        }
    }

    public static void hammerShockBehavior(Player player, Entity target, boolean canHammerShock) {
        if (canHammerShock) {
            Iterator<LivingEntity> iterator = player.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(1.5, 0.25, 1.5)).iterator();

            shock:
            while(true) {
                LivingEntity livingentity;
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
                            } while(livingentity == player);
                        } while(livingentity == target);
                    } while(player.isAlliedTo(livingentity));
                } while(livingentity instanceof ArmorStand && ((ArmorStand) livingentity).isMarker());

                livingentity.knockback(1.2, Mth.sin(player.getYRot() * 0.0175F), -Mth.cos(player.getYRot() * 0.0175F)); //todo knockback direction
                livingentity.hurt(player.damageSources().playerAttack(player), 0.5F);
            }
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
