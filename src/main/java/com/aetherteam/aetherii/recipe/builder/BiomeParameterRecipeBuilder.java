package com.aetherteam.aetherii.recipe.builder;

import com.aetherteam.aetherii.recipe.recipes.block.AbstractBiomeParameterRecipe;
import com.google.gson.JsonObject;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.mojang.datafixers.util.Either;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class BiomeParameterRecipeBuilder implements RecipeBuilder {
    private final Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome;
    private final BlockPropertyPair result;
    private final BlockStateIngredient ingredient;
    private Optional<ResourceLocation> function = Optional.empty();
    private final Factory<?> factory;

    public BiomeParameterRecipeBuilder(BlockPropertyPair result, BlockStateIngredient ingredient, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, Factory<?> factory) {
        this.result = result;
        this.ingredient = ingredient;
        this.biome = biome;
        this.factory = factory;
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, Block result, ResourceKey<Biome> biomeKey, Factory<?> factory) {
        return recipe(BlockPropertyPair.of(result, Map.of()), ingredient, Optional.of(Either.left(biomeKey)), factory);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, BlockPropertyPair resultPair, ResourceKey<Biome> biomeKey, Factory<?> factory) {
        return recipe(BlockPropertyPair.of(resultPair.block(), resultPair.properties()), ingredient, Optional.of(Either.left(biomeKey)), factory);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties, ResourceKey<Biome> biomeKey, Factory<?> factory) {
        return recipe(BlockPropertyPair.of(resultBlock, resultProperties), ingredient, Optional.of(Either.left(biomeKey)), factory);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, Block result, TagKey<Biome> biomeTag, Factory<?> factory) {
        return recipe(BlockPropertyPair.of(result, Map.of()), ingredient, Optional.of(Either.right(biomeTag)), factory);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, BlockPropertyPair resultPair, TagKey<Biome> biomeTag, Factory<?> factory) {
        return recipe(BlockPropertyPair.of(resultPair.block(), resultPair.properties()), ingredient, Optional.of(Either.right(biomeTag)), factory);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties, TagKey<Biome> biomeTag, Factory<?> factory) {
        return recipe(BlockPropertyPair.of(resultBlock, resultProperties), ingredient, Optional.of(Either.right(biomeTag)), factory);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockPropertyPair result, BlockStateIngredient ingredient, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, Factory<?> factory) {
        return new BiomeParameterRecipeBuilder(result, ingredient, biome, factory);
    }

    public RecipeBuilder function(Optional<ResourceLocation> function) {
        this.function = function;
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, CriterionTriggerInstance criterion) {
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.block().asItem();
    }

    @Override
    public void save(Consumer<FinishedRecipe> output, ResourceLocation id) {
        AbstractBiomeParameterRecipe recipe = this.factory.create(id, this.biome, this.ingredient, this.result, this.function.map(CommandFunction.CacheableFunction::new).orElse(CommandFunction.CacheableFunction.NONE));
        output.accept(new Result(id, recipe));
    }

    private record Result(ResourceLocation id, AbstractBiomeParameterRecipe recipe) implements FinishedRecipe {
        @Override
        public void serializeRecipeData(JsonObject json) {
            this.recipe.getBiome().ifPresent(value -> value.ifLeft(key -> json.addProperty("biome", key.location().toString())).ifRight(tag -> json.addProperty("biome", "#" + tag.location())));
            json.add("ingredient", this.recipe.getIngredient().toJson());
            JsonObject result = new JsonObject();
            result.addProperty("block", BuiltInRegistries.BLOCK.getKey(this.recipe.getResult().block()).toString());
            if (!this.recipe.getResult().properties().isEmpty()) {
                JsonObject properties = new JsonObject();
                this.recipe.getResult().properties().forEach((property, value) -> properties.addProperty(property.getName(), value.toString()));
                result.add("properties", properties);
            }
            json.add("result", result);
            ResourceLocation function = this.recipe.getFunction() == null ? null : this.recipe.getFunction().getId();
            if (function != null) {
                json.addProperty("mcfunction", function.toString());
            }
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return this.recipe.getSerializer();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }

    @FunctionalInterface
    public interface Factory<T extends AbstractBiomeParameterRecipe> {
        T create(ResourceLocation id, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, BlockStateIngredient ingredient, BlockPropertyPair result, @Nullable CommandFunction.CacheableFunction function);
    }
}
