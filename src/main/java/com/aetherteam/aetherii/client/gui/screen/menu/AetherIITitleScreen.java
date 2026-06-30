package com.aetherteam.aetherii.client.gui.screen.menu;

import com.aetherteam.aetherii.client.gui.component.menu.AetherIIMenuButton;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.internal.BrandingControl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class AetherIITitleScreen extends TitleScreen implements TitleScreenBehavior, CustomBranding {
    public static final Music MENU = new Music(menuSound(), 0, 0, true);
    private Map<Component, AbstractWidget> widgetsByName = new HashMap<>();
    public int buttonRows;

    private static Holder<SoundEvent> menuSound() {
        return AetherIISoundEvents.MUSIC_MENU.getHolder().orElseGet(() -> Holder.direct(AetherIISoundEvents.MUSIC_MENU.get()));
    }

    public AetherIITitleScreen() {
        super(true, new AetherIILogoRenderer(false));
    }

    @Override
    protected void init() {
        this.buttonRows = 0;
        super.init();
        this.setupButtons();
        this.widgetsByName = this.children().stream()
                .filter(AbstractWidget.class::isInstance)
                .map(AbstractWidget.class::cast)
                .collect(Collectors.toMap(AbstractWidget::getMessage, widget -> widget, (left, right) -> left));
    }

    private void setupButtons() {
        for (Renderable renderable : this.renderables) {
            if (renderable instanceof AbstractWidget abstractWidget && TitleScreenBehavior.isImageButton(abstractWidget.getMessage())) {
                abstractWidget.visible = false;
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int xOffset = 0;
        for (GuiEventListener child : this.children()) {
            if (child instanceof AetherIIMenuButton aetherButton) {
                if (aetherButton.isMouseOver(mouseX, mouseY)) {
                    aetherButton.hoverOffset = Math.min(aetherButton.hoverOffset + 2, 15);
                } else {
                    aetherButton.hoverOffset = Math.max(aetherButton.hoverOffset - 2, 0);
                }
            }
        }
        TitleScreenBehavior.super.handleImageButtons(this, xOffset);
        TitleScreenBehavior.super.handleEssentialButtonsForLeftMenu(this);

        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawString(this.font, "The Aether II 26.1.2-ALPHA.4", 2, this.height - 20, 0xFFFF7575);
    }

    @Override
    public void tick() {
    }

    @Override
    public boolean forEachLineBranding(boolean includeMC, boolean reverse, BiConsumer<Integer, String> lineConsumer, GuiGraphics guiGraphics, int alpha) {
        BrandingControl.forEachLine(true, true, (brandingLine, branding) ->
                guiGraphics.drawString(this.font, branding, this.width - this.font.width(branding) - 1, this.height - (10 + (brandingLine + 1) * (this.font.lineHeight + 1)), 0xFFFFFF | alpha));
        return true;
    }

    @Override
    public boolean forEachAboveCopyrightLineBranding(BiConsumer<Integer, String> lineConsumer, GuiGraphics guiGraphics, int alpha) {
        BrandingControl.forEachAboveCopyrightLine((brandingLine, branding) ->
                guiGraphics.drawString(this.font, branding, 1, this.height - (brandingLine + 1) * (this.font.lineHeight + 1), 0xFFFFFF | alpha));
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T renderable) {
        if (renderable instanceof Button button) {
            if (TitleScreenBehavior.isMainButton(button.getMessage())) {
                AetherIIMenuButton aetherIIButton = new AetherIIMenuButton(this, button);
                this.buttonRows++;
                aetherIIButton.buttonCountOffset = this.buttonRows;
                if (aetherIIButton.getMessage().equals(Component.literal("Makeship"))) {
                    aetherIIButton.makeshipButton = true;
                    aetherIIButton.setX((int) (this.width / 2.0F + 110.0F));
                    aetherIIButton.setY(32);
                    aetherIIButton.setWidth(900 / 7);
                } else {
                    aetherIIButton.setX(16);
                    aetherIIButton.setY(50 + aetherIIButton.buttonCountOffset * 25);
                    aetherIIButton.setWidth(200);
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


