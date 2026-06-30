package com.aetherteam.aetherii.client.gui.screen.menu;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.Map;

public interface TitleScreenBehavior {
    default void handleImageButtons(TitleScreen titleScreen, int xOffset) {
        for (GuiEventListener renderable : titleScreen.children()) {
            if (renderable instanceof Button button) {
                Component buttonText = button.getMessage();
                if (buttonText.equals(Component.translatable("narrator.button.accessibility"))) {
                    button.setX(titleScreen.width - 48 + xOffset);
                    button.setY(4);
                } else if (buttonText.equals(Component.translatable("narrator.button.language"))) {
                    button.setX(titleScreen.width - 24 + xOffset);
                    button.setY(4);
                }
                if (TitleScreenBehavior.isImageButton(buttonText)) {
                    button.visible = true;
                }
            }
        }
    }

    default void handleEssentialButtonsForLeftMenu(TitleScreen titleScreen) {
        for (GuiEventListener child : titleScreen.children()) {
            if (child instanceof AbstractWidget widget) {
                Component message = widget.getMessage();
                if (message.getString().contains("<essential_")) {
                    AbstractWidget languageButton = this.getWidgetsByName().get(Component.translatable("narrator.button.language"));
                    if (languageButton != null) {
                        widget.visible = languageButton.visible;
                    }
                    if (message.equals(Component.literal("<essential_player>"))) {
                        AbstractWidget wardrobeButton = this.getWidgetsByName().get(Component.literal("<essential_wardrobe_2>"));
                        if (wardrobeButton != null) {
                            widget.setX(wardrobeButton.getX() - (widget.getWidth() / 2) + 10);
                        }
                    } else if (message.equals(Component.literal("<essential_wardrobe_2>"))) {
                        AbstractWidget accountButton = this.getWidgetsByName().get(Component.literal("<essential_account>"));
                        if (accountButton != null) {
                            widget.setX(accountButton.getX() - widget.getWidth() - 55);
                        }
                    } else if (message.equals(Component.literal("<essential_reserved_0>"))
                            || message.equals(Component.literal("<essential_invite_host>"))
                            || message.equals(Component.literal("<essential_world_host>"))
                            || message.equals(Component.literal("<essential_social>"))
                            || message.equals(Component.literal("<essential_pictures>"))
                            || message.equals(Component.literal("<essential_settings>"))
                            || message.equals(Component.literal("<essential_account>"))
                            || message.equals(Component.literal("<essential_reserved_10>"))
                            || message.equals(Component.literal("<essential_beta>"))
                            || message.equals(Component.literal("<essential_update>"))
                            || message.equals(Component.literal("<essential_message>"))
                            || message.equals(Component.literal("<essential_wardrobe>"))) {
                        widget.setX(titleScreen.width - widget.getWidth() - 4);
                    }
                }
            }
        }
    }

    static boolean isImageButton(Component buttonText) {
        return buttonText.equals(Component.translatable("narrator.button.accessibility"))
                || buttonText.equals(Component.translatable("narrator.button.language"));
    }

    static boolean isMainButton(Component buttonText) {
        return buttonText.equals(Component.translatable("menu.singleplayer"))
                || buttonText.equals(Component.translatable("menu.multiplayer"))
                || buttonText.equals(Component.literal("Create Test World"))
                || buttonText.equals(Component.translatable("fml.menu.mods"))
                || buttonText.equals(Component.translatable("menu.options"))
                || buttonText.equals(Component.translatable("menu.quit"))
                || buttonText.equals(Component.literal("Makeship"));
    }

    static boolean isHiddenButton(Component buttonText) {
        return buttonText.equals(Component.translatable("menu.online"));
    }

    default Map<Component, AbstractWidget> getWidgetsByName() {
        return new HashMap<>();
    }
}


