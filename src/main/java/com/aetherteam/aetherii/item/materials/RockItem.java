package com.aetherteam.aetherii.item.materials;

import com.aetherteam.aetherii.client.AetherIIClientExtensions;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.projectile.HolystoneRock;
import com.aetherteam.aetherii.item.miscellaneous.ThrowableItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class RockItem extends BlockItem implements ThrowableItem {
    public RockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult interactionresult = this.place(new BlockPlaceContext(context));
        if (!interactionresult.consumesAction()) {
            return this.use(context.getLevel(), context.getPlayer(), context.getHand()).getResult();
        } else {
            return interactionresult;
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        this.throwItem(stack, level, livingEntity, timeLeft, AetherIISoundEvents.ENTITY_ROCK_THROW.get(), new HolystoneRock(level, livingEntity, stack));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.NONE;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(AetherIIClientExtensions.THROWABLE);
    }
}
