package com.aetherteam.aetherii.effect.beneficial;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.living.EffectsSystemAttachment;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class NaturalCamouflageEffect extends MobEffect {
    public NaturalCamouflageEffect() {
        super(MobEffectCategory.NEUTRAL, 0x81A76D);
    }

    public static void onEntityPostTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(AetherIIMobEffects.NATURAL_CAMOUFLAGE)) {
            EffectsSystemAttachment attachment = livingEntity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM);
            attachment.setMotionMultiplier(attachment.getMotionMultiplier().multiply(new Vec3(0.5, 1.0, 0.5)));
        }
    }

    public static void adjustVisibilityModifier(LivingEvent.LivingVisibilityEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(AetherIIMobEffects.NATURAL_CAMOUFLAGE)) {
            event.modifyVisibility(event.getVisibilityModifier() * 0.5F);
        }
    }
}
