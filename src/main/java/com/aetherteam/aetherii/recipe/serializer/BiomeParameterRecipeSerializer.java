package com.aetherteam.aetherii.recipe.serializer;

import com.aetherteam.aetherii.recipe.recipes.block.AbstractBiomeParameterRecipe;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.BlockStateRecipeUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Either;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BiomeParameterRecipeSerializer<T extends AbstractBiomeParameterRecipe> implements RecipeSerializer<T> {
    private final Factory<T> factory;

    public BiomeParameterRecipeSerializer(Factory<T> factory) {
        this.factory = factory;
    }

    @Override
    public T fromJson(ResourceLocation id, JsonObject json) {
        Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome = Optional.empty();
        if (json.has("biome")) {
            String biomeName = GsonHelper.getAsString(json, "biome");
            biome = Optional.of(biomeName.startsWith("#")
                    ? Either.right(TagKey.create(Registries.BIOME, new ResourceLocation(biomeName.substring(1))))
                    : Either.left(ResourceKey.create(Registries.BIOME, new ResourceLocation(biomeName))));
        }

        if (!json.has("ingredient")) {
            throw new JsonSyntaxException("Missing ingredient, expected to find an object or array");
        }
        JsonElement ingredientJson = GsonHelper.isArrayNode(json, "ingredient") ? GsonHelper.getAsJsonArray(json, "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient");
        BlockStateIngredient ingredient = BlockStateIngredient.fromJson(ingredientJson);

        if (!json.has("result")) {
            throw new JsonSyntaxException("Missing result, expected to find an object");
        }
        BlockPropertyPair result = BlockStateRecipeUtil.pairFromJson(GsonHelper.getAsJsonObject(json, "result"));
        CommandFunction.CacheableFunction function = readFunction(GsonHelper.getAsString(json, "mcfunction", ""));
        return this.factory.create(id, biome, ingredient, result, function);
    }

    @Override
    public T fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome = readBiome(buffer);
        BlockStateIngredient ingredient = BlockStateIngredient.fromNetwork(buffer);
        BlockPropertyPair result = BlockStateRecipeUtil.readPair(buffer);
        CommandFunction.CacheableFunction function = readFunction(buffer.readUtf());
        return this.factory.create(id, biome, ingredient, result, function);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        writeBiome(buffer, recipe.getBiome());
        recipe.getIngredient().toNetwork(buffer);
        BlockStateRecipeUtil.writePair(buffer, recipe.getResult());
        writeFunction(buffer, recipe.getFunction());
    }

    private static Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> readBiome(FriendlyByteBuf buffer) {
        if (!buffer.readBoolean()) {
            return Optional.empty();
        }
        boolean isKey = buffer.readBoolean();
        ResourceLocation location = buffer.readResourceLocation();
        return Optional.of(isKey ? Either.left(ResourceKey.create(Registries.BIOME, location)) : Either.right(TagKey.create(Registries.BIOME, location)));
    }

    private static void writeBiome(FriendlyByteBuf buffer, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome) {
        buffer.writeBoolean(biome.isPresent());
        biome.ifPresent(value -> {
            buffer.writeBoolean(value.left().isPresent());
            buffer.writeResourceLocation(value.left().map(ResourceKey::location).orElseGet(() -> value.right().orElseThrow().location()));
        });
    }

    private static CommandFunction.CacheableFunction readFunction(String id) {
        return id == null || id.isEmpty() ? CommandFunction.CacheableFunction.NONE : new CommandFunction.CacheableFunction(new ResourceLocation(id));
    }

    private static void writeFunction(FriendlyByteBuf buffer, @Nullable CommandFunction.CacheableFunction function) {
        ResourceLocation id = function == null ? null : function.getId();
        buffer.writeUtf(id == null ? "" : id.toString());
    }

    public interface Factory<T extends AbstractBiomeParameterRecipe> {
        T create(ResourceLocation id, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, BlockStateIngredient ingredient, BlockPropertyPair result, @Nullable CommandFunction.CacheableFunction function);
    }
}
