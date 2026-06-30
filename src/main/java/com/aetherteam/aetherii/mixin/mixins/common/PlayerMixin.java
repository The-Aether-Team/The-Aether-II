package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.entity.passive.MountableAetherAnimal;
import com.aetherteam.aetherii.item.SpecialAttackStrengthScale;
import com.aetherteam.aetherii.item.equipment.AetherIINeoItemAbilities;
import com.aetherteam.aetherii.mixin.MixinHooks;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.LivingEntityAccessor;
import com.aetherteam.aetherii.mixin.wrappers.common.ItemCooldownsWrapper;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Mutable
    @Final
    @Shadow
    private ItemCooldowns cooldowns;

    @Shadow
    protected abstract boolean wantsToStopRiding();

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(Level level, BlockPos pos, float yRot, GameProfile gameProfile, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        ItemCooldowns itemCooldowns = this.cooldowns;
        itemCooldowns = ((ItemCooldownsWrapper) itemCooldowns).aether_ii$setPlayer(player);
        this.cooldowns = itemCooldowns;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;doPostDamageEffects(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/Entity;)V", shift = At.Shift.AFTER), method = "attack(Lnet/minecraft/world/entity/Entity;)V")
    private void attack(Entity target, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        ItemStack weapon = player.getMainHandItem();
        MixinHooks.shortswordSlashBehavior(player, target, weapon.canPerformAction(AetherIINeoItemAbilities.SHORTSWORD_SLASH));
        MixinHooks.hammerShockBehavior(player, target, weapon.canPerformAction(AetherIINeoItemAbilities.HAMMER_SHOCK));
        MixinHooks.pikeStabBehavior(player, target, weapon.canPerformAction(AetherIINeoItemAbilities.PIKE_STAB));
    }

    /**
     * Used to set whether the player tried to crouch for {@link MountableAetherAnimal}, before crouching is cancelled for mounts by the {@link Player} class.
     *
     * @param ci The {@link CallbackInfo} for the void method return.
     */
    @Inject(at = @At(value = "HEAD"), method = "rideTick()V")
    private void rideTickHead(CallbackInfo ci, @Share("wantsToStopRiding") LocalBooleanRef wantsToStopRiding) {
        Player player = (Player) (Object) this;
        wantsToStopRiding.set(this.wantsToStopRiding());
        if (!player.level().isClientSide()) {
            if (player.isPassenger() && player.getVehicle() instanceof MountableAetherAnimal mount) {
                mount.setPlayerTriedToCrouch(player.isShiftKeyDown());
            }
        }
    }

    @Inject(at = @At(value = "TAIL"), method = "rideTick()V")
    private void rideTickTail(CallbackInfo ci, @Share("wantsToStopRiding") LocalBooleanRef wantsToStopRiding) {
        Player player = (Player) (Object) this;
        if (!player.level().isClientSide() && !player.isShiftKeyDown() && wantsToStopRiding.get()) {
            if (player.isPassenger() && player.getVehicle() instanceof MountableAetherAnimal) {
                player.setShiftKeyDown(true);
            }
        }
    }

    @Inject(method = "getAttackStrengthScale(F)F", at = @At("HEAD"), cancellable = true)
    private void getCurrentItemAttackStrengthDelay(float adjustTicks, CallbackInfoReturnable<Float> cir) {
        Player player = (Player) (Object) this;
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem() instanceof SpecialAttackStrengthScale specialAttackStrengthScale) {
            cir.setReturnValue(specialAttackStrengthScale.getAttackStrengthScale(player.level(), player, itemStack, adjustTicks, ((LivingEntityAccessor) player).aether$getAttackStrengthTicker()));
        }
    }
}
