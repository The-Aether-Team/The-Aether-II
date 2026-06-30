package com.aetherteam.aetherii.item.miscellaneous;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BeastPeltBundleItem extends BundleItem {
    private static final String TAG_ITEMS = "Items";

    public BeastPeltBundleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (this.dropOne(stack, player, !level.isClientSide())) {
            player.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + level.getRandom().nextFloat() * 0.4F);
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }
        return InteractionResultHolder.fail(stack);
    }

    private boolean dropOne(ItemStack stack, Player player, boolean dropItem) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains(TAG_ITEMS)) {
            return false;
        }

        ListTag items = tag.getList(TAG_ITEMS, Tag.TAG_COMPOUND);
        if (items.isEmpty()) {
            return false;
        }

        ItemStack removed = ItemStack.of(items.getCompound(0));
        items.remove(0);
        if (items.isEmpty()) {
            stack.removeTagKey(TAG_ITEMS);
        }
        if (dropItem && !removed.isEmpty()) {
            player.drop(removed, true);
        }
        return true;
    }
}
