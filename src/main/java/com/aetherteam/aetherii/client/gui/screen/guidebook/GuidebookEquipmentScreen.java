package com.aetherteam.aetherii.client.gui.screen.guidebook;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.gui.component.guidebook.GuidebookTab;
import com.aetherteam.aetherii.inventory.menu.GuidebookEquipmentMenu;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.network.packet.serverbound.ClearItemPacket;
import com.aetherteam.aetherii.network.packet.serverbound.HeldCurrencyPacket;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.mojang.datafixers.util.Pair;
import io.wispforest.accessories.AccessoriesInternals;
import io.wispforest.accessories.networking.server.NukeAccessories;
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
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Quaternionf;
import org.joml.Vector2i;
import org.joml.Vector3f;

import javax.annotation.Nullable;

public class GuidebookEquipmentScreen extends AbstractContainerScreen<GuidebookEquipmentMenu> implements Guidebook {
    private static final ResourceLocation GUIDEBOOK_EQUIPMENT_LEFT_PAGE_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/equipment/guidebook_equipment_left.png");
    private static final ResourceLocation GUIDEBOOK_EQUIPMENT_RIGHT_PAGE_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/equipment/guidebook_equipment_right.png");
    private static final ResourceLocation GUIDEBOOK_EQUIPMENT_RIGHT_PAGE_CREATIVE_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/equipment/guidebook_equipment_right_creative.png");
    private static final SimpleContainer DESTROY_ITEM_CONTAINER = new SimpleContainer(1);
    private static final SimpleContainer CURRENCY_CONTAINER = new SimpleContainer(1);

    private final Inventory playerInventory;
    private float xMouse;
    private float yMouse;
    private Component craftingTitle = Component.translatable("container.crafting");
    protected int craftingTitleLabelX;
    protected int craftingTitleLabelY;
    @Nullable
    private Slot destroyItemSlot;
    private Slot currencySlot;
    private int nukeCoolDown = 0;

    public GuidebookEquipmentScreen(GuidebookEquipmentMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.playerInventory = playerInventory;
        this.titleLabelX = -24;
        this.titleLabelY = -3;
        this.craftingTitleLabelX = 139;
        this.craftingTitleLabelY = 12;
        this.inventoryLabelX = 96;
        this.inventoryLabelY = 64;
    }

    @Override
    protected void init() {
        super.init();
        this.initTabs(this);
    }

    @Override
    protected void containerTick() {
        if (this.nukeCoolDown > 0) {
            this.nukeCoolDown--;
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        if (this.getMinecraft().player != null) {
            if (this.getMinecraft().player.isCreative() && this.destroyItemSlot == null) {
                this.destroyItemSlot = new Slot(DESTROY_ITEM_CONTAINER, 0, 121, 34);
                this.getMenu().slots.add(this.destroyItemSlot);
            } else if (!this.getMinecraft().player.isCreative() && this.destroyItemSlot != null) {
                this.getMenu().slots.remove(this.destroyItemSlot);
                this.destroyItemSlot = null;
            }
        }
        if (this.currencySlot == null) {
            this.currencySlot = new Slot(CURRENCY_CONTAINER, 0, 57, 87) {
                @Override
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    if (Minecraft.getInstance().player != null) {
                        var data = Minecraft.getInstance().player.getData(AetherIIDataAttachments.CURRENCY);
                        if (data.getAmount() > 0) {
                            return Pair.of(InventoryMenu.BLOCK_ATLAS, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "item/miscellaneous/glint_coin"));
                        }
                    }
                    return Pair.of(InventoryMenu.BLOCK_ATLAS, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "gui/slot/slot_currency"));
                }
            };
            this.getMenu().slots.add(this.currencySlot);
        }

        if (this.destroyItemSlot != null && this.isHovering(this.destroyItemSlot.x, this.destroyItemSlot.y, 16, 16, mouseX, mouseY)) {
            guiGraphics.renderTooltip(this.font, Component.translatable("inventory.binSlot"), mouseX, mouseY);
        }

        if (this.currencySlot != null) {
            if (Minecraft.getInstance().player != null) {
                var data = Minecraft.getInstance().player.getData(AetherIIDataAttachments.CURRENCY);
                if (data.getAmount() > 0) {
                    if (this.isHovering(this.currencySlot.x, this.currencySlot.y, 16, 16, mouseX, mouseY)) {
                        guiGraphics.renderTooltip(this.font, Component.literal(data.getAmount() + " Glint"), mouseX, mouseY);
                    }
                }
            }
        }

        this.renderTooltip(guiGraphics, mouseX, mouseY);

        this.xMouse = (float) mouseX;
        this.yMouse = (float) mouseY;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        this.renderGuidebookSpread(this, guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int xOffset = Minecraft.getInstance().player != null && Minecraft.getInstance().player.isCreative() ? 19 : 0;
        guiGraphics.drawString(this.font, this.craftingTitle, this.craftingTitleLabelX + xOffset, this.craftingTitleLabelY, 4210752, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 16777215, true);
    }

    @Override
    public void renderGuidebookLeftPage(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Guidebook.super.renderGuidebookLeftPage(screen, guiGraphics, mouseX, mouseY, partialTick);
        this.renderStats(guiGraphics);
        this.renderCurrency(guiGraphics);
        int x = this.leftPos - 49;
        int y = this.topPos + 12;
        Vector2i scissorStart = new Vector2i(x, y);
        Vector2i scissorEnd = new Vector2i(x + 124, y + 70);
        Vector2i size = new Vector2i((int) ((scissorEnd.x - scissorStart.x) / 2.5), scissorEnd.y - scissorStart.y);
        this.renderEntityInInventoryFollowingMouseRotated(guiGraphics, scissorStart, size, scissorStart, scissorEnd, mouseX, mouseY, 0);
        this.renderEntityInInventoryFollowingMouseRotated(guiGraphics, scissorStart.add(size.x, 0), size, scissorStart, scissorEnd, mouseX + 50, mouseY, 180);
    }

    private void renderStats(GuiGraphics guiGraphics) {
        Player player = Minecraft.getInstance().player;
        int x = this.leftPos;
        int y = this.topPos;
        guiGraphics.blitSprite(HEART_CONTAINER_SPRITE, x - 50, y + 91, 9, 9);
        guiGraphics.blitSprite(HEART_SPRITE, x - 50, y + 91, 9, 9);
        guiGraphics.drawString(this.font, Component.literal(String.valueOf((int) (player.getHealth()))), x - 39, y + 92, 16777215, true);
        guiGraphics.blitSprite(ARMOR_SPRITE, x - 25, y + 91, 9, 9);
        guiGraphics.drawString(this.font, Component.literal(String.valueOf(player.getArmorValue())), x - 14, y + 92, 16777215, true);
    }

    private void renderCurrency(GuiGraphics guiGraphics) {

    }

    private void renderEntityInInventoryFollowingMouseRotated(GuiGraphics guiGraphics, Vector2i pos, Vector2i size, Vector2i scissorStart, Vector2i scissorEnd, float mouseX, float mouseY, float rotation) {
        int scale = 30;
        float yOffset = 0.0625F;
        Player entity = this.minecraft.player;
        float f = (float) (pos.x + pos.x + size.x) / 2.0F;
        float g = (float) (pos.y + pos.y + size.y) / 2.0F;
        guiGraphics.enableScissor(scissorStart.x, scissorStart.y, scissorEnd.x, scissorEnd.y);
        float h = (float) Math.atan(((scissorStart.x + scissorStart.x + size.x) / 2.0F - mouseX) / 40.0F);
        float i = (float) Math.atan(((scissorStart.y + scissorStart.y + size.y) / 2.0F - mouseY) / 40.0F);
        Quaternionf quaternionf = new Quaternionf().rotateZ(Mth.PI).rotateY(rotation * Mth.DEG_TO_RAD);
        Quaternionf quaternionf2 = new Quaternionf().rotateX(i * 20.0F * 0.017453292F);
        quaternionf.mul(quaternionf2);
        float j = entity.yBodyRot;
        float k = entity.getYRot();
        float l = entity.getXRot();
        float m = entity.yHeadRotO;
        float n = entity.yHeadRot;
        entity.yBodyRot = 180.0F + h * 30.0F;
        entity.setYRot(180.0F + h * 40.0F);
        entity.setXRot(-i * 20.0F);
        entity.yHeadRot = entity.getYRot();
        entity.yHeadRotO = entity.getYRot();
        Vector3f vector3f = new Vector3f(0.0F, entity.getBbHeight() / 2.0F + yOffset, 0.0F);
        InventoryScreen.renderEntityInInventory(guiGraphics, f, g, scale, vector3f, quaternionf, quaternionf2, entity);
        entity.yBodyRot = j;
        entity.setYRot(k);
        entity.setXRot(l);
        entity.yHeadRotO = m;
        entity.yHeadRot = n;
        guiGraphics.disableScissor();
    }

    @Override
    protected void slotClicked(@Nullable Slot slot, int slotId, int mouseButton, ClickType type) {
        if (this.getMinecraft().player != null && this.getMinecraft().gameMode != null) {
            boolean flag = type == ClickType.QUICK_MOVE;
            if (slot != null || type == ClickType.QUICK_CRAFT) {
                if (slot == null || slot.mayPickup(this.getMinecraft().player)) {
                    if (slot == this.destroyItemSlot && this.destroyItemSlot != null && flag) {
                        for (int j = 0; j < this.getMinecraft().player.inventoryMenu.getItems().size(); ++j) {
                            if (this.nukeCoolDown <= 0) {
                                AccessoriesInternals.getNetworkHandler().sendToServer(new NukeAccessories());
                                this.nukeCoolDown = 10;
                            }
                            this.getMinecraft().gameMode.handleCreativeModeItemAdd(ItemStack.EMPTY, j);
                        }
                    } else {
                        if (slot == this.destroyItemSlot && this.destroyItemSlot != null) {
                            this.getMenu().setCarried(ItemStack.EMPTY);
                            PacketDistributor.sendToServer(new ClearItemPacket());
                        }
                    }
                }
            }
            if (slot != null) {
                if (slot == this.currencySlot) {
                    var data = Minecraft.getInstance().player.getData(AetherIIDataAttachments.CURRENCY);
                    if (type == ClickType.PICKUP) {
                        if (this.getMenu().getCarried().isEmpty() && data.getAmount() > 0 && mouseButton == 1) {
                            ItemStack stack = new ItemStack(AetherIIItems.GLINT_COIN.get());
                            data.setSynched(Minecraft.getInstance().player.getId(), INBTSynchable.Direction.SERVER, "setAmount", data.getAmount() - 1);
                            this.getMenu().setCarried(stack.copy());
                            PacketDistributor.sendToServer(new HeldCurrencyPacket(stack.copy()));
                        } else if (this.getMenu().getCarried().is(AetherIIItems.GLINT_COIN.get()) && mouseButton == 0) {
                            ItemStack stack = this.getMenu().getCarried().copy();
                            stack.shrink(1);
                            data.setSynched(Minecraft.getInstance().player.getId(), INBTSynchable.Direction.SERVER, "setAmount", data.getAmount() + 1);
                            this.getMenu().setCarried(stack);
                            PacketDistributor.sendToServer(new HeldCurrencyPacket(stack));
                        }
                    } else if (type == ClickType.QUICK_MOVE) {
                        if (this.getMenu().getCarried().isEmpty() && data.getAmount() > 0) {
                            ItemStack stack = new ItemStack(AetherIIItems.GLINT_COIN.get());
                            int amount = Math.min(64, data.getAmount());
                            stack.setCount(amount);
                            data.setSynched(Minecraft.getInstance().player.getId(), INBTSynchable.Direction.SERVER, "setAmount", data.getAmount() - amount);
                            this.getMenu().setCarried(stack.copy());
                            PacketDistributor.sendToServer(new HeldCurrencyPacket(stack.copy()));
                        } else if (this.getMenu().getCarried().is(AetherIIItems.GLINT_COIN.get())) {
                            ItemStack stack = this.getMenu().getCarried().copy();
                            int amount = stack.getCount();
                            stack.shrink(amount);
                            data.setSynched(Minecraft.getInstance().player.getId(), INBTSynchable.Direction.SERVER, "setAmount", data.getAmount() + amount);
                            this.getMenu().setCarried(stack);
                            PacketDistributor.sendToServer(new HeldCurrencyPacket(stack));
                        }
                    }
                }
            }
            super.slotClicked(slot, slotId, mouseButton, type);
        }
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
        for (Renderable renderable : this.renderables) {
            if (renderable instanceof GuidebookTab guidebookTab) {
                if (guidebookTab.isMouseOver(mouseX, mouseY)) {
                    return false;
                }
            }
        }
        return super.hasClickedOutside(mouseX, mouseY, guiLeft, guiTop, mouseButton);
    }

    @Override
    public ResourceLocation getLeftPageTexture() {
        return GUIDEBOOK_EQUIPMENT_LEFT_PAGE_LOCATION;
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
    public <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(Screen screen, T widget) {
        return this.addRenderableWidget(widget);
    }
}
