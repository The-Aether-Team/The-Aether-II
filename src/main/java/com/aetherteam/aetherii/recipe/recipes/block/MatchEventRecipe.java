package com.aetherteam.aetherii.recipe.recipes.block;

import com.aetherteam.nitrogen.recipe.BlockStateRecipeUtil;
import net.minecraft.commands.CacheableFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Optional;

public interface MatchEventRecipe {
    default boolean convert(Level level, BlockPos pos, BlockState newState, Optional<CacheableFunction> function) {
        level.setBlockAndUpdate(pos, newState);
        BlockStateRecipeUtil.executeFunction(level, pos, function);
        return true;
    }

    default boolean matches(@Nullable Player player, Level level, BlockPos pos, @Nullable ItemStack stack, BlockState oldState, BlockState newState, RecipeType<?> recipeType) {
//        ItemUseConvertEvent event = AetherEventDispatch.onItemUseConvert(player, level, pos, stack, oldState, newState, recipeType);
//        return !event.isCanceled(); //todo events
        return true;
    }
}
