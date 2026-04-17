package com.aetherteam.aetherii.client.gui.screen.menu;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.util.function.BiConsumer;

public interface CustomBranding {
    boolean forEachLineBranding(boolean includeMC, boolean reverse, BiConsumer<Integer, String> lineConsumer, GuiGraphicsExtractor guiGraphics, int i);

    boolean forEachAboveCopyrightLineBranding(BiConsumer<Integer, String> lineConsumer, GuiGraphicsExtractor guiGraphics, int i);
}
