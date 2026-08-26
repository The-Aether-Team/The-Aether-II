package com.aetherteam.aetherii.recipe.builder;

import com.aetherteam.aetherii.recipe.book.AltarBookCategory;
import com.aetherteam.aetherii.recipe.recipes.item.AltarEnchantingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class AltarEnchantingRecipeBuilder implements RecipeBuilder {
    private final AltarBookCategory bookCategory;
    private final ItemStackTemplate result;
    private final Ingredient ingredient;
    private final float experience;
    private final int fuelCount;
    private final int processingTime;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    public AltarEnchantingRecipeBuilder(AltarBookCategory bookCategory, ItemStackTemplate result, Ingredient ingredient, float experience, int fuelCount, int processingTime) {
        this.bookCategory = bookCategory;
        this.result = result;
        this.ingredient = ingredient;
        this.experience = experience;
        this.fuelCount = fuelCount;
        this.processingTime = processingTime;
    }

    public static AltarEnchantingRecipeBuilder enchanting(Ingredient ingredient, AltarBookCategory bookCategory, ItemStackTemplate result, float experience, int fuelCount, int processingTime) {
        return new AltarEnchantingRecipeBuilder(bookCategory, result, ingredient, experience, fuelCount, processingTime);
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
    public void save(RecipeOutput recipeOutput, String id) {
        RecipeBuilder.super.save(recipeOutput, id);
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        this.ensureValid(id);
        Advancement.Builder builder = output.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        AltarEnchantingRecipe recipe = new AltarEnchantingRecipe(RecipeBuilder.createCraftingCommonInfo(true), new AltarEnchantingRecipe.AltarBookInfo(this.bookCategory, Objects.requireNonNullElse(this.group, "")), this.ingredient, this.result, this.experience, this.fuelCount, this.processingTime);
        output.accept(id, recipe, builder.build(id.identifier().withPrefix("recipes/" + this.bookCategory.getSerializedName() + "/")));
    }

    private void ensureValid(ResourceKey<Recipe<?>> id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.result);
    }
}
