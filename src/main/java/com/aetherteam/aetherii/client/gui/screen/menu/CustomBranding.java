package com.aetherteam.aetherii.client.gui.screen.menu;

import net.minecraft.client.gui.GuiGraphics;

import java.util.function.BiConsumer;

public interface CustomBranding {
    boolean forEachLineBranding(boolean includeMC, boolean reverse, BiConsumer<Integer, String> lineConsumer, GuiGraphics guiGraphics, int alpha);

    boolean forEachAboveCopyrightLineBranding(BiConsumer<Integer, String> lineConsumer, GuiGraphics guiGraphics, int alpha);
}


