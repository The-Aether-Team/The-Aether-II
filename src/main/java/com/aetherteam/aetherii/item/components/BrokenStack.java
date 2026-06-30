package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.util.ItemStackCodecs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public record BrokenStack(ItemStack stack) {
    public static final Codec<BrokenStack> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            ItemStackCodecs.OPTIONAL_CODEC.fieldOf("stack").forGetter(BrokenStack::stack)
    ).apply(builder, BrokenStack::new));
    public static final StreamCodec<FriendlyByteBuf, BrokenStack> STREAM_CODEC = StreamCodec.composite(
            ItemStackCodecs.OPTIONAL_STREAM_CODEC, BrokenStack::stack,
            BrokenStack::new);

    @Override
    public int hashCode() {
        return Objects.hashCode(this.stack());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            if (other instanceof BrokenStack brokenStack) {
                return ItemStack.matches(brokenStack.stack(), this.stack());
            } else {
                return false;
            }
        }
    }
}
