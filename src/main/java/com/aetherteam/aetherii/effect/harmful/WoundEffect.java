package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.Map;

public class WoundEffect extends MobEffect {
    private static final Map<EntityType<?>, Float> DAMAGE_AMOUNT = new ImmutableMap.Builder<EntityType<?>, Float>()
            .put(EntityType.PLAYER, 4.0F)
            .build();

    public WoundEffect() {
        super(MobEffectCategory.HARMFUL, 0xC82B28);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity livingEntity, int amplifier) {
        float damageValue = DAMAGE_AMOUNT.getOrDefault(livingEntity.getType(), 5.0F);
        livingEntity.hurtServer(serverLevel, AetherIIDamageTypes.damageSource(livingEntity.level(), AetherIIDamageTypes.WOUND), damageValue);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int remainingDuration, int amplification) {
        return remainingDuration == 1;
    }
}
