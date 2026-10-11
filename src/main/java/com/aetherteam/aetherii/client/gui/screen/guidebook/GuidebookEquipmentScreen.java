package com.aetherteam.aetherii.client.gui.screen.guidebook;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.gui.component.guidebook.GuidebookTab;
import com.aetherteam.aetherii.client.renderer.entity.state.MoaRenderState;
import com.aetherteam.aetherii.inventory.menu.GuidebookEquipmentMenu;
import com.aetherteam.aetherii.inventory.menu.slot.SaddlebagSlot;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.miscellaneous.CurrencyItem;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.SlotAccessor;
import com.aetherteam.aetherii.network.packet.serverbound.ClearAccessoriesPacket;
import com.aetherteam.aetherii.network.packet.serverbound.ClearItemPacket;
import com.aetherteam.aetherii.network.packet.serverbound.CurrencyAmountPacket;
import com.aetherteam.aetherii.network.packet.serverbound.HeldCurrencyPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GuidebookEquipmentScreen extends AbstractContainerScreen<GuidebookEquipmentMenu> implements Guidebook {
    private static final Identifier GUIDEBOOK_EQUIPMENT_LEFT_PAGE_PLAYER_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/equipment/guidebook_equipment_left_player.png");
    private static final Identifier GUIDEBOOK_EQUIPMENT_LEFT_PAGE_MOA_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/equipment/guidebook_equipment_left_moa.png");
    private static final Identifier GUIDEBOOK_EQUIPMENT_RIGHT_PAGE_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/equipment/guidebook_equipment_right.png");
    private static final Identifier GUIDEBOOK_EQUIPMENT_RIGHT_PAGE_CREATIVE_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/equipment/guidebook_equipment_right_creative.png");
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
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);

        if (this.getMinecraft().player != null) {
            if (this.getMinecraft().player.isCreative() && this.destroyItemSlot == null) {
                this.destroyItemSlot = new Slot(DESTROY_ITEM_CONTAINER, 0, 127, 50);
                this.getMenu().slots.add(this.destroyItemSlot);
            } else if (!this.getMinecraft().player.isCreative() && this.destroyItemSlot != null) {
                this.getMenu().slots.remove(this.destroyItemSlot);
                this.destroyItemSlot = null;
            }
        }

        if (this.destroyItemSlot != null && this.isHovering(this.destroyItemSlot.x, this.destroyItemSlot.y, 16, 16, mouseX, mouseY)) {
            guiGraphics.setTooltipForNextFrame(this.font, Component.translatable("inventory.binSlot"), mouseX, mouseY);
        }

        if (this.currencySlot == null && this.getMenu().getMoa() == null) {
            this.currencySlot = new Slot(CURRENCY_CONTAINER, 0, 64, 112);
            this.getMenu().slots.add(this.currencySlot);
        }
        if (this.currencySlot != null) {
            if (this.getMenu().getMoa() == null) {
                if (Minecraft.getInstance().player != null) {
                    var data = Minecraft.getInstance().player.getData(AetherIIDataAttachments.CURRENCY);
                    if (this.isHovering(this.currencySlot.x, this.currencySlot.y, 16, 16, mouseX, mouseY)) {
                        List<Component> componentList = new ArrayList<>();
                        componentList.add(Component.translatable("gui.aether_ii.guidebook.equipment.pouch.tooltip.title"));
                        componentList.add(Component.translatable("gui.aether_ii.guidebook.equipment.pouch.tooltip.description", data.getAmount()).withStyle(AetherIIItems.CURRENCY_NAME_COLOR));
                        guiGraphics.setComponentTooltipForNextFrame(this.font, componentList, mouseX, mouseY);
                    }
                }
            } else {
                this.getMenu().slots.remove(this.currencySlot);
                this.currencySlot = null;
            }
        }

        this.extractTooltip(guiGraphics, mouseX, mouseY);

        this.xMouse = (float) mouseX;
        this.yMouse = (float) mouseY;
    }

    @Override
    protected void extractSlot(GuiGraphicsExtractor guiGraphics, Slot slot, int p_470717_, int p_470566_) {
        if (slot == this.currencySlot) {
            if (Minecraft.getInstance().player != null) {
                var data = Minecraft.getInstance().player.getData(AetherIIDataAttachments.CURRENCY);
                String text = data.getAmount() > 99 ? "99₊" : String.valueOf(data.getAmount());
                int x = slot.x;
                int y = slot.y;
                guiGraphics.pose().pushMatrix();
                guiGraphics.pose().translate(0.0F, 0.0F);
                guiGraphics.fakeItem(AetherIIItems.GLINT_COIN.toStack(), x, y);
                guiGraphics.itemDecorations(this.font, AetherIIItems.GLINT_COIN.toStack(), x, y, text);
                guiGraphics.pose().popMatrix();
            }
        }
        super.extractSlot(guiGraphics, slot, p_470717_, p_470566_);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        this.renderGuidebookSpread(this, guiGraphics, mouseX, mouseY, partialTick);
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
        LivingEntity entity = this.minecraft.player;
        int size = 30;
        float offsetY = 0.1F;
        if (this.getMenu().getMoa() != null) {
            entity = this.getMenu().getMoa();
            size = 16;
            offsetY = this.getMenu().getMoa().isSitting() ? 0.05F : -0.4F;
        }
        renderEntityInInventoryFollowsAngle(guiGraphics, x0 - 22, y0, x1 - 22, y1, size, offsetY, xAngle, yAngle, 180.0F, entity);
        renderEntityInInventoryFollowsAngle(guiGraphics, x0 + 22, y0, x1 + 22, y1, size, offsetY, xAngle, yAngle, 0.0F, entity);

        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(leftPos, topPos);
        for (Slot slot : this.menu.slots) {
            if (slot instanceof SaddlebagSlot saddlebagSlot && saddlebagSlot.isActive()) {
                ((SlotAccessor) slot).aether$setX(saddlebagSlot.originalX + calculateSlotOffset());
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, Guidebook.SLOT_SPRITE, saddlebagSlot.x - 1, saddlebagSlot.y - 1, 18, 18);
            }
        }
        guiGraphics.pose().popMatrix();
    }

    public static void renderEntityInInventoryFollowsAngle(GuiGraphicsExtractor graphics, int x0, int y0, int x1, int y1, int size, float offsetY, float xAngle, float yAngle, float entityRotation, LivingEntity entity) {
        Quaternionf rotation = new Quaternionf().rotateZ((float) Math.PI);
        Quaternionf xRotation = new Quaternionf().rotateX(yAngle * 20.0F * (float) (Math.PI / 180.0));
        rotation.mul(xRotation);
        EntityRenderState renderState = extractRenderState(entity);
        if (renderState instanceof LivingEntityRenderState livingRenderState) {
            livingRenderState.bodyRot = entityRotation + xAngle * 20.0F;
            livingRenderState.yRot = xAngle * 20.0F;
            if (livingRenderState.pose != Pose.FALL_FLYING) {
                livingRenderState.xRot = -yAngle * 20.0F;
            } else {
                livingRenderState.xRot = 0.0F;
            }

            livingRenderState.boundingBoxWidth = livingRenderState.boundingBoxWidth / livingRenderState.scale;
            livingRenderState.boundingBoxHeight = livingRenderState.boundingBoxHeight / livingRenderState.scale;
            livingRenderState.scale = 1.0F;
        }
        if (renderState instanceof MoaRenderState moaRenderState) {
            moaRenderState.opacity = 1.0F;
        }
        Vector3f translation = new Vector3f(0.0F, renderState.boundingBoxHeight / 2.0F + offsetY, 0.0F);
        graphics.entity(renderState, size, translation, rotation, xRotation, x0, y0, x1, y1);
    }

    private static EntityRenderState extractRenderState(LivingEntity entity) {
        EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        EntityRenderer<? super LivingEntity, ?> renderer = entityRenderDispatcher.getRenderer(entity);
        EntityRenderState renderState = renderer.createRenderState(entity, 1.0F);
        renderState.shadowPieces.clear();
        renderState.outlineColor = 0;
        return renderState;
    }

    private int calculateSlotOffset() {
        if (this.getMenu().getMoa() != null) {
            int rowSize = this.getMenu().getMoa().getSaddlebagRowSize();
            return (8 - rowSize) * 9;
        }
        return 0;
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        int xOffset = Minecraft.getInstance().player != null && Minecraft.getInstance().player.isCreative() ? 19 : 0;
        guiGraphics.text(this.font, this.craftingTitle, this.craftingTitleLabelX + xOffset, this.craftingTitleLabelY, 4210752, false);
        guiGraphics.text(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }

    @Override
    public void renderGuidebookLeftPage(Screen screen, GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        Guidebook.super.renderGuidebookLeftPage(screen, guiGraphics, mouseX, mouseY, partialTick);
        this.renderStats(guiGraphics);
        guiGraphics.centeredText(this.font, this.title, this.titleLabelX, this.titleLabelY, 0xffffffff);
    }

    private void renderStats(GuiGraphicsExtractor guiGraphics) {
        if (this.getMenu().getMoa() != null) {
            int x = 49;
            int y = 94;

            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, Guidebook.HEARTS_SPRITE, x, y, 16, 16);
            guiGraphics.text(this.font, Component.literal(Mth.ceil(this.getMenu().getMoa().getHealth()) + "/" + Mth.ceil(this.getMenu().getMoa().getMaxHealth())), x + 18, y + 4, 0xffffffff, true);

            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, Guidebook.ARMOR_SPRITE, x + 54, y, 16, 16);
            guiGraphics.text(this.font, Component.literal(this.getMenu().getMoa().getArmorValue() + "/20"), x + 72, y + 4, 0xffffffff, true);
        } else {
            Player player = Minecraft.getInstance().player;
            int x = 49;
            int y = 112;

            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, Guidebook.HEARTS_SPRITE, x, y, 16, 16);
            guiGraphics.text(this.font, Component.literal(Mth.ceil(player.getHealth()) + "/" + Mth.ceil(player.getMaxHealth())), x + 18, y + 4, 0xffffffff, true);

            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, Guidebook.ARMOR_SPRITE, x + 54, y, 16, 16);
            guiGraphics.text(this.font, Component.literal(player.getArmorValue() + "/20"), x + 72, y + 4, 0xffffffff, true);
        }
    }

    @Override
    protected void slotClicked(@Nullable Slot slot, int slotId, int mouseButton, ContainerInput type) {
        if (this.getMinecraft().player != null && this.getMinecraft().gameMode != null) {
            boolean flag = type == ContainerInput.QUICK_MOVE;
            if (slot != null || type == ContainerInput.QUICK_CRAFT) {
                if (slot == null || slot.mayPickup(this.getMinecraft().player)) {
                    if (slot == this.destroyItemSlot && this.destroyItemSlot != null && flag) {
                        ClientPacketDistributor.sendToServer(new ClearAccessoriesPacket());
                        for (int j = 0; j < this.getMinecraft().player.inventoryMenu.getItems().size(); ++j) {
                            this.getMinecraft().gameMode.handleCreativeModeItemAdd(ItemStack.EMPTY, j);
                        }
                        return;
                    } else {
                        if (slot == this.destroyItemSlot && this.destroyItemSlot != null) {
                            this.getMenu().setCarried(ItemStack.EMPTY);
                            ClientPacketDistributor.sendToServer(new ClearItemPacket());
                            return;
                        }
                    }
                }
            }
            if (slot != null) {
                if (slot == this.currencySlot) {
                    var data = Minecraft.getInstance().player.getData(AetherIIDataAttachments.CURRENCY);
                    if (type == ContainerInput.PICKUP || type == ContainerInput.QUICK_CRAFT) {
                        if (type == ContainerInput.QUICK_CRAFT) {
                            if (mouseButton == 5) {
                                mouseButton = 1;
                            } else if (mouseButton == 1) {
                                mouseButton = 0;
                            }
                        }
                        if (this.getMenu().getCarried().isEmpty()) {
                            if (data.getAmount() > 0) {
                                ItemStack stack = new ItemStack(AetherIIItems.GLINT_COIN.get());
                                int amount = 0;
                                if (mouseButton == 0) { // pick up stack
                                    amount = Math.min(64, data.getAmount());
                                } else if (mouseButton == 1) { // pick up half a stack
                                    amount = data.getAmount() >= 64 ? 32 : data.getAmount() / 2;
                                }
                                if (amount > 0) {
                                    stack.setCount(amount);
                                    ClientPacketDistributor.sendToServer(new CurrencyAmountPacket(data.getAmount() - amount));

                                    //data.setSynched(Minecraft.getInstance().player.getId(), INBTSynchable.Direction.SERVER, "setAmount", data.getAmount() - amount);
                                    this.getMenu().setCarried(stack.copy());
                                    ClientPacketDistributor.sendToServer(new HeldCurrencyPacket(stack.copy()));
                                    return;
                                }
                            }
                        } else if (this.getMenu().getCarried().getItem() instanceof CurrencyItem currencyItem) {
                            ItemStack stack = this.getMenu().getCarried().copy();
                            int amount = 0;
                            if (mouseButton == 0) { // place carried stack
                                amount = stack.getCount();
                            } else if (mouseButton == 1) { // place single item
                                amount = 1;
                            }
                            if (amount > 0) {
                                stack.shrink(amount);
                                ClientPacketDistributor.sendToServer(new CurrencyAmountPacket(data.getAmount() + (amount * currencyItem.getCurrencyAmount())));

                                //data.setSynched(Minecraft.getInstance().player.getId(), INBTSynchable.Direction.SERVER, "setAmount", data.getAmount() + amount);
                                this.getMenu().setCarried(stack);
                                ClientPacketDistributor.sendToServer(new HeldCurrencyPacket(stack));
                                return;
                            }
                        }
                    }
                }
            }
            super.slotClicked(slot, slotId, mouseButton, type);
        }
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop) {
        for (Renderable renderable : this.renderables) {
            if (renderable instanceof GuidebookTab guidebookTab) {
                if (guidebookTab.isMouseOver(mouseX, mouseY)) {
                    return false;
                }
            }
        }
        return super.hasClickedOutside(mouseX, mouseY, guiLeft, guiTop);
    }

    @Override
    public Identifier getLeftPageTexture() {
        if (this.getMenu().getMoa() != null) {
            return GUIDEBOOK_EQUIPMENT_LEFT_PAGE_MOA_LOCATION;
        } else {
            return GUIDEBOOK_EQUIPMENT_LEFT_PAGE_PLAYER_LOCATION;
        }
    }

    @Override
    public Identifier getRightPageTexture() {
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
        this.getMenu().slots.remove(this.destroyItemSlot);
        this.getMenu().slots.remove(this.currencySlot);
    }

    @Override
    public <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(Screen screen, T widget) {
        return this.addRenderableWidget(widget);
    }
}
