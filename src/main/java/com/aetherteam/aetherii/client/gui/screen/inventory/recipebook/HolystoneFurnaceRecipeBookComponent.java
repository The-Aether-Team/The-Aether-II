package com.aetherteam.aetherii.client.gui.screen.inventory.recipebook;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.FurnaceRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.AbstractFurnaceMenu;

import java.util.List;

public class HolystoneFurnaceRecipeBookComponent extends FurnaceRecipeBookComponent {
    private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(
            Identifier.fromNamespaceAndPath(AetherII.MODID, "recipe_book/holystone_furnace_filter_enabled"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "recipe_book/holystone_furnace_filter_disabled"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "recipe_book/holystone_furnace_filter_enabled_highlighted"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "recipe_book/holystone_furnace_filter_disabled_highlighted"));
    private static final Component FILTER_NAME = Component.translatable("gui.recipebook.toggleRecipes.smeltable");

    public HolystoneFurnaceRecipeBookComponent(AbstractFurnaceMenu menu, List<TabInfo> tabInfos) {
        super(menu, FILTER_NAME, tabInfos);
    }

    @Override
    protected WidgetSprites getFilterButtonTextures() {
        return FILTER_SPRITES;
    }
}
