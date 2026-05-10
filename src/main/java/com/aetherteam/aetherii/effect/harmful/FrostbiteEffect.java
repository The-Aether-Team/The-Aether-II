package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.living.EffectsSystemAttachment;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class FrostbiteEffect extends MobEffect { //todo hot block proximity check system
    public FrostbiteEffect() {
        super(MobEffectCategory.HARMFUL, 0x428B96);
    }

    public static void onEntityPostTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(AetherIIMobEffects.WEBBED)) {
            EffectsSystemAttachment attachment = livingEntity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM);
            attachment.setMotionMultiplier(attachment.getMotionMultiplier().multiply(new Vec3(0.8, 1.0, 0.8)));
        }
    }
}
