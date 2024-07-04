package com.aetherteam.aetherii.client.gui.screen.guidebook;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.component.guidebook.SectionTab;
import com.aetherteam.aetherii.inventory.menu.GuidebookEquipmentMenu;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuidebookDiscoveryScreen extends Screen implements Guidebook {
    ResourceLocation GUIDEBOOK_DISCOVERY_LEFT_PAGE_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/discovery/guidebook_discovery_left.png");
    ResourceLocation GUIDEBOOK_DISCOVERY_RIGHT_PAGE_GENERAL_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/discovery/guidebook_discovery_right_general.png");
    ResourceLocation GUIDEBOOK_DISCOVERY_RIGHT_PAGE_BESTIARY_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/discovery/guidebook_discovery_right_bestiary.png");
    ResourceLocation GUIDEBOOK_DISCOVERY_RIGHT_PAGE_EFFECTS_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/discovery/guidebook_discovery_right_effects.png");

    private final GuidebookEquipmentMenu equipmentMenu;
    private final Inventory playerInventory;
    protected int leftTitleLabelX;
    protected int leftTitleLabelY;
    protected DiscoverySectionTab currentSectionTab = DiscoverySectionTab.BESTIARY;

    protected GuidebookDiscoveryScreen(GuidebookEquipmentMenu menu, Inventory playerInventory, Component title) {
        super(title);
        this.equipmentMenu = menu;
        this.playerInventory = playerInventory;
        this.leftTitleLabelX = -25;
        this.leftTitleLabelY = 7;
    }

    @Override
    protected void init() {
        super.init();
        this.initTabs(this);

        int x = ((this.width - Guidebook.PAGE_WIDTH) / 2) - 64;
        int y = (this.height / 2) - 72;
        this.addRenderableWidget(new SectionTab(this, DiscoverySectionTab.BESTIARY, x, y, 42, 19, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/icon_bestiary")));
        x += 43;
        this.addRenderableWidget(new SectionTab(this, DiscoverySectionTab.EFFECTS, x, y, 42, 19, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/icon_effects")));
        x += 43;
        this.addRenderableWidget(new SectionTab(this, DiscoverySectionTab.EXPLORATION, x, y, 42, 19, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/icon_exploration")));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderTransparentBackground(guiGraphics);
        this.renderGuidebookSpread(this, guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void renderGuidebookLeftPage(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int x = (this.width - Guidebook.PAGE_WIDTH) / 2;
        int y = (this.height - Guidebook.PAGE_HEIGHT) / 2;
        Guidebook.super.renderGuidebookLeftPage(screen, guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawString(this.font, this.title, x + this.leftTitleLabelX, y + this.leftTitleLabelY, 16777215, true);
    }

    @Override
    protected void renderMenuBackground(GuiGraphics partialTick) { }

    @Override
    protected void renderBlurredBackground(float partialTick) { }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);
        if (Minecraft.getInstance().options.keyInventory.isActiveAndMatches(mouseKey)) {
            this.onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public ResourceLocation getLeftPageTexture() {
        return GUIDEBOOK_DISCOVERY_LEFT_PAGE_LOCATION;
    }

    @Override
    public ResourceLocation getRightPageTexture() {
        if (this.currentSectionTab == DiscoverySectionTab.BESTIARY) {
            return GUIDEBOOK_DISCOVERY_RIGHT_PAGE_BESTIARY_LOCATION;
        } else if (this.currentSectionTab == DiscoverySectionTab.EFFECTS) {
            return GUIDEBOOK_DISCOVERY_RIGHT_PAGE_EFFECTS_LOCATION;
        }
        return GUIDEBOOK_DISCOVERY_RIGHT_PAGE_GENERAL_LOCATION;
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
    public boolean isPauseScreen() {
        return false;
    }

    public void setCurrentSectionTab(DiscoverySectionTab currentSectionTab) {
        this.currentSectionTab = currentSectionTab;
    }

    public DiscoverySectionTab getCurrentSectionTab() {
        return this.currentSectionTab;
    }

    @Override
    public <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(Screen screen, T widget) {
        return this.addRenderableWidget(widget);
    }

    public enum DiscoverySectionTab {
        BESTIARY,
        EFFECTS,
        EXPLORATION;
    }
}
