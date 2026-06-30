package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;
import java.util.Map;

public class WoundEffect extends InstantenousMobEffect {
    private static final Map<EntityType<?>, Float> DAMAGE_AMOUNT = new ImmutableMap.Builder<EntityType<?>, Float>()
            .put(EntityType.PLAYER, 4.0F)
            .build();

    public WoundEffect() {
        super(MobEffectCategory.HARMFUL, 0xC82B28);
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity trueSource, LivingEntity livingEntity, int amplifier, double distance) {
        float damageValue = DAMAGE_AMOUNT.getOrDefault(livingEntity.getType(), 5.0F);
        if (source == null) {
            livingEntity.hurt(AetherIIDamageTypes.damageSource(livingEntity.level(), AetherIIDamageTypes.WOUND), damageValue);
        } else {
            livingEntity.hurt(AetherIIDamageTypes.indirectEntityDamageSource(livingEntity.level(), AetherIIDamageTypes.WOUND, source, trueSource), damageValue);
        }
    }
}
