package com.aetherteam.aetherii.integration.jei.categories.block;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.recipes.block.AmbrosiumRecipe;
import com.aetherteam.nitrogen.integration.jei.categories.block.AbstractBlockStateRecipeCategory;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.network.chat.Component;

public class AmbrosiumConversionRecipeCategory extends AbstractBlockStateRecipeCategory<AmbrosiumRecipe> {

    public static final IRecipeType<AmbrosiumRecipe> AMBROSIUM_CONVERSION = IRecipeType.create(AetherII.MODID, "ambrosium_enchanting", AmbrosiumRecipe.class);

    public AmbrosiumConversionRecipeCategory(IGuiHelper helper, IPlatformFluidHelper<?> fluidHelper) {
        super(AMBROSIUM_CONVERSION, Component.translatable("gui.aether_ii.jei.ambrosium"), helper.createDrawableItemLike(AetherIIItems.AMBROSIUM_SHARD), fluidHelper);
    }
}
