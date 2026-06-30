package com.aetherteam.aetherii.integration.jei.categories.block;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.recipes.block.AmbrosiumRecipe;
import com.aetherteam.nitrogen.integration.jei.categories.block.AbstractBlockStateRecipeCategory;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class AmbrosiumConversionRecipeCategory extends AbstractBlockStateRecipeCategory<AmbrosiumRecipe> {
    public static final RecipeType<AmbrosiumRecipe> AMBROSIUM_CONVERSION = RecipeType.create(AetherII.MODID, "ambrosium_enchanting", AmbrosiumRecipe.class);
    private final Component title = Component.translatable("gui.aether_ii.jei.ambrosium");

    public AmbrosiumConversionRecipeCategory(IGuiHelper helper, IPlatformFluidHelper<?> fluidHelper) {
        super("ambrosium_enchanting", new ResourceLocation(AetherII.MODID, "ambrosium_enchanting"), helper.createBlankDrawable(96, 28), helper.createDrawableItemLike(AetherIIItems.AMBROSIUM_SHARD.get()), AMBROSIUM_CONVERSION, fluidHelper);
    }

    @Override
    public Component getTitle() {
        return this.title;
    }
}
