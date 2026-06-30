package com.aetherteam.aetherii.recipe.display;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.world.flag.FeatureFlagSet;
import com.aetherteam.aetherii.recipe.display.RecipeDisplay;
import com.aetherteam.aetherii.recipe.display.SlotDisplay;

public record AltarRecipeDisplay(SlotDisplay ingredient, SlotDisplay fuel, SlotDisplay result, SlotDisplay craftingStation, int fuelCount, int duration, float experience) implements RecipeDisplay {
    public static final MapCodec<AltarRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    SlotDisplay.CODEC.fieldOf("ingredient").forGetter(AltarRecipeDisplay::ingredient),
                    SlotDisplay.CODEC.fieldOf("fuel").forGetter(AltarRecipeDisplay::fuel),
                    SlotDisplay.CODEC.fieldOf("result").forGetter(AltarRecipeDisplay::result),
                    SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(AltarRecipeDisplay::craftingStation),
                    Codec.INT.fieldOf("fuel_count").forGetter(AltarRecipeDisplay::fuelCount),
                    Codec.INT.fieldOf("duration").forGetter(AltarRecipeDisplay::duration),
                    Codec.FLOAT.fieldOf("experience").forGetter(AltarRecipeDisplay::experience))
            .apply(instance, AltarRecipeDisplay::new));
    public static final StreamCodec<FriendlyByteBuf, AltarRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
            SlotDisplay.STREAM_CODEC, AltarRecipeDisplay::ingredient,
            SlotDisplay.STREAM_CODEC, AltarRecipeDisplay::fuel,
            SlotDisplay.STREAM_CODEC, AltarRecipeDisplay::result,
            SlotDisplay.STREAM_CODEC, AltarRecipeDisplay::craftingStation,
            ByteBufCodecs.VAR_INT, AltarRecipeDisplay::fuelCount,
            ByteBufCodecs.VAR_INT, AltarRecipeDisplay::duration,
            ByteBufCodecs.FLOAT, AltarRecipeDisplay::experience,
            AltarRecipeDisplay::new);
    public static final RecipeDisplay.Type<AltarRecipeDisplay> TYPE = new RecipeDisplay.Type<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public RecipeDisplay.Type<AltarRecipeDisplay> type() {
        return TYPE;
    }

    @Override
    public boolean isEnabled(FeatureFlagSet featureFlagSet) {
        return this.ingredient.isEnabled(featureFlagSet) && this.fuel().isEnabled(featureFlagSet) && RecipeDisplay.super.isEnabled(featureFlagSet);
    }
}
