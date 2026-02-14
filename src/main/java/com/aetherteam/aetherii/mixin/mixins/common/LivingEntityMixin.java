package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @ModifyArgs(method = "travelInAir(Lnet/minecraft/world/phys/Vec3;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(DDD)V"))
    private void travelInAir(Args args) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        boolean flag = livingEntity.getDeltaMovement().y <= 0.0;
        if (flag && livingEntity.isUsingItem() && livingEntity.getUseItem().is(AetherIITags.Items.TOOLS_GLIDERS)) {
            args.set(1, -0.08);
        }
    }
}
