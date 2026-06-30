package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.living.EffectsSystemAttachment;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.MobEffectInstanceAccessor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;

public class WebbedEffect extends MobEffect {
    public WebbedEffect() {
        super(MobEffectCategory.HARMFUL, 0xC7C5BB);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity.getDeltaMovement().length() > 1.5) {
            livingEntity.removeEffect(AetherIIMobEffects.WEBBED.get());
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public static void onEntityPostTick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity.hasEffect(AetherIIMobEffects.WEBBED.get())) {
            EffectsSystemAttachment attachment = AetherIIDataAttachments.get(livingEntity, AetherIIDataAttachments.EFFECTS_SYSTEM);
            attachment.setMotionMultiplier(attachment.getMotionMultiplier().multiply(new Vec3(0.1, 1.0, 0.1)));
        }
    }

    public static void reduceByJumping(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance instance = entity.getEffect(AetherIIMobEffects.WEBBED.get());
        if (instance != null) {
            ((MobEffectInstanceAccessor) instance).aether_ii$setDuration(Math.max(0, instance.mapDuration(mapper -> mapper - 10)));
        }
    }
}
