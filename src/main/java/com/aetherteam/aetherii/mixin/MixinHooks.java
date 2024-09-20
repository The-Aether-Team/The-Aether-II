package com.aetherteam.aetherii.mixin;

import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.renderer.level.HighlandsSpecialEffects;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import com.aetherteam.aetherii.entity.AetherIIAttributes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
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
            float sweepRange = (float) player.getAttributeValue(AetherIIAttributes.SWEEP_RANGE);
            if (sweepRange > 0) {
                for (LivingEntity other : player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(sweepRange, sweepRange, sweepRange))) {
                    if (other != player && other != target && !player.isAlliedTo(other) && (!(other instanceof ArmorStand armorStand) || !armorStand.isMarker())) {
                        float sweepKnockback = (float) player.getAttributeValue(AetherIIAttributes.SWEEP_KNOCKBACK);
                        float sweepDamage = (float) player.getAttributeValue(AetherIIAttributes.SWEEP_DAMAGE);
                        if (sweepKnockback > 0) {
                            other.knockback(sweepKnockback, Mth.sin(player.getYRot() * (float) (Math.PI / 180.0)) * 2.5 * sweepKnockback, -Mth.cos(player.getYRot() * (float) (Math.PI / 180.0)) * 2.5 * sweepKnockback);
                        }
                        if (sweepDamage > 0) {
                            other.hurt(AetherIIDamageTypes.playerAoe(player.level(), player), sweepDamage);
                        }
                    }
                }
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);
                sweepAttack(player);
            }
        }
    }

    private static void sweepAttack(Player player) {
        double d0 = -Mth.sin(player.getYRot() * (float) (Math.PI / 180.0));
        double d1 = Mth.cos(player.getYRot() * (float) (Math.PI / 180.0));
        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX() + d0, player.getY(0.5), player.getZ() + d1, 0, d0, 0.0, d1, 0.0);
        }
    }

    public static void hammerShockBehavior(Player player, Entity target, boolean canHammerShock) {
        if (canHammerShock) {
            float shockRange = (float) player.getAttributeValue(AetherIIAttributes.SHOCK_RANGE);
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

                        float shockKnockback = (float) player.getAttributeValue(AetherIIAttributes.SHOCK_KNOCKBACK);
                        float shockDamage = (float) player.getAttributeValue(AetherIIAttributes.SHOCK_DAMAGE);
                        if (shockKnockback > 0) {
                            other.knockback(shockKnockback, x * shockKnockback, z * shockKnockback);
                        }
                        if (shockDamage > 0) {
                            other.hurt(AetherIIDamageTypes.playerAoe(player.level(), player), shockDamage);
                        }
                    }
                }
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), AetherIISoundEvents.PLAYER_ATTACK_SHOCK.get(), player.getSoundSource(), 1.0F, 1.0F);
                shockAttack(player, target);
            }
        }
    }

    private static void shockAttack(Player player, Entity target) {
        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, target.getX(), target.getY(0.5), target.getZ(), 0, 0.0, 0.0, 0.0, 0.0);//todo
        }
    }

    public static void spearStabBehavior(Player player, Entity target, boolean canSpearStab) {
        if (canSpearStab) {
            float stabRadius = (float) player.getAttributeValue(AetherIIAttributes.STAB_RADIUS);
            float stabDistance = (float) player.getAttributeValue(AetherIIAttributes.STAB_DISTANCE);
            if (stabRadius > 0 || stabDistance > 0) {
                for (LivingEntity other : player.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(stabDistance, stabDistance, stabDistance), (other) -> withinStabDistance(player, target, other, stabRadius, stabDistance))) {
                    if (other != player && other != target && !player.isAlliedTo(other) && (!(other instanceof ArmorStand armorStand) || !armorStand.isMarker())) {
                        float stabKnockback = (float) player.getAttributeValue(AetherIIAttributes.STAB_KNOCKBACK);
                        float stabDamage = (float) player.getAttributeValue(AetherIIAttributes.STAB_DAMAGE);
                        if (stabKnockback > 0) {
                            other.knockback(stabKnockback, Mth.sin(player.getYRot() * (float) (Math.PI / 180.0)) * 2.5 * stabKnockback, -Mth.cos(player.getYRot() * (float) (Math.PI / 180.0)) * 2.5 * stabKnockback);
                        }
                        if (stabDamage > 0) {
                            other.hurt(AetherIIDamageTypes.playerAoeNoKnockback(player.level(), player), stabDamage);
                        }
                    }
                }
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), AetherIISoundEvents.PLAYER_ATTACK_STAB.get(), player.getSoundSource(), 1.0F, 1.0F);
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
        double d0 = -Mth.sin(player.getYRot() * (float) (Math.PI / 180.0)) * 0.5;
        double d1 = Mth.cos(player.getYRot() * (float) (Math.PI / 180.0)) * 0.5;
        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX() + d0, player.getY(0.5), player.getZ() + d1, 0, d0, 0.0, d1, 0.0);//todo
        }
    }

    public static ParticleOptions replaceSplashParticles(Entity entity, ParticleOptions particleOptions) {
        if (entity.level() instanceof ClientLevel clientLevel && clientLevel.effects() instanceof HighlandsSpecialEffects) {
            if (particleOptions == ParticleTypes.SPLASH) {
                return AetherIIParticleTypes.SPLASH.get();
            }
        }
        return particleOptions;
    }
}
