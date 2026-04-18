package com.aetherteam.aetherii.integration.jei.categories.item;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDataMaps;
import com.aetherteam.aetherii.recipe.recipes.OutputEntry;
import com.aetherteam.aetherii.recipe.recipes.item.HourglassRestoringRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.display.SlotDisplay;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AmberHourglassRecipeCategory extends AbstractRecipeCategory<HourglassRestoringRecipe> {
    public static final IRecipeType<HourglassRestoringRecipe> HOURGLASS_RESTORING = IRecipeType.create(AetherII.MODID, "hourglass_restoring", HourglassRestoringRecipe.class);
    private final IDrawable background;
    private final IDrawable leftProgress;
    private final IDrawable rightProgress;
    private final IDrawable leftFuel;
    private final IDrawable rightFuel;

    public AmberHourglassRecipeCategory(IGuiHelper helper) {
        super(HOURGLASS_RESTORING, Component.translatable("gui.aether_ii.jei.amber_hourglass"), helper.createDrawableItemLike(AetherIIBlocks.AMBER_HOURGLASS), 110, 110);
        this.background = helper.drawableBuilder(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/jei/amber_hourglass.png"), 0, 0, 108, 108).setTextureSize(108, 108).build();
        this.leftProgress = helper.drawableBuilder(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/sprites/container/amber_hourglass/progress_bar_left.png"), 0, 0, 28, 47).setTextureSize(28, 47).buildAnimated(200, IDrawableAnimated.StartDirection.TOP, false);
        this.rightProgress = helper.drawableBuilder(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/sprites/container/amber_hourglass/progress_bar_right.png"), 0, 0, 28, 47).setTextureSize(28, 47).buildAnimated(200, IDrawableAnimated.StartDirection.TOP, false);
        this.leftFuel = helper.drawableBuilder(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/sprites/container/amber_hourglass/fuel_bar_left.png"), 0, 0, 4, 20).setTextureSize(4, 20).buildAnimated(400, IDrawableAnimated.StartDirection.TOP, true);
        this.rightFuel = helper.drawableBuilder(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/sprites/container/amber_hourglass/fuel_bar_right.png"), 0, 0, 4, 20).setTextureSize(4, 20).buildAnimated(400, IDrawableAnimated.StartDirection.TOP, true);
    }

    @Override
    public void draw(HourglassRestoringRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics, 1, 1);
        this.leftProgress.draw(guiGraphics, 8, 8);
        this.rightProgress.draw(guiGraphics, 74, 8);
        this.leftFuel.draw(guiGraphics, 38, 45);
        this.rightFuel.draw(guiGraphics, 68, 45);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, HourglassRestoringRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 47, 15).add(recipe.ingredient()).setStandardSlotBackground();
        builder.addSlot(RecipeIngredientRole.INPUT, 47, 47).addItemStacks(createFuels()).setStandardSlotBackground();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 15, 79).add(processOutput(recipe.results().output1())).setOutputSlotBackground();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 47, 79).add(processOutput(recipe.results().output2())).setOutputSlotBackground();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 79, 79).add(processOutput(recipe.results().output3())).setOutputSlotBackground();
    }

    private static List<ItemStack> createFuels() {
        List<ItemStack> fuels = new ArrayList<>();
        Registry<Item> registry = Minecraft.getInstance().level.registryAccess().lookupOrThrow(Registries.ITEM);
        registry.getDataMap(AetherIIDataMaps.AMBER_HOURGLASS_FUELS).forEach((key, fuel) -> fuels.add(new ItemStack(registry.getValue(key))));
        return fuels;
    }

    private static SlotDisplay processOutput(OutputEntry.BaseEntry entry) {
        List<SlotDisplay> result = entry.list().stream().distinct().filter(Objects::nonNull).map(SlotDisplay.ItemStackSlotDisplay::new).collect(Collectors.toUnmodifiableList());
        return new SlotDisplay.Composite(result);
    }
}
