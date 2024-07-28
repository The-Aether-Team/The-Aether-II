package com.aetherteam.aetherii.item.miscellaneous.bucket;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.EffectCures;

public class SkyrootMilkBucketItem extends MilkBucketItem {
    public SkyrootMilkBucketItem(Properties properties) {
        super(properties);
    }

    /**
     * Acts like a vanilla Milk Bucket and consumes the item using {@link ConsumableItem#consume(Item, ItemStack, LivingEntity)}.
     *
     * @param stack The {@link ItemStack} in use.
     * @param level The {@link Level} of the user.
     * @param user  The {@link LivingEntity} using the stack.
     * @return The {@link ItemStack} after using, which is a Skyroot Bucket if successful.
     */
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        if (user instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }
        if (!level.isClientSide()) {
            user.removeEffectsCuredBy(EffectCures.MILK);
        }
        if (user instanceof Player player) {
            return ItemUtils.createFilledResult(stack, player, new ItemStack(Items.BUCKET), false);
        } else {
            stack.consume(1, user);
            return stack;
        }
    }
}
