package com.aetherteam.aetherii.client.event.listeners;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;

public class LevelClientListener {
    public static void onKeyPress(InputEvent.Key event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (event.getKey() == 297) { //F8
            minecraft.grabPanoramixScreenshot(minecraft.gameDirectory, minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight());
        }
    }
}
