package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.EffectCure;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class WoundEffect extends InstantenousMobEffect {
    private static final Map<EntityType<?>, Float> DAMAGE_AMOUNT = new ImmutableMap.Builder<EntityType<?>, Float>()
            .put(EntityType.PLAYER, 4.0F)
            .build();

    public WoundEffect() {
        super(MobEffectCategory.HARMFUL, 13118248);
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity livingEntity, int amplifier, double health) {
        float damageValue = DAMAGE_AMOUNT.getOrDefault(livingEntity.getType(), 5.0F);
        livingEntity.hurt(AetherIIDamageTypes.indirectEntityDamageSource(livingEntity.level(), AetherIIDamageTypes.WOUND, source, indirectSource), damageValue);
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
        cures.clear();
    }
}
