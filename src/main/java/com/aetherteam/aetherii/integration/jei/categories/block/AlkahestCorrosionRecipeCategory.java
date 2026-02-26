package com.aetherteam.aetherii.integration.jei.categories.block;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.recipes.block.AlkahestCorrosionRecipe;
import com.aetherteam.nitrogen.integration.jei.categories.block.AbstractBlockStateRecipeCategory;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.network.chat.Component;

public class AlkahestCorrosionRecipeCategory extends AbstractBlockStateRecipeCategory<AlkahestCorrosionRecipe> {

    public static final IRecipeType<AlkahestCorrosionRecipe> ALKAHEST_CORROSION = IRecipeType.create(AetherII.MODID, "alkahest_corrosion", AlkahestCorrosionRecipe.class);

    public AlkahestCorrosionRecipeCategory(IGuiHelper helper, IPlatformFluidHelper<?> fluidHelper) {
        super(ALKAHEST_CORROSION, Component.translatable("gui.aether_ii.jei.alkahest"), helper.createDrawableItemLike(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER), fluidHelper);
    }
}
