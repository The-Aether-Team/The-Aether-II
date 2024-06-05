package com.aetherteam.aetherii.recipe.recipes.item;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.recipe.AetherIIRecipeSerializers;
import com.aetherteam.aetherii.recipe.AetherIIRecipeTypes;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.level.Level;

public class ArtisanryRecipe extends SingleItemRecipe {
    public ArtisanryRecipe(String group, Ingredient ingredient, ItemStack result) {
        super(AetherIIRecipeTypes.ARTISANRY.get(), AetherIIRecipeSerializers.ARTISANRY.get(), group, ingredient, result);
    }

    @Override
    public boolean matches(Container container, Level level) {
        return this.ingredient.test(container.getItem(0));
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(AetherIIBlocks.ARTISANS_BENCH.get());
    }

    public static class Serializer extends SingleItemRecipe.Serializer<ArtisanryRecipe> {
        public Serializer() {
            super(ArtisanryRecipe::new);
        }
    }
}
