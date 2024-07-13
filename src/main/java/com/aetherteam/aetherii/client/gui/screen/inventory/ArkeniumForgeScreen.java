package com.aetherteam.aetherii.client.gui.screen.inventory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.ArkeniumForgeMenu;
import com.aetherteam.aetherii.item.ReinforcementTier;
import com.aetherteam.aetherii.network.packet.serverbound.ForgeRenamePacket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.RenderType;
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
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

//todo
//  get naming to work; probably shouldnt take cost.
//  forging button will have to consume materials and update equipment stack; may need packets to the server for this but not sure.
//  need to render selection outline for tier icons dependent on input material count. also i forget if theyre meant to act as buttons as well.
//  need to render the charm displays and "slots" around the item when inputted.
//  forging button tooltip

public class ArkeniumForgeScreen extends AbstractContainerScreen<ArkeniumForgeMenu> implements ContainerListener {
    private static final ResourceLocation TEXT_FIELD_SPRITE = ResourceLocation.withDefaultNamespace("container/anvil/text_field");
    private static final ResourceLocation TEXT_FIELD_DISABLED_SPRITE = ResourceLocation.withDefaultNamespace("container/anvil/text_field_disabled");
    private static final WidgetSprites FORGE_BUTTON_SPRITE = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/forge_button"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/forge_button_disabled"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/forge_button_selected"));
    private static final ResourceLocation TIER_1_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/tier_1");
    private static final ResourceLocation TIER_2_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/tier_2");
    private static final ResourceLocation TIER_3_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/tier_3");
    private static final ResourceLocation TIER_4_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/tier_4");
    private static final ResourceLocation TIER_SELECTION_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/tier_selection");
    private static final ResourceLocation ARKENIUM_FORGE_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/menu/arkenium_forge.png");
    private static final List<ResourceLocation> TIER_LOCATIONS = List.of(TIER_1_SPRITE, TIER_2_SPRITE, TIER_3_SPRITE, TIER_4_SPRITE);
    private EditBox name;
    private ImageButton forgeButton;
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
        this.name.setValue("");
        this.addWidget(this.name);
        this.name.setEditable(this.menu.getSlot(0).hasItem());

        this.forgeButton = this.addRenderableWidget(new ImageButton(this.leftPos + 130, this.topPos + 63, 20, 20, FORGE_BUTTON_SPRITE, button -> {
            if (button.isActive()) {
                this.onNameChanged(this.name.getValue());
            }
        }));
        this.forgeButton.setTooltip(Tooltip.create(Component.literal("Forge Item"))); //todo translate
        this.forgeButton.active = false;

        this.menu.addSlotListener(this);
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
        if (!this.menu.getInput().isEmpty() && !this.name.getValue().equals(this.menu.getInput().getHoverName().getString())) { //todo more checks
            if (!this.forgeButton.active) {
                this.forgeButton.active = true;
            }
        } else {
            if (this.forgeButton.active) {
                this.forgeButton.active = false;
            }
        }

        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.name.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(ARKENIUM_FORGE_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);

        ItemStack input = this.menu.getInput();

        guiGraphics.blitSprite(!input.isEmpty() ? TEXT_FIELD_SPRITE : TEXT_FIELD_DISABLED_SPRITE, this.leftPos + 33, this.topPos + 20, 110, 16);

        if (!input.isEmpty()) {
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            poseStack.scale(2, 2, 1);
            guiGraphics.renderItem(input, (i + 73) / 2, (j + 58) / 2); //todo figure out more stable positioning
            poseStack.popPose();

            int tierCount = this.menu.getTierCount(input);
            if (tierCount > 0) {
                int spriteSize = 16;
                int areaWidth = 162;
                int x = this.leftPos + 7;
                int y = this.topPos + 110;
                for (int tier = 1; tier <= tierCount; tier++) {
                    int offsetX = x + ((areaWidth / (tierCount + 1)) * tier);
                    guiGraphics.blitSprite(TIER_LOCATIONS.get(tier - 1), offsetX - (spriteSize / 2), y, spriteSize, spriteSize);

                    ReinforcementTier.Cost cost = this.menu.getCostForTier(input, tier);
                    if (cost != null) {
                        ItemStack primary = new ItemStack(cost.primaryMaterial(), cost.primaryCount());
                        guiGraphics.renderFakeItem(primary, offsetX - spriteSize, y + 18);
                        guiGraphics.fill(RenderType.guiGhostRecipeOverlay(), offsetX - spriteSize, y + 18, (offsetX - spriteSize) + 16, (y + 18) + 16, 822083583);
                        guiGraphics.renderItemDecorations(this.font, primary, offsetX - spriteSize, y + 18);

                        ItemStack secondary = new ItemStack(cost.secondaryMaterial(), cost.secondaryCount());
                        guiGraphics.renderFakeItem(secondary, offsetX, y + 18);
                        guiGraphics.fill(RenderType.guiGhostRecipeOverlay(), offsetX, y + 18, offsetX + 16, (y + 18) + 16, 822083583);
                        guiGraphics.renderItemDecorations(this.font, secondary, offsetX, y + 18);
                    }
                }
            }
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
                PacketDistributor.sendToServer(new ForgeRenamePacket(s));
            }
        }
    }
}
