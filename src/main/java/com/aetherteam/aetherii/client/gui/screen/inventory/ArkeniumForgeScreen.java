package com.aetherteam.aetherii.client.gui.screen.inventory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.ArkeniumForgeMenu;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.EditBoxAccessor;
import com.aetherteam.aetherii.network.packet.serverbound.ForgeRenamePacket;
import com.aetherteam.aetherii.network.packet.serverbound.ForgeSlotCharmsPacket;
import com.aetherteam.aetherii.network.packet.serverbound.ForgeTriggerSoundPacket;
import com.aetherteam.aetherii.network.packet.serverbound.ForgeUpgradePacket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class ArkeniumForgeScreen extends AbstractContainerScreen<ArkeniumForgeMenu> {
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
    private static final ResourceLocation TIER_SELECTED_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/tier_selected");
    private static final ResourceLocation TIER_COMPLETED_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/tier_completed");
    private static final ResourceLocation ARKENIUM_FORGE_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/menu/arkenium_forge.png");
    private static final List<ResourceLocation> TIER_LOCATIONS = List.of(TIER_1_SPRITE, TIER_2_SPRITE, TIER_3_SPRITE, TIER_4_SPRITE);
    private EditBox name;
    private ImageButton forgeButton;
    private ItemStack lastInput = ItemStack.EMPTY;

    public ArkeniumForgeScreen(ArkeniumForgeMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
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
                this.onItemUpgraded();
                this.onCharmSlotted();
                PacketDistributor.sendToServer(new ForgeTriggerSoundPacket());
            }
        }));
        this.forgeButton.setTooltip(Tooltip.create(Component.translatable("gui.aether_ii.arkenium_forge.forge_button.tooltip")));
        this.forgeButton.active = false;
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
        String nameValue = this.menu.getInput().getHoverName().getString();
        boolean editable = !this.menu.getInput().isEmpty();
        if (!ItemStack.matches(this.menu.getInput(), this.lastInput) && !this.name.getValue().equals(nameValue)) {
            this.name.setValue(nameValue);
        }
        if (this.menu.getInput().isEmpty() && !this.name.getValue().isEmpty()) {
            this.name.setValue("");
        }
        if (((EditBoxAccessor) this.name).callIsEditable() != editable) {
            this.name.setEditable(editable);
        }
        if (this.menu.getInput().isEmpty() && this.name.isFocused()) {
            this.name.setFocused(false);
        } else if (!this.menu.getInput().isEmpty() && !this.name.isFocused()) {
            this.name.setFocused(true);
        }

        if (!this.menu.getInput().isEmpty() && (!this.name.getValue().equals(this.menu.getInput().getHoverName().getString())
                || this.menu.getTierForMaterials() > this.menu.getTierForItem()
                || this.menu.hasNewCharms())) {
            if (!this.forgeButton.active) {
                this.forgeButton.active = true;
            }
        } else {
            if (this.forgeButton.active) {
                this.forgeButton.active = false;
                this.forgeButton.setFocused(false);
            }
        }

        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.name.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);

        this.lastInput = this.menu.getInput();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(ARKENIUM_FORGE_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);

        ItemStack input = this.menu.getInput();

        guiGraphics.blitSprite(!input.isEmpty() ? TEXT_FIELD_SPRITE : TEXT_FIELD_DISABLED_SPRITE, this.leftPos + 33, this.topPos + 20, 110, 16);

        if (!input.isEmpty()) {
            ItemStack displayStack = input.copy();
            if (this.forgeButton.active) {
                if (this.menu.getTierForMaterials() > 0) {
                    displayStack.set(AetherIIDataComponents.REINFORCEMENT_TIER, ReinforcementTier.values()[this.menu.getTierForMaterials() - 1]);
                }
            }

            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            poseStack.scale(2, 2, 1);
            poseStack.translate((this.leftPos + 72) / 2.0, (this.topPos + 57) / 2.0, 0);
            guiGraphics.renderItem(displayStack, 0, 0);
            poseStack.popPose();

            ReinforcementTier reinforcementTier = input.get(AetherIIDataComponents.REINFORCEMENT_TIER);
            ReinforcementTier displayTier = displayStack.get(AetherIIDataComponents.REINFORCEMENT_TIER);

            if (displayTier != null) {
                guiGraphics.blitSprite(TIER_LOCATIONS.get(displayTier.getTier() - 1), this.leftPos + 80, this.topPos + 91, 16, 16);
            }

            int tierCount = this.menu.getTierCount();
            if (tierCount > 0) {
                int spriteSize = 16;
                int areaWidth = 162;
                int x = this.leftPos + 7;
                int y = this.topPos + 110;
                int currentTier = this.menu.getTierForMaterials();
                for (int tier = 1; tier <= tierCount; tier++) {
                    int offsetX = x + ((areaWidth / (tierCount + 1)) * tier);

                    guiGraphics.blitSprite(TIER_LOCATIONS.get(tier - 1), offsetX - (spriteSize / 2), y, spriteSize, spriteSize);

                    ReinforcementTier.Cost cost = this.menu.getCostForTier(tier);
                    int primaryCost = this.menu.getPrimaryCostForTier(tier);
                    int secondaryCost = this.menu.getSecondaryCostForTier(tier);
                    if (cost != null && primaryCost != -1 && secondaryCost != -1) {
                        if (reinforcementTier == null || tier > reinforcementTier.getTier()) {
                            ItemStack primary = new ItemStack(cost.primaryMaterial(), primaryCost);
                            ItemStack secondary = new ItemStack(cost.secondaryMaterial(), secondaryCost);

                            if (!primary.isEmpty()) {
                                int secondX = secondary.isEmpty() ? 8 : 0;
                                guiGraphics.renderFakeItem(primary, secondX + offsetX - spriteSize, y + 18);
                                guiGraphics.fill(RenderType.guiGhostRecipeOverlay(), secondX + offsetX - spriteSize, y + 18, secondX + (offsetX - spriteSize) + 16, (y + 18) + 16, 822083583);
                                guiGraphics.renderItemDecorations(this.font, primary, secondX + offsetX - spriteSize, y + 18);
                            }
                            if (!secondary.isEmpty()) {
                                guiGraphics.renderFakeItem(secondary, offsetX, y + 18);
                                guiGraphics.fill(RenderType.guiGhostRecipeOverlay(), offsetX, y + 18, offsetX + 16, (y + 18) + 16, 822083583);
                                guiGraphics.renderItemDecorations(this.font, secondary, offsetX, y + 18);
                            }
                        } else {
                            guiGraphics.blitSprite(TIER_COMPLETED_SPRITE, offsetX - (spriteSize / 2), y + 19, spriteSize, spriteSize);
                        }
                    }
                    if (currentTier == tier && currentTier > this.menu.getTierForItem()) {
                        guiGraphics.blitSprite(TIER_SELECTED_SPRITE, offsetX - (9), y - 1, 18, 18);
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

    private void onItemUpgraded() {
        if (this.menu.upgradeItem()) {
            PacketDistributor.sendToServer(new ForgeUpgradePacket());
        }
    }

    private void onCharmSlotted() {
        if (this.menu.slotCharms()) {
            PacketDistributor.sendToServer(new ForgeSlotCharmsPacket());
        }
    }

    private void onNameChanged(String name) {
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
