package com.aetherteam.aetherii.recipe.book;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.neoforged.neoforge.client.event.RegisterRecipeBookSearchCategoriesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIRecipeBookCategories {
    public static final DeferredRegister<RecipeBookCategory> RECIPE_BOOK_CATEGORIES = DeferredRegister.create(BuiltInRegistries.RECIPE_BOOK_CATEGORY, AetherII.MODID);

    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> AMBER_HOURGLASS_RESTORATION = RECIPE_BOOK_CATEGORIES.register("amber_hourglass_restoration", RecipeBookCategory::new);
    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> AMBER_HOURGLASS_UNCRAFTING = RECIPE_BOOK_CATEGORIES.register("amber_hourglass_uncrafting", RecipeBookCategory::new);

    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> ALTAR_FOOD = RECIPE_BOOK_CATEGORIES.register("altar_food", RecipeBookCategory::new);
    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> ALTAR_BLOCKS = RECIPE_BOOK_CATEGORIES.register("altar_blocks", RecipeBookCategory::new);
    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> ALTAR_REPAIRING = RECIPE_BOOK_CATEGORIES.register("altar_repairing", RecipeBookCategory::new);
    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> ALTAR_MISC = RECIPE_BOOK_CATEGORIES.register("altar_misc", RecipeBookCategory::new);

    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> ALKAHEST_PURIFIER_ITEMS = RECIPE_BOOK_CATEGORIES.register("alkahest_purifier_items", RecipeBookCategory::new);
    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> ALKAHEST_PURIFIER_BLOCKS = RECIPE_BOOK_CATEGORIES.register("alkahest_purifier_blocks", RecipeBookCategory::new);

    public static final RecipeBookCategory AMBER_HOURGLASS_SEARCH = new RecipeBookCategory();
    public static final RecipeBookCategory ALTAR_SEARCH = new RecipeBookCategory();
    public static final RecipeBookCategory ALKAHEST_PURIFIER_SEARCH = new RecipeBookCategory();

    public static void registerRecipeBookSearchCategories(RegisterRecipeBookSearchCategoriesEvent event) {
        event.register(AMBER_HOURGLASS_SEARCH, AMBER_HOURGLASS_RESTORATION.get(), AMBER_HOURGLASS_UNCRAFTING.get());
        event.register(ALTAR_SEARCH, ALTAR_FOOD.get(), ALTAR_BLOCKS.get(), ALTAR_REPAIRING.get(), ALTAR_MISC.get());
        event.register(ALKAHEST_PURIFIER_SEARCH, ALKAHEST_PURIFIER_ITEMS.get(), ALKAHEST_PURIFIER_BLOCKS.get());
    }
}
