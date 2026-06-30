package com.aetherteam.aetherii.util;

import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public final class ItemStackCodecs {
    public static final Codec<ItemStack> OPTIONAL_CODEC = ItemStack.CODEC;
    public static final StreamCodec<FriendlyByteBuf, ItemStack> STREAM_CODEC = StreamCodec.of(
            FriendlyByteBuf::writeItem,
            FriendlyByteBuf::readItem);
    public static final StreamCodec<FriendlyByteBuf, ItemStack> OPTIONAL_STREAM_CODEC = STREAM_CODEC;

    private ItemStackCodecs() {
    }
}
