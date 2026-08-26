package com.aetherteam.aetherii.recipe.builder;

import com.aetherteam.aetherii.recipe.book.AmberHourglassBookCategory;
import com.aetherteam.aetherii.recipe.recipes.item.HourglassRestoringRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class HourglassRestoringRecipeBuilder implements RecipeBuilder {
    private final AmberHourglassBookCategory bookCategory;
    private final HourglassRestoringRecipe.HourglassOutput results;
    private final Ingredient ingredient;
    private final float experience;
    private final int processingTime;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    public HourglassRestoringRecipeBuilder(AmberHourglassBookCategory bookCategory, HourglassRestoringRecipe.HourglassOutput results, Ingredient ingredient, float experience, int processingTime) {
        this.bookCategory = bookCategory;
        this.results = results;
        this.ingredient = ingredient;
        this.experience = experience;
        this.processingTime = processingTime;
    }

    public static HourglassRestoringRecipeBuilder restoring(Ingredient ingredient, HourglassRestoringRecipe.HourglassOutput results, float experience, int processingTime, boolean uncrafting) {
        return new HourglassRestoringRecipeBuilder(uncrafting ? AmberHourglassBookCategory.UNCRAFTING : AmberHourglassBookCategory.RESTORATION, results, ingredient, experience, processingTime);
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
    public void save(RecipeOutput recipeOutput, String id) {
        RecipeBuilder.super.save(recipeOutput, id);
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        this.ensureValid(id);
        Advancement.Builder builder = output.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        HourglassRestoringRecipe recipe = new HourglassRestoringRecipe(RecipeBuilder.createCraftingCommonInfo(true), new HourglassRestoringRecipe.AmberHourglassBookInfo(this.bookCategory, Objects.requireNonNullElse(this.group, "")), this.ingredient, this.results, this.experience, this.processingTime);
        output.accept(id, recipe, builder.build(id.identifier().withPrefix("recipes/" + this.bookCategory.getSerializedName() + "/")));
    }

    private void ensureValid(ResourceKey<Recipe<?>> id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.results.output1().process(RandomSource.create()));
    }
}
