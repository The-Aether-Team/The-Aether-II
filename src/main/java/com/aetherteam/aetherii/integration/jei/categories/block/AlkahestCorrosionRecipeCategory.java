package com.aetherteam.aetherii.integration.jei.categories.block;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.recipes.block.AlkahestCorrosionRecipe;
import com.aetherteam.nitrogen.integration.jei.categories.block.AbstractBlockStateRecipeCategory;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class AlkahestCorrosionRecipeCategory extends AbstractBlockStateRecipeCategory<AlkahestCorrosionRecipe> {
    public static final RecipeType<AlkahestCorrosionRecipe> ALKAHEST_CORROSION = RecipeType.create(AetherII.MODID, "alkahest_corrosion", AlkahestCorrosionRecipe.class);
    private final Component title = Component.translatable("gui.aether_ii.jei.alkahest");

    public AlkahestCorrosionRecipeCategory(IGuiHelper helper, IPlatformFluidHelper<?> fluidHelper) {
        super("alkahest_corrosion", new ResourceLocation(AetherII.MODID, "alkahest_corrosion"), helper.createBlankDrawable(96, 28), helper.createDrawableItemLike(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER.get()), ALKAHEST_CORROSION, fluidHelper);
    }

    @Override
    public Component getTitle() {
        return this.title;
    }
}
