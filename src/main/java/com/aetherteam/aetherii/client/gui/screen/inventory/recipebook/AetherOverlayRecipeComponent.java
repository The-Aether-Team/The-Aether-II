package com.aetherteam.aetherii.client.gui.screen.inventory.recipebook;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.OverlayRecipeComponentAccessor;
import com.aetherteam.aetherii.recipe.display.AlkahestPurifierRecipeDisplay;
import com.aetherteam.aetherii.recipe.display.AltarRecipeDisplay;
import com.aetherteam.aetherii.recipe.display.AmberHourglassRecipeDisplay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.OverlayRecipeComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.gui.screens.recipebook.SlotSelectTime;
import net.minecraft.client.renderer.RenderPipelines;
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
    private final SlotSelectTime slotSelectTime;

    public AetherOverlayRecipeComponent(RecipeBookComponent<?> parent, SlotSelectTime slotSelectTime, boolean isFurnaceMenu) {
        super(slotSelectTime, isFurnaceMenu);
        this.parent = parent;
        this.slotSelectTime = slotSelectTime;
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

    public class OverlaySingleRecipeButton extends OverlayRecipeButton {
        private static final ResourceLocation ENABLED_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "recipe_book/single_overlay");
        private static final ResourceLocation HIGHLIGHTED_ENABLED_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "recipe_book/single_overlay_highlighted");
        private static final ResourceLocation DISABLED_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "recipe_book/single_overlay_disabled");
        private static final ResourceLocation HIGHLIGHTED_DISABLED_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "recipe_book/single_overlay_disabled_highlighted");

        private final boolean isCraftable;
        private final List<Pos> slots;

        public OverlaySingleRecipeButton(int x, int y, RecipeDisplayId recipe, boolean isCraftable, List<Pos> slots) {
            super(x, y, recipe, isCraftable, slots);
            this.isCraftable = isCraftable;
            this.slots = slots;
        }

        @Override
        public void renderWidget(GuiGraphics guiGraphics, int p_283483_, int p_282919_, float p_282165_) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.getSprite(this.isCraftable), this.getX(), this.getY(), this.width, this.height);
            float x = (float) (this.getX() + 2);
            float y = (float) (this.getY() + 2);

            for (Pos pos : this.slots) {
                guiGraphics.pose().pushMatrix();
                guiGraphics.pose().translate(x + (float) pos.x(), y + (float) pos.y());
//                guiGraphics.pose().scale(0.375F, 0.375F);
//                guiGraphics.pose().scale(1.0F, 1.0F);
                guiGraphics.pose().translate(-8.0F, -8.0F);
                guiGraphics.renderItem(pos.selectIngredient(AetherOverlayRecipeComponent.this.slotSelectTime.currentIndex()), 0, 0);
                guiGraphics.pose().popMatrix();
            }
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

    public class OverlayHourglassRecipeButton extends OverlaySingleRecipeButton {
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
    }

    public class OverlayAltarRecipeButton extends OverlaySingleRecipeButton {
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
    }

    public class OverlayPurifierRecipeButton extends OverlaySingleRecipeButton {
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
    }
}
