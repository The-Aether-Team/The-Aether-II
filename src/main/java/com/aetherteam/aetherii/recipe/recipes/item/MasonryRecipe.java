package com.aetherteam.aetherii.recipe.recipes.item;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.recipe.AetherIIRecipeSerializers;
import com.aetherteam.aetherii.recipe.AetherIIRecipeTypes;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.level.Level;

public class MasonryRecipe extends SingleItemRecipe {
    public MasonryRecipe(String group, Ingredient ingredient, ItemStack result) {
        super(AetherIIRecipeTypes.MASONRY.get(), AetherIIRecipeSerializers.MASONRY.get(), group, ingredient, result);
    }

    @Override
    public boolean matches(Container container, Level level) {
        return this.ingredient.test(container.getItem(0));
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(AetherIIBlocks.MASONRY_BENCH.get());
    }

    public static class Serializer extends SingleItemRecipe.Serializer<MasonryRecipe> {
        public Serializer() {
            super(MasonryRecipe::new);
        }
    }
}
