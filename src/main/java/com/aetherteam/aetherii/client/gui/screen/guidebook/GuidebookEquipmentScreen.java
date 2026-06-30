package com.aetherteam.aetherii.client.gui.screen.guidebook;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.gui.AetherIIGuiGraphics;
import com.aetherteam.aetherii.client.gui.component.guidebook.GuidebookTab;
import com.aetherteam.aetherii.inventory.menu.GuidebookEquipmentMenu;
import com.aetherteam.aetherii.inventory.menu.slot.SaddlebagSlot;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.miscellaneous.CurrencyItem;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.SlotAccessor;
import com.aetherteam.aetherii.network.ClientPacketDistributor;
import com.aetherteam.aetherii.network.packet.serverbound.ClearAccessoriesPacket;
import com.aetherteam.aetherii.network.packet.serverbound.ClearItemPacket;
import com.aetherteam.aetherii.network.packet.serverbound.CurrencyAmountPacket;
import com.aetherteam.aetherii.network.packet.serverbound.HeldCurrencyPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.List;

public class GuidebookEquipmentScreen extends AbstractContainerScreen<GuidebookEquipmentMenu> implements Guidebook {
    private static final ResourceLocation GUIDEBOOK_EQUIPMENT_LEFT_PAGE_PLAYER_LOCATION = new ResourceLocation(AetherII.MODID, "textures/gui/guidebook/equipment/guidebook_equipment_left_player.png");
    private static final ResourceLocation GUIDEBOOK_EQUIPMENT_LEFT_PAGE_MOA_LOCATION = new ResourceLocation(AetherII.MODID, "textures/gui/guidebook/equipment/guidebook_equipment_left_moa.png");
    private static final ResourceLocation GUIDEBOOK_EQUIPMENT_RIGHT_PAGE_LOCATION = new ResourceLocation(AetherII.MODID, "textures/gui/guidebook/equipment/guidebook_equipment_right.png");
    private static final ResourceLocation GUIDEBOOK_EQUIPMENT_RIGHT_PAGE_CREATIVE_LOCATION = new ResourceLocation(AetherII.MODID, "textures/gui/guidebook/equipment/guidebook_equipment_right_creative.png");
    private static final SimpleContainer DESTROY_ITEM_CONTAINER = new SimpleContainer(1);
    private static final SimpleContainer CURRENCY_CONTAINER = new SimpleContainer(1);

    private final Inventory playerInventory;
    private final Component craftingTitle = Component.translatable("container.crafting");
    protected int craftingTitleLabelX;
    protected int craftingTitleLabelY;
    private float xMouse;
    private float yMouse;
    @Nullable
    private Slot destroyItemSlot;
    @Nullable
    private Slot currencySlot;

    public GuidebookEquipmentScreen(GuidebookEquipmentMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.playerInventory = playerInventory;
        this.titleLabelX = 100;
        this.titleLabelY = 13;
        this.craftingTitleLabelX = 145;
        this.craftingTitleLabelY = 28;
        this.inventoryLabelX = 102;
        this.inventoryLabelY = 80;
    }

    @Override
    protected void init() {
        this.imageWidth = Guidebook.PAGE_WIDTH;
        this.imageHeight = Guidebook.PAGE_HEIGHT;
        super.init();
        this.initTabs(this);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.updateDynamicSlots();
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderCurrencySlot(guiGraphics);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
        this.renderCustomTooltips(guiGraphics, mouseX, mouseY);
        this.xMouse = (float) mouseX;
        this.yMouse = (float) mouseY;
    }

    private void updateDynamicSlots() {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            if (player.isCreative() && this.destroyItemSlot == null) {
                this.destroyItemSlot = new Slot(DESTROY_ITEM_CONTAINER, 0, 127, 50);
                this.menu.slots.add(this.destroyItemSlot);
            } else if (!player.isCreative() && this.destroyItemSlot != null) {
                this.menu.slots.remove(this.destroyItemSlot);
                this.destroyItemSlot = null;
            }
        }

        if (this.menu.getMoa() == null) {
            if (this.currencySlot == null) {
                this.currencySlot = new Slot(CURRENCY_CONTAINER, 0, 64, 112);
                this.menu.slots.add(this.currencySlot);
            }
        } else if (this.currencySlot != null) {
            this.menu.slots.remove(this.currencySlot);
            this.currencySlot = null;
        }
    }

    private void renderCurrencySlot(GuiGraphics guiGraphics) {
        if (this.currencySlot != null && Minecraft.getInstance().player != null) {
            var data = AetherIIDataAttachments.get(Minecraft.getInstance().player, AetherIIDataAttachments.CURRENCY);
            String text = data.getAmount() > 99 ? "99₊" : String.valueOf(data.getAmount());
            int x = this.leftPos + this.currencySlot.x;
            int y = this.topPos + this.currencySlot.y;
            ItemStack coin = new ItemStack(AetherIIItems.GLINT_COIN.get());
            guiGraphics.renderItem(coin, x, y);
            guiGraphics.renderItemDecorations(this.font, coin, x, y, text);
        }
    }

    private void renderCustomTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (this.destroyItemSlot != null && this.isHovering(this.destroyItemSlot.x, this.destroyItemSlot.y, 16, 16, mouseX, mouseY)) {
            guiGraphics.renderTooltip(this.font, Component.translatable("inventory.binSlot"), mouseX, mouseY);
        }
        if (this.currencySlot != null && this.isHovering(this.currencySlot.x, this.currencySlot.y, 16, 16, mouseX, mouseY) && Minecraft.getInstance().player != null) {
            var data = AetherIIDataAttachments.get(Minecraft.getInstance().player, AetherIIDataAttachments.CURRENCY);
            List<Component> components = new ArrayList<>();
            components.add(Component.translatable("gui.aether_ii.guidebook.equipment.pouch.tooltip.title"));
            components.add(Component.translatable("gui.aether_ii.guidebook.equipment.pouch.tooltip.description", data.getAmount()).withStyle(AetherIIItems.CURRENCY_NAME_COLOR));
            guiGraphics.renderComponentTooltip(this.font, components, mouseX, mouseY);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        this.renderGuidebookSpread(this, guiGraphics, mouseX, mouseY, partialTick);
        this.renderEntityPreview(guiGraphics, mouseX, mouseY);
        this.renderSaddlebagSlotBackings(guiGraphics);
    }

    private void renderEntityPreview(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int leftPos = this.leftPos;
        int topPos = this.topPos;
        int width = 124;
        int height = 70;
        int x0 = leftPos - 54;
        int y0 = topPos + 37;
        int x1 = x0 + width;
        int y1 = y0 + height;
        float centerX = (x0 + x1) / 2.0F;
        float centerY = (y0 + y1) / 2.0F;
        float xAngle = (float) Math.atan((centerX - mouseX) / 40.0F);
        float yAngle = (float) Math.atan((centerY - mouseY) / 40.0F);
        LivingEntity entity = this.minecraft != null ? this.minecraft.player : null;
        int size = 30;
        if (this.menu.getMoa() != null) {
            entity = this.menu.getMoa();
            size = 16;
        }
        if (entity != null) {
            renderEntityInInventoryFollowsAngle(guiGraphics, (int) centerX - 22, y1, size, xAngle, yAngle, 180.0F, entity);
            renderEntityInInventoryFollowsAngle(guiGraphics, (int) centerX + 22, y1, size, xAngle, yAngle, 0.0F, entity);
        }
    }

    public static void renderEntityInInventoryFollowsAngle(GuiGraphics guiGraphics, int x, int y, int size, float xAngle, float yAngle, float entityRotation, LivingEntity entity) {
        Quaternionf rotation = new Quaternionf().rotateZ(Mth.PI);
        Quaternionf xRotation = new Quaternionf().rotateX(yAngle * 20.0F * Mth.DEG_TO_RAD);
        rotation.mul(xRotation);

        float yBodyRot = entity.yBodyRot;
        float yRot = entity.getYRot();
        float xRot = entity.getXRot();
        float yHeadRotO = entity.yHeadRotO;
        float yHeadRot = entity.yHeadRot;
        try {
            entity.yBodyRot = entityRotation + xAngle * 20.0F;
            entity.setYRot(entityRotation + xAngle * 40.0F);
            entity.setXRot(-yAngle * 20.0F);
            entity.yHeadRot = entity.getYRot();
            entity.yHeadRotO = entity.getYRot();
            InventoryScreen.renderEntityInInventory(guiGraphics, x, y, size, rotation, xRotation, entity);
        } finally {
            entity.yBodyRot = yBodyRot;
            entity.setYRot(yRot);
            entity.setXRot(xRot);
            entity.yHeadRotO = yHeadRotO;
            entity.yHeadRot = yHeadRot;
        }
    }

    private void renderSaddlebagSlotBackings(GuiGraphics guiGraphics) {
        for (Slot slot : this.menu.slots) {
            if (slot instanceof SaddlebagSlot saddlebagSlot && saddlebagSlot.isActive()) {
                ((SlotAccessor) slot).aether$setX(saddlebagSlot.originalX + this.calculateSlotOffset());
                AetherIIGuiGraphics.blitSprite(guiGraphics, Guidebook.SLOT_SPRITE, this.leftPos + saddlebagSlot.x - 1, this.topPos + saddlebagSlot.y - 1, 18, 18);
            }
        }
    }

    private int calculateSlotOffset() {
        if (this.menu.getMoa() != null) {
            int rowSize = this.menu.getMoa().getSaddlebagRowSize();
            return (8 - rowSize) * 9;
        }
        return 0;
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int xOffset = Minecraft.getInstance().player != null && Minecraft.getInstance().player.isCreative() ? 19 : 0;
        guiGraphics.drawString(this.font, this.craftingTitle, this.craftingTitleLabelX + xOffset, this.craftingTitleLabelY, 4210752, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }

    @Override
    public void renderGuidebookLeftPage(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Guidebook.super.renderGuidebookLeftPage(screen, guiGraphics, mouseX, mouseY, partialTick);
        this.renderStats(guiGraphics);
        guiGraphics.drawCenteredString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0xffffffff);
    }

    private void renderStats(GuiGraphics guiGraphics) {
        if (this.menu.getMoa() != null) {
            int x = 49;
            int y = 94;
            AetherIIGuiGraphics.blitSprite(guiGraphics, Guidebook.HEARTS_SPRITE, x, y, 16, 16);
            guiGraphics.drawString(this.font, Component.literal(Mth.ceil(this.menu.getMoa().getHealth()) + "/" + Mth.ceil(this.menu.getMoa().getMaxHealth())), x + 18, y + 4, 0xffffffff, true);
            AetherIIGuiGraphics.blitSprite(guiGraphics, Guidebook.ARMOR_SPRITE, x + 54, y, 16, 16);
            guiGraphics.drawString(this.font, Component.literal(this.menu.getMoa().getArmorValue() + "/20"), x + 72, y + 4, 0xffffffff, true);
        } else if (Minecraft.getInstance().player != null) {
            Player player = Minecraft.getInstance().player;
            int x = 49;
            int y = 112;
            AetherIIGuiGraphics.blitSprite(guiGraphics, Guidebook.HEARTS_SPRITE, x, y, 16, 16);
            guiGraphics.drawString(this.font, Component.literal(Mth.ceil(player.getHealth()) + "/" + Mth.ceil(player.getMaxHealth())), x + 18, y + 4, 0xffffffff, true);
            AetherIIGuiGraphics.blitSprite(guiGraphics, Guidebook.ARMOR_SPRITE, x + 54, y, 16, 16);
            guiGraphics.drawString(this.font, Component.literal(player.getArmorValue() + "/20"), x + 72, y + 4, 0xffffffff, true);
        }
    }

    @Override
    protected void slotClicked(@Nullable Slot slot, int slotId, int mouseButton, ClickType type) {
        if (this.minecraft != null && this.minecraft.player != null && this.minecraft.gameMode != null) {
            boolean quickMove = type == ClickType.QUICK_MOVE;
            if (slot != null || type == ClickType.QUICK_CRAFT) {
                if (slot == null || slot.mayPickup(this.minecraft.player)) {
                    if (slot == this.destroyItemSlot && quickMove) {
                        ClientPacketDistributor.sendToServer(new ClearAccessoriesPacket());
                        for (int j = 0; j < this.minecraft.player.inventoryMenu.getItems().size(); ++j) {
                            this.minecraft.gameMode.handleCreativeModeItemAdd(ItemStack.EMPTY, j);
                        }
                        return;
                    } else if (slot == this.destroyItemSlot) {
                        this.menu.setCarried(ItemStack.EMPTY);
                        ClientPacketDistributor.sendToServer(new ClearItemPacket());
                        return;
                    }
                }
            }
            if (slot == this.currencySlot && Minecraft.getInstance().player != null) {
                var data = AetherIIDataAttachments.get(Minecraft.getInstance().player, AetherIIDataAttachments.CURRENCY);
                if (type == ClickType.PICKUP || type == ClickType.QUICK_CRAFT) {
                    if (type == ClickType.QUICK_CRAFT) {
                        if (mouseButton == 5) {
                            mouseButton = 1;
                        } else if (mouseButton == 1) {
                            mouseButton = 0;
                        }
                    }
                    if (this.menu.getCarried().isEmpty()) {
                        if (data.getAmount() > 0) {
                            ItemStack stack = new ItemStack(AetherIIItems.GLINT_COIN.get());
                            int amount = 0;
                            if (mouseButton == 0) {
                                amount = Math.min(64, data.getAmount());
                            } else if (mouseButton == 1) {
                                amount = data.getAmount() >= 64 ? 32 : data.getAmount() / 2;
                            }
                            if (amount > 0) {
                                stack.setCount(amount);
                                ClientPacketDistributor.sendToServer(new CurrencyAmountPacket(data.getAmount() - amount));
                                this.menu.setCarried(stack.copy());
                                ClientPacketDistributor.sendToServer(new HeldCurrencyPacket(stack.copy()));
                                return;
                            }
                        }
                    } else if (this.menu.getCarried().getItem() instanceof CurrencyItem currencyItem) {
                        ItemStack stack = this.menu.getCarried().copy();
                        int amount = 0;
                        if (mouseButton == 0) {
                            amount = stack.getCount();
                        } else if (mouseButton == 1) {
                            amount = 1;
                        }
                        if (amount > 0) {
                            stack.shrink(amount);
                            ClientPacketDistributor.sendToServer(new CurrencyAmountPacket(data.getAmount() + amount * currencyItem.getCurrencyAmount()));
                            this.menu.setCarried(stack);
                            ClientPacketDistributor.sendToServer(new HeldCurrencyPacket(stack));
                            return;
                        }
                    }
                }
            }
            super.slotClicked(slot, slotId, mouseButton, type);
        }
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int button) {
        for (Renderable renderable : this.renderables) {
            if (renderable instanceof GuidebookTab guidebookTab && guidebookTab.isMouseOver(mouseX, mouseY)) {
                return false;
            }
        }
        return super.hasClickedOutside(mouseX, mouseY, guiLeft, guiTop, button);
    }

    @Override
    public ResourceLocation getLeftPageTexture() {
        if (this.menu.getMoa() != null) {
            return GUIDEBOOK_EQUIPMENT_LEFT_PAGE_MOA_LOCATION;
        }
        return GUIDEBOOK_EQUIPMENT_LEFT_PAGE_PLAYER_LOCATION;
    }

    @Override
    public ResourceLocation getRightPageTexture() {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.isCreative()) {
            return GUIDEBOOK_EQUIPMENT_RIGHT_PAGE_CREATIVE_LOCATION;
        }
        return GUIDEBOOK_EQUIPMENT_RIGHT_PAGE_LOCATION;
    }

    @Override
    public GuidebookEquipmentMenu getEquipmentMenu() {
        return this.menu;
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
    public void switchTab() {
        this.menu.slots.remove(this.destroyItemSlot);
        this.menu.slots.remove(this.currencySlot);
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
