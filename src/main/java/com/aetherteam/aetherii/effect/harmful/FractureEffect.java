package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.living.EffectsSystemAttachment;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.MobEffectInstanceAccessor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;

public class FractureEffect extends MobEffect {
    public FractureEffect() {
        super(MobEffectCategory.HARMFUL, 0xD6D2B4);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity.isSprinting()) {
            livingEntity.hurt(AetherIIDamageTypes.damageSource(livingEntity.level(), AetherIIDamageTypes.FRACTURE), 1.0F);
            livingEntity.setSprinting(false);
        }
        if (livingEntity.isCrouching() && livingEntity.getDeltaMovement().x() == 0 && livingEntity.getDeltaMovement().z() == 0) {
            MobEffectInstance instance = livingEntity.getEffect(AetherIIMobEffects.FRACTURE.get());
            if (instance != null) {
                ((MobEffectInstanceAccessor) instance).aether_ii$setDuration(Math.max(0, instance.mapDuration(mapper -> mapper - 4)));
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public static void onEntityPostTick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity.hasEffect(AetherIIMobEffects.FRACTURE.get())) {
            EffectsSystemAttachment attachment = AetherIIDataAttachments.get(livingEntity, AetherIIDataAttachments.EFFECTS_SYSTEM);
            attachment.setMotionMultiplier(attachment.getMotionMultiplier().multiply(new Vec3(0.7, 1.0, 0.7)));
        }
    }
}
