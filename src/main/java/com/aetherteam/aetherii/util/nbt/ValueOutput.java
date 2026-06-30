package com.aetherteam.aetherii.util.nbt;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;

public class ValueOutput {
    private final CompoundTag tag = new CompoundTag();

    public void putDouble(String key, double value) {
        this.tag.putDouble(key, value);
    }

    public <T> void store(String key, Codec<T> codec, T value) {
        codec.encodeStart(NbtOps.INSTANCE, value).result().ifPresent(tag -> this.tag.put(key, tag));
    }

    public CompoundTag buildResult() {
        return this.tag;
    }
}
