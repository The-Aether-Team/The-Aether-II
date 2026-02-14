package com.aetherteam.aetherii.client.gui.screen.guidebook;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.GuidebookEntry;
import com.aetherteam.aetherii.client.gui.component.guidebook.DescriptionButton;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscoveryDescriptionScreen extends Screen {
    private static final WidgetSprites ARROW_LEFT_TEXTURES = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/arrow_left"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/arrow_left"));
    private static final WidgetSprites ARROW_RIGHT_TEXTURES = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/arrow_right"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/arrow_right"));

    protected final Screen lastScreen;
    protected final GuidebookEntry entry;

    private ImageButton previousButton, nextButton;
    private final Map<Integer, List<FormattedCharSequence>> pages = new HashMap<>();
    private int currentPageNumber = 0;

    public DiscoveryDescriptionScreen(Screen lastScreen, GuidebookEntry entry) {
        super(Component.translatable(entry.getName()));
        this.lastScreen = lastScreen;
        this.entry = entry;
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new DescriptionButton(this, 36, 12, Guidebook.MAGNIFYING_GLASS));
        this.previousButton = this.addRenderableWidget(new ImageButton(32, this.height - 28, 16, 16, ARROW_LEFT_TEXTURES, (button) -> {
            if (this.currentPageNumber > 0) {
                this.currentPageNumber--;
            }
        }));
        this.nextButton = this.addRenderableWidget(new ImageButton(this.width - 32 - 16, this.height - 28, 16, 16, ARROW_RIGHT_TEXTURES, (button) -> {
            if (this.currentPageNumber < this.pages.size() - 1) {
                this.currentPageNumber++;
            }
        }));
        this.pages.clear();
        this.createPages(Component.translatable(this.entry.getDescriptionKey())); // Sets up pages.
        if (this.currentPageNumber > this.pages.size() - 1) {
            this.currentPageNumber = this.pages.size() - 1;
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderTransparentBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(this.font, Component.translatable(this.entry.getName()).withStyle(ChatFormatting.UNDERLINE), this.width / 2, 10, 0xffffffff);

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, Guidebook.DESCRIPTION_BORDER_LEFT_SPRITE, 35, 30, 10, 120);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, Guidebook.DESCRIPTION_BORDER_RIGHT_SPRITE, this.width - 35 - 10, 30, 10, 120);

        this.createText(guiGraphics, this.pages.get(this.currentPageNumber), (this.width - 325) / 2, 30);

        guiGraphics.drawCenteredString(this.font, Component.literal(String.valueOf(this.currentPageNumber + 1)).append("/").append(String.valueOf(this.pages.size())), this.width / 2, this.height - 20, 0xffffffff);

        this.previousButton.active = this.currentPageNumber > 0;
        this.nextButton.active = this.currentPageNumber < this.pages.size() - 1;
    }

    private void createPages(Component entry) {
        List<FormattedCharSequence> formattedText = new ArrayList<>(this.font.split(entry, 325));
        List<FormattedCharSequence> firstPage;
        int lines = (this.height - 70) / 10;
        if (formattedText.size() < lines) {
            firstPage = formattedText.subList(0, formattedText.size());
            this.pages.put(0, firstPage);
        } else {
            firstPage = formattedText.subList(0, lines);
            this.pages.put(0, firstPage);

            List<FormattedCharSequence> remainingPages = formattedText.subList(lines, formattedText.size());
            final List<List<FormattedCharSequence>> list = Lists.partition(remainingPages, lines);

            for (int i = 1; i < list.size() + 1; i++) {
                this.pages.put(i, list.get(i - 1));
            }
        }
    }

    private void createText(GuiGraphics guiGraphics, List<FormattedCharSequence> reorderingProcessors, int x, int y) {
        int length = 0;
        for (FormattedCharSequence line : reorderingProcessors) {
            guiGraphics.drawString(this.font, line,  x, y + (length * 10), 0xffffffff, false);
            length++;
        }
    }

    @Override
    protected void renderBlurredBackground(GuiGraphics guiGraphics) { }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public Screen getLastScreen() {
        return this.lastScreen;
    }
}
