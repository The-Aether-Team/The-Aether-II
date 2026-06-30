package com.aetherteam.aetherii.recipe.builder;

import com.aetherteam.aetherii.recipe.book.AltarBookCategory;
import com.aetherteam.aetherii.recipe.recipes.item.AltarEnchantingRecipe;
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
import com.aetherteam.aetherii.item.components.ItemStackTemplate;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class AltarEnchantingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final AltarBookCategory bookCategory;
    private final ItemStackTemplate result;
    private final Ingredient ingredient;
    private final float experience;
    private final int fuelCount;
    private final int processingTime;
    private final Map<String, CriterionTriggerInstance> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    public AltarEnchantingRecipeBuilder(RecipeCategory category, AltarBookCategory bookCategory, ItemStackTemplate result, Ingredient ingredient, float experience, int fuelCount, int processingTime) {
        this.category = category;
        this.bookCategory = bookCategory;
        this.result = result;
        this.ingredient = ingredient;
        this.experience = experience;
        this.fuelCount = fuelCount;
        this.processingTime = processingTime;
    }

    public static AltarEnchantingRecipeBuilder enchanting(Ingredient ingredient, RecipeCategory category, AltarBookCategory bookCategory, ItemStackTemplate result, float experience, int fuelCount, int processingTime) {
        return new AltarEnchantingRecipeBuilder(category, bookCategory, result, ingredient, experience, fuelCount, processingTime);
    }

    @Override
    public AltarEnchantingRecipeBuilder unlockedBy(String name, CriterionTriggerInstance criterion) {
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
        return this.result.item();
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
        AltarEnchantingRecipe recipe = new AltarEnchantingRecipe(id, Objects.requireNonNullElse(this.group, ""), this.bookCategory, this.ingredient, this.result, this.experience, this.fuelCount, this.processingTime);
        output.accept(new Result(id, recipe, builder, id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    private record Result(ResourceLocation id, AltarEnchantingRecipe recipe, Advancement.Builder advancement, ResourceLocation advancementId) implements FinishedRecipe {
        @Override
        public void serializeRecipeData(JsonObject json) {
            AltarEnchantingRecipe.toJson(this.recipe).entrySet().forEach(entry -> json.add(entry.getKey(), entry.getValue()));
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return AltarEnchantingRecipe.SERIALIZER;
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
