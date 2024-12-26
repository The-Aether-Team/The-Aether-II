package com.aetherteam.aetherii.item.materials;

import com.aetherteam.aetherii.entity.projectile.HolystoneRock;
import com.aetherteam.aetherii.item.miscellaneous.ThrowableItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class RockItem extends BlockItem implements ThrowableItem {
    public RockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult interactionresult = this.place(new BlockPlaceContext(context));
        if (!interactionresult.consumesAction()) {
            InteractionResult interactionresult1 = this.use(context.getLevel(), context.getPlayer(), context.getHand());
            return interactionresult1 == InteractionResult.CONSUME ? InteractionResult.CONSUME : interactionresult1;
        } else {
            return interactionresult;
        }
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        this.throwItem(stack, level, livingEntity, timeLeft, SoundEvents.SNOWBALL_THROW, new HolystoneRock(level, livingEntity, stack));
        return true;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack pStack) {
        return ItemUseAnimation.NONE; //todo the use animations seem extensible now
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity livingEntity) {
        return 72000;
    }
}