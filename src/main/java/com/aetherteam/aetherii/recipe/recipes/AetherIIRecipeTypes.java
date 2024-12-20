package com.aetherteam.aetherii.recipe.recipes;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.recipe.recipes.block.*;
import com.aetherteam.aetherii.recipe.recipes.item.AltarEnchantingRecipe;
import com.aetherteam.aetherii.recipe.recipes.item.IrradiationCleansingRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, AetherII.MODID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<AmbrosiumRecipe>> AMBROSIUM_ENCHANTING = RECIPE_TYPES.register("ambrosium_enchanting", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "ambrosium_enchanting")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<IrradiationRecipe>> DUST_IRRADIATION = RECIPE_TYPES.register("dust_irradiation", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "dust_irradiation")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<AcidCorrosionRecipe>> ACID_CORROSION = RECIPE_TYPES.register("acid_corrosion", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "acid_corrosion")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<SwetGelRecipe>> SWET_GEL_CONVERSION = RECIPE_TYPES.register("swet_gel_conversion", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "swet_gel_conversion")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<IcestoneFreezableRecipe>> ICESTONE_FREEZABLE = RECIPE_TYPES.register("icestone_freezable", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "icestone_freezable")));

    public static final DeferredHolder<RecipeType<?>, RecipeType<AltarEnchantingRecipe>> ALTAR_ENCHANTING = RECIPE_TYPES.register("altar_enchanting", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "altar_enchanting")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<IrradiationCleansingRecipe>> IRRADIATION_CLEANSING = RECIPE_TYPES.register("irradiation_cleansing", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "irradiation_cleansing")));
}