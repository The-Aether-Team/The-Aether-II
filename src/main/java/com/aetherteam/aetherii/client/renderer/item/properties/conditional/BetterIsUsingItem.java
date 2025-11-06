package com.aetherteam.aetherii.client.renderer.item.properties.conditional;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public record BetterIsUsingItem() implements ConditionalItemModelProperty {
    public static final MapCodec<BetterIsUsingItem> MAP_CODEC = MapCodec.unit(new BetterIsUsingItem());

    @Override
    public boolean get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int i, ItemDisplayContext context) {
        return entity != null && entity.isUsingItem() && ItemStack.isSameItem(entity.getUseItem(), stack);
    }

    @Override
    public MapCodec<BetterIsUsingItem> type() {
        return MAP_CODEC;
    }
}
