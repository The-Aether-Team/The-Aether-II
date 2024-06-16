package com.aetherteam.aetherii.item.materials;

import com.aetherteam.aetherii.client.AetherIIClientItemExtensions;
import com.aetherteam.aetherii.entity.projectile.HolystoneRock;
import com.aetherteam.aetherii.entity.projectile.SkyrootPinecone;
import com.aetherteam.aetherii.item.miscellaneous.ThrowableItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class SkyrootPineconeItem extends Item implements ThrowableItem {
    public SkyrootPineconeItem(Properties properties) {
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
        this.throwItem(stack, level, livingEntity, timeLeft, SoundEvents.SNOWBALL_THROW, new SkyrootPinecone(level, livingEntity));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.CUSTOM;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(AetherIIClientItemExtensions.throwable);
    }
}
