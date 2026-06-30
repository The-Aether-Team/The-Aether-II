package com.aetherteam.aetherii.recipe.recipes.block;

import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import com.aetherteam.nitrogen.recipe.serializer.BlockStateRecipeSerializer;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
public class AlkahestCorrosionRecipe extends AbstractBlockStateRecipe implements MatchEventRecipe {
    public static final RecipeSerializer<AlkahestCorrosionRecipe> SERIALIZER = new BlockStateRecipeSerializer<>(AlkahestCorrosionRecipe::new);

    public AlkahestCorrosionRecipe(ResourceLocation id, BlockStateIngredient ingredient, BlockPropertyPair result, @Nullable CommandFunction.CacheableFunction function) {
        super(AetherIIRecipeTypes.ALKAHEST_CORROSION.get(), id, ingredient, result, function);
    }

    @Override
    public boolean matches(@Nullable Player player, Level level, BlockPos pos, @Nullable ItemStack stack, BlockState oldState, BlockState newState, RecipeType<?> recipeType) {
        return this.matches(level, pos, oldState) && MatchEventRecipe.super.matches(player, level, pos, stack, oldState, newState, recipeType);
    }

    @Override
    public RecipeSerializer<AlkahestCorrosionRecipe> getSerializer() {
        return SERIALIZER;
    }
}
