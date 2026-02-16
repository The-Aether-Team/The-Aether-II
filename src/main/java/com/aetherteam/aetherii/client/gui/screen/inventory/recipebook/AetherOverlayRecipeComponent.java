package com.aetherteam.aetherii.client.gui.screen.inventory.recipebook;

import com.aetherteam.aetherii.mixin.mixins.client.accessor.OverlayRecipeComponentAccessor;
import com.aetherteam.aetherii.recipe.display.AlkahestPurifierRecipeDisplay;
import com.aetherteam.aetherii.recipe.display.AltarRecipeDisplay;
import com.aetherteam.aetherii.recipe.display.AmberHourglassRecipeDisplay;
import net.minecraft.client.gui.screens.recipebook.OverlayRecipeComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.gui.screens.recipebook.SlotSelectTime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.RecipeDisplayEntry;
import net.minecraft.world.item.crafting.display.RecipeDisplayId;

import java.util.Collections;
import java.util.List;

public class AetherOverlayRecipeComponent extends OverlayRecipeComponent {
    private final RecipeBookComponent<?> parent;

    public AetherOverlayRecipeComponent(RecipeBookComponent<?> parent, SlotSelectTime slotSelectTime, boolean isFurnaceMenu) {
        super(slotSelectTime, isFurnaceMenu);
        this.parent = parent;
    }

    @Override
    public void init(RecipeCollection collection, ContextMap contextMap, boolean isFiltering, int x, int y, int overlayX, int overlayY, float width) {
        super.init(collection, contextMap, isFiltering, x, y, overlayX, overlayY, width);
        OverlayRecipeComponentAccessor overlayAccessor = (OverlayRecipeComponentAccessor) this;
        overlayAccessor.aether_ii$getRecipeButtons().removeIf((button) -> button instanceof OverlayRecipeButton);

        List<RecipeDisplayEntry> craftable = collection.getSelectedRecipes(RecipeCollection.CraftableStatus.CRAFTABLE);
        List<RecipeDisplayEntry> uncraftable = isFiltering ? Collections.emptyList() : collection.getSelectedRecipes(RecipeCollection.CraftableStatus.NOT_CRAFTABLE);
        int i = craftable.size();
        int j = i + uncraftable.size();
        int k = j <= 16 ? 4 : 5;
        for (int i1 = 0; i1 < j; ++i1) {
            boolean flag = i1 < i;
            RecipeDisplayEntry displayEntry = flag ? craftable.get(i1) : uncraftable.get(i1 - i);
            int j1 = overlayAccessor.aether_ii$getX() + 4 + 25 * (i1 % k);
            int k1 = overlayAccessor.aether_ii$getY() + 5 + 25 * (i1 / k);

            if (this.parent instanceof AmberHourglassRecipeBookComponent) {
                overlayAccessor.aether_ii$getRecipeButtons().add(new OverlayHourglassRecipeButton(j1, k1, displayEntry.id(), displayEntry.display(), contextMap, flag));
            } else if (this.parent instanceof AltarRecipeBookComponent) {
                overlayAccessor.aether_ii$getRecipeButtons().add(new OverlayAltarRecipeButton(j1, k1, displayEntry.id(), displayEntry.display(), contextMap, flag));
            } else if (this.parent instanceof AlkahestPurifierRecipeBookComponent) {
                overlayAccessor.aether_ii$getRecipeButtons().add(new OverlayPurifierRecipeButton(j1, k1, displayEntry.id(), displayEntry.display(), contextMap, flag));
            }
        }
    }

    public class OverlayHourglassRecipeButton extends OverlayRecipeButton {
        private static final ResourceLocation ENABLED_SPRITE = ResourceLocation.withDefaultNamespace("recipe_book/furnace_overlay");
        private static final ResourceLocation HIGHLIGHTED_ENABLED_SPRITE = ResourceLocation.withDefaultNamespace("recipe_book/furnace_overlay_highlighted");
        private static final ResourceLocation DISABLED_SPRITE = ResourceLocation.withDefaultNamespace("recipe_book/furnace_overlay_disabled");
        private static final ResourceLocation HIGHLIGHTED_DISABLED_SPRITE = ResourceLocation.withDefaultNamespace("recipe_book/furnace_overlay_disabled_highlighted");

        public OverlayHourglassRecipeButton(int x, int y, RecipeDisplayId recipe, RecipeDisplay recipeDisplay, ContextMap contextMap, boolean isCraftable) {
            super(x, y, recipe, isCraftable, calculateIngredientsPositions(recipeDisplay, contextMap));
        }

        private static List<Pos> calculateIngredientsPositions(RecipeDisplay recipeDisplay, ContextMap contextMap) {
            if (recipeDisplay instanceof AmberHourglassRecipeDisplay display) {
                List<ItemStack> list = display.ingredient().resolveForStacks(contextMap);
                if (!list.isEmpty()) {
                    return List.of(createGridPos(1, 1, list));
                }
            }
            return List.of();
        }

        @Override
        protected ResourceLocation getSprite(boolean highlight) {
            if (highlight) {
                return this.isHoveredOrFocused() ? HIGHLIGHTED_ENABLED_SPRITE : ENABLED_SPRITE;
            } else {
                return this.isHoveredOrFocused() ? HIGHLIGHTED_DISABLED_SPRITE : DISABLED_SPRITE;
            }
        }
    }

    public class OverlayAltarRecipeButton extends OverlayRecipeButton {
        private static final ResourceLocation ENABLED_SPRITE = ResourceLocation.withDefaultNamespace("recipe_book/furnace_overlay");
        private static final ResourceLocation HIGHLIGHTED_ENABLED_SPRITE = ResourceLocation.withDefaultNamespace("recipe_book/furnace_overlay_highlighted");
        private static final ResourceLocation DISABLED_SPRITE = ResourceLocation.withDefaultNamespace("recipe_book/furnace_overlay_disabled");
        private static final ResourceLocation HIGHLIGHTED_DISABLED_SPRITE = ResourceLocation.withDefaultNamespace("recipe_book/furnace_overlay_disabled_highlighted");

        public OverlayAltarRecipeButton(int x, int y, RecipeDisplayId recipe, RecipeDisplay recipeDisplay, ContextMap contextMap, boolean isCraftable) {
            super(x, y, recipe, isCraftable, calculateIngredientsPositions(recipeDisplay, contextMap));
        }

        private static List<Pos> calculateIngredientsPositions(RecipeDisplay recipeDisplay, ContextMap contextMap) {
            if (recipeDisplay instanceof AltarRecipeDisplay display) {
                List<ItemStack> list = display.ingredient().resolveForStacks(contextMap);
                if (!list.isEmpty()) {
                    return List.of(createGridPos(1, 1, list));
                }
            }
            return List.of();
        }

        @Override
        protected ResourceLocation getSprite(boolean highlight) {
            if (highlight) {
                return this.isHoveredOrFocused() ? HIGHLIGHTED_ENABLED_SPRITE : ENABLED_SPRITE;
            } else {
                return this.isHoveredOrFocused() ? HIGHLIGHTED_DISABLED_SPRITE : DISABLED_SPRITE;
            }
        }
    }

    public class OverlayPurifierRecipeButton extends OverlayRecipeButton {
        private static final ResourceLocation ENABLED_SPRITE = ResourceLocation.withDefaultNamespace("recipe_book/furnace_overlay");
        private static final ResourceLocation HIGHLIGHTED_ENABLED_SPRITE = ResourceLocation.withDefaultNamespace("recipe_book/furnace_overlay_highlighted");
        private static final ResourceLocation DISABLED_SPRITE = ResourceLocation.withDefaultNamespace("recipe_book/furnace_overlay_disabled");
        private static final ResourceLocation HIGHLIGHTED_DISABLED_SPRITE = ResourceLocation.withDefaultNamespace("recipe_book/furnace_overlay_disabled_highlighted");

        public OverlayPurifierRecipeButton(int x, int y, RecipeDisplayId recipe, RecipeDisplay recipeDisplay, ContextMap contextMap, boolean isCraftable) {
            super(x, y, recipe, isCraftable, calculateIngredientsPositions(recipeDisplay, contextMap));
        }

        private static List<Pos> calculateIngredientsPositions(RecipeDisplay recipeDisplay, ContextMap contextMap) {
            if (recipeDisplay instanceof AlkahestPurifierRecipeDisplay display) {
                List<ItemStack> list = display.ingredient().resolveForStacks(contextMap);
                if (!list.isEmpty()) {
                    return List.of(createGridPos(1, 1, list));
                }
            }
            return List.of();
        }

        @Override
        protected ResourceLocation getSprite(boolean highlight) {
            if (highlight) {
                return this.isHoveredOrFocused() ? HIGHLIGHTED_ENABLED_SPRITE : ENABLED_SPRITE;
            } else {
                return this.isHoveredOrFocused() ? HIGHLIGHTED_DISABLED_SPRITE : DISABLED_SPRITE;
            }
        }
    }
}
