package com.aetherteam.aetherii.recipe.book;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIRecipeBookCategories {
    public static final DeferredRegister<RecipeBookCategory> RECIPE_BOOK_CATEGORIES = DeferredRegister.create(BuiltInRegistries.RECIPE_BOOK_CATEGORY, AetherII.MODID);

    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> ALTAR_FOOD = RECIPE_BOOK_CATEGORIES.register("altar_food", RecipeBookCategory::new);
    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> ALTAR_BLOCKS = RECIPE_BOOK_CATEGORIES.register("altar_blocks", RecipeBookCategory::new);
    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> ALTAR_REPAIRING = RECIPE_BOOK_CATEGORIES.register("altar_blocks", RecipeBookCategory::new);
    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> ALTAR_MISC = RECIPE_BOOK_CATEGORIES.register("altar_misc", RecipeBookCategory::new);
}
