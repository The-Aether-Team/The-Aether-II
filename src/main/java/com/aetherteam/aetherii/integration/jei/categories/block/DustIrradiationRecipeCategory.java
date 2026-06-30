package com.aetherteam.aetherii.integration.jei.categories.block;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.recipes.block.IrradiationRecipe;
import com.aetherteam.nitrogen.integration.jei.categories.block.AbstractBlockStateRecipeCategory;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class DustIrradiationRecipeCategory extends AbstractBlockStateRecipeCategory<IrradiationRecipe> {
    public static final RecipeType<IrradiationRecipe> DUST_IRRADIATION = RecipeType.create(AetherII.MODID, "dust_irradiation", IrradiationRecipe.class);
    private final Component title = Component.translatable("gui.aether_ii.jei.irradiated_dust");

    public DustIrradiationRecipeCategory(IGuiHelper helper, IPlatformFluidHelper<?> fluidHelper) {
        super("dust_irradiation", new ResourceLocation(AetherII.MODID, "dust_irradiation"), helper.createBlankDrawable(96, 28), helper.createDrawableItemLike(AetherIIItems.IRRADIATED_DUST.get()), DUST_IRRADIATION, fluidHelper);
    }

    @Override
    public Component getTitle() {
        return this.title;
    }
}
