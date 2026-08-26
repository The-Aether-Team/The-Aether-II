package com.aetherteam.aetherii.recipe.recipes.block;

import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;

public class AccessoryFreezableRecipe extends AbstractBiomeParameterRecipe {
    public static final MapCodec<AccessoryFreezableRecipe> MAP_CODEC = AbstractBiomeParameterRecipe.biomeCodec(AccessoryFreezableRecipe::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, AccessoryFreezableRecipe> STREAM_CODEC = AbstractBiomeParameterRecipe.biomeStreamCodec(AccessoryFreezableRecipe::new);
    public static final RecipeSerializer<AccessoryFreezableRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    public AccessoryFreezableRecipe(Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, BlockStateIngredient ingredient, BlockPropertyPair result, Optional<Identifier> function) {
        super(AetherIIRecipeTypes.ACCESSORY_FREEZABLE.get(), biome, ingredient, result, function);
    }

    public AccessoryFreezableRecipe(BlockStateIngredient ingredient, BlockPropertyPair result, Optional<Identifier> function) {
        this(Optional.empty(), ingredient, result, function);
    }

    @Override
    public RecipeSerializer<AccessoryFreezableRecipe> getSerializer() {
        return SERIALIZER;
    }
}