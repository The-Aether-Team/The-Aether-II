package com.aetherteam.aetherii.effect.beneficial;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.living.EffectsSystemAttachment;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;

public class NaturalCamouflageEffect extends MobEffect {
    public NaturalCamouflageEffect() {
        super(MobEffectCategory.NEUTRAL, 0x81A76D);
    }

    public static void onEntityPostTick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity.hasEffect(AetherIIMobEffects.NATURAL_CAMOUFLAGE.get())) {
            EffectsSystemAttachment attachment = AetherIIDataAttachments.get(livingEntity, AetherIIDataAttachments.EFFECTS_SYSTEM);
            attachment.setMotionMultiplier(attachment.getMotionMultiplier().multiply(new Vec3(0.5, 1.0, 0.5)));
        }
    }

    public static void adjustVisibilityModifier(LivingEvent.LivingVisibilityEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(AetherIIMobEffects.NATURAL_CAMOUFLAGE.get())) {
            event.modifyVisibility(event.getVisibilityModifier() * 0.5F);
        }
    }
}
