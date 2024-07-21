package com.aetherteam.aetherii.client.gui.screen.guidebook.discovery;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.GuidebookDiscoveryAttachment;
import com.aetherteam.aetherii.client.gui.screen.guidebook.Guidebook;
import com.aetherteam.aetherii.client.gui.screen.guidebook.GuidebookDiscoveryScreen;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBestiaryEntries;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageResistances;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.GuiSpriteManager;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BestiarySection extends DiscoverySection<BestiaryEntry> { //todo make entry ordering logical not alphabetical
    private static final ResourceLocation GUIDEBOOK_DISCOVERY_RIGHT_PAGE_BESTIARY_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/discovery/guidebook_discovery_right_bestiary.png");
    private static final ResourceLocation SLASH_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/stats/slash");
    private static final ResourceLocation IMPACT_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/stats/impact");
    private static final ResourceLocation PIERCE_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/stats/pierce");
    private static final ResourceLocation UNDISCOVERED_ENTRY_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/undiscovered");
    private static final ResourceLocation DISCOVERED_ENTRY_FALLBACK_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/bestiary/default");
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
        int leftPos = 31;
        int topPos = 60;
        int i = 0;
        List<BestiaryEntry> visibleEntries = this.entries.size() > this.maxSlots() ? this.entries.subList(Math.max(0, this.getSlotOffset()), Math.min(this.getSlotOffset() + this.maxSlots(), this.entries.size())) : this.entries;
        for (BestiaryEntry entry : visibleEntries) {
            GuiSpriteManager guiSpriteManager = Minecraft.getInstance().getGuiSprites();

            ResourceLocation sprite;
            if (this.isObserved(entry)) {
                sprite = entry.icon();
                if (guiSpriteManager.getSprite(sprite).equals(guiSpriteManager.getSprite(MissingTextureAtlasSprite.getLocation()))) {
                    sprite = DISCOVERED_ENTRY_FALLBACK_SPRITE;
                }
            } else {
                sprite = UNDISCOVERED_ENTRY_SPRITE;
            }

            int x = i % 6;
            int y = i / 6;
            int slotX = leftPos + (x * 18);
            int slotY = topPos + (y * 18);
            guiGraphics.blitSprite(sprite, slotX, slotY, 16, 16);

            boolean isHovered = hoveredEntry != null && entry.entityType() == hoveredEntry.entityType();
            boolean isSelected = this.selectedEntry != null && entry.entityType() == this.selectedEntry.entityType();

            if (isHovered || isSelected) {
                guiGraphics.fillGradient(RenderType.guiOverlay(), slotX, slotY, slotX + 16, slotY + 16, -2130706433, -2130706433, 0);
            }
            i++;
        }
        this.renderScrollbar(guiGraphics);
        this.renderSlotTooltips(guiGraphics, mouseX, mouseY);
    }

    private void renderScrollbar(GuiGraphics guiGraphics) {
        int scrollbarTop = 59;
        int scrollbarLeft = 140;
        ResourceLocation location = Guidebook.SCROLLER.get(this.isScrollActive(), this.scrolling);
        guiGraphics.blitSprite(location, scrollbarLeft, (int) (scrollbarTop + this.scrollY), 6, 9); // Render scrollbar.
    }

    private void renderSlotTooltips(GuiGraphics guiGraphics, double mouseX, double mouseY) {
        BestiaryEntry entry = this.getEntryFromSlot(mouseX, mouseY);
        if (entry != null) {
            int leftPagePos = (this.screen.width / 2) - Guidebook.PAGE_WIDTH;
            int topPos = (this.screen.height - Guidebook.BACKING_HEIGHT) / 2;
            Component name = Component.translatable("gui.aether_ii.guidebook.discovery.bestiary.entry.unknown");
            if (this.isObserved(entry)) {
                name = Component.translatable(entry.entityType().value().getDescriptionId());
            }
            guiGraphics.renderTooltip(Minecraft.getInstance().font, name, (int) (mouseX - leftPagePos), (int) (mouseY - topPos));
        }
    }

    @Override
    public void renderInformation(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.getSelectedEntry() != null) {
            Level level = Minecraft.getInstance().level;
            Font font = Minecraft.getInstance().font;
            if (level != null) {
                Entity entity = this.getSelectedEntry().entityType().value().create(level);
                if (entity instanceof LivingEntity livingEntity) {
                    guiGraphics.drawCenteredString(font, Component.translatable(this.getSelectedEntry().entityType().value().getDescriptionId()), 88, 13, 16777215);

                    if (this.isUnderstood(this.getSelectedEntry())) {
                        int x = 27;
                        int y = 29;

                        guiGraphics.blitSprite(Guidebook.HEARTS_SPRITE, x, y, 16, 16);
                        String health = String.valueOf((int) livingEntity.getMaxHealth());
                        this.renderIconValue(guiGraphics, x, y, health);
                        this.renderTooltipOverIcon(font, guiGraphics, mouseX, mouseY, x, y, Component.translatable("gui.aether_ii.guidebook.discovery.bestiary.stat.health", health));

                        y += 17;
                        guiGraphics.blitSprite(SLASH_SPRITE, x, y, 16, 16);
                        int slashDefense = (int) AetherIIDamageResistances.getSlashDefense(this.registryAccess, entity);
                        Component slashTooltip = this.getDamageTypeComponent(slashDefense, "slash");
                        this.renderIconValue(guiGraphics, x, y, String.valueOf(-slashDefense));
                        this.renderTooltipOverIcon(font, guiGraphics, mouseX, mouseY, x, y, slashTooltip);

                        y += 17;
                        guiGraphics.blitSprite(IMPACT_SPRITE, x, y, 16, 16);
                        int impactDefense = (int) AetherIIDamageResistances.getImpactDefense(this.registryAccess, entity);
                        Component impactTooltip = this.getDamageTypeComponent(impactDefense, "impact");
                        this.renderIconValue(guiGraphics, x, y, String.valueOf(-impactDefense));
                        this.renderTooltipOverIcon(font, guiGraphics, mouseX, mouseY, x, y,impactTooltip);

                        y += 17;
                        guiGraphics.blitSprite(PIERCE_SPRITE, x, y, 16, 16);
                        int pierceDefense = (int) AetherIIDamageResistances.getPierceDefense(this.registryAccess, entity);
                        Component pierceTooltip = this.getDamageTypeComponent(pierceDefense, "pierce");
                        this.renderIconValue(guiGraphics, x, y, String.valueOf(-pierceDefense));
                        this.renderTooltipOverIcon(font, guiGraphics, mouseX, mouseY, x, y, pierceTooltip);

                        //todo effect resistance render

                        //todo drops display

                        //todo food display
                    }

                    this.drawDescriptionString(guiGraphics, Minecraft.getInstance().font, Component.translatable(this.getSelectedEntry().descriptionKey()));
                }
            }
        }
    }

    private void renderIconValue(GuiGraphics guiGraphics, int x, int y, String value) {
        Font font = Minecraft.getInstance().font;
        guiGraphics.drawString(font, value, x + 19 - 2 - font.width(value), y + 6 + 3, 16777215, true);
    }

    private Component getDamageTypeComponent(int value, String type) {
        Component component;
        Component damageType = Component.translatable("aether_ii." + type);
        if (value > 0) {
            component = Component.translatable("gui.aether_ii.guidebook.discovery.bestiary.stat.damage_resistance", -value, damageType);
        } else if (value < 0) {
            component = Component.translatable("gui.aether_ii.guidebook.discovery.bestiary.stat.damage_weakness", "+" + -value, damageType);
        } else {
            component = Component.translatable("gui.aether_ii.guidebook.discovery.bestiary.stat.damage_none", damageType);
        }
        return component;
    }

    private void renderTooltipOverIcon(Font font, GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y, Component component) { //todo text wrapping
        int rightPagePos = (this.screen.width / 2);
        int topPos = (this.screen.height - Guidebook.BACKING_HEIGHT) / 2;
        double mouseXDiff = (mouseX - rightPagePos) - x;
        double mouseYDiff = (mouseY - topPos) - y;
        if (mouseYDiff <= 15 && mouseYDiff >= 0 && mouseXDiff <= 15 && mouseXDiff >= 0) {
            guiGraphics.renderTooltip(font, component, mouseX - rightPagePos, mouseY - topPos);
        }
    }

    private void drawDescriptionString(GuiGraphics guiGraphics, Font font, Component component) {
        int x = 21;
        int y = 103;
        int width = 140;
        for (FormattedCharSequence formattedcharsequence : font.split(component, width)) {
            guiGraphics.drawString(font, formattedcharsequence, x, y, -1, true);
            y += 9;
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
        if (entry != null && (this.isObserved(entry) || this.isUnderstood(entry))) {
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

    private boolean isObserved(BestiaryEntry entry) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            GuidebookDiscoveryAttachment attachment = player.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY);
            return attachment.getObservedBestiaryEntries().stream().map((holder) -> holder.value().entityType()).anyMatch((e) -> e.value() == entry.entityType().value());
        }
        return false;
    }

    private boolean isUnderstood(BestiaryEntry entry) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            GuidebookDiscoveryAttachment attachment = player.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY);
            return attachment.getUnderstoodBestiaryEntries().stream().map((holder) -> holder.value().entityType()).anyMatch((e) -> e.value() == entry.entityType().value());
        }
        return false;
    }

    @Override
    public ResourceLocation getRightPageTexture() {
        return GUIDEBOOK_DISCOVERY_RIGHT_PAGE_BESTIARY_LOCATION;
    }
}
