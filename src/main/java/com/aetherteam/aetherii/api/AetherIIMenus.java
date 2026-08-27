package com.aetherteam.aetherii.api;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.screen.menu.AetherIITitleScreen;
import com.aetherteam.cumulus.api.CumulusEntrypoint;
import com.aetherteam.cumulus.api.Menu;
import com.aetherteam.cumulus.api.MenuInitializer;
import com.aetherteam.cumulus.api.MenuRegisterCallback;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

@CumulusEntrypoint
public class AetherIIMenus implements MenuInitializer {
    // Icons
    private static final Identifier AETHER_II_ICON = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/menu_api/menu_icon_aether_ii.png");

    // Names
    private static final Component AETHER_II_NAME = Component.translatable("aether_ii.menu_title.the_aether_ii");

    // Panorama
    public static final Identifier AETHER_II_PANORAMA = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/title/panorama/panorama"); // Registered in AetherIIClient

    // Menus
    public static final Menu AETHER_II = new Menu(AETHER_II_ICON, AETHER_II_NAME, new AetherIITitleScreen(), new Menu.Properties().music(AetherIITitleScreen.MENU).panorama(() -> new CubeMap(AETHER_II_PANORAMA)));

    @Override
    public void registerMenus(MenuRegisterCallback menuRegisterCallback) {
        menuRegisterCallback.registerMenu(Identifier.fromNamespaceAndPath(AetherII.MODID, "the_aether_ii"), AETHER_II);
    }
}