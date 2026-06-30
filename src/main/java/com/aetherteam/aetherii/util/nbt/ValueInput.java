package com.aetherteam.aetherii.util.nbt;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ValueInput {
    private final CompoundTag tag;

    public ValueInput(CompoundTag tag) {
        this.tag = tag;
    }

    public double getDoubleOr(String key, double fallback) {
        return this.tag.contains(key) ? this.tag.getDouble(key) : fallback;
    }

    public <T> Optional<T> read(String key, Codec<T> codec) {
        if (!this.tag.contains(key)) {
            return Optional.empty();
        }
        return codec.parse(NbtOps.INSTANCE, this.tag.get(key)).result();
    }

    public ValueInputList childrenListOrEmpty(String key) {
        if (!this.tag.contains(key, Tag.TAG_LIST)) {
            return new ValueInputList(List.of());
        }
        List<ValueInput> children = new ArrayList<>();
        ListTag list = this.tag.getList(key, Tag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            children.add(new ValueInput(list.getCompound(i)));
        }
        return new ValueInputList(children);
    }

    public CompoundTag compoundTag() {
        return this.tag;
    }

    public record ValueInputList(List<ValueInput> children) {
        public Stream<ValueInput> stream() {
            return this.children.stream();
        }
    }
}
