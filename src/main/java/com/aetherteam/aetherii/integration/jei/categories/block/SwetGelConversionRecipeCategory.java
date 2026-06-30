package com.aetherteam.aetherii.integration.jei.categories.block;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.recipes.block.SwetGelRecipe;
import com.aetherteam.nitrogen.integration.jei.categories.block.AbstractBlockStateRecipeCategory;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SwetGelConversionRecipeCategory extends AbstractBlockStateRecipeCategory<SwetGelRecipe> {
    public static final RecipeType<SwetGelRecipe> SWET_GEL_CONVERSION = RecipeType.create(AetherII.MODID, "swet_gel_conversion", SwetGelRecipe.class);
    private final Component title = Component.translatable("gui.aether_ii.jei.swet_gel");

    public SwetGelConversionRecipeCategory(IGuiHelper helper, IPlatformFluidHelper<?> fluidHelper) {
        super("swet_gel_conversion", new ResourceLocation(AetherII.MODID, "swet_gel_conversion"), helper.createBlankDrawable(96, 28), helper.createDrawableItemLike(AetherIIItems.SWET_GEL.get()), SWET_GEL_CONVERSION, fluidHelper);
    }

    @Override
    public Component getTitle() {
        return this.title;
    }
}
