package com.aetherteam.aetherii.recipe.recipes;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.recipe.recipes.block.*;
import com.aetherteam.aetherii.recipe.recipes.item.AlkahestPurificationRecipe;
import com.aetherteam.aetherii.recipe.recipes.item.AltarEnchantingRecipe;
import com.aetherteam.aetherii.recipe.recipes.item.HourglassRestoringRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, AetherII.MODID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<AmbrosiumRecipe>> AMBROSIUM_ENCHANTING = RECIPE_TYPES.register("ambrosium_enchanting", () -> RecipeType.simple(Identifier.fromNamespaceAndPath(AetherII.MODID, "ambrosium_enchanting")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<IrradiationRecipe>> DUST_IRRADIATION = RECIPE_TYPES.register("dust_irradiation", () -> RecipeType.simple(Identifier.fromNamespaceAndPath(AetherII.MODID, "dust_irradiation")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<AlkahestCorrosionRecipe>> ALKAHEST_CORROSION = RECIPE_TYPES.register("alkahest_corrosion", () -> RecipeType.simple(Identifier.fromNamespaceAndPath(AetherII.MODID, "alkahest_corrosion")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<SwetGelRecipe>> SWET_GEL_CONVERSION = RECIPE_TYPES.register("swet_gel_conversion", () -> RecipeType.simple(Identifier.fromNamespaceAndPath(AetherII.MODID, "swet_gel_conversion")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<IcestoneFreezableRecipe>> ICESTONE_FREEZABLE = RECIPE_TYPES.register("icestone_freezable", () -> RecipeType.simple(Identifier.fromNamespaceAndPath(AetherII.MODID, "icestone_freezable")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<AccessoryFreezableRecipe>> ACCESSORY_FREEZABLE = RECIPE_TYPES.register("accessory_freezable", () -> RecipeType.simple(Identifier.fromNamespaceAndPath(AetherII.MODID, "accessory_freezable")));

    public static final DeferredHolder<RecipeType<?>, RecipeType<HourglassRestoringRecipe>> HOURGLASS_RESTORING = RECIPE_TYPES.register("hourglass_restoring", () -> RecipeType.simple(Identifier.fromNamespaceAndPath(AetherII.MODID, "hourglass_restoring")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<AltarEnchantingRecipe>> ALTAR_ENCHANTING = RECIPE_TYPES.register("altar_enchanting", () -> RecipeType.simple(Identifier.fromNamespaceAndPath(AetherII.MODID, "altar_enchanting")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<AlkahestPurificationRecipe>> ALKAHEST_PURIFICATION = RECIPE_TYPES.register("alkahest_purification", () -> RecipeType.simple(Identifier.fromNamespaceAndPath(AetherII.MODID, "alkahest_purification")));
}