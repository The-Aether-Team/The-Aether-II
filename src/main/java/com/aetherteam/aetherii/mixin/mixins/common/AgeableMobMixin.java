package com.aetherteam.aetherii.mixin.mixins.common;

import net.minecraft.world.entity.AgeableMob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AgeableMob.class)
public class AgeableMobMixin {
    @Inject(method = "aiStep()V", at = @At("TAIL"))
    private void aether_ii$lockAge(CallbackInfo ci) {
        AgeableMob mob = (AgeableMob) (Object) this;
        if (!mob.level().isClientSide() && mob.getPersistentData().getBoolean("aether_ii:age_locked") && mob.getAge() < 0) {
            mob.setAge(-24000);
        }
    }
}
