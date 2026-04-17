package com.aetherteam.aetherii.client.gui.screen.guidebook;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.GuidebookEquipmentMenu;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class GuidebookJournalScreen extends Screen implements Guidebook {
    private static final Identifier GUIDEBOOK_JOURNAL_LEFT_PAGE_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/journal/guidebook_journal_left.png");
    private static final Identifier GUIDEBOOK_JOURNAL_RIGHT_PAGE_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/journal/guidebook_journal_right.png");

    private final GuidebookEquipmentMenu equipmentMenu;
    private final Inventory playerInventory;
    protected int titleLabelX;
    protected int titleLabelY;
    private float xMouse;
    private float yMouse;

    protected GuidebookJournalScreen(GuidebookEquipmentMenu menu, Inventory playerInventory, Component title) {
        super(title);
        this.equipmentMenu = menu;
        this.playerInventory = playerInventory;
        this.titleLabelX = 100;
        this.titleLabelY = 13;
    }

    @Override
    protected void init() {
        super.init();
        this.initTabs(this);
    }

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderTransparentBackground(guiGraphics);
        this.renderGuidebookSpread(this, guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        this.xMouse = (float) mouseX;
        this.yMouse = (float) mouseY;
    }

    @Override
    public void renderGuidebookLeftPage(Screen screen, GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        Guidebook.super.renderGuidebookLeftPage(screen, guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0xffffffff);
    }

    @Override
    protected void renderMenuBackground(GuiGraphicsExtractor partialTick) { }

    @Override
    protected void renderBlurredBackground(GuiGraphicsExtractor guiGraphics) { }

    @Override
    public boolean keyPressed(KeyEvent event) {
        InputConstants.Key mouseKey = InputConstants.getKey(event);
        if (Minecraft.getInstance().options.keyInventory.isActiveAndMatches(mouseKey)) {
            this.onClose();
            return true;
        }
        return super.keyPressed(event);
    }

    @Override
    public Identifier getLeftPageTexture() {
        return GUIDEBOOK_JOURNAL_LEFT_PAGE_LOCATION;
    }

    @Override
    public Identifier getRightPageTexture() {
        return GUIDEBOOK_JOURNAL_RIGHT_PAGE_LOCATION;
    }

    @Override
    public GuidebookEquipmentMenu getEquipmentMenu() {
        return this.equipmentMenu;
    }

    @Override
    public Inventory getPlayerInventory() {
        return this.playerInventory;
    }

    @Override
    public float getMouseX() {
        return this.xMouse;
    }

    @Override
    public float getMouseY() {
        return this.yMouse;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(Screen screen, T widget) {
        return this.addRenderableWidget(widget);
    }
}