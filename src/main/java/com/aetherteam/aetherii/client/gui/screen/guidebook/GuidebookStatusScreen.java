package com.aetherteam.aetherii.client.gui.screen.guidebook;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.inventory.menu.GuidebookEquipmentMenu;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class GuidebookStatusScreen extends Screen implements Guidebook {
    private static final ResourceLocation GUIDEBOOK_STATUS_LEFT_PAGE_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/status/guidebook_status_left.png");
    private static final ResourceLocation GUIDEBOOK_STATUS_RIGHT_PAGE_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/status/guidebook_status_right.png");

    private final GuidebookEquipmentMenu equipmentMenu;
    private final Inventory playerInventory;
    private final Component rightTitle = Component.translatable("gui.aether_ii.guidebook.status.mount.title");
    protected int titleLabelX;
    protected int titleLabelY;
    private float xMouse;
    private float yMouse;

    protected GuidebookStatusScreen(GuidebookEquipmentMenu menu, Inventory playerInventory, Component title) {
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
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderTransparentBackground(guiGraphics);
        this.renderGuidebookSpread(this, guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        int leftPos = (this.width / 2) - PAGE_WIDTH;
        int topPos = (this.height - PAGE_HEIGHT) / 2;
        int x = 104;
        int y = 7;
        int xOffset = 9;
        int yOffset = 19;
        int width = 59;
        int height = 69;

        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, leftPos + x + xOffset, topPos + y + yOffset, leftPos + x + xOffset + width, topPos + y + yOffset + height, 30, 0.0625F, this.xMouse, this.yMouse, this.minecraft.player);

        this.xMouse = (float) mouseX;
        this.yMouse = (float) mouseY;
    }

    @Override
    public void renderGuidebookLeftPage(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Guidebook.super.renderGuidebookLeftPage(screen, guiGraphics, mouseX, mouseY, partialTick);

        Player player = Minecraft.getInstance().player;
        int x = 31;
        int y = 5;

        guiGraphics.drawCenteredString(this.font, this.title, this.titleLabelX, this.titleLabelY, 16777215);

        guiGraphics.blitSprite(Guidebook.HEARTS_SPRITE, x, y + 22, 16, 16);
        guiGraphics.drawString(this.font, Component.literal((int) (player.getHealth()) + "/" + (int) (player.getMaxHealth())), x + 20, y + 26, 16777215, true);

        guiGraphics.blitSprite(Guidebook.ARMOR_SPRITE, x, y + 38, 16, 16);
        guiGraphics.drawString(this.font, Component.literal(player.getArmorValue() + "/20"), x + 20, y + 42, 16777215, true);

        var data = Minecraft.getInstance().player.getData(AetherIIDataAttachments.CURRENCY);
        guiGraphics.renderItem(AetherIIItems.GLINT_COIN.toStack(), x, y + 53);
        guiGraphics.drawString(this.font, Component.literal(String.valueOf(data.getAmount())), x + 20, y + 58, 16777215, true);
    }

    @Override
    public void renderGuidebookRightPage(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Guidebook.super.renderGuidebookRightPage(screen, guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.drawCenteredString(this.font, this.rightTitle, this.titleLabelX - 12, this.titleLabelY, 16777215);
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
        return GUIDEBOOK_STATUS_LEFT_PAGE_LOCATION;
    }

    @Override
    public ResourceLocation getRightPageTexture() {
        return GUIDEBOOK_STATUS_RIGHT_PAGE_LOCATION;
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

    @Override
    public <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(Screen screen, T widget) {
        return this.addRenderableWidget(widget);
    }
}
