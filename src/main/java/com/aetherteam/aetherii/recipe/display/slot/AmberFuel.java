package com.aetherteam.aetherii.recipe.display.slot;

import com.aetherteam.aetherii.data.resources.registries.AetherIIDataMaps;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import com.aetherteam.aetherii.recipe.display.DisplayContentsFactory;
import com.aetherteam.aetherii.recipe.display.SlotDisplay;

import java.util.stream.Stream;

public class AmberFuel implements SlotDisplay {
    public static final AmberFuel INSTANCE = new AmberFuel();
    public static final MapCodec<AmberFuel> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<FriendlyByteBuf, AmberFuel> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final Type<AmberFuel> TYPE = new Type<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public Type<AmberFuel> type() {
        return TYPE;
    }

    @Override
    public <T> Stream<T> resolve(Object contextMap, DisplayContentsFactory<T> factory) {
        if (factory instanceof DisplayContentsFactory.ForStacks<T> forstacks) {
            return AetherIIDataMaps.amberHourglassFuelItems().map(ItemStack::new).map(forstacks::forStack);
        }
        return Stream.empty();
    }
}
