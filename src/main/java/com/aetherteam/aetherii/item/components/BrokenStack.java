package com.aetherteam.aetherii.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public record BrokenStack(ItemStack stack) {
    public static final Codec<BrokenStack> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            ItemStack.OPTIONAL_CODEC.fieldOf("stack").forGetter(BrokenStack::stack)
    ).apply(builder, BrokenStack::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, BrokenStack> STREAM_CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_STREAM_CODEC, BrokenStack::stack,
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
            if (other instanceof BrokenStack(ItemStack otherStack)) {
                return ItemStack.matches(otherStack, this.stack());
            } else {
                return false;
            }
        }
    }
}
