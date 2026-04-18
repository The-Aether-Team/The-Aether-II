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
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class GuidebookDiscoveryScreen extends Screen implements Guidebook {
    private static final Identifier GUIDEBOOK_DISCOVERY_LEFT_PAGE_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/discovery/guidebook_discovery_left.png");

    private final GuidebookEquipmentMenu equipmentMenu;
    private final Inventory playerInventory;
    private final BestiarySection bestiarySection;
    private final EffectsSection effectsSection;
    private final ExplorationSection explorationSection;
    protected int titleLabelX;
    protected int titleLabelY;
    private float xMouse;
    private float yMouse;
    protected DiscoverySection<?, ?> currentSection;

    protected GuidebookDiscoveryScreen(GuidebookEquipmentMenu menu, Inventory playerInventory, Component title) {
        super(title);
        this.equipmentMenu = menu;
        this.playerInventory = playerInventory;
        this.bestiarySection = new BestiarySection(playerInventory.player.registryAccess(), this, Component.translatable("gui.aether_ii.guidebook.discovery.bestiary.title"));
        this.effectsSection = new EffectsSection(playerInventory.player.registryAccess(), this, Component.translatable("gui.aether_ii.guidebook.discovery.effects.title"));
        this.explorationSection = new ExplorationSection(playerInventory.player.registryAccess(), this, Component.translatable("gui.aether_ii.guidebook.discovery.exploration.title"));

        this.titleLabelX = 100;
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

        int x = ((this.width + 2) / 2) - Guidebook.PAGE_WIDTH + 36;
        int y = (this.height / 2) - 72;
        this.addRenderableWidget(new SectionTab(this, this.bestiarySection, x, y, 42, 19, Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/icon_bestiary")));
        x += 43;
        this.addRenderableWidget(new SectionTab(this, this.effectsSection, x, y, 42, 19, Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/icon_effects")));
//        x += 43;
//        this.addRenderableWidget(new SectionTab(this, this.explorationSection, x, y, 42, 19, Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/icon_exploration"))); //todo

        this.currentSection.initSection();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);
        this.renderGuidebookSpread(this, graphics, mouseX, mouseY, partialTick);
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);

        this.xMouse = (float) mouseX;
        this.yMouse = (float) mouseY;
    }

    @Override
    public void renderGuidebookBacking(Screen screen, GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        Guidebook.super.renderGuidebookBacking(screen, guiGraphics, mouseX, mouseY, partialTick);
        this.currentSection.renderBg(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void renderGuidebookFowardPage(Screen screen, GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        Guidebook.super.renderGuidebookFowardPage(screen, guiGraphics, mouseX, mouseY, partialTick);
        this.currentSection.renderFoward(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void renderGuidebookLeftPage(Screen screen, GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        Guidebook.super.renderGuidebookLeftPage(screen, guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.centeredText(this.font, this.title, this.titleLabelX, this.titleLabelY, 0xffffffff);
        this.currentSection.renderEntries(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void renderGuidebookRightPage(Screen screen, GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        Guidebook.super.renderGuidebookRightPage(screen, guiGraphics, mouseX, mouseY, partialTick);
        this.currentSection.renderInformation(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    protected void extractMenuBackground(GuiGraphicsExtractor graphics) {
    }

    @Override
    protected void extractBlurredBackground(GuiGraphicsExtractor graphics) {
    }

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
    public boolean mouseDragged(MouseButtonEvent event, double dragX, double dragY) {
        return this.currentSection.mouseDragged(event, dragX, dragY, super.mouseDragged(event, dragX, dragY));
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        return this.currentSection.mouseScrolled(mouseX, mouseY, scrollX, scrollY, true);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean held) {
        return this.currentSection.mouseClicked(event, held, super.mouseClicked(event, held));
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        return this.currentSection.mouseReleased(event, super.mouseReleased(event));
    }

    @Override
    public Identifier getLeftPageTexture() {
        return GUIDEBOOK_DISCOVERY_LEFT_PAGE_LOCATION;
    }

    @Override
    public Identifier getRightPageTexture() {
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

    public void setCurrentSectionTab(DiscoverySection<?, ?> currentSection) {
        this.currentSection = currentSection;
    }

    public DiscoverySection<?, ?> getCurrentSection() {
        return this.currentSection;
    }

    @Override
    public <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(Screen screen, T widget) {
        return this.addRenderableWidget(widget);
    }
}
