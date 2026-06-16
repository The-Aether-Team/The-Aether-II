package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.living.EffectsSystemAttachment;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
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
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.Map;

public class ImmolationEffect extends MobEffect {
    private static final Map<EntityType<?>, Float> DAMAGE_AMOUNT = new ImmutableMap.Builder<EntityType<?>, Float>()
            .put(EntityType.PLAYER, 10.0F)
            .build();

    public ImmolationEffect() {
        super(MobEffectCategory.HARMFUL, 0xFF872B);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity livingEntity, int amplifier) {
        for (Entity entity : serverLevel.getEntities(livingEntity, AABB.ofSize(livingEntity.position(), 5, 5, 5), (entity) -> entity instanceof LivingEntity living && !living.hasEffect(AetherIIMobEffects.IMMOLATION))) {
            if (entity instanceof LivingEntity living) {
                living.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(living, EffectBuildupPresets.IMMOLATION, 20);
            }
        }
        if (livingEntity.tickCount % 10 == 0) {
            if (livingEntity.getHealth() >= livingEntity.getMaxHealth() - DAMAGE_AMOUNT.getOrDefault(livingEntity.getType(), 10.0F)) {
                livingEntity.hurt(AetherIIDamageTypes.damageSource(livingEntity.level(), AetherIIDamageTypes.IMMOLATION), 1.0F);
            }
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
        if (livingEntity.isInWater()) {
            livingEntity.removeEffect(AetherIIMobEffects.IMMOLATION);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    public static void onEntityPostTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            EffectsSystemAttachment attachment = livingEntity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM);
            if (attachment.hasBuildup(AetherIIMobEffects.IMMOLATION)) {
                if (livingEntity.isInWater()) {
                    attachment.removeBuildup(AetherIIMobEffects.IMMOLATION);
                }
            }
        }
    }
}
