package com.aetherteam.aetherii.client.gui.screen.inventory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.ItemReinforcement;
import com.aetherteam.aetherii.client.gui.AetherIIGuiGraphics;
import com.aetherteam.aetherii.client.gui.component.inventory.ForgeButton;
import com.aetherteam.aetherii.client.gui.component.inventory.ReinforcementTierButton;
import com.aetherteam.aetherii.data.resources.registries.AetherIIItemReinforcements;
import com.aetherteam.aetherii.inventory.menu.ArkeniumForgeMenu;
import com.aetherteam.aetherii.inventory.menu.slot.ForgeCharmSlot;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.Charms;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import com.aetherteam.aetherii.network.ClientPacketDistributor;
import com.aetherteam.aetherii.network.packet.serverbound.ForgeRenamePacket;
import com.aetherteam.aetherii.network.packet.serverbound.ForgeSlotCharmsPacket;
import com.aetherteam.aetherii.network.packet.serverbound.ForgeTriggerSoundPacket;
import com.aetherteam.aetherii.network.packet.serverbound.ForgeUpgradePacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ArkeniumForgeScreen extends AbstractContainerScreen<ArkeniumForgeMenu> {
    private static final ResourceLocation ANVIL_LOCATION = new ResourceLocation("textures/gui/container/anvil.png");
    private static final ResourceLocation TIER_1_SPRITE = new ResourceLocation(AetherII.MODID, "container/arkenium_forge/tier_1");
    private static final ResourceLocation TIER_2_SPRITE = new ResourceLocation(AetherII.MODID, "container/arkenium_forge/tier_2");
    private static final ResourceLocation TIER_3_SPRITE = new ResourceLocation(AetherII.MODID, "container/arkenium_forge/tier_3");
    private static final ResourceLocation TIER_4_SPRITE = new ResourceLocation(AetherII.MODID, "container/arkenium_forge/tier_4");
    private static final ResourceLocation SLOT_LOCKED = new ResourceLocation(AetherII.MODID, "container/arkenium_forge/slot_charm_locked");
    private static final ResourceLocation ARKENIUM_FORGE_LOCATION = new ResourceLocation(AetherII.MODID, "textures/gui/menu/arkenium_forge.png");
    public static final List<ResourceLocation> TIER_LOCATIONS = List.of(TIER_1_SPRITE, TIER_2_SPRITE, TIER_3_SPRITE, TIER_4_SPRITE);

    private final List<ReinforcementTierButton> tierButtons = new ArrayList<>();
    private EditBox name;
    private ItemStack lastInput = ItemStack.EMPTY;
    private ReinforcementTier selectedTier;

    public ArkeniumForgeScreen(ArkeniumForgeMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void init() {
        this.imageWidth = 176;
        this.imageHeight = 255;
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.inventoryLabelY = this.imageHeight - 94;
        this.selectedTier = null;
        this.tierButtons.clear();
        this.lastInput = this.menu.getInput().copy();
        this.initButtons();
        this.initName();
    }

    private void initButtons() {
        if (this.minecraft != null && this.minecraft.level != null) {
            ForgeButton forgeButton = this.addRenderableWidget(new ForgeButton(this, this.leftPos + 130, this.topPos + 63, 20, 20, button -> {
                if (button.isActive()) {
                    this.onNameChanged(this.name.getValue());
                    this.onItemUpgraded();
                    this.onCharmSlotted();
                    ClientPacketDistributor.sendToServer(new ForgeTriggerSoundPacket());
                }
            }));
            forgeButton.setTooltip(Tooltip.create(Component.translatable("gui.aether_ii.arkenium_forge.forge_button.tooltip")));

            ItemStack input = this.menu.getInput();
            int tierCount = ReinforcementTier.getTierCount(this.minecraft.level.registryAccess(), input);
            if (tierCount > 0) {
                int areaWidth = 162;
                int x = this.leftPos + 7;
                int y = this.topPos + 110;
                for (int tier = 1; tier <= tierCount; tier++) {
                    int offsetX = x + (areaWidth / (tierCount + 1)) * tier;
                    ReinforcementTier labelTier = ReinforcementTier.values()[tier - 1];
                    ItemReinforcement reinforcement = AetherIIItemReinforcements.get(this.minecraft.level.registryAccess(), input);
                    if (reinforcement != null) {
                        ReinforcementTierButton tierButton = new ReinforcementTierButton(this, labelTier, offsetX - 8, y, 20, 20, button -> {
                            if (button.isActive()) {
                                this.selectedTier = labelTier;
                            }
                        });

                        MutableComponent tooltip = ReinforcementTier.createReinforcementComponent(tier).copy();
                        tooltip = tooltip.append(CommonComponents.NEW_LINE);
                        tooltip = tooltip.append(reinforcement.upgrades()[tier - 1].description());
                        tierButton.setTooltip(Tooltip.create(tooltip));
                        this.tierButtons.add(this.addRenderableWidget(tierButton));
                    }
                }
            }
        }
    }

    private void initName() {
        this.name = new EditBox(this.font, this.leftPos + 36, this.topPos + 24, 103, 12, Component.empty());
        this.name.setCanLoseFocus(false);
        this.name.setTextColor(-1);
        this.name.setTextColorUneditable(-1);
        this.name.setBordered(false);
        this.name.setMaxLength(50);
        this.name.setValue(this.menu.getInput().isEmpty() ? "" : this.menu.getInput().getHoverName().getString());
        this.name.setEditable(this.menu.getSlot(0).hasItem());
        this.addWidget(this.name);
        if (this.menu.getSlot(0).hasItem()) {
            this.setInitialFocus(this.name);
        }
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        String currentName = this.name != null ? this.name.getValue() : "";
        super.resize(minecraft, width, height);
        if (this.name != null && !currentName.isEmpty()) {
            this.name.setValue(currentName);
        }
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        if (this.name != null) {
            this.name.tick();
        }
        if (!ItemStack.matches(this.menu.getInput(), this.lastInput)) {
            this.rebuildWidgets();
        } else if (this.menu.getInput().isEmpty() && this.name != null && !this.name.getValue().isEmpty()) {
            this.name.setValue("");
            this.name.setEditable(false);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderLockedCharmSlots(guiGraphics);
        this.name.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
        this.renderPreviewTooltip(this.font, guiGraphics, mouseX, mouseY, this.leftPos + 70, this.topPos + 55, 35, 35, this.createDisplayStack());
        this.renderCharmSlotTooltips(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        ItemStack input = this.menu.getInput();
        int x = this.leftPos;
        int y = this.topPos;
        guiGraphics.blit(ARKENIUM_FORGE_LOCATION, x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
        guiGraphics.blit(ANVIL_LOCATION, this.leftPos + 33, this.topPos + 20, 0, 166 + (!input.isEmpty() ? 0 : 16), 110, 16);

        if (!input.isEmpty() && this.minecraft != null && this.minecraft.level != null) {
            ItemStack displayStack = this.createDisplayStack();
            Charms charms = AetherIIDataComponents.get(displayStack, AetherIIDataComponents.CHARMS);
            if (charms != null) {
                List<Charms.CharmHolder> charmHolders = charms.charmHolders();
                for (Slot slot : this.getMenu().slots) {
                    if (slot instanceof ForgeCharmSlot forgeCharmSlot && forgeCharmSlot.getCharmIndex() < charmHolders.size()) {
                        Charms.CharmHolder charmHolder = charmHolders.get(forgeCharmSlot.getCharmIndex());
                        if (!forgeCharmSlot.hasItem() && (!forgeCharmSlot.isActive() || charmHolder.getTier().getValue() != forgeCharmSlot.getCharmTier().getValue())) {
                            ResourceLocation texture = new ResourceLocation(AetherII.MODID, "container/arkenium_forge/slot_" + charmHolder.getType().name().toLowerCase(Locale.ROOT) + "_charm_" + charmHolder.getTier().getValue());
                            AetherIIGuiGraphics.blitSprite(guiGraphics, texture, x + slot.x, y + slot.y, 16, 16);
                        }
                    }
                }
            }

            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(this.leftPos + 72.0F, this.topPos + 57.0F, 100.0F);
            guiGraphics.pose().scale(2.0F, 2.0F, 1.0F);
            guiGraphics.renderItem(displayStack, 0, 0);
            guiGraphics.pose().popPose();

            this.renderTierCosts(guiGraphics, input);
        }
    }

    private ItemStack createDisplayStack() {
        ItemStack input = this.menu.getInput();
        if (input.isEmpty()) {
            return ItemStack.EMPTY;
        }
        ItemStack displayStack = input.copy();
        if (this.minecraft != null && this.minecraft.level != null && this.selectedTier != null) {
            ItemReinforcement reinforcement = AetherIIItemReinforcements.get(this.minecraft.level.registryAccess(), displayStack);
            if (reinforcement != null) {
                displayStack = reinforcement.modify(displayStack, this.selectedTier.getTierNumber()).copy();
            }
        }
        if (this.minecraft != null && this.minecraft.player != null) {
            this.getMenu().replaceCharms(this.minecraft.player, displayStack, false);
        }
        if (this.name != null && this.nameDifferent()) {
            displayStack.setHoverName(Component.literal(this.name.getValue()));
        }
        return displayStack;
    }

    private void renderTierCosts(GuiGraphics guiGraphics, ItemStack input) {
        if (this.minecraft == null || this.minecraft.level == null) {
            return;
        }
        for (ReinforcementTierButton button : this.tierButtons) {
            ReinforcementTier labelTier = button.getTier();
            int tier = labelTier.getTierNumber();
            ItemReinforcement.Cost cost = ReinforcementTier.getCostForTier(this.minecraft.level.registryAccess(), input, tier);
            int primaryCost = ReinforcementTier.getPrimaryCostForTier(this.minecraft.level.registryAccess(), input, tier);
            int secondaryCost = ReinforcementTier.getSecondaryCostForTier(this.minecraft.level.registryAccess(), input, tier);
            if (cost != null && primaryCost != -1 && secondaryCost != -1 && !button.isCompleted()) {
                ItemStack primary = new ItemStack(cost.primaryCost().item(), primaryCost);
                ItemStack secondary = cost.secondaryCost().isPresent() ? new ItemStack(cost.secondaryCost().get().item(), secondaryCost) : ItemStack.EMPTY;
                if (!primary.isEmpty()) {
                    int offset = secondary.isEmpty() ? button.getWidth() / 2 : 0;
                    int x = offset + button.getX() - 8;
                    int y = button.getY() + button.getHeight() + 2;
                    guiGraphics.renderItem(primary, x, y);
                    guiGraphics.renderItemDecorations(this.font, primary, x, y);
                }
                if (!secondary.isEmpty()) {
                    int x = button.getX() + 8;
                    int y = button.getY() + button.getHeight() + 2;
                    guiGraphics.renderItem(secondary, x, y);
                    guiGraphics.renderItemDecorations(this.font, secondary, x, y);
                }
            }
        }
    }

    private void renderLockedCharmSlots(GuiGraphics guiGraphics) {
        for (Slot slot : this.getMenu().slots) {
            if (slot instanceof ForgeCharmSlot charmSlot && charmSlot.isActive() && charmSlot.isLocked(this.menu.getInput())) {
                AetherIIGuiGraphics.blitSprite(guiGraphics, SLOT_LOCKED, this.leftPos + charmSlot.x, this.topPos + charmSlot.y, 16, 16);
            }
        }
    }

    private void renderCharmSlotTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (this.menu.getInput().isEmpty()) {
            return;
        }
        for (Slot slot : this.getMenu().slots) {
            if (slot instanceof ForgeCharmSlot forgeCharmSlot && forgeCharmSlot.isActive() && !forgeCharmSlot.hasItem() && this.isHovering(forgeCharmSlot.x, forgeCharmSlot.y, 16, 16, mouseX, mouseY)) {
                List<Component> tooltipLines = new ArrayList<>();
                tooltipLines.add(Component.translatable("gui.aether_ii.arkenium_forge.charm_slot.tooltip"));
                tooltipLines.add(Charms.createCharmTierComponent(forgeCharmSlot.getCharmTier()).append(CommonComponents.SPACE).append(Charms.createCharmTypeComponent(forgeCharmSlot.getCharmType())).withStyle(ChatFormatting.GRAY));
                guiGraphics.renderComponentTooltip(this.font, tooltipLines, mouseX, mouseY);
            }
        }
    }

    private void renderPreviewTooltip(Font font, GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y, int width, int height, ItemStack stack) {
        if (!stack.isEmpty() && mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            guiGraphics.renderTooltip(font, stack, mouseX, mouseY);
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
    public boolean charTyped(char codePoint, int modifiers) {
        return this.name.charTyped(codePoint, modifiers) || super.charTyped(codePoint, modifiers);
    }

    public boolean canForge() {
        if (!this.menu.getInput().isEmpty()) {
            return this.nameDifferent() || this.selectedTier != null || this.menu.hasNewCharms();
        }
        return false;
    }

    public boolean nameDifferent() {
        return this.name != null && !this.menu.getInput().isEmpty() && !this.name.getValue().equals(this.menu.getInput().getHoverName().getString());
    }

    private void onItemUpgraded() {
        if (this.minecraft != null && this.minecraft.level != null && this.menu.upgradeItem(this.minecraft.level.registryAccess(), this.selectedTier)) {
            ClientPacketDistributor.sendToServer(new ForgeUpgradePacket(this.selectedTier));
        }
    }

    private void onCharmSlotted() {
        if (this.minecraft != null && this.minecraft.player != null && this.menu.slotCharms(this.minecraft.player)) {
            ClientPacketDistributor.sendToServer(new ForgeSlotCharmsPacket());
        }
    }

    private void onNameChanged(String name) {
        ItemStack stack = this.menu.getInput();
        if (!stack.isEmpty()) {
            String value = name;
            if (!stack.hasCustomHoverName() && name.equals(stack.getHoverName().getString())) {
                value = "";
            }
            if (this.menu.setItemName(value)) {
                ClientPacketDistributor.sendToServer(new ForgeRenamePacket(value));
            }
        }
    }
}
