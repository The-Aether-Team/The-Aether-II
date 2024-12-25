package com.aetherteam.aetherii.recipe.builder;

import com.aetherteam.aetherii.recipe.book.AltarBookCategory;
import com.aetherteam.aetherii.recipe.recipes.item.AltarEnchantingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class AltarEnchantingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final AltarBookCategory bookCategory;
    private final ItemStack result;
    private final Ingredient ingredient;
    private final float experience;
    private final int fuelCount;
    private final int processingTime;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    public AltarEnchantingRecipeBuilder(RecipeCategory category, AltarBookCategory bookCategory, ItemStack result, Ingredient ingredient, float experience, int fuelCount, int processingTime) {
        this.category = category;
        this.bookCategory = bookCategory;
        this.result = result;
        this.ingredient = ingredient;
        this.experience = experience;
        this.fuelCount = fuelCount;
        this.processingTime = processingTime;
    }

    public static AltarEnchantingRecipeBuilder enchanting(Ingredient ingredient, RecipeCategory category, ItemStack result, float experience, int fuelCount, int processingTime) {
        return new AltarEnchantingRecipeBuilder(category, determineRecipeCategory(new ItemStack(ingredient.items().toList().getFirst().value()), result), result, ingredient, experience, fuelCount, processingTime);
    }

    @Override
    public AltarEnchantingRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public AltarEnchantingRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        this.ensureValid(id);
        Advancement.Builder builder = output.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        AltarEnchantingRecipe recipe = new AltarEnchantingRecipe(Objects.requireNonNullElse(this.group, ""), this.bookCategory, this.ingredient, this.result, this.experience, this.fuelCount, this.processingTime);
        output.accept(id, recipe, builder.build(id.location().withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private static AltarBookCategory determineRecipeCategory(ItemStack ingredient, ItemStack result) {
        if (result.getItem().components().has(DataComponents.FOOD)) {
            return AltarBookCategory.FOOD;
        } else if (result.getItem() instanceof BlockItem) {
            return AltarBookCategory.BLOCKS;
        } else if (ingredient.getItem() == result.getItem()) {
            return AltarBookCategory.REPAIRING;
        } else {
            return AltarBookCategory.MISC;
        }
    }

    private void ensureValid(ResourceKey<Recipe<?>> id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
