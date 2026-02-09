package com.aetherteam.aetherii.recipe.recipes;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OutputEntry {
    public static Codec<BaseEntry> ENTRY_CODEC = EntryType.CODEC.dispatch(BaseEntry::type, type -> type.codec.fieldOf("value"));
    public static StreamCodec<RegistryFriendlyByteBuf, BaseEntry> ENTRY_STREAM_CODEC = EntryType.STREAM_CODEC.dispatch(BaseEntry::type, type -> type.streamCodec);

    public record ListEntry(WeightedList<BaseEntry> entries) implements BaseEntry {
        public static Codec<ListEntry> CODEC = WeightedList.codec(Codec.lazyInitialized(() -> OutputEntry.ENTRY_CODEC)).xmap(ListEntry::new, ListEntry::entries);
        public static StreamCodec<RegistryFriendlyByteBuf, ListEntry> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC);

        @Override
        public List<ItemStack> list() {
            List<ItemStack> stacks = new ArrayList<>();
            this.entries().unwrap().stream().map(Weighted::value).forEach((baseEntry) -> stacks.addAll(baseEntry.list()));
            return stacks;
        }

        @Override
        public ItemStack process(RandomSource random) {
            return this.entries().getRandomOrThrow(random).process(random);
        }

        @Override
        public EntryType type() {
            return EntryType.LIST;
        }
    }

    public record ItemEntry(ItemStack stack) implements BaseEntry {
        public static Codec<ItemEntry> CODEC = ItemStack.OPTIONAL_CODEC.xmap(ItemEntry::new, ItemEntry::stack);
        public static StreamCodec<RegistryFriendlyByteBuf, ItemEntry> STREAM_CODEC = ItemStack.OPTIONAL_STREAM_CODEC.map(ItemEntry::new, ItemEntry::stack);

        @Override
        public List<ItemStack> list() {
            return List.of(this.stack());
        }

        public ItemStack process(RandomSource random) {
            return this.stack();
        }

        @Override
        public EntryType type() {
            return EntryType.ITEM;
        }
    }

    public interface BaseEntry {
        List<ItemStack> list();

        ItemStack process(RandomSource random);

        EntryType type();
    }

    public enum EntryType implements StringRepresentable {
        LIST(ListEntry.CODEC, ListEntry.STREAM_CODEC),
        ITEM(ItemEntry.CODEC, ItemEntry.STREAM_CODEC);

        public static final StringRepresentable.EnumCodec<EntryType> CODEC = StringRepresentable.fromEnum(EntryType::values);
        public static final StreamCodec<RegistryFriendlyByteBuf, EntryType> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC);
        final Codec<? extends BaseEntry> codec;
        final StreamCodec<RegistryFriendlyByteBuf, ? extends BaseEntry> streamCodec;

        EntryType(Codec<? extends BaseEntry> codec, StreamCodec<RegistryFriendlyByteBuf, ? extends BaseEntry> streamCodec) {
            this.codec = codec;
            this.streamCodec = streamCodec;
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
