package com.aetherteam.aetherii.client.gui.screen.inventory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.ArkeniumForgeMenu;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundRenameItemPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

//todo
//  get naming to work; probably shouldnt take cost.
//  need to add forging button
//  forging button will have to consume materials and update equipment stack; may need packets to the server for this but not sure.
//  will need some conditional positioning to render the upgrade tier icons and the respective material costs;
//  i do still need to figure out how reinforcing will work; do i make it a recipe type? how much will need to be configurable when it might only be the inputs. and if the inputs are static then a recipe might not even be needed. maybe only a single special type thats hardcoded
//  need to render the charm displays and "slots" around the item when inputted.

public class ArkeniumForgeScreen extends AbstractContainerScreen<ArkeniumForgeMenu> implements ContainerListener {
    private static final ResourceLocation TEXT_FIELD_SPRITE = ResourceLocation.withDefaultNamespace("container/anvil/text_field");
    private static final ResourceLocation TEXT_FIELD_DISABLED_SPRITE = ResourceLocation.withDefaultNamespace("container/anvil/text_field_disabled");
    private static final ResourceLocation ARKENIUM_FORGE_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/menu/arkenium_forge.png");
    private EditBox name;
    private final Player player;

    public ArkeniumForgeScreen(ArkeniumForgeMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.player = playerInventory.player;
    }

    @Override
    protected void init() {
        super.init();
        this.imageWidth = 176;
        this.imageHeight = 251;
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.inventoryLabelY = this.imageHeight - 94;

        this.name = new EditBox(this.font, this.leftPos + 36, this.topPos + 24, 103, 12, Component.empty());
        this.name.setCanLoseFocus(false);
        this.name.setTextColor(-1);
        this.name.setTextColorUneditable(-1);
        this.name.setBordered(false);
        this.name.setMaxLength(50);
        this.name.setResponder(this::onNameChanged);
        this.name.setValue("");
        this.addWidget(this.name);
        this.name.setEditable(this.menu.getSlot(0).hasItem());
    }

    @Override
    protected void setInitialFocus() {
        this.setInitialFocus(this.name);
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        String s = this.name.getValue();
        this.init(minecraft, width, height);
        this.name.setValue(s);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.name.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(ARKENIUM_FORGE_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);

        ItemStack input = this.menu.getItems().getFirst();

        guiGraphics.blitSprite(!input.isEmpty() ? TEXT_FIELD_SPRITE : TEXT_FIELD_DISABLED_SPRITE, this.leftPos + 33, this.topPos + 20, 110, 16);

        if (!input.isEmpty()) {
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            poseStack.scale(2, 2, 1);
            guiGraphics.renderItem(input, (i + 73) / 2, (j + 58) / 2);
            poseStack.popPose();
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.minecraft.player.closeContainer();
        }
        return this.name.keyPressed(keyCode, scanCode, modifiers) || this.name.canConsumeInput() || super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void slotChanged(AbstractContainerMenu containerToSend, int slotInd, ItemStack stack) {
        if (slotInd == 0) {
            this.name.setValue(stack.isEmpty() ? "" : stack.getHoverName().getString());
            this.name.setEditable(!stack.isEmpty());
            this.setFocused(this.name);
        }
    }

    @Override
    public void dataChanged(AbstractContainerMenu containerMenu, int dataSlotIndex, int value) { }

    private void onNameChanged(String name) { //todo
        Slot slot = this.menu.getSlot(0);
        if (slot.hasItem()) {
            String s = name;
            if (!slot.getItem().has(DataComponents.CUSTOM_NAME) && name.equals(slot.getItem().getHoverName().getString())) {
                s = "";
            }

            if (this.menu.setItemName(s)) {
                this.minecraft.player.connection.send(new ServerboundRenameItemPacket(s));
            }
        }
    }
}
