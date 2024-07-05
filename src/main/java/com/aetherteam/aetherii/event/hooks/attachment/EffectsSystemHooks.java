package com.aetherteam.aetherii.event.hooks.attachment;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.world.entity.LivingEntity;

public class EffectsSystemHooks {
    public static void tickEffectsSystemAttachment(LivingEntity livingEntity) {
        livingEntity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).tick();
    }
}
