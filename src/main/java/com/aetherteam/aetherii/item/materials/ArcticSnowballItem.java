package com.aetherteam.aetherii.item.materials;

import com.aetherteam.aetherii.client.AetherIIClientItemExtensions;
import com.aetherteam.aetherii.entity.projectile.ArcticSnowball;
import com.aetherteam.aetherii.item.miscellaneous.ThrowableItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class ArcticSnowballItem extends SnowballItem implements ThrowableItem {
    public ArcticSnowballItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        this.throwItem(stack, level, livingEntity, timeLeft, SoundEvents.SNOWBALL_THROW, new ArcticSnowball(level, livingEntity));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.CUSTOM;
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity livingEntity) {
        return 72000;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(AetherIIClientItemExtensions.throwable);
    }
}