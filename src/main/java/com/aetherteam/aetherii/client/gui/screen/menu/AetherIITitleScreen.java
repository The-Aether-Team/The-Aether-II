package com.aetherteam.aetherii.client.gui.screen.menu;

import com.aetherteam.aetherii.client.gui.component.menu.AetherIIMenuButton;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.TitleScreenAccessor;
import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.cumulus.client.gui.screen.DynamicMenuButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.Music;
import net.neoforged.neoforge.internal.BrandingControl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class AetherIITitleScreen extends TitleScreen implements TitleScreenBehavior, CustomBranding {
    public static final Music MENU = new Music(AetherIISoundEvents.MUSIC_MENU, 20, 100, true);
    private final boolean alignedLeft;
    private Map<Component, AbstractWidget> widgetsByName = new HashMap<>();

    public int buttonRows = 0;
    public int lastY = 0;

    public AetherIITitleScreen() {
        this(true);
    }

    public AetherIITitleScreen(boolean alignedLeft) {
        super();
        this.alignedLeft = alignedLeft;
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
        /*if (AetherIIConfig.CLIENT.enable_server_button.get()) {
            Component component = ((TitleScreenAccessor) this).callGetMultiplayerDisabledReason();
            boolean flag = component == null;
            Tooltip tooltip = component != null ? Tooltip.create(component) : null;
            Button serverButton = this.addRenderableWidget(Button.builder(Component.translatable("gui.aether.menu.server"), (button) -> {
                ServerData serverData = new ServerData("OATS", "oats.aether-mod.net", ServerData.Type.OTHER);
                ConnectScreen.startConnecting(this, this.minecraft, ServerAddress.parseString(serverData.ip), serverData, false, null);
            }).bounds(this.width / 2 - 100, (this.height / 4 + 48) + 24 * 3, 200, 20).tooltip(tooltip).build());
            serverButton.active = flag;
            Predicate<AbstractWidget> predicate = (abstractWidget) -> (abstractWidget.getMessage().equals(Component.translatable("menu.multiplayer")) || abstractWidget.getMessage().equals(Component.translatable("menu.online")));
            this.children().removeIf(button -> button instanceof AbstractWidget abstractWidget && predicate.test(abstractWidget));
            this.renderables.removeIf(button -> button instanceof AbstractWidget abstractWidget && predicate.test(abstractWidget));
        }

         */
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
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
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
                if (dynamicMenuButton.enabled) {
                    xOffset -= 24;
                }
            }
        }
        TitleScreenBehavior.super.handleImageButtons(this, xOffset);
        if (this.alignedLeft) {
            TitleScreenBehavior.super.handleEssentialButtonsForLeftMenu(this);
        }
    }

    @Override
    public boolean forEachLineBranding(boolean includeMC, boolean reverse, BiConsumer<Integer, String> lineConsumer, GuiGraphics guiGraphics, int i) {
        if (this.alignedLeft) {
            BrandingControl.forEachLine(true, true, (brandingLine, branding) ->
                guiGraphics.drawString(font, branding, this.width - font.width(branding) - 1, this.height - (10 + (brandingLine + 1) * (font.lineHeight + 1)), 16777215 | i)
            );
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean forEachAboveCopyrightLineBranding(BiConsumer<Integer, String> lineConsumer, GuiGraphics guiGraphics, int i) {
        if (this.alignedLeft) {
            BrandingControl.forEachAboveCopyrightLine((brandingLine, branding) ->
                guiGraphics.drawString(font, branding, 1, this.height - (brandingLine + 1) * (font.lineHeight + 1), 16777215 | i)
            );
            return true;
        } else {
            return false;
        }
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

                // Sets button values that determine their positioning on the screen.
                if (this.isAlignedLeft()) {
                    this.buttonRows++;
                } else {
                    if (this.lastY < aetherIIButton.originalY) {
                        this.lastY = aetherIIButton.originalY;
                        this.buttonRows++;
                    }
                }
                if (buttonText.equals(Component.translatable("gui.aether_ii.menu.server"))) {
                    aetherIIButton.serverButton = true;
                    aetherIIButton.buttonCountOffset = 2;
                } else {
                    aetherIIButton.buttonCountOffset = this.buttonRows;
                }
               // if (AetherConfig.CLIENT.enable_server_button.get() && buttonText.equals(Component.translatable("menu.singleplayer"))) {
               //     this.buttonRows++;
               // }
                if (this.isAlignedLeft()) { // Changes button positioning dependent on whether the parent title screen is aligned left or not.
                    aetherIIButton.setX(16);
                    aetherIIButton.setY(50 + aetherIIButton.buttonCountOffset * 25);
                } else {
                    aetherIIButton.setY(this.height / 4 + 31 + 25 * (aetherIIButton.buttonCountOffset - 1));
                }
                return (T) super.addRenderableWidget(aetherIIButton);
            }
        }
        return super.addRenderableWidget(renderable);
    }

    public boolean isAlignedLeft() {
        return this.alignedLeft;
    }

    @Override
    public Map<Component, AbstractWidget> getWidgetsByName() {
        return this.widgetsByName;
    }
}
