package com.aetherteam.aetherii.integration.jei.categories.block;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.recipe.recipes.block.IcestoneFreezableRecipe;
import com.aetherteam.nitrogen.integration.jei.categories.block.AbstractBlockStateRecipeCategory;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class IcestoneFreezingRecipeCategory extends AbstractBlockStateRecipeCategory<IcestoneFreezableRecipe> {
    public static final RecipeType<IcestoneFreezableRecipe> ICESTONE_FREEZABLE = RecipeType.create(AetherII.MODID, "icestone_freezable", IcestoneFreezableRecipe.class);
    private final Component title = Component.translatable("gui.aether_ii.jei.icestone");

    public IcestoneFreezingRecipeCategory(IGuiHelper helper, IPlatformFluidHelper<?> fluidHelper) {
        super("icestone_freezable", new ResourceLocation(AetherII.MODID, "icestone_freezable"), helper.createBlankDrawable(96, 28), helper.createDrawableItemLike(AetherIIBlocks.ICESTONE.get()), ICESTONE_FREEZABLE, fluidHelper);
    }

    @Override
    public Component getTitle() {
        return this.title;
    }
}
