package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.AetherIIToolActions;
import com.aetherteam.aetherii.mixin.MixinHooks;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.Map;

@Mixin(Player.class)
public class PlayerMixin { //todo sounds, particles, and stats
    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"), method = "attack(Lnet/minecraft/world/entity/Entity;)V")
    private ItemStack getItemInHand(ItemStack original, @Share("canShortswordSlash") LocalBooleanRef canShortswordSlash, @Share("canHammerShock") LocalBooleanRef canHammerShock, @Share("canSpearStab") LocalBooleanRef canSpearStab) {
        canShortswordSlash.set(original.canPerformAction(AetherIIToolActions.SHORTSWORD_SLASH));
        canHammerShock.set(original.canPerformAction(AetherIIToolActions.HAMMER_SHOCK));
        canSpearStab.set(original.canPerformAction(AetherIIToolActions.SPEAR_STAB));
        return original;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setLastHurtMob(Lnet/minecraft/world/entity/Entity;)V", shift = At.Shift.BEFORE), method = "attack(Lnet/minecraft/world/entity/Entity;)V")
    private void attack(Entity target, CallbackInfo ci, @Share("canShortswordSlash") LocalBooleanRef canShortswordSlash, @Share("canHammerShock") LocalBooleanRef canHammerShock, @Share("canSpearStab") LocalBooleanRef canSpearStab) {
        Player player = (Player) (Object) this;
        MixinHooks.shortswordSlashBehavior(player, target, canShortswordSlash.get());
        MixinHooks.hammerShockBehavior(player, target, canHammerShock.get());
        MixinHooks.spearStabBehavior(player, target, canSpearStab.get());
    }

    @Inject(at = @At(value = "HEAD"), method = "tick()V")
    private void tick(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        BlockPos pos = player.blockPosition();
        AetherII.cachedDynamicLightPoints.put(player, pos, 12); //todo optimization
        if (AetherII.cachedDynamicLightPoints.containsColumn(player)) {
            AetherII.cachedDynamicLightPoints.row(player).entrySet().removeIf((e) -> e.getKey() != pos);
        }
    }
}
