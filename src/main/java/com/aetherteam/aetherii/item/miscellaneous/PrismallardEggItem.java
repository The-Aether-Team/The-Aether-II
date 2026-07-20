package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.projectile.ArcticSnowball;
import com.aetherteam.aetherii.entity.projectile.ThrownPrismallardEgg;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

public class PrismallardEggItem extends Item implements ProjectileItem, ThrowableItem {
    public PrismallardEggItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        this.throwItem(stack, level, livingEntity, timeLeft, AetherIISoundEvents.ENTITY_PRISMALLARD_EGG_THROW.get(), new ThrownPrismallardEgg(level, livingEntity, stack));
        return true;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity livingEntity) {
        return 72000;
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        return new ThrownPrismallardEgg(level, position.x(), position.y(), position.z(), itemStack);
    }
}