package com.aetherteam.aetherii.recipe.display.slot;

import com.aetherteam.aetherii.data.resources.registries.AetherIIDataMaps;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.display.DisplayContentsFactory;
import net.minecraft.world.item.crafting.display.SlotDisplay;

import java.util.stream.Stream;

public class AmberFuel implements SlotDisplay {
    public static final AmberFuel INSTANCE = new AmberFuel();
    public static final MapCodec<AmberFuel> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, AmberFuel> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final Type<AmberFuel> TYPE = new Type<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public Type<AmberFuel> type() {
        return TYPE;
    }

    @Override
    public <T> Stream<T> resolve(ContextMap contextMap, DisplayContentsFactory<T> factory) {
        if (factory instanceof DisplayContentsFactory.ForStacks<T> forstacks) {
            Stream<Holder.Reference<Item>> items = BuiltInRegistries.ITEM.getDataMap(AetherIIDataMaps.AMBER_HOURGLASS_FUELS).keySet().stream().map(BuiltInRegistries.ITEM::getOrThrow);
            return items.map(forstacks::forStack);
        }
        return Stream.empty();
    }
}
