package com.aetherteam.aetherii.integration.jei.categories.block;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.recipes.block.SwetGelRecipe;
import com.aetherteam.nitrogen.integration.jei.categories.block.AbstractBlockStateRecipeCategory;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.network.chat.Component;

public class SwetGelConversionRecipeCategory extends AbstractBlockStateRecipeCategory<SwetGelRecipe> {
    public static final IRecipeType<SwetGelRecipe> SWET_GEL_CONVERSION = IRecipeType.create(AetherII.MODID, "swet_gel_conversion", SwetGelRecipe.class);

    public SwetGelConversionRecipeCategory(IGuiHelper helper, IPlatformFluidHelper<?> fluidHelper) {
        super(SWET_GEL_CONVERSION, Component.translatable("gui.aether_ii.jei.swet_gel"), helper.createDrawableItemLike(AetherIIItems.SWET_GEL), fluidHelper);
    }
}
