package com.aetherteam.aetherii.recipe.builder;

import com.aetherteam.aetherii.recipe.book.AmberHourglassBookCategory;
import com.aetherteam.aetherii.recipe.recipes.item.HourglassRestoringRecipe;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class HourglassRestoringRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final AmberHourglassBookCategory bookCategory;
    private final HourglassRestoringRecipe.HourglassOutput results;
    private final Ingredient ingredient;
    private final float experience;
    private final int processingTime;
    private final Map<String, CriterionTriggerInstance> criteria = new LinkedHashMap<>();
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
        return new HourglassRestoringRecipeBuilder(category, uncrafting ? AmberHourglassBookCategory.UNCRAFTING : AmberHourglassBookCategory.RESTORATION, results, ingredient, experience, processingTime);
    }

    @Override
    public HourglassRestoringRecipeBuilder unlockedBy(String name, CriterionTriggerInstance criterion) {
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
        return this.results.output1().process(RandomSource.create()).getItem();
    }

    @Override
    public void save(Consumer<FinishedRecipe> output, ResourceLocation id) {
        this.ensureValid(id);
        Advancement.Builder builder = Advancement.Builder.recipeAdvancement()
                .parent(ROOT_RECIPE_ADVANCEMENT)
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(RequirementsStrategy.OR);
        this.criteria.forEach(builder::addCriterion);
        HourglassRestoringRecipe recipe = new HourglassRestoringRecipe(id, Objects.requireNonNullElse(this.group, ""), this.bookCategory, this.ingredient, this.results, this.experience, this.processingTime);
        output.accept(new Result(id, recipe, builder, id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    private record Result(ResourceLocation id, HourglassRestoringRecipe recipe, Advancement.Builder advancement, ResourceLocation advancementId) implements FinishedRecipe {
        @Override
        public void serializeRecipeData(JsonObject json) {
            HourglassRestoringRecipe.toJson(this.recipe).entrySet().forEach(entry -> json.add(entry.getKey(), entry.getValue()));
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return HourglassRestoringRecipe.SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
