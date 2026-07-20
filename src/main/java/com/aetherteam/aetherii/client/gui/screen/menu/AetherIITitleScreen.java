package com.aetherteam.aetherii.client.gui.screen.menu;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.component.menu.AetherIIMenuButton;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.TitleScreenAccessor;
import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.cumulus.client.gui.screen.DynamicMenuButton;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.Music;
import net.minecraft.util.Util;
import net.neoforged.neoforge.internal.BrandingControl;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class AetherIITitleScreen extends TitleScreen implements TitleScreenBehavior, CustomBranding {
    public static final Music MENU = new Music(AetherIISoundEvents.MUSIC_MENU, 0, 0, true);
    private Map<Component, AbstractWidget> widgetsByName = new HashMap<>();

    public int buttonRows = 0;
    public int lastY = 0;

    public AetherIITitleScreen() {
        super();
        TitleScreenAccessor accessor = ((TitleScreenAccessor) this);
        accessor.aetherII$setFading(true);
        accessor.aetherII$setLogoRenderer(new AetherIILogoRenderer(false));
    }

    @Override
    protected void init() {
        TitleScreenAccessor accessor = (TitleScreenAccessor) this;
        this.buttonRows = 0;
        this.lastY = 0;
        super.init();
        if (this.minecraft != null) {
            accessor.aetherII$setSplash(null);
        }
        this.setupButtons();
        this.widgetsByName = this.children().stream().filter(e -> e instanceof AbstractWidget).map(e -> (AbstractWidget) e)
            .collect(Collectors.toMap(AbstractWidget::getMessage, e -> e));
    }

    public void setupButtons() {
        for (Renderable renderable : this.renderables) {
            if (renderable instanceof AbstractWidget abstractWidget) {
                Component buttonText = abstractWidget.getMessage();
                if (TitleScreenBehavior.isImageButton(buttonText)) {
                    abstractWidget.visible = false; // The visibility handling is necessary here to avoid a bug where the buttons will render in the center of the screen before they have a specified offset.
                }
            }
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
        int xOffset = CumulusConfig.CLIENT.enable_menu_api.get() && CumulusConfig.CLIENT.enable_menu_list_button.get() ? -62 : 0;
        for (GuiEventListener child : this.children()) {
            if (child instanceof AetherIIMenuButton aetherButton) { // Smoothly shifts the Aether-styled buttons to the right slightly when hovered over.
                if (aetherButton.isMouseOver(mouseX, mouseY)) {
                    if (aetherButton.hoverOffset < 15) {
                        aetherButton.hoverOffset += 2;
                    }
                } else {
                    if (aetherButton.hoverOffset > 0) {
                        aetherButton.hoverOffset -= 2;
                    }
                }
            }
            if (child instanceof DynamicMenuButton dynamicMenuButton) {  // Increases the x-offset to the left for image buttons if there are menu buttons on the screen.
                if (dynamicMenuButton.active) {
                    xOffset -= 24;
                }
            }
        }
        TitleScreenBehavior.super.handleImageButtons(this, xOffset);
        TitleScreenBehavior.super.handleEssentialButtonsForLeftMenu(this);

        guiGraphics.text(this.font, "The Aether II 26.1.2-ALPHA.4", 2, this.height - 10, 0xFFFF7575);
    }

    @Override
    public boolean forEachLineBranding(boolean includeMC, boolean reverse, BiConsumer<Integer, String> lineConsumer, GuiGraphicsExtractor guiGraphics, int i) {
        BrandingControl.forEachLine(true, true, (brandingLine, branding) ->
                guiGraphics.text(font, branding, this.width - font.width(branding) - 1, this.height - (10 + (brandingLine + 1) * (font.lineHeight + 1)), 16777215 | i)
        );
        return true;
    }

    @Override
    public boolean forEachAboveCopyrightLineBranding(BiConsumer<Integer, String> lineConsumer, GuiGraphicsExtractor guiGraphics, int i) {
        BrandingControl.forEachAboveCopyrightLine((brandingLine, branding) ->
                guiGraphics.text(font, branding, 1, this.height - (brandingLine + 1) * (font.lineHeight + 1), 16777215 | i)
        );
        return true;
    }

    // Fixes realm icons rendering in the aether menu
    @Override
    public void tick() {
    }

    /**
     * Changes main menu buttons into Aether-styled main menu buttons.<br><br>
     * Warning for "unchecked" is suppressed because the buttons should always be able to be cast.
     *
     * @param renderable A renderable widget.
     * @return A new renderable widget.
     */
    @Override
    @SuppressWarnings("unchecked")
    protected <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T renderable) {
        if (renderable instanceof Button button) {
            if (TitleScreenBehavior.isMainButton(button.getMessage())) {
                AetherIIMenuButton aetherIIButton = new AetherIIMenuButton(this, button);
                Component buttonText = aetherIIButton.getMessage();

                this.buttonRows++;

                if (buttonText.equals(Component.literal("Makeship"))) {
                    aetherIIButton.makeshipButton = true;
                }

                // Sets button values that determine their positioning on the screen.
                //this.buttonRows++;
                aetherIIButton.buttonCountOffset = this.buttonRows;
                if (!aetherIIButton.makeshipButton) {
                    aetherIIButton.setX(16);
                    aetherIIButton.setY(50 + aetherIIButton.buttonCountOffset * 25);
                    aetherIIButton.setWidth(200);
                } else {
                    aetherIIButton.setX((int) ((this.width / 2.0F + (220.0F / 2.0F))));
                    aetherIIButton.setY(32);
                    aetherIIButton.setWidth(900 / 7);
                }
                return (T) super.addRenderableWidget(aetherIIButton);
            } else if (TitleScreenBehavior.isHiddenButton(button.getMessage())) {
                button.active = false;
                button.visible = false;
            }
        }
        return super.addRenderableWidget(renderable);
    }

    @Override
    public Map<Component, AbstractWidget> getWidgetsByName() {
        return this.widgetsByName;
    }
}
