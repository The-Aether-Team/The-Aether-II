package com.aetherteam.aetherii.api;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.screen.menu.AetherIITitleScreen;
import com.aetherteam.cumulus.api.Menu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class AetherIIMenus {
    // Icons
    private static final ResourceLocation AETHER_II_ICON = new ResourceLocation(AetherII.MODID, "textures/gui/menu_api/menu_icon_aether_ii.png");

    // Names
    private static final Component AETHER_II_NAME = Component.translatable("aether_ii.menu_title.the_aether_ii");

    // Panorama
    public static final ResourceLocation AETHER_II_PANORAMA = new ResourceLocation(AetherII.MODID, "textures/gui/title/panorama/panorama"); // Registered in AetherIIClient

    // Menus
    public static final Menu AETHER_II = new Menu(AETHER_II_ICON, AETHER_II_NAME, new AetherIITitleScreen(), () -> true, new Menu.Properties().music(AetherIITitleScreen.MENU));
}
