package com.aetherteam.aetherii.recipe.recipes.block;

import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.serializer.BiomeParameterRecipeSerializer;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.mojang.datafixers.util.Either;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Optional;

public class SwetGelRecipe extends AbstractBiomeParameterRecipe implements MatchEventRecipe {
    public static final RecipeSerializer<SwetGelRecipe> SERIALIZER = new BiomeParameterRecipeSerializer<>(SwetGelRecipe::new);

    public SwetGelRecipe(ResourceLocation id, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, BlockStateIngredient ingredient, BlockPropertyPair result, CommandFunction.CacheableFunction function) {
        super(AetherIIRecipeTypes.SWET_GEL_CONVERSION.get(), id, biome, ingredient, result, function);
    }

    public SwetGelRecipe(ResourceLocation id, BlockStateIngredient ingredient, BlockPropertyPair result, CommandFunction.CacheableFunction function) {
        this(id, Optional.empty(), ingredient, result, function);
    }

    @Override
    public boolean matches(@Nullable Player player, Level level, BlockPos pos, @Nullable ItemStack stack, BlockState oldState, BlockState newState, RecipeType<?> recipeType) {
        return this.matches(level, pos, oldState) && MatchEventRecipe.super.matches(player, level, pos, stack, oldState, newState, recipeType);
    }

    @Override
    public RecipeSerializer<SwetGelRecipe> getSerializer() {
        return SERIALIZER;
    }
}
