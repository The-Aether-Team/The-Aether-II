package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

import java.util.Map;

public class ImmolationEffect extends MobEffect { //todo preventative measures
    private static final Map<EntityType<?>, Float> DAMAGE_AMOUNT = new ImmutableMap.Builder<EntityType<?>, Float>()
            .put(EntityType.PLAYER, 10.0F)
            .build();

    public ImmolationEffect() {
        super(MobEffectCategory.HARMFUL, 16746283);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity livingEntity, int amplifier) {
        for (Entity entity : serverLevel.getEntities(livingEntity, AABB.ofSize(livingEntity.position(), 5, 5, 5), (entity) -> entity instanceof LivingEntity living && !living.hasEffect(AetherIIEffects.IMMOLATION))) {
            if (entity instanceof LivingEntity living) {
                living.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(EffectBuildupPresets.IMMOLATION, 100);
            }
        }
        if (livingEntity.getHealth() >= livingEntity.getMaxHealth() - DAMAGE_AMOUNT.getOrDefault(livingEntity.getType(), 10.0F)) { //todo possibly duration based.
            livingEntity.hurt(AetherIIDamageTypes.damageSource(livingEntity.level(), AetherIIDamageTypes.IMMOLATION), 1.0F);
        }
        serverLevel.sendParticles(ParticleTypes.FLAME,
                livingEntity.getX() + (serverLevel.getRandom().nextGaussian() / 5.0),
                livingEntity.getY() + (serverLevel.getRandom().nextGaussian() / 3.0),
                livingEntity.getZ() + (serverLevel.getRandom().nextGaussian() / 5.0),
                2, 0.0, 0.0, 0.0, 0.0F);
        serverLevel.sendParticles(ParticleTypes.SMOKE,
                livingEntity.getX() + (serverLevel.getRandom().nextGaussian() / 5.0),
                livingEntity.getY() + (serverLevel.getRandom().nextGaussian() / 3.0),
                livingEntity.getZ() + (serverLevel.getRandom().nextGaussian() / 5.0),
                2, 0.0, 0.0, 0.0, 0.0F);
        if (livingEntity.isInWaterRainOrBubble()) {
            livingEntity.removeEffect(AetherIIEffects.IMMOLATION);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
