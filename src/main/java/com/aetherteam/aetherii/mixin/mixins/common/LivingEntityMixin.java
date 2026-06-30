package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.advancement.trigger.AetherIIAdvancementTriggers;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import com.aetherteam.aetherii.item.components.TooltipDisplay;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "travel(Lnet/minecraft/world/phys/Vec3;)V", at = @At("TAIL"))
    private void travel(Vec3 travelVector, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        Vec3 movement = livingEntity.getDeltaMovement();
        if (!livingEntity.onGround() && !livingEntity.isFallFlying() && !livingEntity.isInWater() && !livingEntity.isInLava() && movement.y < -0.08 && livingEntity.isUsingItem() && livingEntity.getUseItem().is(AetherIITags.Items.TOOLS_GLIDERS)) {
            livingEntity.setDeltaMovement(movement.x, -0.08, movement.z);
        }
    }

    @WrapOperation(method = "causeFallDamage(FFLnet/minecraft/world/damagesource/DamageSource;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;calculateFallDamage(FF)I"))
    private int causeFallDamage(LivingEntity livingEntity, float fallDistance, float damageModifier, Operation<Integer> original) {
        int damage = original.call(livingEntity, fallDistance, damageModifier);
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            AetherIIAdvancementTriggers.FALL_ON_GROUND.get().trigger(serverPlayer, fallDistance, damage);
        }
        return damage;
    }
}
