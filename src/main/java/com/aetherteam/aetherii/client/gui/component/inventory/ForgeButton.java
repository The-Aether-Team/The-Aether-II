package com.aetherteam.aetherii.client.gui.component.inventory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.screen.inventory.ArkeniumForgeScreen;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.Identifier;

public class ForgeButton extends ImageButton {
    private static final WidgetSprites FORGE_BUTTON_SPRITE = new WidgetSprites(
            Identifier.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/forge_button"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/forge_button_disabled"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/forge_button_selected"));
    private final ArkeniumForgeScreen parentScreen;

    public ForgeButton(ArkeniumForgeScreen parentScreen, int x, int y, int width, int height, OnPress onPress) {
        super(x, y, width, height, FORGE_BUTTON_SPRITE, onPress);
        this.parentScreen = parentScreen;
    }

    @Override
    public boolean isActive() {
        return this.parentScreen.canForge();
    }

    @Override
    public boolean isFocused() {
        if (!this.isActive()) {
            return false;
        }
        return super.isFocused();
    }
}
