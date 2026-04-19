package com.aetherteam.aetherii.recipe;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.recipe.recipes.block.*;
import com.aetherteam.aetherii.recipe.recipes.item.AlkahestPurificationRecipe;
import com.aetherteam.aetherii.recipe.recipes.item.AltarEnchantingRecipe;
import com.aetherteam.aetherii.recipe.recipes.item.HourglassRestoringRecipe;
import com.aetherteam.aetherii.recipe.recipes.item.special.LootRepairRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, AetherII.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AmbrosiumRecipe>> AMBROSIUM_ENCHANTING = RECIPE_SERIALIZERS.register("ambrosium_enchanting", () -> AmbrosiumRecipe.SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<IrradiationRecipe>> DUST_IRRADIATION = RECIPE_SERIALIZERS.register("dust_irradiation", () -> IrradiationRecipe.SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AlkahestCorrosionRecipe>> ALKAHEST_CORROSION = RECIPE_SERIALIZERS.register("alkahest_corrosion", () -> AlkahestCorrosionRecipe.SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SwetGelRecipe>> SWET_GEL_CONVERSION = RECIPE_SERIALIZERS.register("swet_gel_conversion", () -> SwetGelRecipe.SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<IcestoneFreezableRecipe>> ICESTONE_FREEZABLE = RECIPE_SERIALIZERS.register("icestone_freezable", () -> IcestoneFreezableRecipe.SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AccessoryFreezableRecipe>> ACCESSORY_FREEZABLE = RECIPE_SERIALIZERS.register("accessory_freezable", () -> AccessoryFreezableRecipe.SERIALIZER);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HourglassRestoringRecipe>> HOURGLASS_RESTORING = RECIPE_SERIALIZERS.register("hourglass_restoring", () -> HourglassRestoringRecipe.SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AltarEnchantingRecipe>> ALTAR_ENCHANTING = RECIPE_SERIALIZERS.register("altar_enchanting", () -> AltarEnchantingRecipe.SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AlkahestPurificationRecipe>> ALKAHEST_PURIFICATION = RECIPE_SERIALIZERS.register("alkahest_purification", () -> AlkahestPurificationRecipe.SERIALIZER);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<LootRepairRecipe>> LOOT_REPAIR = RECIPE_SERIALIZERS.register("loot_repair", () -> LootRepairRecipe.SERIALIZER);

}
