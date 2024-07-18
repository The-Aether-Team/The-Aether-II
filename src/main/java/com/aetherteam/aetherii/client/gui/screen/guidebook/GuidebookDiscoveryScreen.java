package com.aetherteam.aetherii.client.gui.screen.guidebook;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.component.guidebook.SectionTab;
import com.aetherteam.aetherii.client.gui.screen.guidebook.discovery.BestiarySection;
import com.aetherteam.aetherii.client.gui.screen.guidebook.discovery.DiscoverySection;
import com.aetherteam.aetherii.client.gui.screen.guidebook.discovery.EffectsSection;
import com.aetherteam.aetherii.client.gui.screen.guidebook.discovery.ExplorationSection;
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

// todo
//   need to figure out scrolling again...
//   i should probably make the sections into renderable panels by using the proper interfaces and parent classes
public class GuidebookDiscoveryScreen extends Screen implements Guidebook {
    private static final ResourceLocation GUIDEBOOK_DISCOVERY_LEFT_PAGE_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/discovery/guidebook_discovery_left.png");

    private final GuidebookEquipmentMenu equipmentMenu;
    private final Inventory playerInventory;
    private final BestiarySection bestiarySection;
    private final EffectsSection effectsSection;
    private final ExplorationSection explorationSection;
    protected int titleLabelX;
    protected int titleLabelY;
    protected DiscoverySection<?> currentSection;

    protected GuidebookDiscoveryScreen(GuidebookEquipmentMenu menu, Inventory playerInventory, Component title) {
        super(title);
        this.equipmentMenu = menu;
        this.playerInventory = playerInventory;
        this.bestiarySection = new BestiarySection(playerInventory.player.registryAccess(), this, Component.translatable("gui.aether_ii.guidebook.discovery.bestiary.title"));
        this.effectsSection = new EffectsSection(playerInventory.player.registryAccess(), this, Component.translatable("gui.aether_ii.guidebook.discovery.effects.title"));
        this.explorationSection = new ExplorationSection(playerInventory.player.registryAccess(), this, Component.translatable("gui.aether_ii.guidebook.discovery.exploration.title"));

        this.titleLabelX = 88;
        this.titleLabelY = 13;
        this.currentSection = this.bestiarySection;
    }

    public void initDiscovery() {
        this.clearWidgets();
        this.clearFocus();
        this.init();
    }

    @Override
    protected void init() {
        super.init();
        this.initTabs(this);

        int x = ((this.width) / 2) - Guidebook.PAGE_WIDTH + 24;
        int y = (this.height / 2) - 72;
        this.addRenderableWidget(new SectionTab(this, this.bestiarySection, x, y, 42, 19, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/icon_bestiary")));
        x += 43;
        this.addRenderableWidget(new SectionTab(this, this.effectsSection, x, y, 42, 19, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/icon_effects")));
        x += 43;
        this.addRenderableWidget(new SectionTab(this, this.explorationSection, x, y, 42, 19, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/icon_exploration")));

        this.currentSection.initSection();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderTransparentBackground(guiGraphics);
        this.renderGuidebookSpread(this, guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.currentSection.renderBg(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void renderGuidebookLeftPage(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Guidebook.super.renderGuidebookLeftPage(screen, guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(this.font, this.title, this.titleLabelX, this.titleLabelY, 16777215);
        this.currentSection.renderEntries(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void renderGuidebookRightPage(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Guidebook.super.renderGuidebookRightPage(screen, guiGraphics, mouseX, mouseY, partialTick);
        this.currentSection.renderInformation(guiGraphics, mouseX, mouseY, partialTick);
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
        return this.getCurrentSection().getRightPageTexture();
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

    public void setCurrentSectionTab(DiscoverySection<?> currentSection) {
        this.currentSection = currentSection;
    }

    public DiscoverySection<?> getCurrentSection() {
        return this.currentSection;
    }

    @Override
    public <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(Screen screen, T widget) {
        return this.addRenderableWidget(widget);
    }
}
