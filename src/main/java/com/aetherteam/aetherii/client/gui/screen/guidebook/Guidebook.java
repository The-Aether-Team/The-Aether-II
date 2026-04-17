package com.aetherteam.aetherii.client.gui.screen.guidebook;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.component.guidebook.GuidebookTab;
import com.aetherteam.aetherii.inventory.menu.GuidebookEquipmentMenu;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

import java.util.function.BiFunction;

public interface Guidebook {
    WidgetSprites EQUIPMENT_TAB = new WidgetSprites(Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/equipment_tab"), Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/equipment_tab_selected"));
    WidgetSprites STATUS_TAB = new WidgetSprites(Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/status_tab"), Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/status_tab_selected"));
    WidgetSprites DISCOVERY_TAB = new WidgetSprites(Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/discovery_tab"), Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/discovery_tab_selected"));
    WidgetSprites JOURNAL_TAB = new WidgetSprites(Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/journal_tab"), Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/journal_tab_selected"));
    WidgetSprites REWARDS_TAB = new WidgetSprites(Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/rewards_tab"), Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/rewards_tab_selected"));
    WidgetSprites SCROLLER = new WidgetSprites(Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/scroller"), Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/scroller_selected"));
    WidgetSprites MAGNIFYING_GLASS = new WidgetSprites(Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/magnifying_glass"), Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/magnifying_glass"));
    WidgetSprites RETURN = new WidgetSprites(Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/return"), Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/return"));
    Identifier GUIDEBOOK_LEFT_BACKING_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/guidebook_backing_left.png");
    Identifier GUIDEBOOK_RIGHT_BACKING_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/guidebook_backing_right.png");
    Identifier SLOT_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/slot");
    Identifier EXCLAMATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/exclamation");
    Identifier HEARTS_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/stats/hearts");
    Identifier ARMOR_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/stats/armor");
    Identifier DESCRIPTION_BORDER_LEFT_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/border_left");
    Identifier DESCRIPTION_BORDER_RIGHT_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/border_right");
    int PAGE_WIDTH = 188;
    int PAGE_HEIGHT = 198;

    default void initTabs(Screen screen) {
        Tab[] tabs = Tab.values();
        int tabCount = tabs.length;
        int singleTabWidth = 26;
        int singleTabHeight = 35;
        int totalWidth = (tabCount - 1) + (tabCount * singleTabWidth);
        int x = screen.width / 2 - (totalWidth / 2);
        int y = 0;
        for (Tab tab : tabs) {
            Screen screenToOpen = tab.getScreen().apply(this.getEquipmentMenu(), this.getPlayerInventory());
            GuidebookTab tabButton = new GuidebookTab(screen, screenToOpen, x, y, singleTabWidth, singleTabHeight, tab.getSprite());
            tabButton.setTooltip(Tooltip.create(screenToOpen.getTitle()));
            this.addRenderableWidget(screen, tabButton);
            x += singleTabWidth + 1;
        }
    }

    default void renderGuidebookSpread(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int leftPagePos = ((screen.width + 2) / 2) - PAGE_WIDTH;
        int rightPagePos = (screen.width / 2);
        int topPos = (screen.height - PAGE_HEIGHT) / 2;
        this.renderGuidebookBacking(screen, guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate((float) leftPagePos, (float) topPos);
        this.renderGuidebookLeftPage(screen, guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.pose().popMatrix();

        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate((float) rightPagePos, (float) topPos);
        this.renderGuidebookRightPage(screen, guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.pose().popMatrix();

        this.renderGuidebookFowardPage(screen, guiGraphics, mouseX, mouseY, partialTick);
    }

    default void renderGuidebookFowardPage(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

    }

    default void renderGuidebookBacking(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int leftPagePos = ((screen.width + 2) / 2) - PAGE_WIDTH;
        int rightPagePos = (screen.width / 2);
        int topPos = (screen.height - PAGE_HEIGHT) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, GUIDEBOOK_LEFT_BACKING_LOCATION, leftPagePos, topPos, 0, 0, PAGE_WIDTH, PAGE_HEIGHT, 256, 256);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, GUIDEBOOK_RIGHT_BACKING_LOCATION, rightPagePos, topPos, 0, 0, PAGE_WIDTH, PAGE_HEIGHT, 256, 256);
    }

    default void renderGuidebookLeftPage(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.getLeftPageTexture(), 0, 0, 0, 0, PAGE_WIDTH, PAGE_HEIGHT, 256, 256);
    }

    default void renderGuidebookRightPage(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.getRightPageTexture(), 0, 0, 0, 0, PAGE_WIDTH, PAGE_HEIGHT, 256, 256);
    }

    default void switchTab() {

    }

    Identifier getLeftPageTexture();

    Identifier getRightPageTexture();

    GuidebookEquipmentMenu getEquipmentMenu();

    Inventory getPlayerInventory();

    float getMouseX();

    float getMouseY();

    <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(Screen screen, T widget);

    enum Tab {
        EQUIPMENT(EQUIPMENT_TAB, (menu, inventory) -> new GuidebookEquipmentScreen(menu, inventory, Component.translatable("gui.aether_ii.guidebook.equipment.title"))),
//        STATUS(STATUS_TAB, (menu, inventory) -> new GuidebookStatusScreen(menu, inventory, Component.translatable("gui.aether_ii.guidebook.status.title"))), //todo
        DISCOVERY(DISCOVERY_TAB, (menu, inventory) -> new GuidebookDiscoveryScreen(menu, inventory, Component.translatable("gui.aether_ii.guidebook.discovery.title")));
//        JOURNAL(JOURNAL_TAB, (menu, inventory) -> new GuidebookJournalScreen(menu, inventory, Component.translatable("gui.aether_ii.guidebook.journal.title"))); //todo
//        REWARDS(REWARDS_TAB, (menu, inventory) -> new GuidebookRewardsScreen(menu, inventory, Component.translatable("gui.aether_ii.guidebook.rewards.title"))); //todo

        private final WidgetSprites sprite;
        private final BiFunction<GuidebookEquipmentMenu, Inventory, Screen> screen;

        Tab(WidgetSprites sprite, BiFunction<GuidebookEquipmentMenu, Inventory, Screen> screen) {
            this.sprite = sprite;
            this.screen = screen;
        }

        public WidgetSprites getSprite() {
            return this.sprite;
        }

        public BiFunction<GuidebookEquipmentMenu, Inventory, Screen> getScreen() {
            return this.screen;
        }
    }
}
