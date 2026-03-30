package com.aetherteam.aetherii.recipe.recipes.block;

import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.serializer.AetherIIRecipeSerializers;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import com.aetherteam.nitrogen.recipe.serializer.BlockStateRecipeSerializer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.Optional;

public class AccessoryFreezableRecipe extends AbstractBlockStateRecipe {
    public AccessoryFreezableRecipe(BlockStateIngredient ingredient, BlockPropertyPair result, Optional<Identifier> function) {
        super(AetherIIRecipeTypes.ACCESSORY_FREEZABLE.get(), ingredient, result, function);
    }

    @Override
    public RecipeSerializer<AccessoryFreezableRecipe> getSerializer() {
        return AetherIIRecipeSerializers.ACCESSORY_FREEZABLE.get();
    }

    public static class Serializer extends BlockStateRecipeSerializer<AccessoryFreezableRecipe> {
        public Serializer() {
            super(AccessoryFreezableRecipe::new);
        }
    }
}