package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.inventory.AetherIIRecipeBookTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.builder.book.AltarBookCategory;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.item.AltarEnchantingRecipe;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.client.event.RegisterRecipeBookCategoriesEvent;

import java.util.function.Supplier;

public class AetherIIRecipeCategories {
    public static final Supplier<RecipeBookCategories> ALTAR_SEARCH = Suppliers.memoize(() -> RecipeBookCategories.create("ALTAR_SEARCH", new ItemStack(Items.COMPASS)));
    public static final Supplier<RecipeBookCategories> ALTAR_FOOD = Suppliers.memoize(() -> RecipeBookCategories.create("ALTAR_FOOD", new ItemStack(AetherIIItems.ENCHANTED_BLUEBERRY.get())));
    public static final Supplier<RecipeBookCategories> ALTAR_BLOCKS = Suppliers.memoize(() -> RecipeBookCategories.create("ALTAR_BLOCKS", new ItemStack(AetherIIBlocks.QUICKSOIL_GLASS.get())));
    public static final Supplier<RecipeBookCategories> ALTAR_REPAIR = Suppliers.memoize(() -> RecipeBookCategories.create("ALTAR_REPAIR", new ItemStack(AetherIIItems.ZANITE_PICKAXE.get())));
    public static final Supplier<RecipeBookCategories> ALTAR_MISC = Suppliers.memoize(() -> RecipeBookCategories.create("ALTAR_MISC", new ItemStack(AetherIIItems.GRAVITITE_PLATE.get())));

    public static void registerRecipeCategories(RegisterRecipeBookCategoriesEvent event) {
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
    }
}
