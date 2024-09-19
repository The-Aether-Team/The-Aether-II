package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class VenomEffect extends MobEffect {
    public VenomEffect() {
        super(MobEffectCategory.HARMFUL, 7289241);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity.getHealth() > 1.0F) {
            livingEntity.hurt(AetherIIDamageTypes.damageSource(livingEntity.level(), AetherIIDamageTypes.VENOM), 1.0F);
        }
        if (livingEntity instanceof Player player && player.getFoodData().getFoodLevel() > 1) {
            player.getFoodData().setSaturation(0.0F);
            player.causeFoodExhaustion(4.0F);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 25 >> amplifier;
        return i == 0 || duration % i == 0;
    }
}
