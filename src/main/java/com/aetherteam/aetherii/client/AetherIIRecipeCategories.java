package com.aetherteam.aetherii.client;

import com.google.common.base.Suppliers;
import net.minecraft.world.item.crafting.RecipeBookCategories;

import java.util.function.Supplier;

public class AetherIIRecipeCategories {
    public static final Supplier<RecipeBookCategories> ALTAR_SEARCH = Suppliers.memoize(() -> RecipeBookCategories.valueOf("AETHER_II_ALTAR_SEARCH"));
    public static final Supplier<RecipeBookCategories> ALTAR_FOOD = Suppliers.memoize(() -> RecipeBookCategories.valueOf("AETHER_II_ALTAR_FOOD"));
    public static final Supplier<RecipeBookCategories> ALTAR_BLOCKS = Suppliers.memoize(() -> RecipeBookCategories.valueOf("AETHER_II_ALTAR_BLOCKS"));
    public static final Supplier<RecipeBookCategories> ALTAR_REPAIR = Suppliers.memoize(() -> RecipeBookCategories.valueOf("AETHER_II_ALTAR_REPAIR"));
    public static final Supplier<RecipeBookCategories> ALTAR_MISC = Suppliers.memoize(() -> RecipeBookCategories.valueOf("AETHER_II_ALTAR_MISC"));

    //TODO RECIPE BOOK CATEGORY
    /*public static void registerRecipeCategories(RegisterRecipeBookCategoriesEvent event) {
        // Altar Enchanting
        event.registerBookCategories(AetherIIRecipeBookTypes.ALTAR, ImmutableList.of(ALTAR_SEARCH.get(), ALTAR_FOOD.get(), ALTAR_BLOCKS.get(), ALTAR_REPAIR.get(), ALTAR_MISC.get()));
        event.registerAggregateCategory(ALTAR_SEARCH.get(), ImmutableList.of(ALTAR_FOOD.get(), ALTAR_BLOCKS.get(), ALTAR_REPAIR.get(), ALTAR_MISC.get()));
        event.registerRecipeCategoryFinder(AetherIIRecipeTypes.ALTAR_ENCHANTING.get(), recipe -> {
            if (recipe.value() instanceof AltarEnchantingRecipe altarEnchantingRecipe) {
                if (altarEnchantingRecipe.category() == AltarBookCategory.FOOD) {
                    return ALTAR_FOOD.get();
                } else if (altarEnchantingRecipe.category() == AltarBookCategory.BLOCKS) {
                    return ALTAR_BLOCKS.get();
                } else if (altarEnchantingRecipe.category() == AltarBookCategory.REPAIRING) {
                    return ALTAR_REPAIR.get();
                }
            }
            return ALTAR_MISC.get();
        });
    }*/
}
