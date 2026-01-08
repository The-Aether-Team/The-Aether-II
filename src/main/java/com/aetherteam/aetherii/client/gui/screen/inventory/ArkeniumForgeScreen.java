package com.aetherteam.aetherii.client.gui.screen.inventory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.component.inventory.ForgeButton;
import com.aetherteam.aetherii.client.gui.component.inventory.ReinforcementTierButton;
import com.aetherteam.aetherii.inventory.menu.ArkeniumForgeMenu;
import com.aetherteam.aetherii.inventory.menu.slot.ForgeCharmSlot;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.Charms;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.EditBoxAccessor;
import com.aetherteam.aetherii.network.packet.serverbound.ForgeRenamePacket;
import com.aetherteam.aetherii.network.packet.serverbound.ForgeSlotCharmsPacket;
import com.aetherteam.aetherii.network.packet.serverbound.ForgeTriggerSoundPacket;
import com.aetherteam.aetherii.network.packet.serverbound.ForgeUpgradePacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.joml.Matrix3x2fStack;

import java.util.ArrayList;
import java.util.List;

public class ArkeniumForgeScreen extends AbstractContainerScreen<ArkeniumForgeMenu> {
    private static final ResourceLocation TEXT_FIELD_SPRITE = ResourceLocation.withDefaultNamespace("container/anvil/text_field");
    private static final ResourceLocation TEXT_FIELD_DISABLED_SPRITE = ResourceLocation.withDefaultNamespace("container/anvil/text_field_disabled");
    private static final ResourceLocation TIER_1_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/tier_1");
    private static final ResourceLocation TIER_2_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/tier_2");
    private static final ResourceLocation TIER_3_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/tier_3");
    private static final ResourceLocation TIER_4_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/tier_4");
    private static final ResourceLocation SLOT_LOCKED = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/slot_charm_locked");
    private static final ResourceLocation ARKENIUM_FORGE_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/menu/arkenium_forge.png");
    public static final List<ResourceLocation> TIER_LOCATIONS = List.of(TIER_1_SPRITE, TIER_2_SPRITE, TIER_3_SPRITE, TIER_4_SPRITE);
    private final List<ReinforcementTierButton> tierButtons = new ArrayList<>();
    private EditBox name;
    private ItemStack lastInput = ItemStack.EMPTY;
    private ReinforcementTier selectedTier;

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
        this.selectedTier = null;

        this.initButtons();
        this.initName();
    }

    protected void initButtons() {
        ImageButton forgeButton = this.addRenderableWidget(new ForgeButton(this, this.leftPos + 130, this.topPos + 63, 20, 20, button -> {
            if (button.isActive()) {
                this.onNameChanged(this.name.getValue());
                this.onItemUpgraded();
                this.onCharmSlotted();
                ClientPacketDistributor.sendToServer(new ForgeTriggerSoundPacket());
            }
        }));
        forgeButton.setTooltip(Tooltip.create(Component.translatable("gui.aether_ii.arkenium_forge.forge_button.tooltip")));

        this.tierButtons.clear();

        ItemStack input = this.menu.getInput();
        int tierCount = ReinforcementTier.getTierCount(input);
        if (tierCount > 0) {
            int spriteSize = 16;
            int areaWidth = 162;
            int x = this.leftPos + 7;
            int y = this.topPos + 108;
            for (int tier = 1; tier <= tierCount; tier++) {
                int offsetX = x + ((areaWidth / (tierCount + 1)) * tier);

                ReinforcementTier labelTier = ReinforcementTier.values()[tier - 1];
                ReinforcementTier.Stats labelStats = labelTier.getStat(input);
                if (labelStats != null) {
                    ReinforcementTierButton tierButton = new ReinforcementTierButton(this, labelTier, offsetX - (spriteSize / 2), y, 20, 20, button -> {
                        if (button.isActive()) {
                            this.selectedTier = labelTier;
                        }
                    });

                    MutableComponent component = ReinforcementTier.createReinforcementComponent(tier).copy();
                    component = component
                            .append(CommonComponents.NEW_LINE)
                            .append(Component.literal("+").withStyle(ChatFormatting.GRAY).append(Component.literal(String.valueOf(labelStats.durabilityToAdd()))).append(CommonComponents.SPACE).append(Component.translatable("gui.aether_ii.arkenium_forge.tooltip.durability")));
                    if (!labelStats.charmsToSet().charmHolders().isEmpty()) {
                        component = component
                                .append(CommonComponents.NEW_LINE)
                                .append(Component.literal("+").withStyle(ChatFormatting.GRAY).append(Component.literal(String.valueOf(labelStats.charmsToSet().charmHolders().size()))).append(CommonComponents.SPACE).append(Component.translatable("gui.aether_ii.arkenium_forge.tooltip.charms")));
                    }
                    tierButton.setTooltip(Tooltip.create(component));
                    this.tierButtons.add(this.addRenderableWidget(tierButton));
                }
            }
        }
    }

    protected void initName() {
        this.name = new EditBox(this.font, this.leftPos + 36, this.topPos + 24, 103, 12, Component.empty());
        this.name.setCanLoseFocus(false);
        this.name.setTextColor(-1);
        this.name.setTextColorUneditable(-1);
        this.name.setBordered(false);
        this.name.setMaxLength(50);
        this.name.setValue("");
        this.addWidget(this.name);
        this.name.setEditable(this.menu.getSlot(0).hasItem());
    }

    protected void reinit(Minecraft minecraft, int width, int height) {
        String s = this.name.getValue();
        this.init(minecraft, width, height);
        this.name.setValue(s);
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        this.reinit(minecraft, width, height);
    }

    @Override
    protected void setInitialFocus() {
        this.setInitialFocus(this.name);
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

        if (!ItemStack.matches(this.menu.getInput(), this.lastInput)) {
            this.reinit(this.minecraft, this.width, this.height);
        }

        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.name.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);

        this.lastInput = this.menu.getInput().copy();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        ItemStack input = this.menu.getInput();
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ARKENIUM_FORGE_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, !input.isEmpty() ? TEXT_FIELD_SPRITE : TEXT_FIELD_DISABLED_SPRITE, this.leftPos + 33, this.topPos + 20, 110, 16);

        if (!input.isEmpty()) {
            ItemStack displayStack = input.copy();
            if (this.selectedTier != null) {
                displayStack.set(AetherIIDataComponents.REINFORCEMENT_TIER, this.selectedTier);
                Charms charms = this.getMenu().upgradeCharmSlots(this.selectedTier);
                if (charms != null) {
                    displayStack.set(AetherIIDataComponents.CHARMS, charms);
                }
            }
            this.getMenu().replaceCharms(displayStack, false);
            if (this.nameDifferent()) {
                displayStack.set(DataComponents.CUSTOM_NAME, Component.literal(this.name.getValue()));
            }

            float itemX = (this.leftPos + 72) / 2.0F;
            float itemY = (this.topPos + 57) / 2.0F;
            Matrix3x2fStack poseStack = guiGraphics.pose();
            poseStack.pushMatrix();
            poseStack.scale(2, 2);
            poseStack.translate(itemX, itemY);
            guiGraphics.renderItem(displayStack, 0, 0);
            this.renderItemTooltipForSpace(this.font, guiGraphics, mouseX, mouseY, (int) (itemX - 1) * 2, (int) (itemY - 1) * 2, 35, 35, displayStack);
            poseStack.popMatrix();

            for (ReinforcementTierButton button : this.tierButtons) {
                ReinforcementTier labelTier = button.getTier();
                int tier = labelTier.getTierNumber();
                ReinforcementTier.Cost cost = ReinforcementTier.getCostForTier(input, tier);
                int primaryCost = ReinforcementTier.getPrimaryCostForTier(input, tier);
                int secondaryCost = ReinforcementTier.getSecondaryCostForTier(input, tier);
                if (cost != null && primaryCost != -1 && secondaryCost != -1) {
                    if (!button.isCompleted()) {
                        ItemStack primary = new ItemStack(cost.primaryMaterial(), primaryCost);
                        ItemStack secondary = new ItemStack(cost.secondaryMaterial(), secondaryCost);

                        if (!primary.isEmpty()) {
                            int secondX = secondary.isEmpty() ? button.getWidth() / 2 : 0;
                            int x = secondX + button.getX() - 8;
                            int y = button.getY() + button.getHeight();
                            guiGraphics.renderFakeItem(primary, x, y);
                            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ArkeniumForgeMenu.SLOT_PRIMARY, x, y, 16, 16);
                            guiGraphics.renderItemDecorations(this.font, primary, x, y);
                        }
                        if (!secondary.isEmpty()) {
                            int x = button.getX() + 8;
                            int y = button.getY() + button.getHeight();
                            guiGraphics.renderFakeItem(secondary, x, y);
                            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ArkeniumForgeMenu.SLOT_SECONDARY, x, y, 16, 16);
                            guiGraphics.renderItemDecorations(this.font, secondary, x, y);
                        }
                    }
                }
            }
        }
    }

    private void renderItemTooltipForSpace(Font font, GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y, int xSize, int ySize, ItemStack stack) {
        int mouseXDiff = mouseX - x;
        int mouseYDiff = mouseY - y;
        if (mouseXDiff >= 0 && mouseXDiff <= xSize && mouseYDiff >= 0 && mouseYDiff <= ySize) {
            guiGraphics.setTooltipForNextFrame(font, stack, mouseX, mouseY);
        }
    }

    @Override
    protected void renderSlot(GuiGraphics guiGraphics, Slot slot) {
        super.renderSlot(guiGraphics, slot);
        if (slot instanceof ForgeCharmSlot charmSlot) {
            if (charmSlot.isActive() && charmSlot.isLocked()) {
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_LOCKED, charmSlot.x, charmSlot.y, 16, 16);
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

    public boolean canForge() {
        if (!this.menu.getInput().isEmpty()) {
            return this.nameDifferent() || this.selectedTier != null || this.menu.hasNewCharms();
        }
        return false;
    }

    public boolean nameDifferent() {
        return !this.menu.getInput().isEmpty() && !this.name.getValue().equals(this.menu.getInput().getHoverName().getString());
    }

    private void onItemUpgraded() {
        if (this.menu.upgradeItem(this.selectedTier)) {
            ClientPacketDistributor.sendToServer(new ForgeUpgradePacket(this.selectedTier));
        }
    }

    private void onCharmSlotted() {
        if (this.menu.slotCharms()) {
            ClientPacketDistributor.sendToServer(new ForgeSlotCharmsPacket());
        }
    }

    private void onNameChanged(String name) {
        ItemStack stack = this.menu.getInput();
        if (!stack.isEmpty()) {
            String s = name;
            if (!stack.has(DataComponents.CUSTOM_NAME) && name.equals(stack.getHoverName().getString())) {
                s = "";
            }
            if (this.menu.setItemName(s)) {
                ClientPacketDistributor.sendToServer(new ForgeRenamePacket(s));
            }
        }
    }
}
