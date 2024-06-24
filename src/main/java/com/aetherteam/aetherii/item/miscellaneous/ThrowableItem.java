package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.projectile.HolystoneRock;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface ThrowableItem {
    default void throwItem(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft, SoundEvent sound, ThrowableItemProjectile projectile) {
        level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), sound, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)); //TODO SOUND
        if (!level.isClientSide()) {
            int i = stack.getUseDuration(livingEntity) - timeLeft;
            float velocity = Math.min(1.5F * (i / 20.0F), 1.5F);
            float inaccuracy = Mth.clamp(4.0F - (velocity * 2.0F), 1.0F, 4.0F);
            projectile.setItem(stack);
            projectile.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot(), 0.0F, velocity, inaccuracy);
            level.addFreshEntity(projectile);
        }
        if (level.isClientSide()) {
            livingEntity.swing(livingEntity.getUsedItemHand());
        }
        if (livingEntity instanceof Player player) {
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
                player.getCooldowns().addCooldown(stack.getItem(), 10);
            }
        }
    }
}
