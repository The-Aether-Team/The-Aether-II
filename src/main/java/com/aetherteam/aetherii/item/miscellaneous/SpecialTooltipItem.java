package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.item.consumables.AetherConsumableItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Supplier;

public class SpecialTooltipItem extends Item {
    public final TooltipTemplate tooltip;
    private final Supplier<ItemStack> remainder;

    public SpecialTooltipItem(TooltipTemplate tooltip, Properties properties) {
        this(tooltip, properties, () -> ItemStack.EMPTY);
    }

    public SpecialTooltipItem(TooltipTemplate tooltip, Properties properties, Supplier<ItemStack> remainder) {
        super(properties);
        this.tooltip = tooltip;
        this.remainder = remainder;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        return AetherConsumableItem.finishUsingItem(this, stack, level, entity, this.remainder.get());
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return AetherConsumableItem.getUseDurationFor(stack, super.getUseDuration(stack));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return AetherConsumableItem.getUseAnimationFor(stack, super.getUseAnimation(stack));
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        AetherConsumableItem.triggerUseEffects(stack, level, entity, remainingUseDuration);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return AetherConsumableItem.useConsumable(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.level.Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        this.tooltip.appendHoverText(stack, level, tooltipComponents, tooltipFlag);
    }
}
