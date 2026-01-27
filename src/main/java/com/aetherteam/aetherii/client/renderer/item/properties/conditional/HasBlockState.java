package com.aetherteam.aetherii.client.renderer.item.properties.conditional;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public record HasBlockState() implements ConditionalItemModelProperty {
    public static final MapCodec<HasBlockState> MAP_CODEC = MapCodec.unit(new HasBlockState());

    @Override
    public boolean get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int i, ItemDisplayContext context) {
        return stack.get(AetherIIDataComponents.BLOCK_STATE) != null;
    }

    @Override
    public MapCodec<HasBlockState> type() {
        return MAP_CODEC;
    }
}
