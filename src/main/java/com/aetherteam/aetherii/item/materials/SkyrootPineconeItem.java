package com.aetherteam.aetherii.item.materials;

import com.aetherteam.aetherii.entity.projectile.SkyrootPinecone;
import com.aetherteam.aetherii.item.miscellaneous.ThrowableItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;

public class SkyrootPineconeItem extends Item implements ThrowableItem {
    public SkyrootPineconeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResult.CONSUME.heldItemTransformedTo(stack); //todo ?
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        this.throwItem(stack, level, livingEntity, timeLeft, SoundEvents.SNOWBALL_THROW, new SkyrootPinecone(level, livingEntity));
        return super.releaseUsing(stack, level, livingEntity, timeLeft); //todo ?
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack pStack) {
        return ItemUseAnimation.NONE; //todo
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity livingEntity) {
        return 72000;
    }
}