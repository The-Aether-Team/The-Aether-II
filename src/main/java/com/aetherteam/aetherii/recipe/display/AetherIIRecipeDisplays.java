package com.aetherteam.aetherii.recipe.display;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIRecipeDisplays {
    public static final DeferredRegister<RecipeDisplay.Type<?>> RECIPE_DISPLAYS = DeferredRegister.create(BuiltInRegistries.RECIPE_DISPLAY, AetherII.MODID);

    public static final DeferredHolder<RecipeDisplay.Type<?>, RecipeDisplay.Type<AmberHourglassRecipeDisplay>> AMBER_HOURGLASS = RECIPE_DISPLAYS.register("amber_hourglass", () -> AmberHourglassRecipeDisplay.TYPE);
    public static final DeferredHolder<RecipeDisplay.Type<?>, RecipeDisplay.Type<AltarRecipeDisplay>> ALTAR = RECIPE_DISPLAYS.register("altar", () -> AltarRecipeDisplay.TYPE);
    public static final DeferredHolder<RecipeDisplay.Type<?>, RecipeDisplay.Type<AlkahestPurifierRecipeDisplay>> ALKAHEST_PURIFIER = RECIPE_DISPLAYS.register("alkahest_purifier", () -> AlkahestPurifierRecipeDisplay.TYPE);
}