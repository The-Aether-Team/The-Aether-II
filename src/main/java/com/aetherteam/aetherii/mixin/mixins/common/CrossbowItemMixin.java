package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {
    @Invoker("addChargedProjectile")
    private static void aether_ii$addChargedProjectile(ItemStack crossbowStack, ItemStack ammo) {
        throw new AssertionError();
    }

    @WrapOperation(method = "use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CrossbowItem;getShootingPower(Lnet/minecraft/world/item/ItemStack;)F"))
    private float aether_ii$getShootingPower(ItemStack stack, Operation<Float> original) {
        if (stack.getItem() instanceof TieredCrossbowItem tieredCrossbowItem) {
            return tieredCrossbowItem.getCrossbowShootingPower(stack);
        }
        return original.call(stack);
    }

    @Inject(method = "tryLoadProjectiles(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
    private static void aether_ii$tryLoadProjectiles(LivingEntity shooter, ItemStack crossbowStack, CallbackInfoReturnable<Boolean> cir) {
        if (!(crossbowStack.getItem() instanceof TieredCrossbowItem tieredCrossbowItem)) {
            return;
        }

        int multishotLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, crossbowStack);
        int projectileCount = multishotLevel == 0 ? 1 : 3;
        ServerLevel serverLevel = shooter.level() instanceof ServerLevel level ? level : null;
        projectileCount = tieredCrossbowItem.getProjectileCount(serverLevel, crossbowStack, shooter, projectileCount);

        boolean creative = shooter instanceof Player player && player.getAbilities().instabuild;
        ItemStack projectileStack = shooter.getProjectile(crossbowStack);
        ItemStack originalProjectileStack = projectileStack.copy();

        for (int i = 0; i < projectileCount; ++i) {
            if (i > 0) {
                projectileStack = originalProjectileStack.copy();
            }

            if (projectileStack.isEmpty() && creative && shooter instanceof Player player) {
                projectileStack = tieredCrossbowItem.getDefaultCreativeAmmo(player, crossbowStack);
                originalProjectileStack = projectileStack.copy();
            }

            if (!aether_ii$loadProjectile(shooter, crossbowStack, projectileStack, i > 0, creative)) {
                cir.setReturnValue(false);
                return;
            }
        }

        cir.setReturnValue(true);
    }

    @WrapOperation(method = "performShooting(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CrossbowItem;shootProjectile(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;FZFFF)V"))
    private static void aether_ii$shootProjectile(Level level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, ItemStack ammo, float pitch, boolean creative, float velocity, float inaccuracy, float projectileAngle, Operation<Void> original) {
        if (weapon.getItem() instanceof TieredCrossbowItem tieredCrossbowItem) {
            ServerLevel serverLevel = level instanceof ServerLevel server ? server : null;
            float spread = tieredCrossbowItem.getProjectileSpread(serverLevel, weapon, shooter, Math.abs(projectileAngle));
            projectileAngle = projectileAngle == 0.0F ? 0.0F : Math.copySign(spread, projectileAngle);
        }
        original.call(level, shooter, hand, weapon, ammo, pitch, creative, velocity, inaccuracy, projectileAngle);
    }

    private static boolean aether_ii$loadProjectile(LivingEntity shooter, ItemStack crossbowStack, ItemStack projectileStack, boolean multishotCopy, boolean creative) {
        if (projectileStack.isEmpty()) {
            return false;
        }

        boolean creativeArrow = creative && projectileStack.getItem() instanceof ArrowItem;
        ItemStack loadedProjectile;
        if (!creativeArrow && !creative && !multishotCopy) {
            loadedProjectile = projectileStack.split(1);
            if (projectileStack.isEmpty() && shooter instanceof Player player) {
                player.getInventory().removeItem(projectileStack);
            }
        } else {
            loadedProjectile = projectileStack.copy();
        }

        aether_ii$addChargedProjectile(crossbowStack, loadedProjectile);
        return true;
    }
}
