package com.aetherteam.aetherii.recipe.recipes.block;

import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.serializer.BiomeParameterRecipeSerializer;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.mojang.datafixers.util.Either;
import net.minecraft.commands.CommandFunction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;

public class AccessoryFreezableRecipe extends AbstractBiomeParameterRecipe {
    public static final RecipeSerializer<AccessoryFreezableRecipe> SERIALIZER = new BiomeParameterRecipeSerializer<>(AccessoryFreezableRecipe::new);

    public AccessoryFreezableRecipe(ResourceLocation id, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, BlockStateIngredient ingredient, BlockPropertyPair result, CommandFunction.CacheableFunction function) {
        super(AetherIIRecipeTypes.ACCESSORY_FREEZABLE.get(), id, biome, ingredient, result, function);
    }

    public AccessoryFreezableRecipe(ResourceLocation id, BlockStateIngredient ingredient, BlockPropertyPair result, CommandFunction.CacheableFunction function) {
        this(id, Optional.empty(), ingredient, result, function);
    }

    @Override
    public RecipeSerializer<AccessoryFreezableRecipe> getSerializer() {
        return SERIALIZER;
    }
}
