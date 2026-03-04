package com.aetherteam.aetherii.recipe.recipes.block;

import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.serializer.AetherIIRecipeSerializers;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import com.aetherteam.nitrogen.recipe.serializer.BlockStateRecipeSerializer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Optional;

public class AlkahestCorrosionRecipe extends AbstractBlockStateRecipe implements MatchEventRecipe {
    public AlkahestCorrosionRecipe(BlockStateIngredient ingredient, BlockPropertyPair result, Optional<Identifier> function) {
        super(AetherIIRecipeTypes.ALKAHEST_CORROSION.get(), ingredient, result, function);
    }

    @Override
    public boolean matches(@Nullable Player player, Level level, BlockPos pos, @Nullable ItemStack stack, BlockState oldState, BlockState newState, RecipeType<?> recipeType) {
        return this.matches(level, pos, oldState) && MatchEventRecipe.super.matches(player, level, pos, stack, oldState, newState, recipeType);
    }

    @Override
    public RecipeSerializer<AlkahestCorrosionRecipe> getSerializer() {
        return AetherIIRecipeSerializers.ALKAHEST_CORROSION.get();
    }

    public static class Serializer extends BlockStateRecipeSerializer<AlkahestCorrosionRecipe> {
        public Serializer() {
            super(AlkahestCorrosionRecipe::new);
        }
    }
}
