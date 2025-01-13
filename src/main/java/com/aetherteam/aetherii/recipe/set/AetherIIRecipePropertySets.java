package com.aetherteam.aetherii.recipe.set;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.RecipeManagerAccessor;
import com.aetherteam.aetherii.recipe.recipes.block.AmbrosiumRecipe;
import com.aetherteam.aetherii.recipe.recipes.block.IrradiationRecipe;
import com.aetherteam.aetherii.recipe.recipes.block.SwetGelRecipe;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AetherIIRecipePropertySets {
    public static final ResourceKey<RecipePropertySet> ALTAR_INPUT = register("altar_input");
    public static final ResourceKey<RecipePropertySet> AMBROSIUM_ENCHANTING_STATES = register("ambrosium_enchanting_states");
    public static final ResourceKey<RecipePropertySet> SWET_GEL_CONVERSION_STATES = register("swet_gel_conversion_states");
    public static final ResourceKey<RecipePropertySet> DUST_IRRADIATION_STATES = register("dust_irradiation_states");

    private static ResourceKey<RecipePropertySet> register(String id) {
        return ResourceKey.create(RecipePropertySet.TYPE_KEY, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, id));
    }

    public static void addToMap() {
        ImmutableMap.Builder<ResourceKey<RecipePropertySet>, RecipeManager.IngredientExtractor> propertySets = ImmutableMap.<ResourceKey<RecipePropertySet>, RecipeManager.IngredientExtractor>builder()
                .put(AetherIIRecipePropertySets.AMBROSIUM_ENCHANTING_STATES, (recipe) -> recipe instanceof AmbrosiumRecipe blockStateRecipe ? Optional.of(fromBlockStateIngredient(blockStateRecipe.getIngredient())) : Optional.empty())
                .put(AetherIIRecipePropertySets.SWET_GEL_CONVERSION_STATES, (recipe) -> recipe instanceof SwetGelRecipe blockStateRecipe ? Optional.of(fromBlockStateIngredient(blockStateRecipe.getIngredient())) : Optional.empty())
                .put(AetherIIRecipePropertySets.DUST_IRRADIATION_STATES, (recipe) -> recipe instanceof IrradiationRecipe blockStateRecipe ? Optional.of(fromBlockStateIngredient(blockStateRecipe.getIngredient())) : Optional.empty())
                .putAll(RecipeManagerAccessor.getPropertySets());

        RecipeManagerAccessor.setPropertySets(propertySets.build());
    }

    private static Ingredient fromBlockStateIngredient(BlockStateIngredient ingredient) {
        List<ItemLike> itemLikes = new ArrayList<>();
        for (BlockPropertyPair pair : Objects.requireNonNull(ingredient.getPairs())) {
            itemLikes.add(pair.block());
        }
        return Ingredient.of(itemLikes.stream());
    }
}
