package com.aetherteam.aetherii.recipe.book;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.inventory.AetherIIRecipeBookTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.item.AlkahestPurificationRecipe;
import com.aetherteam.aetherii.recipe.recipes.item.AltarEnchantingRecipe;
import com.aetherteam.aetherii.recipe.recipes.item.HourglassRestoringRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;

import java.util.List;

public class AetherIIRecipeBookCategories {
    public static final RecipeBookCategory AMBER_HOURGLASS_SEARCH = new RecipeBookCategory("AETHER_II_AMBER_HOURGLASS_SEARCH", () -> new ItemStack[]{new ItemStack(Items.COMPASS)});
    public static final RecipeBookCategory AMBER_HOURGLASS_RESTORATION = new RecipeBookCategory("AETHER_II_AMBER_HOURGLASS_RESTORATION", () -> new ItemStack[]{new ItemStack(AetherIIItems.ZANITE_GEMSTONE.get())});
    public static final RecipeBookCategory AMBER_HOURGLASS_UNCRAFTING = new RecipeBookCategory("AETHER_II_AMBER_HOURGLASS_UNCRAFTING", () -> new ItemStack[]{new ItemStack(AetherIIItems.SKYROOT_PICKAXE.get())});

    public static final RecipeBookCategory ALTAR_SEARCH = new RecipeBookCategory("AETHER_II_ALTAR_SEARCH", () -> new ItemStack[]{new ItemStack(Items.COMPASS)});
    public static final RecipeBookCategory ALTAR_FOOD = new RecipeBookCategory("AETHER_II_ALTAR_FOOD", () -> new ItemStack[]{new ItemStack(AetherIIItems.ENCHANTED_BLUEBERRY.get())});
    public static final RecipeBookCategory ALTAR_BLOCKS = new RecipeBookCategory("AETHER_II_ALTAR_BLOCKS", () -> new ItemStack[]{new ItemStack(AetherIIBlocks.QUICKSOIL_GLASS.get())});
    public static final RecipeBookCategory ALTAR_REPAIRING = new RecipeBookCategory("AETHER_II_ALTAR_REPAIRING", () -> new ItemStack[]{new ItemStack(AetherIIItems.SKYROOT_PICKAXE.get())});
    public static final RecipeBookCategory ALTAR_MISC = new RecipeBookCategory("AETHER_II_ALTAR_MISC", () -> new ItemStack[]{new ItemStack(AetherIIItems.GRAVITITE_PLATE.get())});

    public static final RecipeBookCategory ALKAHEST_PURIFIER_SEARCH = new RecipeBookCategory("AETHER_II_ALKAHEST_PURIFIER_SEARCH", () -> new ItemStack[]{new ItemStack(Items.COMPASS)});
    public static final RecipeBookCategory ALKAHEST_PURIFIER_ITEMS = new RecipeBookCategory("AETHER_II_ALKAHEST_PURIFIER_ITEMS", () -> new ItemStack[]{new ItemStack(AetherIIItems.IRRADIATED_CHUNK.get())});
    public static final RecipeBookCategory ALKAHEST_PURIFIER_BLOCKS = new RecipeBookCategory("AETHER_II_ALKAHEST_PURIFIER_BLOCKS", () -> new ItemStack[]{new ItemStack(AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get())});

    public static void registerRecipeBookSearchCategories(RegisterRecipeBookCategoriesEvent event) {
        event.registerBookCategories(AetherIIRecipeBookTypes.AMBER_HOURGLASS, List.of(
                AMBER_HOURGLASS_SEARCH.get(),
                AMBER_HOURGLASS_RESTORATION.get(),
                AMBER_HOURGLASS_UNCRAFTING.get()));
        event.registerAggregateCategory(AMBER_HOURGLASS_SEARCH.get(), List.of(
                AMBER_HOURGLASS_RESTORATION.get(),
                AMBER_HOURGLASS_UNCRAFTING.get()));
        event.registerRecipeCategoryFinder(AetherIIRecipeTypes.HOURGLASS_RESTORING.get(), recipe -> {
            if (recipe instanceof HourglassRestoringRecipe hourglassRecipe) {
                return hourglassRecipe.recipeBookCategory().get();
            }
            return AMBER_HOURGLASS_RESTORATION.get();
        });

        event.registerBookCategories(AetherIIRecipeBookTypes.ALTAR, List.of(
                ALTAR_SEARCH.get(),
                ALTAR_FOOD.get(),
                ALTAR_BLOCKS.get(),
                ALTAR_REPAIRING.get(),
                ALTAR_MISC.get()));
        event.registerAggregateCategory(ALTAR_SEARCH.get(), List.of(
                ALTAR_FOOD.get(),
                ALTAR_BLOCKS.get(),
                ALTAR_REPAIRING.get(),
                ALTAR_MISC.get()));
        event.registerRecipeCategoryFinder(AetherIIRecipeTypes.ALTAR_ENCHANTING.get(), recipe -> {
            if (recipe instanceof AltarEnchantingRecipe altarRecipe) {
                return altarRecipe.recipeBookCategory().get();
            }
            return ALTAR_MISC.get();
        });

        event.registerBookCategories(AetherIIRecipeBookTypes.ALKAHEST_PURIFIER, List.of(
                ALKAHEST_PURIFIER_SEARCH.get(),
                ALKAHEST_PURIFIER_ITEMS.get(),
                ALKAHEST_PURIFIER_BLOCKS.get()));
        event.registerAggregateCategory(ALKAHEST_PURIFIER_SEARCH.get(), List.of(
                ALKAHEST_PURIFIER_ITEMS.get(),
                ALKAHEST_PURIFIER_BLOCKS.get()));
        event.registerRecipeCategoryFinder(AetherIIRecipeTypes.ALKAHEST_PURIFICATION.get(), recipe -> {
            if (recipe instanceof AlkahestPurificationRecipe purifierRecipe) {
                return purifierRecipe.recipeBookCategory().get();
            }
            return ALKAHEST_PURIFIER_ITEMS.get();
        });
    }

    public static void init() {
        AMBER_HOURGLASS_SEARCH.get();
        AMBER_HOURGLASS_RESTORATION.get();
        AMBER_HOURGLASS_UNCRAFTING.get();
        ALTAR_SEARCH.get();
        ALTAR_FOOD.get();
        ALTAR_BLOCKS.get();
        ALTAR_REPAIRING.get();
        ALTAR_MISC.get();
        ALKAHEST_PURIFIER_SEARCH.get();
        ALKAHEST_PURIFIER_ITEMS.get();
        ALKAHEST_PURIFIER_BLOCKS.get();
    }
}
