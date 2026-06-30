package com.aetherteam.aetherii.recipe.display;

import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.flag.FeatureFlagSet;

public interface RecipeDisplay {
    SlotDisplay result();

    Type<? extends RecipeDisplay> type();

    default boolean isEnabled(FeatureFlagSet featureFlagSet) {
        return true;
    }

    record Type<T extends RecipeDisplay>(MapCodec<T> codec, StreamCodec<FriendlyByteBuf, T> streamCodec) {
    }
}
