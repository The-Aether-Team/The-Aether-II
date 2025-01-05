package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipePropertySet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(RecipeManager.class)
public interface RecipeManagerAccessor {
    @Accessor("RECIPE_PROPERTY_SETS")
    static Map<ResourceKey<RecipePropertySet>, RecipeManager.IngredientExtractor> getPropertySets() {
        throw new UnsupportedOperationException();
    }

    @Mutable
    @Accessor("RECIPE_PROPERTY_SETS")
    static void setPropertySets(Map<ResourceKey<RecipePropertySet>, RecipeManager.IngredientExtractor> propertySets) {
        throw new UnsupportedOperationException();
    }
}
