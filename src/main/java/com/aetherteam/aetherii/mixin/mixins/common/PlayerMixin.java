package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.item.AetherIIToolActions;
import com.aetherteam.aetherii.mixin.MixinHooks;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin { //todo sounds, particles, and stats
    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"), method = "attack(Lnet/minecraft/world/entity/Entity;)V")
    private ItemStack getItemInHand(ItemStack original, @Share("canShortswordSlash") LocalBooleanRef canShortswordSlash, @Share("canSpearStab") LocalBooleanRef canSpearStab, @Share("canHammerShock") LocalBooleanRef canHammerShock) {
        canShortswordSlash.set(original.canPerformAction(AetherIIToolActions.SHORTSWORD_SLASH));
        canSpearStab.set(original.canPerformAction(AetherIIToolActions.SPEAR_STAB));
        canHammerShock.set(original.canPerformAction(AetherIIToolActions.HAMMER_SHOCK));
        return original;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setLastHurtMob(Lnet/minecraft/world/entity/Entity;)V", shift = At.Shift.BEFORE), method = "attack(Lnet/minecraft/world/entity/Entity;)V")
    private void attack(Entity target, CallbackInfo ci, @Share("canShortswordSlash") LocalBooleanRef canShortswordSlash, @Share("canSpearStab") LocalBooleanRef canSpearStab, @Share("canHammerShock") LocalBooleanRef canHammerShock) {
        Player player = (Player) (Object) this;
        MixinHooks.shortswordSlashBehavior(player, target, canShortswordSlash.get());
        MixinHooks.spearStabBehavior(player, target, canSpearStab.get());
        MixinHooks.hammerShockBehavior(player, target, canHammerShock.get());
    }
}
