package com.aetherteam.aetherii.mixin;

import com.aetherteam.aetherii.entity.AetherIIAttributes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class MixinHooks {
    public static void shortswordSlashBehavior(Player player, Entity target, boolean canShortswordSlash) {
        if (canShortswordSlash) {
            float slashRange = (float) player.getAttributeValue(AetherIIAttributes.SLASH_RANGE.get());
            if (slashRange > 0) {
                for (LivingEntity other : player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(slashRange, slashRange, slashRange))) {
                    if (other != player && other != target && !player.isAlliedTo(other) && (!(other instanceof ArmorStand armorStand) || !armorStand.isMarker())) {
                        other.knockback(0.4F, Mth.sin(player.getYRot() * (float) (Math.PI / 180.0)), -Mth.cos(player.getYRot() * (float) (Math.PI / 180.0)));
                        other.hurt(player.damageSources().playerAttack(player), 1.0F);
                    }
                }
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);
                sweepAttack(player);
            }
        }
    }

    private static void sweepAttack(Player player) {
        double d0 = -Mth.sin(player.getYRot() * 0.0175F);
        double d1 = Mth.cos(player.getYRot() * 0.0175F);
        if (player.level() instanceof ServerLevel) {
            ((ServerLevel) player.level()).sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX() + d0, player.getY(0.5), player.getZ() + d1, 0, d0, 0.0, d1, 0.0);
        }
    }

    public static void spearStabBehavior(Player player, Entity target, boolean canSpearStab) {
        if (canSpearStab) {
            float stabRadius = (float) player.getAttributeValue(AetherIIAttributes.STAB_RADIUS.get());
            float stabDistance = (float) player.getAttributeValue(AetherIIAttributes.STAB_DISTANCE.get());
            if (stabRadius > 0 || stabDistance > 0) {
                for (LivingEntity other : player.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(stabDistance, stabDistance, stabDistance), (other) -> withinStabDistance(player, target, other, stabRadius, stabDistance))) {
                    if (other != player && other != target && !player.isAlliedTo(other) && (!(other instanceof ArmorStand armorStand) || !armorStand.isMarker())) {
                        other.knockback(0.1, Mth.sin(player.getYRot() * 0.0175F), -Mth.cos(player.getYRot() * 0.0175F));
                        other.hurt(player.damageSources().playerAttack(player), 2.0F);
                    }
                }
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);//todo
                stabAttack(player);
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
            float shockRange = (float) player.getAttributeValue(AetherIIAttributes.SHOCK_RANGE.get());
            if (shockRange > 0) {
                for (LivingEntity other : player.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(shockRange, shockRange, shockRange))) {
                    if (other != player && other != target && !player.isAlliedTo(other) && (!(other instanceof ArmorStand armorStand) || !armorStand.isMarker())) {
                        double x = target.position().x() - other.position().x();
                        double z = target.position().z() - other.position().z();
                        if (x > 0) {
                            x = 5;
                        } else if (x < 0) {
                            x = -5;
                        }
                        if (z > 0) {
                            z = 5;
                        } else if (z < 0) {
                            z = -5;
                        }

                        other.knockback(1.0, x, z);
                        other.hurt(player.damageSources().playerAttack(player), 0.5F);
                    }
                }
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);//todo
                shockAttack(player);
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
