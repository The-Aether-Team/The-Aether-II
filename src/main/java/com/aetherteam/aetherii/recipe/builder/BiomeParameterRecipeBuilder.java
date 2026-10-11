package com.aetherteam.aetherii.recipe.builder;

import com.aetherteam.aetherii.recipe.recipes.block.AbstractBiomeParameterRecipe;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.mojang.datafixers.util.Either;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Optional;

public class BiomeParameterRecipeBuilder implements RecipeBuilder {
    private final Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome;
    private final BlockPropertyPair result;
    private final BlockStateIngredient ingredient;
    private Optional<Identifier> function = Optional.empty();
    private final AbstractBiomeParameterRecipe.Factory<?> factory;

    public BiomeParameterRecipeBuilder(BlockPropertyPair result, BlockStateIngredient ingredient, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, AbstractBiomeParameterRecipe.Factory<?> factory) {
        this.result = result;
        this.ingredient = ingredient;
        this.biome = biome;
        this.factory = factory;
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, Block result, ResourceKey<Biome> biomeKey, AbstractBiomeParameterRecipe.Factory<?> factory) {
        return recipe(BlockPropertyPair.of(result, Optional.empty()), ingredient, Optional.of(Either.left(biomeKey)), factory);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, BlockPropertyPair resultPair, ResourceKey<Biome> biomeKey, AbstractBiomeParameterRecipe.Factory<?> factory) {
        return recipe(BlockPropertyPair.of(resultPair.block(), resultPair.properties()), ingredient, Optional.of(Either.left(biomeKey)), factory);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, Block resultBlock, HashSet<Property.Value<?>> resultProperties, ResourceKey<Biome> biomeKey, AbstractBiomeParameterRecipe.Factory<?> factory) {
        return recipe(BlockPropertyPair.of(resultBlock, Optional.ofNullable(resultProperties)), ingredient, Optional.of(Either.left(biomeKey)), factory);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, Block result, TagKey<Biome> biomeTag, AbstractBiomeParameterRecipe.Factory<?> factory) {
        return recipe(BlockPropertyPair.of(result, Optional.empty()), ingredient, Optional.of(Either.right(biomeTag)), factory);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, BlockPropertyPair resultPair, TagKey<Biome> biomeTag, AbstractBiomeParameterRecipe.Factory<?> factory) {
        return recipe(BlockPropertyPair.of(resultPair.block(), resultPair.properties()), ingredient, Optional.of(Either.right(biomeTag)), factory);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, Block resultBlock, HashSet<Property.Value<?>> resultProperties, TagKey<Biome> biomeTag, AbstractBiomeParameterRecipe.Factory<?> factory) {
        return recipe(BlockPropertyPair.of(resultBlock, Optional.of(resultProperties)), ingredient, Optional.of(Either.right(biomeTag)), factory);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockPropertyPair result, BlockStateIngredient ingredient, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, AbstractBiomeParameterRecipe.Factory<?> factory) {
        return new BiomeParameterRecipeBuilder(result, ingredient, biome, factory);
    }

    public RecipeBuilder function(Optional<Identifier> function) {
        this.function = function;
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> id) {
        AbstractBiomeParameterRecipe recipe = (AbstractBiomeParameterRecipe) this.factory.create(this.biome, this.ingredient, this.result, this.function);
        recipeOutput.accept(id, recipe, null);
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.result.block().asItem().getDefaultInstance());
    }
}
