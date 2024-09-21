package com.aetherteam.aetherii.client.gui;

import com.aetherteam.aetherii.client.gui.component.toast.GuidebookToast;
import net.minecraft.client.Minecraft;

public class GuidebookUtil {
    public static void addGuidebookToast(GuidebookToast.Type toastType, GuidebookToast.Icons toastIcon) {
        GuidebookToast toast = new GuidebookToast(toastType, toastIcon);
        Minecraft.getInstance().getToasts().addToast(toast);
    }
}
