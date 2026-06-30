package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.recipe.recipes.block.MatchEventRecipe;
import com.aetherteam.nitrogen.recipe.recipes.BlockStateRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface ItemUseConversion<R extends MatchEventRecipe & BlockStateRecipe> {
    default <T extends R> InteractionResult convertBlock(RecipeType<T> recipeType, UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        ItemStack heldItem = context.getItemInHand();
        BlockState oldBlockState = level.getBlockState(pos);

        for (T recipe : level.getRecipeManager().getAllRecipesFor(recipeType)) {
            if (recipe != null) {
                BlockState newState = recipe.getResultState(oldBlockState);
                if (recipe.matches(player, level, pos, heldItem, oldBlockState, newState, recipeType)) {
                    if (level instanceof ServerLevel) {
                            if (recipe.convert(level, pos, newState, recipe.getFunction())) { // Converts the block according to the recipe on the server side.
                                if (player != null && !player.getAbilities().instabuild) { // Consumes the item being used for conversion if possible.
                                    heldItem.shrink(1);
                                }
                                return InteractionResult.CONSUME;
                            }
                    } else {
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }

        return InteractionResult.PASS;
    }

    default <T extends R> boolean convertBlockWithoutContext(RecipeType<T> recipeType, ServerLevel level, BlockPos pos, ItemStack stack) {
        if (!level.isClientSide()) {
            BlockState oldBlockState = level.getBlockState(pos);
            for (T recipe : level.getRecipeManager().getAllRecipesFor(recipeType)) {
                if (recipe != null) {
                    BlockState newState = recipe.getResultState(oldBlockState);
                    if (recipe.matches(null, level, pos, null, oldBlockState, newState, recipeType)) {
                        if (recipe.convert(level, pos, newState, recipe.getFunction())) {
                            stack.shrink(1);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
