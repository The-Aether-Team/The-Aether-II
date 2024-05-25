package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.AetherIIToolActions;
import com.aetherteam.aetherii.mixin.mixins.MixinHooks;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(Player.class)
public class PlayerMixin { //todo sounds, particles, and stats
    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"), method = "attack(Lnet/minecraft/world/entity/Entity;)V")
    private ItemStack getItemInHand(ItemStack original, @Share("canSpearStab") LocalBooleanRef canSpearStab, @Share("canHammerShock") LocalBooleanRef canHammerShock) {
        canSpearStab.set(original.canPerformAction(AetherIIToolActions.SPEAR_STAB));
        canHammerShock.set(original.canPerformAction(AetherIIToolActions.HAMMER_SHOCK));
        return original;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setLastHurtMob(Lnet/minecraft/world/entity/Entity;)V", shift = At.Shift.BEFORE), method = "attack(Lnet/minecraft/world/entity/Entity;)V")
    private void attack(Entity target, CallbackInfo ci, @Share("canSpearStab") LocalBooleanRef canSpearStab, @Share("canHammerShock") LocalBooleanRef canHammerShock) {
        Player player = (Player) (Object) this;
        MixinHooks.stabBehavior(player, target, canSpearStab.get());
        MixinHooks.shockBehavior(player, target, canHammerShock.get());
    }


}
