package com.aetherteam.aetherii.recipe;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.recipe.recipes.block.AmbrosiumRecipe;
import com.aetherteam.aetherii.recipe.recipes.block.IcestoneFreezableRecipe;
import com.aetherteam.aetherii.recipe.recipes.block.SwetGelRecipe;
import com.aetherteam.aetherii.recipe.recipes.item.ArtisanryRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, AetherII.MODID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<AmbrosiumRecipe>> AMBROSIUM_ENCHANTING = RECIPE_TYPES.register("ambrosium_enchanting", () -> RecipeType.simple(new ResourceLocation(AetherII.MODID, "ambrosium_enchanting")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<SwetGelRecipe>> SWET_GEL_CONVERSION = RECIPE_TYPES.register("swet_gel_conversion", () -> RecipeType.simple(new ResourceLocation(AetherII.MODID, "swet_gel_conversion")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<IcestoneFreezableRecipe>> ICESTONE_FREEZABLE = RECIPE_TYPES.register("icestone_freezable", () -> RecipeType.simple(new ResourceLocation(AetherII.MODID, "icestone_freezable")));

    public static final DeferredHolder<RecipeType<?>, RecipeType<ArtisanryRecipe>> ARTISANRY = RECIPE_TYPES.register("artisanry", () -> RecipeType.simple(new ResourceLocation(AetherII.MODID, "artisanry")));
}