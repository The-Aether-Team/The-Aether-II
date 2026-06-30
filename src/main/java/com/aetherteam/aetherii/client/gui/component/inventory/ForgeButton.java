package com.aetherteam.aetherii.client.gui.component.inventory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.screen.inventory.ArkeniumForgeScreen;
import com.aetherteam.aetherii.client.gui.component.AetherIIImageButton;
import com.aetherteam.aetherii.client.gui.component.AetherIIWidgetSprites;
import net.minecraft.resources.ResourceLocation;

public class ForgeButton extends AetherIIImageButton {
    private static final AetherIIWidgetSprites FORGE_BUTTON_SPRITE = new AetherIIWidgetSprites(
            new ResourceLocation(AetherII.MODID, "container/arkenium_forge/forge_button"),
            new ResourceLocation(AetherII.MODID, "container/arkenium_forge/forge_button_disabled"),
            new ResourceLocation(AetherII.MODID, "container/arkenium_forge/forge_button_selected"));
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


