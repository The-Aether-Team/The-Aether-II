package com.aetherteam.aetherii.item.miscellaneous.bucket;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.level.Level;

public class SkyrootMilkBucketItem extends MilkBucketItem {
    public SkyrootMilkBucketItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide()) {
            entity.curePotionEffects(stack);
            entity.curePotionEffects(Items.MILK_BUCKET.getDefaultInstance());
        }
        if (entity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }
        if (entity instanceof Player player && !player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        return stack.isEmpty() ? new ItemStack(AetherIIItems.SKYROOT_BUCKET.get()) : stack;
    }
}
