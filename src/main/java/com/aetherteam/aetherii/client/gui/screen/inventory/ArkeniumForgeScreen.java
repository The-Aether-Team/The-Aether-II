package com.aetherteam.aetherii.client.gui.screen.inventory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.ItemReinforcement;
import com.aetherteam.aetherii.client.gui.component.inventory.ForgeButton;
import com.aetherteam.aetherii.client.gui.component.inventory.ReinforcementTierButton;
import com.aetherteam.aetherii.data.resources.registries.AetherIIItemReinforcements;
import com.aetherteam.aetherii.inventory.menu.ArkeniumForgeMenu;
import com.aetherteam.aetherii.inventory.menu.slot.ForgeCharmSlot;
import com.aetherteam.aetherii.item.AetherIIItems;
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
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.joml.Matrix3x2fStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ArkeniumForgeScreen extends AbstractContainerScreen<ArkeniumForgeMenu> {
    private static final Identifier TEXT_FIELD_SPRITE = Identifier.withDefaultNamespace("container/anvil/text_field");
    private static final Identifier TEXT_FIELD_DISABLED_SPRITE = Identifier.withDefaultNamespace("container/anvil/text_field_disabled");
    private static final Identifier TIER_1_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/tier_1");
    private static final Identifier TIER_2_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/tier_2");
    private static final Identifier TIER_3_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/tier_3");
    private static final Identifier TIER_4_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/tier_4");
    private static final Identifier SLOT_LOCKED = Identifier.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/slot_charm_locked");
    private static final Identifier ARKENIUM_FORGE_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/menu/arkenium_forge.png");
    public static final List<Identifier> TIER_LOCATIONS = List.of(TIER_1_SPRITE, TIER_2_SPRITE, TIER_3_SPRITE, TIER_4_SPRITE);
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
        this.imageHeight = 255;
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.inventoryLabelY = this.imageHeight - 94;
        this.selectedTier = null;

        this.initButtons();
        this.initName();
    }

    protected void initButtons() {
        if (Minecraft.getInstance().level != null) {
            RegistryAccess registryAccess = Minecraft.getInstance().level.registryAccess();
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
            int tierCount = ReinforcementTier.getTierCount(registryAccess, input);
            if (tierCount > 0) {
                int spriteSize = 16;
                int areaWidth = 162;
                int x = this.leftPos + 7;
                int y = this.topPos + 110;
                for (int tier = 1; tier <= tierCount; tier++) {
                    int offsetX = x + ((areaWidth / (tierCount + 1)) * tier);

                    ReinforcementTier labelTier = ReinforcementTier.values()[tier - 1];
                    ItemReinforcement reinforcement = AetherIIItemReinforcements.get(registryAccess, input);
                    if (reinforcement != null) {
                        ReinforcementTierButton tierButton = new ReinforcementTierButton(this, labelTier, offsetX - (spriteSize / 2), y, 20, 20, button -> {
                            if (button.isActive()) {
                                this.selectedTier = labelTier;
                            }
                        });

                        MutableComponent component = ReinforcementTier.createReinforcementComponent(tier).copy();
                        component = component.append(CommonComponents.NEW_LINE);
                        component = component.append(reinforcement.upgrades()[tier - 1].description());
                        tierButton.setTooltip(Tooltip.create(component));
                        this.tierButtons.add(this.addRenderableWidget(tierButton));
                    }
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

    protected void reinit(int width, int height) {
        String s = this.name.getValue();
        this.init(width, height);
        this.name.setValue(s);
    }

    @Override
    public void resize(int width, int height) {
        this.reinit(width, height);
    }

    @Override
    protected void setInitialFocus() {
        this.setInitialFocus(this.name);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
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
            this.reinit(this.width, this.height);
        }

        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        this.name.extractRenderState(graphics, mouseX, mouseY, partialTick);
        this.extractTooltip(graphics, mouseX, mouseY);

        this.lastInput = this.menu.getInput().copy();
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        ItemStack input = this.menu.getInput();
        super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ARKENIUM_FORGE_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, !input.isEmpty() ? TEXT_FIELD_SPRITE : TEXT_FIELD_DISABLED_SPRITE, this.leftPos + 33, this.topPos + 20, 110, 16);

        if (Minecraft.getInstance().level != null) {
            RegistryAccess registryAccess = Minecraft.getInstance().level.registryAccess();
            if (!input.isEmpty()) {
                ItemStack displayStack = input.copy();
                if (this.selectedTier != null) {
                    ItemReinforcement reinforcement = AetherIIItemReinforcements.get(registryAccess, displayStack);
                    if (reinforcement != null) {
                        displayStack = reinforcement.modify(displayStack, this.selectedTier.getTierNumber()).copy();
                        Charms charms = displayStack.get(AetherIIDataComponents.CHARMS);
                        if (charms != null) {
                            List<Charms.CharmHolder> charmHolders = charms.charmHolders();
                            for (Slot slot : this.getMenu().slots) {
                                if (slot instanceof ForgeCharmSlot forgeCharmSlot) {
                                    if (forgeCharmSlot.getCharmIndex() < charmHolders.size()) {
                                        Charms.CharmHolder charmHolder = charmHolders.get(forgeCharmSlot.getCharmIndex());
                                        if (!forgeCharmSlot.hasItem() && (!forgeCharmSlot.isActive() || charmHolder.getTier().getValue() != forgeCharmSlot.getCharmTier().getValue())) {
                                            Identifier texture = Identifier.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/slot_" + charmHolder.getType().name().toLowerCase(Locale.ROOT) + "_charm_" + charmHolder.getTier().getValue());
                                            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, texture, i + slot.x, j + slot.y, 16, 16);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                this.getMenu().replaceCharms(Minecraft.getInstance().player, displayStack, false);
                if (this.nameDifferent()) {
                    displayStack.set(DataComponents.CUSTOM_NAME, Component.literal(this.name.getValue()));
                }

                float itemX = (this.leftPos + 72) / 2.0F;
                float itemY = (this.topPos + 57) / 2.0F;
                Matrix3x2fStack poseStack = guiGraphics.pose();
                poseStack.pushMatrix();
                poseStack.scale(2, 2);
                poseStack.translate(itemX, itemY);
                guiGraphics.item(displayStack, 0, 0);
                this.renderItemTooltipForSpace(this.font, guiGraphics, mouseX, mouseY, (int) (itemX - 1) * 2, (int) (itemY - 1) * 2, 35, 35, displayStack);
                poseStack.popMatrix();

                for (ReinforcementTierButton button : this.tierButtons) {
                    ReinforcementTier labelTier = button.getTier();
                    int tier = labelTier.getTierNumber();
                    ItemReinforcement.Cost cost = ReinforcementTier.getCostForTier(registryAccess, input, tier);
                    int primaryCost = ReinforcementTier.getPrimaryCostForTier(registryAccess, input, tier);
                    int secondaryCost = ReinforcementTier.getSecondaryCostForTier(registryAccess, input, tier);
                    if (cost != null && primaryCost != -1 && secondaryCost != -1) {
                        if (!button.isCompleted()) {
                            ItemStack primary = new ItemStack(cost.primaryCost().item(), primaryCost);
                            ItemStack secondary = cost.secondaryCost().isPresent() ? new ItemStack(cost.secondaryCost().get().item(), secondaryCost) : ItemStack.EMPTY;

                            if (!primary.isEmpty()) {
                                int secondX = secondary.isEmpty() ? button.getWidth() / 2 : 0;
                                int x = secondX + button.getX() - 8;
                                int y = button.getY() + button.getHeight() + 2;
                                guiGraphics.fakeItem(primary, x, y);
                                guiGraphics.itemDecorations(this.font, primary, x, y);
                            }
                            if (!secondary.isEmpty()) {
                                int x = button.getX() + 8;
                                int y = button.getY() + button.getHeight() + 2;
                                guiGraphics.fakeItem(secondary, x, y);
                                guiGraphics.itemDecorations(this.font, secondary, x, y);
                            }
                        }
                    }
                }

                for (Slot slot : this.getMenu().slots) {
                    if (slot instanceof ForgeCharmSlot forgeCharmSlot) {
                        if (forgeCharmSlot.isActive() && !forgeCharmSlot.hasItem() && this.isHovering(forgeCharmSlot.x, forgeCharmSlot.y, 16, 16, mouseX, mouseY)) {
                            List<Component> tooltipLines = new ArrayList<>();
                            tooltipLines.add(Component.translatable("gui.aether_ii.arkenium_forge.charm_slot.tooltip"));
                            tooltipLines.add(Charms.createCharmTierComponent(forgeCharmSlot.getCharmTier()).append(CommonComponents.SPACE).append(Charms.createCharmTypeComponent(forgeCharmSlot.getCharmType())).withStyle(ChatFormatting.GRAY));
                            guiGraphics.setComponentTooltipForNextFrame(this.font, tooltipLines, mouseX, mouseY);
                        }
                    }
                }
            }
        }
    }

    private void renderItemTooltipForSpace(Font font, GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, int x, int y, int xSize, int ySize, ItemStack stack) {
        int mouseXDiff = mouseX - x;
        int mouseYDiff = mouseY - y;
        if (mouseXDiff >= 0 && mouseXDiff <= xSize && mouseYDiff >= 0 && mouseYDiff <= ySize) {
            guiGraphics.setTooltipForNextFrame(font, stack, mouseX, mouseY);
        }
    }

    @Override
    protected void extractSlot(GuiGraphicsExtractor guiGraphics, Slot slot, int p_470717_, int p_470566_) {
        super.extractSlot(guiGraphics, slot, p_470717_, p_470566_);
        if (slot instanceof ForgeCharmSlot charmSlot) {
            if (charmSlot.isActive() && charmSlot.isLocked(this.menu.getInput())) {
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_LOCKED, charmSlot.x, charmSlot.y, 16, 16);
            }
        }
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (event.key() == 256) {
            this.minecraft.player.closeContainer();
        }
        return this.name.keyPressed(event) || this.name.canConsumeInput() || super.keyPressed(event);
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
        if (Minecraft.getInstance().level != null && this.menu.upgradeItem(Minecraft.getInstance().level.registryAccess(), this.selectedTier)) {
            ClientPacketDistributor.sendToServer(new ForgeUpgradePacket(this.selectedTier));
        }
    }

    private void onCharmSlotted() {
        if (this.menu.slotCharms(Minecraft.getInstance().player)) {
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
