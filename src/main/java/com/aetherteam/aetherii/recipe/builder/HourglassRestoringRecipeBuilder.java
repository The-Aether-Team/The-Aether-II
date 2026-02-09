package com.aetherteam.aetherii.recipe.builder;

import com.aetherteam.aetherii.recipe.book.AmberHourglassBookCategory;
import com.aetherteam.aetherii.recipe.recipes.item.HourglassRestoringRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class HourglassRestoringRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final AmberHourglassBookCategory bookCategory;
    private final HourglassRestoringRecipe.HourglassOutput results;
    private final Ingredient ingredient;
    private final float experience;
    private final int processingTime;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    public HourglassRestoringRecipeBuilder(RecipeCategory category, AmberHourglassBookCategory bookCategory, HourglassRestoringRecipe.HourglassOutput results, Ingredient ingredient, float experience, int processingTime) {
        this.category = category;
        this.bookCategory = bookCategory;
        this.results = results;
        this.ingredient = ingredient;
        this.experience = experience;
        this.processingTime = processingTime;
    }

    public static HourglassRestoringRecipeBuilder restoring(Ingredient ingredient, RecipeCategory category, HourglassRestoringRecipe.HourglassOutput results, float experience, int processingTime, boolean uncrafting) {
        return new HourglassRestoringRecipeBuilder(category, uncrafting ? AmberHourglassBookCategory.UNCRAFTING : determineRecipeCategory(new ItemStack(ingredient.items().toList().getFirst().value()), results), results, ingredient, experience, processingTime);
    }

    @Override
    public HourglassRestoringRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public HourglassRestoringRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        return Items.AIR; //todo?
    }

    @Override
    public void save(RecipeOutput recipeOutput, String id) {
        RecipeBuilder.super.save(recipeOutput, id);
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        this.ensureValid(id);
        Advancement.Builder builder = output.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        HourglassRestoringRecipe recipe = new HourglassRestoringRecipe(Objects.requireNonNullElse(this.group, ""), this.bookCategory, this.ingredient, this.results, this.experience, this.processingTime);
        output.accept(id, recipe, builder.build(id.location().withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private static AmberHourglassBookCategory determineRecipeCategory(ItemStack ingredient, HourglassRestoringRecipe.HourglassOutput results) { //todo determine based on results? how do we actually go about this in the recipe book.
        if (ingredient.getItem() instanceof BlockItem) {
            return AmberHourglassBookCategory.BLOCKS;
        } else {
            return AmberHourglassBookCategory.ITEMS;
        }
    }

    private void ensureValid(ResourceKey<Recipe<?>> id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
