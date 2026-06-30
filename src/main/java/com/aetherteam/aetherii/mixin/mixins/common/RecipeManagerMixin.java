package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.recipe.AetherIIRecipePriorities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Optional;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {
    @Shadow
    private Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipes;

    @Inject(method = "getRecipeFor(Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/world/Container;Lnet/minecraft/world/level/Level;)Ljava/util/Optional;", at = @At("HEAD"), cancellable = true)
    @SuppressWarnings("unchecked")
    private <C extends Container, T extends Recipe<C>> void getPrioritizedRecipe(RecipeType<T> recipeType, C container, Level level, CallbackInfoReturnable<Optional<T>> cir) {
        if (recipeType != RecipeType.CRAFTING) {
            return;
        }

        T prioritizedRecipe = null;
        int prioritizedValue = 0;
        Map<ResourceLocation, Recipe<?>> recipes = this.recipes.get(recipeType);
        if (recipes == null) {
            return;
        }

        for (Map.Entry<ResourceLocation, Recipe<?>> entry : recipes.entrySet()) {
            T recipe = (T) entry.getValue();
            int priority = AetherIIRecipePriorities.get(entry.getKey());
            if (priority > prioritizedValue && recipe.matches(container, level)) {
                prioritizedRecipe = recipe;
                prioritizedValue = priority;
            }
        }

        if (prioritizedRecipe != null) {
            cir.setReturnValue(Optional.of(prioritizedRecipe));
        }
    }
}
