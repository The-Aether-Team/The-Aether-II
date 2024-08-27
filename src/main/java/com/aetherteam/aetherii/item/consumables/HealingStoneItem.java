package com.aetherteam.aetherii.item.consumables;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class HealingStoneItem extends Item {
    public HealingStoneItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack itemstack = super.getDefaultInstance();
        itemstack.set(AetherIIDataComponents.HEALING_STONE_CHARGES, 1);
        return itemstack;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        Integer charges = stack.get(AetherIIDataComponents.HEALING_STONE_CHARGES);
        Player player = entityLiving instanceof Player ? (Player) entityLiving : null;

        if (charges != null && charges > 0) {
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            }

            if (player != null) {
                player.awardStat(Stats.ITEM_USED.get(this));
                player.heal(8.0F);
                //todo  poisoning and extra health
            }

            if (player != null && !player.hasInfiniteMaterials()) {
                stack.set(AetherIIDataComponents.HEALING_STONE_CHARGES, Math.max(charges - 1, 0));
            }
        }

        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        Integer charges = itemStack.get(AetherIIDataComponents.HEALING_STONE_CHARGES);
        if (charges != null && charges > 0) {
            return ItemUtils.startUsingInstantly(level, player, hand);
        }
        return InteractionResultHolder.pass(itemStack);
    }
}
