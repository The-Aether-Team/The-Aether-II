package com.aetherteam.aetherii.recipe.builder;

import com.aetherteam.aetherii.recipe.book.AlkahestPurifierBookCategory;
import com.aetherteam.aetherii.recipe.recipes.OutputEntry;
import com.aetherteam.aetherii.recipe.recipes.item.AlkahestPurificationRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class AlkahestPurificationRecipeBuilder implements RecipeBuilder {
    private final AlkahestPurifierBookCategory bookCategory;
    private final OutputEntry.BaseEntry results;
    private final OutputEntry.BaseEntry byproducts;
    private final Ingredient ingredient;
    private final float experience;
    private final int alkahestUsage;
    private final int processingTime;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    public AlkahestPurificationRecipeBuilder(AlkahestPurifierBookCategory bookCategory, OutputEntry.BaseEntry results, OutputEntry.BaseEntry byproducts, Ingredient ingredient, float experience, int alkahestUsage, int processingTime) {
        this.bookCategory = bookCategory;
        this.results = results;
        this.byproducts = byproducts;
        this.ingredient = ingredient;
        this.experience = experience;
        this.alkahestUsage = alkahestUsage;
        this.processingTime = processingTime;
    }

    public static AlkahestPurificationRecipeBuilder recipe(Ingredient ingredient, AlkahestPurifierBookCategory bookCategory, OutputEntry.BaseEntry results, OutputEntry.BaseEntry byproducts, float experience, int alkahestUsage, int processingTime) {
        return new AlkahestPurificationRecipeBuilder(bookCategory, results, byproducts, ingredient, experience, alkahestUsage, processingTime);
    }

    @Override
    public AlkahestPurificationRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public AlkahestPurificationRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        this.ensureValid(id);
        Advancement.Builder builder = output.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        AlkahestPurificationRecipe recipe = new AlkahestPurificationRecipe(RecipeBuilder.createCraftingCommonInfo(true), new AlkahestPurificationRecipe.AlkahestPurifierBookInfo(this.bookCategory, Objects.requireNonNullElse(this.group, "")), this.ingredient, this.results, this.byproducts, this.experience, this.alkahestUsage, this.processingTime);
        output.accept(id, recipe, builder.build(id.identifier().withPrefix("recipes/" + this.bookCategory.getSerializedName() + "/")));
    }

    private void ensureValid(ResourceKey<Recipe<?>> id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.results.process(RandomSource.create()));
    }
}
