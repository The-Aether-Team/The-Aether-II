package com.aetherteam.aetherii.recipe.builder;

import com.aetherteam.aetherii.recipe.book.AlkahestPurifierBookCategory;
import com.aetherteam.aetherii.recipe.book.AltarBookCategory;
import com.aetherteam.aetherii.recipe.recipes.item.AlkahestPurificationRecipe;
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
import net.minecraft.util.random.SimpleWeightedRandomList;
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

public class AlkahestPurificationRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final AlkahestPurifierBookCategory bookCategory;
    private final SimpleWeightedRandomList<ItemStack> results;
    private final Ingredient ingredient;
    private final ItemStack byproduct;
    private final float experience;
    private final int alkahestUsage;
    private final int processingTime;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    public AlkahestPurificationRecipeBuilder(RecipeCategory category, AlkahestPurifierBookCategory bookCategory, SimpleWeightedRandomList<ItemStack> results, Ingredient ingredient, ItemStack byproduct, float experience, int alkahestUsage, int processingTime) {
        this.category = category;
        this.bookCategory = bookCategory;
        this.results = results;
        this.ingredient = ingredient;
        this.byproduct = byproduct;
        this.experience = experience;
        this.alkahestUsage = alkahestUsage;
        this.processingTime = processingTime;
    }

    //todo i need the byproduct to be randomized too...

    public static AlkahestPurificationRecipeBuilder recipe(Ingredient ingredient, RecipeCategory category, SimpleWeightedRandomList<ItemStack> results, ItemStack byproduct, float experience, int alkahestUsage, int processingTime) {
        return new AlkahestPurificationRecipeBuilder(category, determineRecipeCategory(new ItemStack(results.unwrap().getFirst().data().getItem())), results, ingredient, byproduct, experience, alkahestUsage, processingTime);
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
    public Item getResult() {
        return Items.AIR;
    } //todo?

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        this.ensureValid(id);
        Advancement.Builder builder = output.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        AlkahestPurificationRecipe recipe = new AlkahestPurificationRecipe(Objects.requireNonNullElse(this.group, ""), this.bookCategory, this.ingredient, this.results, this.byproduct, this.experience, this.alkahestUsage, this.processingTime);
        output.accept(id, recipe, builder.build(id.location().withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private static AlkahestPurifierBookCategory determineRecipeCategory(ItemStack result) {
        if (result.getItem() instanceof BlockItem) {
            return AlkahestPurifierBookCategory.BLOCKS;
        } else {
            return AlkahestPurifierBookCategory.ITEMS;
        }
    }

    private void ensureValid(ResourceKey<Recipe<?>> id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
