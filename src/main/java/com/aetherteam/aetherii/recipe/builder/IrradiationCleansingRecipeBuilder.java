package com.aetherteam.aetherii.recipe.builder;

import com.aetherteam.aetherii.recipe.recipes.item.AltarEnchantingRecipe;
import com.aetherteam.aetherii.recipe.recipes.item.IrradiationCleansingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class IrradiationCleansingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final SimpleWeightedRandomList<ItemStack> results;
    private final Ingredient ingredient;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    public IrradiationCleansingRecipeBuilder(RecipeCategory category, Ingredient ingredient, SimpleWeightedRandomList<ItemStack> results) {
        this.category = category;
        this.results = results;
        this.ingredient = ingredient;
    }

    public static IrradiationCleansingRecipeBuilder recipe(RecipeCategory category, Ingredient ingredient, SimpleWeightedRandomList<ItemStack> results) {
        return new IrradiationCleansingRecipeBuilder(category, ingredient, results);
    }

    @Override
    public IrradiationCleansingRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public IrradiationCleansingRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        return Items.AIR;
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        this.ensureValid(id);
        Advancement.Builder builder = output.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        IrradiationCleansingRecipe recipe = new IrradiationCleansingRecipe(Objects.requireNonNullElse(this.group, ""), this.ingredient, this.results);
        output.accept(id, recipe, builder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
