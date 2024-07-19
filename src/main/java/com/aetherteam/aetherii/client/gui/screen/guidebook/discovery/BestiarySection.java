package com.aetherteam.aetherii.client.gui.screen.guidebook.discovery;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.client.gui.screen.guidebook.Guidebook;
import com.aetherteam.aetherii.client.gui.screen.guidebook.GuidebookDiscoveryScreen;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBestiaryEntries;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BestiarySection extends DiscoverySection<BestiaryEntry> {
    private static final ResourceLocation GUIDEBOOK_DISCOVERY_RIGHT_PAGE_BESTIARY_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/discovery/guidebook_discovery_right_bestiary.png");
    private static final WidgetSprites SCROLL_WIDGET = new WidgetSprites(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/scroller"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/scroller_selected"));
    private List<Float> snapPoints;
    private boolean scrolling;
    private float scrollY;
    private float rotation = 0.0F;

    public BestiarySection(RegistryAccess registryAccess, GuidebookDiscoveryScreen screen, Component title) {
        super(registryAccess, AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY, screen, title);
    }

    @Override
    public void initSection() {
        super.initSection();
        this.snapPoints = new ArrayList<>();
        int remainingSlots = Mth.ceil((this.entries.size() - this.maxSlots()) / (double) this.scrollIncrement());
        for (int y = 0; y <= remainingSlots; y++) {
            this.snapPoints.add((this.scrollbarGutterHeight() / remainingSlots) * y);
        }
        this.rotation = 0.0F;
    }

    @Override
    public void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int rightPagePos = (this.screen.width / 2);
        int topPos = (this.screen.height - Guidebook.BACKING_HEIGHT) / 2;
        if (this.getSelectedEntry() != null) {
            Level level = Minecraft.getInstance().level;
            if (level != null) {
                Entity entity = this.getSelectedEntry().entityType().value().create(level);
                if (entity instanceof LivingEntity livingEntity) {
                    int x = 24;
                    int y = 28;
                    int width = 125;
                    int height = 69;
                    this.rotation = Mth.wrapDegrees(Mth.lerp(partialTick, this.rotation, this.rotation + 1.5F));
                    int scale = (int) (30 / livingEntity.getBoundingBox().getSize());
                    this.renderRotatingEntity(guiGraphics, rightPagePos + x, topPos + y, rightPagePos + x + width, topPos + y + height, scale, 0.2225F, this.rotation, -15.0F, livingEntity);
                }
            }
        }
        this.renderSlotTooltips(guiGraphics, mouseX, mouseY);
    }

    public void renderRotatingEntity(GuiGraphics guiGraphics, int startX, int startY, int endX, int endY, int scale, float yOffset, float angleXComponent, float angleYComponent, LivingEntity livingEntity) {
        float posX = (float) (startX + endX) / 2.0F;
        float posY = (float) (startY + endY) / 2.0F;
        guiGraphics.enableScissor(startX, startY, endX, endY);
        Quaternionf xQuaternion = new Quaternionf().rotateZ(Mth.PI);
        Quaternionf zQuaternion = new Quaternionf().rotateX(angleYComponent * Mth.DEG_TO_RAD);
        xQuaternion.mul(zQuaternion);
        float yBodyRot = livingEntity.yBodyRot;
        float yRot = livingEntity.getYRot();
        float xRot = livingEntity.getXRot();
        livingEntity.setYBodyRot(180.0F + angleXComponent);
        livingEntity.setYRot(180.0F + angleXComponent);
        livingEntity.setXRot(-angleYComponent);
        livingEntity.setYHeadRot(livingEntity.getYRot());
        livingEntity.yHeadRotO = livingEntity.getYRot();
        Vector3f vector3f = new Vector3f(0.0F, livingEntity.getBbHeight() / 2.0F + yOffset, 0.0F);

        InventoryScreen.renderEntityInInventory(guiGraphics, posX, posY, scale, vector3f, xQuaternion, zQuaternion, livingEntity);
        livingEntity.setYBodyRot(yBodyRot);
        livingEntity.setYRot(yRot);
        livingEntity.setXRot(xRot);
        guiGraphics.disableScissor();
    }

    @Override
    public void renderEntries(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderEntries(guiGraphics, mouseX, mouseY, partialTick);
        BestiaryEntry hoveredEntry = this.getEntryFromSlot(mouseX, mouseY);
        int leftPos = 30;
        int topPos = 60;
        int i = 0;
        List<BestiaryEntry> visibleEntries = this.entries.size() > this.maxSlots() ? this.entries.subList(Math.max(0, this.getSlotOffset()), Math.min(this.getSlotOffset() + this.maxSlots(), this.entries.size())) : this.entries;
        for (BestiaryEntry entry : visibleEntries) {
            ResourceLocation sprite = entry.icon(); //todo undiscovered;
            int x = i % 6;
            int y = i / 6;
            int slotX = leftPos + (x * 18);
            int slotY = topPos + (y * 18);
            guiGraphics.blitSprite(sprite, slotX, slotY, 16, 16);

            boolean isHovered = hoveredEntry != null && entry.entityType() == hoveredEntry.entityType();
            boolean isSelected = this.selectedEntry != null && entry.entityType() == this.selectedEntry.entityType();

            if (isHovered || isSelected) {
                guiGraphics.fillGradient(RenderType.guiOverlay(), slotX + 1, slotY, slotX + 17, slotY + 16, -2130706433, -2130706433, 0);
            }
            i++;
        }
        this.renderScrollbar(guiGraphics);
    }

    private void renderScrollbar(GuiGraphics guiGraphics) {
        int scrollbarTop = 59;
        int scrollbarLeft = 140;
        ResourceLocation location = SCROLL_WIDGET.get(this.isScrollActive(), this.scrolling);
        guiGraphics.blitSprite(location, scrollbarLeft , (int) (scrollbarTop + this.scrollY), 6, 9); // Render scrollbar.
    }

    private void renderSlotTooltips(GuiGraphics guiGraphics, double mouseX, double mouseY) {
        BestiaryEntry entry = this.getEntryFromSlot(mouseX, mouseY);
        if (entry != null) {
            Component name = Component.translatable(entry.entityType().value().getDescriptionId());
            guiGraphics.renderTooltip(Minecraft.getInstance().font, name, (int) mouseX, (int) mouseY);
        }
    }

    @Override
    public void renderInformation(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.getSelectedEntry() != null) {
            guiGraphics.drawCenteredString(this.screen.getMinecraft().font, Component.translatable(this.getSelectedEntry().entityType().value().getDescriptionId()), 88, 13, 16777215);
        }
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY, boolean original) {
        if (this.isScrollActive()) {
            int leftPos = (this.screen.width / 2) - Guidebook.PAGE_WIDTH;
            int topPos = (this.screen.height - Guidebook.BACKING_HEIGHT) / 2;
            if (button == 0) {
                float scrollbarGutterLeft = leftPos + 140.0F;
                float scrollbarGutterTop = topPos + 59.0F;
                double mouseXDiff = mouseX - scrollbarGutterLeft;
                double mouseYDiff = mouseY - scrollbarGutterTop;
                if (mouseYDiff <= 108 && mouseYDiff >= 0 && ((mouseXDiff <= 6 && mouseXDiff >= 0) || this.scrolling)) {
                    this.scrolling = true; // Set the scrollbar as currently scrolling.
                    this.scrollY = Math.max(0, Math.min((float) mouseYDiff - (this.scrollbarHeight() / 2.0F), this.scrollbarGutterHeight())); // Set the offset for where to render the scrollbar.
                    return true;
                }
            }
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY, original);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY, boolean original) {
        if (this.isScrollActive()) {
            int i = 0;
            int index = this.getSlotOffset() / this.scrollIncrement();
            if (index != -1) {
                i = index;
            }
            if (scrollY < 0) {
                i = Math.min(i + 1, this.snapPoints.size() - 1);
            } else if (scrollY > 0) {
                i = Math.max(i - 1, 0);
            }
            this.scrollY = this.snapPoints.get(i); // Set the scrollbar offset to a specified snapping point position.
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY, original);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button, boolean original) { //todo
        BestiaryEntry entry = this.getEntryFromSlot(mouseX, mouseY);
        if (entry != null) {
            this.selectedEntry = entry;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button, original);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button, boolean original) {
        this.scrolling = false;
        return super.mouseReleased(mouseX, mouseY, button, original);
    }

    @Nullable
    private BestiaryEntry getEntryFromSlot(double mouseX, double mouseY) {
        int slot = this.getSlotIndex(mouseX, mouseY);
        if (slot != -1) {
            int trueSlot = slot + this.getSlotOffset(); // Determines the true index to get from the list of Moa Skins, if there is a slot offset from scrolling.
            if (trueSlot < this.entries.size()) {
                return this.entries.get(trueSlot);
            }
        }
        return null;
    }

    private int getSlotIndex(double mouseX, double mouseY) {
        int leftPos = (this.screen.width / 2) - Guidebook.PAGE_WIDTH;
        int topPos = (this.screen.height - Guidebook.BACKING_HEIGHT) / 2;
        int slotLeft = leftPos + 30; //todo
        int slotTop = topPos + 59;
        double mouseXDiff = mouseX - slotLeft;
        double mouseYDiff = mouseY - slotTop;
        int slot = ((int) (mouseXDiff / 18)) + (((int) (mouseYDiff / 18)) * 6);
        return mouseYDiff < 108 && mouseYDiff > 0 && mouseXDiff < 108 && mouseXDiff > 0 ? slot : -1;
    }

    private int getSlotOffset() {
        int offset = 0;
        int index = this.snapPoints.indexOf(this.scrollY);
        if (index != -1) {
            offset = index;
        } else {
            for (int i = 0; i < this.snapPoints.size() - 1; i++) {
                float currentPoint = this.snapPoints.get(i);
                float nextPoint = this.snapPoints.get(i + 1);
                float midway = currentPoint + ((nextPoint - currentPoint) / 2.0F);
                if (this.scrollY > midway && this.scrollY < nextPoint) { // Closer to nextPoint.
                    offset = i + 1;
                } else if (this.scrollY <= midway && this.scrollY > currentPoint) { // Closer to currentPoint.
                    offset = i;
                }
            }
        }
        return offset * this.scrollIncrement();
    }

    private boolean isScrollActive() {
        return this.entries.size() > this.maxSlots();
    }

    private int scrollIncrement() {
        return 6;
    }

    private float scrollbarHeight() {
        return 9.0F;
    }

    private float scrollbarGutterHeight() {
        return 108 - this.scrollbarHeight();
    }

    private int maxSlots() {
        return 36;
    }

    @Override
    public ResourceLocation getRightPageTexture() {
        return GUIDEBOOK_DISCOVERY_RIGHT_PAGE_BESTIARY_LOCATION;
    }
}
