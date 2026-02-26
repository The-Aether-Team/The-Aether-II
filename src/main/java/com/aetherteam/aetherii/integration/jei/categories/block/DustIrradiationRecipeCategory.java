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

public class DustIrradiationRecipeCategory extends AbstractBlockStateRecipeCategory<IrradiationRecipe> {

    public static final IRecipeType<IrradiationRecipe> DUST_IRRADIATION = IRecipeType.create(AetherII.MODID, "dust_irradiation", IrradiationRecipe.class);

    public DustIrradiationRecipeCategory(IGuiHelper helper, IPlatformFluidHelper<?> fluidHelper) {
        super(DUST_IRRADIATION, Component.translatable("gui.aether_ii.jei.irradiated_dust"), helper.createDrawableItemLike(AetherIIItems.IRRADIATED_DUST), fluidHelper);
    }
}
