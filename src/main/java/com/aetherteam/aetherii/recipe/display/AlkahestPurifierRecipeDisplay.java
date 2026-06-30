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

public record AlkahestPurifierRecipeDisplay(SlotDisplay ingredient, SlotDisplay fuel, SlotDisplay result, SlotDisplay byproduct, SlotDisplay craftingStation, int alkahestUsage, int duration, float experience) implements RecipeDisplay {
    public static final MapCodec<AlkahestPurifierRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    SlotDisplay.CODEC.fieldOf("ingredient").forGetter(AlkahestPurifierRecipeDisplay::ingredient),
                    SlotDisplay.CODEC.fieldOf("fuel").forGetter(AlkahestPurifierRecipeDisplay::fuel),
                    SlotDisplay.CODEC.fieldOf("result").forGetter(AlkahestPurifierRecipeDisplay::result),
                    SlotDisplay.CODEC.fieldOf("byproduct").forGetter(AlkahestPurifierRecipeDisplay::byproduct),
                    SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(AlkahestPurifierRecipeDisplay::craftingStation),
                    Codec.INT.fieldOf("alkahest_usage").forGetter(AlkahestPurifierRecipeDisplay::alkahestUsage),
                    Codec.INT.fieldOf("duration").forGetter(AlkahestPurifierRecipeDisplay::duration),
                    Codec.FLOAT.fieldOf("experience").forGetter(AlkahestPurifierRecipeDisplay::experience))
            .apply(instance, AlkahestPurifierRecipeDisplay::new));
    public static final StreamCodec<FriendlyByteBuf, AlkahestPurifierRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
            SlotDisplay.STREAM_CODEC, AlkahestPurifierRecipeDisplay::ingredient,
            SlotDisplay.STREAM_CODEC, AlkahestPurifierRecipeDisplay::fuel,
            SlotDisplay.STREAM_CODEC, AlkahestPurifierRecipeDisplay::result,
            SlotDisplay.STREAM_CODEC, AlkahestPurifierRecipeDisplay::byproduct,
            SlotDisplay.STREAM_CODEC, AlkahestPurifierRecipeDisplay::craftingStation,
            ByteBufCodecs.VAR_INT, AlkahestPurifierRecipeDisplay::alkahestUsage,
            ByteBufCodecs.VAR_INT, AlkahestPurifierRecipeDisplay::duration,
            ByteBufCodecs.FLOAT, AlkahestPurifierRecipeDisplay::experience,
            AlkahestPurifierRecipeDisplay::new);
    public static final RecipeDisplay.Type<AlkahestPurifierRecipeDisplay> TYPE = new RecipeDisplay.Type<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public RecipeDisplay.Type<AlkahestPurifierRecipeDisplay> type() {
        return TYPE;
    }

    @Override
    public boolean isEnabled(FeatureFlagSet featureFlagSet) {
        return this.ingredient.isEnabled(featureFlagSet) && this.fuel().isEnabled(featureFlagSet) && RecipeDisplay.super.isEnabled(featureFlagSet);
    }
}
