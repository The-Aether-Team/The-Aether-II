package com.aetherteam.aetherii.item.equipment.weapons;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class DartShooterItem extends ProjectileWeaponItem {
    public static final int FIRE_RATE = 4;

    public DartShooterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (isLoaded(stack) && isCharged(stack)) {
            player.startUsingItem(hand);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int count) {
        InteractionHand hand = livingEntity.getUsedItemHand();
        if (!level.isClientSide()) {
            if (isLoaded(stack) && isCharged(stack)) {
                float f = (float) (stack.getUseDuration(livingEntity) - count) / 5.0F;
                this.performShooting(level, livingEntity, hand, stack, 1.0F, f, null);
            }
        }
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int count) {
        int i = this.getUseDuration(stack, livingEntity) - count;
        return getPowerForTime(i, stack, livingEntity) >= 1.0F && isCharged(stack);
    }

    @Override
    public boolean useOnRelease(ItemStack stack) {
        return true;
    }

    private void performShooting(Level level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, float velocity, float inaccuracy, @Nullable LivingEntity target) {
        if (level instanceof ServerLevel serverlevel) {
            if (shooter instanceof Player player) {
                if (EventHooks.onArrowLoose(weapon, shooter.level(), player, 1, true) < 0) {
                    return;
                }
            }
            if (shooter.tickCount % FIRE_RATE == 0) {
                ChargedProjectiles projectiles = weapon.get(DataComponents.CHARGED_PROJECTILES);
                if (projectiles != null && !projectiles.isEmpty()) {
                    this.shoot(serverlevel, shooter, hand, weapon, projectiles.getItems(), velocity, inaccuracy, false, target);
                    weapon.set(AetherIIDataComponents.DARTS_LOADED, getDartsLoaded(weapon) - 1);
                    if (!isLoaded(weapon)) {
                        weapon.hurtAndBreak(1, shooter, LivingEntity.getSlotForHand(hand));
                        weapon.remove(DataComponents.CHARGED_PROJECTILES);
                        weapon.remove(AetherIIDataComponents.BUILDUP_CONTENTS);
                        weapon.remove(AetherIIDataComponents.DARTS_LOADED);
                    }
                }
            }
        }
    }

    @Override
    protected void shootProjectile(LivingEntity livingEntity, Projectile projectile, int i, float v, float v1, float v2, @Nullable LivingEntity livingEntity1) {
        projectile.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot() + v2, 0.0F, v, v1);
    }

    private static float getPowerForTime(int timeLeft, ItemStack stack, LivingEntity shooter) {
        return Math.min((float) timeLeft / (float) getChargeDuration(stack, shooter), 1.0F);
    }

    public static int getChargeDuration(ItemStack stack, LivingEntity shooter) {
        return Mth.floor(1.25F * 20.0F);
    }

    public static boolean isCharged(ItemStack stack) {
        return !stack.getOrDefault(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY).isEmpty();
    }

    public static boolean isLoaded(ItemStack stack) {
        return getDartsLoaded(stack) > 0;
    }

    public static int getDartsLoaded(ItemStack stack) {
        return stack.getOrDefault(AetherIIDataComponents.DARTS_LOADED, 0);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return (stack) -> false;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity livingEntity) {
        return AmberDartsItem.FULL_AMOUNT * FIRE_RATE;
    }

    @Override
    protected int getDurabilityUse(ItemStack stack) {
        return 0;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return (isCharged(stack) && isLoaded(stack)) || super.isBarVisible(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        if (isCharged(stack) && isLoaded(stack)) {
            return ARGB.colorFromFloat(1.0F, 0.973F, 0.71F, 0.184F);
        }
        return super.getBarColor(stack);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        if (isCharged(stack) && isLoaded(stack)) {
            return Mth.ceil(((float) getDartsLoaded(stack) / (float) AmberDartsItem.FULL_AMOUNT) * 13);
        }
        return super.getBarWidth(stack);
    }
}
