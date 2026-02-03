package com.aetherteam.aetherii.client.gui.screen.menu.logo;

import com.aetherteam.aetherii.client.gui.screen.menu.CustomPosition;
import net.minecraft.client.gui.components.LogoRenderer;

public class LeftLogoRenderer extends LogoRenderer implements CustomPosition {
    public int screenWidth;

    public LeftLogoRenderer(boolean keepLogoThroughFade) {
        super(keepLogoThroughFade);
    }

    @Override
    public float getXOffset(float x) {
        return x - ((float) this.screenWidth / 2) + 144;
    }

    @Override
    public float getYOffset(float y) {
        return y - 11;
    }
}
