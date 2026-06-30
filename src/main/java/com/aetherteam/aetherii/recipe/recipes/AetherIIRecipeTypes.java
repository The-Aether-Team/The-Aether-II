package com.aetherteam.aetherii.recipe.recipes;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.recipe.recipes.block.*;
import com.aetherteam.aetherii.recipe.recipes.item.AltarEnchantingRecipe;
import com.aetherteam.aetherii.recipe.recipes.item.AlkahestPurificationRecipe;
import com.aetherteam.aetherii.recipe.recipes.item.HourglassRestoringRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AetherIIRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, AetherII.MODID);

    public static final RegistryObject<RecipeType<AmbrosiumRecipe>> AMBROSIUM_ENCHANTING = RECIPE_TYPES.register("ambrosium_enchanting", () -> RecipeType.simple(new ResourceLocation(AetherII.MODID, "ambrosium_enchanting")));
    public static final RegistryObject<RecipeType<IrradiationRecipe>> DUST_IRRADIATION = RECIPE_TYPES.register("dust_irradiation", () -> RecipeType.simple(new ResourceLocation(AetherII.MODID, "dust_irradiation")));
    public static final RegistryObject<RecipeType<AlkahestCorrosionRecipe>> ALKAHEST_CORROSION = RECIPE_TYPES.register("alkahest_corrosion", () -> RecipeType.simple(new ResourceLocation(AetherII.MODID, "alkahest_corrosion")));
    public static final RegistryObject<RecipeType<SwetGelRecipe>> SWET_GEL_CONVERSION = RECIPE_TYPES.register("swet_gel_conversion", () -> RecipeType.simple(new ResourceLocation(AetherII.MODID, "swet_gel_conversion")));
    public static final RegistryObject<RecipeType<IcestoneFreezableRecipe>> ICESTONE_FREEZABLE = RECIPE_TYPES.register("icestone_freezable", () -> RecipeType.simple(new ResourceLocation(AetherII.MODID, "icestone_freezable")));
    public static final RegistryObject<RecipeType<AccessoryFreezableRecipe>> ACCESSORY_FREEZABLE = RECIPE_TYPES.register("accessory_freezable", () -> RecipeType.simple(new ResourceLocation(AetherII.MODID, "accessory_freezable")));

    public static final RegistryObject<RecipeType<HourglassRestoringRecipe>> HOURGLASS_RESTORING = RECIPE_TYPES.register("hourglass_restoring", () -> RecipeType.simple(new ResourceLocation(AetherII.MODID, "hourglass_restoring")));
    public static final RegistryObject<RecipeType<AltarEnchantingRecipe>> ALTAR_ENCHANTING = RECIPE_TYPES.register("altar_enchanting", () -> RecipeType.simple(new ResourceLocation(AetherII.MODID, "altar_enchanting")));
    public static final RegistryObject<RecipeType<AlkahestPurificationRecipe>> ALKAHEST_PURIFICATION = RECIPE_TYPES.register("alkahest_purification", () -> RecipeType.simple(new ResourceLocation(AetherII.MODID, "alkahest_purification")));
}
