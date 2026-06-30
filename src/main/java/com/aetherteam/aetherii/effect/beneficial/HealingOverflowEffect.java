package com.aetherteam.aetherii.effect.beneficial;

import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;

import java.util.function.Consumer;

public class HealingOverflowEffect extends MobEffect {
    public HealingOverflowEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFCD400);
    }

    @Override
    public void applyEffectTick(LivingEntity mob, int amplification) {
        if (mob.getAbsorptionAmount() <= 0.0F) {
            mob.removeEffect(AetherIIMobEffects.HEALING_OVERFLOW.get());
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new IClientMobEffectExtensions() {
            @Override
            public boolean isVisibleInInventory(MobEffectInstance instance) {
                return false;
            }

            @Override
            public boolean isVisibleInGui(MobEffectInstance instance) {
                return false;
            }
        });
    }
}
