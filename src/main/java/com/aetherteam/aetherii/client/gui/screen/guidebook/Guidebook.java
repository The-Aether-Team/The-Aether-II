package com.aetherteam.aetherii.client.gui.screen.guidebook;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.component.guidebook.GuidebookTab;
import com.aetherteam.aetherii.inventory.menu.GuidebookEquipmentMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Tooltip;
import com.aetherteam.aetherii.client.gui.component.AetherIIWidgetSprites;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.function.BiFunction;

public interface Guidebook {
    AetherIIWidgetSprites EQUIPMENT_TAB = new AetherIIWidgetSprites(new ResourceLocation(AetherII.MODID, "guidebook/equipment_tab"), new ResourceLocation(AetherII.MODID, "guidebook/equipment_tab_selected"));
    AetherIIWidgetSprites STATUS_TAB = new AetherIIWidgetSprites(new ResourceLocation(AetherII.MODID, "guidebook/status_tab"), new ResourceLocation(AetherII.MODID, "guidebook/status_tab_selected"));
    AetherIIWidgetSprites DISCOVERY_TAB = new AetherIIWidgetSprites(new ResourceLocation(AetherII.MODID, "guidebook/discovery_tab"), new ResourceLocation(AetherII.MODID, "guidebook/discovery_tab_selected"));
    AetherIIWidgetSprites JOURNAL_TAB = new AetherIIWidgetSprites(new ResourceLocation(AetherII.MODID, "guidebook/journal_tab"), new ResourceLocation(AetherII.MODID, "guidebook/journal_tab_selected"));
    AetherIIWidgetSprites REWARDS_TAB = new AetherIIWidgetSprites(new ResourceLocation(AetherII.MODID, "guidebook/rewards_tab"), new ResourceLocation(AetherII.MODID, "guidebook/rewards_tab_selected"));
    AetherIIWidgetSprites SCROLLER = new AetherIIWidgetSprites(new ResourceLocation(AetherII.MODID, "guidebook/scroller"), new ResourceLocation(AetherII.MODID, "guidebook/scroller_selected"));
    AetherIIWidgetSprites MAGNIFYING_GLASS = new AetherIIWidgetSprites(new ResourceLocation(AetherII.MODID, "guidebook/magnifying_glass"), new ResourceLocation(AetherII.MODID, "guidebook/magnifying_glass"));
    AetherIIWidgetSprites RETURN = new AetherIIWidgetSprites(new ResourceLocation(AetherII.MODID, "guidebook/return"), new ResourceLocation(AetherII.MODID, "guidebook/return"));
    ResourceLocation GUIDEBOOK_LEFT_BACKING_LOCATION = new ResourceLocation(AetherII.MODID, "textures/gui/guidebook/guidebook_backing_left.png");
    ResourceLocation GUIDEBOOK_RIGHT_BACKING_LOCATION = new ResourceLocation(AetherII.MODID, "textures/gui/guidebook/guidebook_backing_right.png");
    ResourceLocation SLOT_SPRITE = new ResourceLocation(AetherII.MODID, "guidebook/slot");
    ResourceLocation EXCLAMATION = new ResourceLocation(AetherII.MODID, "guidebook/exclamation");
    ResourceLocation HEARTS_SPRITE = new ResourceLocation(AetherII.MODID, "guidebook/stats/hearts");
    ResourceLocation ARMOR_SPRITE = new ResourceLocation(AetherII.MODID, "guidebook/stats/armor");
    ResourceLocation DESCRIPTION_BORDER_LEFT_SPRITE = new ResourceLocation(AetherII.MODID, "guidebook/border_left");
    ResourceLocation DESCRIPTION_BORDER_RIGHT_SPRITE = new ResourceLocation(AetherII.MODID, "guidebook/border_right");
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

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(leftPagePos, topPos, 0.0D);
        this.renderGuidebookLeftPage(screen, guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.pose().popPose();

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(rightPagePos, topPos, 0.0D);
        this.renderGuidebookRightPage(screen, guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.pose().popPose();

        this.renderGuidebookFowardPage(screen, guiGraphics, mouseX, mouseY, partialTick);
    }

    default void renderGuidebookFowardPage(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

    }

    default void renderGuidebookBacking(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int leftPagePos = ((screen.width + 2) / 2) - PAGE_WIDTH;
        int rightPagePos = (screen.width / 2);
        int topPos = (screen.height - PAGE_HEIGHT) / 2;
        guiGraphics.blit(GUIDEBOOK_LEFT_BACKING_LOCATION, leftPagePos, topPos, 0, 0, PAGE_WIDTH, PAGE_HEIGHT, 256, 256);
        guiGraphics.blit(GUIDEBOOK_RIGHT_BACKING_LOCATION, rightPagePos, topPos, 0, 0, PAGE_WIDTH, PAGE_HEIGHT, 256, 256);
    }

    default void renderGuidebookLeftPage(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(this.getLeftPageTexture(), 0, 0, 0, 0, PAGE_WIDTH, PAGE_HEIGHT, 256, 256);
    }

    default void renderGuidebookRightPage(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(this.getRightPageTexture(), 0, 0, 0, 0, PAGE_WIDTH, PAGE_HEIGHT, 256, 256);
    }

    default void switchTab() {

    }

    ResourceLocation getLeftPageTexture();

    ResourceLocation getRightPageTexture();

    GuidebookEquipmentMenu getEquipmentMenu();

    Inventory getPlayerInventory();

    float getMouseX();

    float getMouseY();

    <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(Screen screen, T widget);

    enum Tab {
        EQUIPMENT(EQUIPMENT_TAB, (menu, inventory) -> new GuidebookEquipmentScreen(menu, inventory, Component.translatable("gui.aether_ii.guidebook.equipment.title"))),
//        STATUS(STATUS_TAB, (menu, inventory) -> new GuidebookStatusScreen(menu, inventory, Component.translatable("gui.aether_ii.guidebook.status.title"))), // TODO WIP ALPHA THINGS
        DISCOVERY(DISCOVERY_TAB, (menu, inventory) -> new GuidebookDiscoveryScreen(menu, inventory, Component.translatable("gui.aether_ii.guidebook.discovery.title")));
//        JOURNAL(JOURNAL_TAB, (menu, inventory) -> new GuidebookJournalScreen(menu, inventory, Component.translatable("gui.aether_ii.guidebook.journal.title"))); // TODO WIP ALPHA THINGS
//        REWARDS(REWARDS_TAB, (menu, inventory) -> new GuidebookRewardsScreen(menu, inventory, Component.translatable("gui.aether_ii.guidebook.rewards.title"))); // TODO WIP ALPHA THINGS

        private final AetherIIWidgetSprites sprite;
        private final BiFunction<GuidebookEquipmentMenu, Inventory, Screen> screen;

        Tab(AetherIIWidgetSprites sprite, BiFunction<GuidebookEquipmentMenu, Inventory, Screen> screen) {
            this.sprite = sprite;
            this.screen = screen;
        }

        public AetherIIWidgetSprites getSprite() {
            return this.sprite;
        }

        public BiFunction<GuidebookEquipmentMenu, Inventory, Screen> getScreen() {
            return this.screen;
        }
    }
}


