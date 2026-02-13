package com.aetherteam.aetherii.recipe.serializer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.recipe.recipes.block.*;
import com.aetherteam.aetherii.recipe.recipes.item.AltarEnchantingRecipe;
import com.aetherteam.aetherii.recipe.recipes.item.AlkahestPurificationRecipe;
import com.aetherteam.aetherii.recipe.recipes.item.HourglassRestoringRecipe;
import com.aetherteam.nitrogen.recipe.serializer.BlockStateRecipeSerializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, AetherII.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, BlockStateRecipeSerializer<AmbrosiumRecipe>> AMBROSIUM_ENCHANTING = RECIPE_SERIALIZERS.register("ambrosium_enchanting", AmbrosiumRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, BlockStateRecipeSerializer<IrradiationRecipe>> DUST_IRRADIATION = RECIPE_SERIALIZERS.register("dust_irradiation", IrradiationRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, BlockStateRecipeSerializer<AlkahestCorrosionRecipe>> ALKAHEST_CORROSION = RECIPE_SERIALIZERS.register("alkahest_corrosion", AlkahestCorrosionRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, BiomeParameterRecipeSerializer<SwetGelRecipe>> SWET_GEL_CONVERSION = RECIPE_SERIALIZERS.register("swet_gel_conversion", SwetGelRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, BiomeParameterRecipeSerializer<IcestoneFreezableRecipe>> ICESTONE_FREEZABLE = RECIPE_SERIALIZERS.register("icestone_freezable", IcestoneFreezableRecipe.Serializer::new);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HourglassRestoringRecipe>> HOURGLASS_RESTORING = RECIPE_SERIALIZERS.register("hourglass_restoring", HourglassRestoringRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AltarEnchantingRecipe>> ALTAR_ENCHANTING = RECIPE_SERIALIZERS.register("altar_enchanting", AltarEnchantingRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AlkahestPurificationRecipe>> ALKAHEST_PURIFICATION = RECIPE_SERIALIZERS.register("alkahest_purification", AlkahestPurificationRecipe.Serializer::new);
}
