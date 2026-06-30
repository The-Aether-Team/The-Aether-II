package com.aetherteam.aetherii.recipe.display;

import com.aetherteam.aetherii.item.components.ItemStackTemplate;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.stream.Stream;

public interface SlotDisplay {
    Codec<SlotDisplay> CODEC = Codec.unit(Empty.INSTANCE);
    StreamCodec<FriendlyByteBuf, SlotDisplay> STREAM_CODEC = StreamCodec.unit(Empty.INSTANCE);

    Type<? extends SlotDisplay> type();

    default boolean isEnabled(FeatureFlagSet featureFlagSet) {
        return true;
    }

    default <T> Stream<T> resolve(Object context, DisplayContentsFactory<T> factory) {
        return Stream.empty();
    }

    record Type<T extends SlotDisplay>(MapCodec<T> codec, StreamCodec<FriendlyByteBuf, T> streamCodec) {
    }

    final class Empty implements SlotDisplay {
        public static final Empty INSTANCE = new Empty();
        public static final MapCodec<Empty> MAP_CODEC = MapCodec.unit(INSTANCE);
        public static final StreamCodec<FriendlyByteBuf, Empty> STREAM_CODEC = StreamCodec.unit(INSTANCE);
        public static final Type<Empty> TYPE = new Type<>(MAP_CODEC, STREAM_CODEC);

        private Empty() {
        }

        @Override
        public Type<Empty> type() {
            return TYPE;
        }
    }

    record Composite(List<SlotDisplay> contents) implements SlotDisplay {
        public static final MapCodec<Composite> MAP_CODEC = MapCodec.unit(new Composite(List.of()));
        public static final StreamCodec<FriendlyByteBuf, Composite> STREAM_CODEC = StreamCodec.unit(new Composite(List.of()));
        public static final Type<Composite> TYPE = new Type<>(MAP_CODEC, STREAM_CODEC);

        @Override
        public Type<Composite> type() {
            return TYPE;
        }
    }

    record ItemStackSlotDisplay(ItemStackTemplate stack) implements SlotDisplay {
        public static final MapCodec<ItemStackSlotDisplay> MAP_CODEC = MapCodec.unit(new ItemStackSlotDisplay(new ItemStackTemplate(net.minecraft.world.item.Items.AIR)));
        public static final StreamCodec<FriendlyByteBuf, ItemStackSlotDisplay> STREAM_CODEC = StreamCodec.unit(new ItemStackSlotDisplay(new ItemStackTemplate(net.minecraft.world.item.Items.AIR)));
        public static final Type<ItemStackSlotDisplay> TYPE = new Type<>(MAP_CODEC, STREAM_CODEC);

        @Override
        public Type<ItemStackSlotDisplay> type() {
            return TYPE;
        }
    }

    record ItemSlotDisplay(Item item) implements SlotDisplay {
        public ItemSlotDisplay(ItemLike item) {
            this(item.asItem());
        }

        public static final MapCodec<ItemSlotDisplay> MAP_CODEC = MapCodec.unit(new ItemSlotDisplay(net.minecraft.world.item.Items.AIR));
        public static final StreamCodec<FriendlyByteBuf, ItemSlotDisplay> STREAM_CODEC = StreamCodec.unit(new ItemSlotDisplay(net.minecraft.world.item.Items.AIR));
        public static final Type<ItemSlotDisplay> TYPE = new Type<>(MAP_CODEC, STREAM_CODEC);

        @Override
        public Type<ItemSlotDisplay> type() {
            return TYPE;
        }
    }

    record TagSlotDisplay(TagKey<Item> tag) implements SlotDisplay {
        public static final MapCodec<TagSlotDisplay> MAP_CODEC = MapCodec.unit(null);
        public static final StreamCodec<FriendlyByteBuf, TagSlotDisplay> STREAM_CODEC = StreamCodec.unit(null);
        public static final Type<TagSlotDisplay> TYPE = new Type<>(MAP_CODEC, STREAM_CODEC);

        @Override
        public Type<TagSlotDisplay> type() {
            return TYPE;
        }
    }
}
