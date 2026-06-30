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

import java.util.List;

public record AmberHourglassRecipeDisplay(SlotDisplay ingredient, SlotDisplay fuel, SlotDisplay result1, SlotDisplay result2, SlotDisplay result3, SlotDisplay craftingStation, int duration, float experience) implements RecipeDisplay {
    public static final MapCodec<AmberHourglassRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            SlotDisplay.CODEC.fieldOf("ingredient").forGetter(AmberHourglassRecipeDisplay::ingredient),
            SlotDisplay.CODEC.fieldOf("fuel").forGetter(AmberHourglassRecipeDisplay::fuel),
            SlotDisplay.CODEC.fieldOf("result1").forGetter(AmberHourglassRecipeDisplay::result1),
            SlotDisplay.CODEC.fieldOf("result2").forGetter(AmberHourglassRecipeDisplay::result2),
            SlotDisplay.CODEC.fieldOf("result3").forGetter(AmberHourglassRecipeDisplay::result3),
            SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(AmberHourglassRecipeDisplay::craftingStation),
            Codec.INT.fieldOf("duration").forGetter(AmberHourglassRecipeDisplay::duration),
            Codec.FLOAT.fieldOf("experience").forGetter(AmberHourglassRecipeDisplay::experience)
    ).apply(instance, AmberHourglassRecipeDisplay::new));
    public static final StreamCodec<FriendlyByteBuf, AmberHourglassRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
            SlotDisplay.STREAM_CODEC, AmberHourglassRecipeDisplay::ingredient,
            SlotDisplay.STREAM_CODEC, AmberHourglassRecipeDisplay::fuel,
            SlotDisplay.STREAM_CODEC, AmberHourglassRecipeDisplay::result1,
            SlotDisplay.STREAM_CODEC, AmberHourglassRecipeDisplay::result2,
            SlotDisplay.STREAM_CODEC, AmberHourglassRecipeDisplay::result3,
            SlotDisplay.STREAM_CODEC, AmberHourglassRecipeDisplay::craftingStation,
            ByteBufCodecs.VAR_INT, AmberHourglassRecipeDisplay::duration,
            ByteBufCodecs.FLOAT, AmberHourglassRecipeDisplay::experience,
            AmberHourglassRecipeDisplay::new);

    public static final RecipeDisplay.Type<AmberHourglassRecipeDisplay> TYPE = new RecipeDisplay.Type<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public SlotDisplay result() {
        return new SlotDisplay.Composite(List.of(this.result1(), this.result2(), this.result3()));
    }

    @Override
    public RecipeDisplay.Type<AmberHourglassRecipeDisplay> type() {
        return TYPE;
    }

    @Override
    public boolean isEnabled(FeatureFlagSet featureFlagSet) {
        return this.ingredient.isEnabled(featureFlagSet) && this.fuel().isEnabled(featureFlagSet) && RecipeDisplay.super.isEnabled(featureFlagSet);
    }
}
