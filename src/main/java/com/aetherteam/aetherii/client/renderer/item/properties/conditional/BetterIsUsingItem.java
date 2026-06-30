package com.aetherteam.aetherii.client.renderer.item.properties.conditional;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public record BetterIsUsingItem() {
    public static final MapCodec<BetterIsUsingItem> MAP_CODEC = MapCodec.unit(new BetterIsUsingItem());

    public boolean get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int i, ItemDisplayContext context) {
        return entity != null && ItemStack.isSameItem(stack, entity.getItemInHand(entity.getUsedItemHand())) && entity.isUsingItem();
    }

    public MapCodec<BetterIsUsingItem> type() {
        return MAP_CODEC;
    }
}
