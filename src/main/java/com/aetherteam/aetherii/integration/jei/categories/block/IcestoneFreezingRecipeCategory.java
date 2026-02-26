package com.aetherteam.aetherii.integration.jei.categories.block;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.recipes.block.IcestoneFreezableRecipe;
import com.aetherteam.aetherii.recipe.recipes.block.IrradiationRecipe;
import com.aetherteam.nitrogen.integration.jei.categories.block.AbstractBlockStateRecipeCategory;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.network.chat.Component;

public class IcestoneFreezingRecipeCategory extends AbstractBlockStateRecipeCategory<IcestoneFreezableRecipe> {

    public static final IRecipeType<IcestoneFreezableRecipe> ICESTONE_FREEZABLE = IRecipeType.create(AetherII.MODID, "icestone_freezable", IcestoneFreezableRecipe.class);

    public IcestoneFreezingRecipeCategory(IGuiHelper helper, IPlatformFluidHelper<?> fluidHelper) {
        super(ICESTONE_FREEZABLE, Component.translatable("gui.aether_ii.jei.icestone"), helper.createDrawableItemLike(AetherIIBlocks.ICESTONE), fluidHelper);
    }
}
