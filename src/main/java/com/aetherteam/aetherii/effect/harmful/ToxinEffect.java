package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ToxinEffect extends MobEffect {
    public ToxinEffect() {
        super(MobEffectCategory.HARMFUL, 7720557);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity livingEntity, int amplifier) {
        if (livingEntity.getHealth() > 1.0F) {
            livingEntity.hurt(AetherIIDamageTypes.damageSource(livingEntity.level(), AetherIIDamageTypes.TOXIN), 1.0F);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 25 >> amplifier;
        return i == 0 || duration % i == 0;
    }
}
