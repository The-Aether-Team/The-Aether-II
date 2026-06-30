package com.aetherteam.aetherii.client.gui.screen.guidebook.discovery;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.GuidebookEntry;
import com.aetherteam.aetherii.api.guidebook.MutableEntry;
import com.aetherteam.aetherii.client.gui.screen.guidebook.Guidebook;
import com.aetherteam.aetherii.client.gui.screen.guidebook.GuidebookDiscoveryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import com.aetherteam.aetherii.network.ClientPacketDistributor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class DiscoverySection<S extends GuidebookEntry, T extends MutableEntry> {
    private static final ResourceLocation GUIDEBOOK_DISCOVERY_RIGHT_PAGE_GENERAL_LOCATION = new ResourceLocation(AetherII.MODID, "textures/gui/guidebook/discovery/guidebook_discovery_right_general.png");
    protected final RegistryAccess registryAccess;
    protected final ResourceKey<Registry<S>> registryKey;
    protected final GuidebookDiscoveryScreen screen;
    protected final Component title;
    protected final List<T> entries = new ArrayList<>();
    public T selectedEntry;
    protected List<Float> snapPoints;
    protected boolean scrolling;
    protected float scrollY;

    public DiscoverySection(RegistryAccess registryAccess, ResourceKey<Registry<S>> registryKey, GuidebookDiscoveryScreen screen, Component title) {
        this.registryAccess = registryAccess;
        this.registryKey = registryKey;
        this.screen = screen;
        this.title = title;
    }

    public void initSection() {
        this.snapPoints = new ArrayList<>();

        int remainingSlots = Mth.ceil((this.getOrderedEntries().size() - this.maxSlots()) / (double) this.scrollIncrement());
        for (int y = 0; y <= remainingSlots; y++) {
            this.snapPoints.add((this.scrollbarGutterHeight() / remainingSlots) * y);
        }
    }

    public abstract void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);

    public abstract void renderFoward(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);


    public void renderEntries(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.drawString(this.screen.getMinecraft().font, this.getTitle(), 40, 48, 0xffffffff, true);
    }

    public abstract void renderInformation(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);

    protected void renderScrollbar(GuiGraphics guiGraphics) {
        int scrollbarTop = 59;
        int scrollbarLeft = 151;
        ResourceLocation location = Guidebook.SCROLLER.get(this.isScrollActive(), this.scrolling);
        com.aetherteam.aetherii.client.gui.AetherIIGuiGraphics.blitSprite(guiGraphics, location, scrollbarLeft, (int) (scrollbarTop + this.scrollY), 6, 9); // Render scrollbar.
    }

    protected void renderFakeSlot(GuiGraphics guiGraphics, Font font, List<Component> tooltip, ItemStack stack, double mouseX, double mouseY, int x, int y) {
        int rightPagePos = (this.screen.width / 2);
        int topPos = (this.screen.height - Guidebook.PAGE_HEIGHT) / 2;
        double mouseXDiff = (mouseX - rightPagePos) - x;
        double mouseYDiff = (mouseY - topPos) - y;
        com.aetherteam.aetherii.client.gui.AetherIIGuiGraphics.blitSprite(guiGraphics, Guidebook.SLOT_SPRITE, x, y, 18, 18);
        x += 1;
        y += 1;
        guiGraphics.renderItem(stack, x, y);
        guiGraphics.renderItemDecorations(font, stack, x, y);
        if (mouseYDiff <= 15 && mouseYDiff >= 0 && mouseXDiff <= 15 && mouseXDiff >= 0) {
            guiGraphics.fillGradient(x, y, x + 16, y + 16, -2130706433, -2130706433);
            guiGraphics.renderComponentTooltip(font, tooltip, (int) (mouseX - rightPagePos), (int) (mouseY - topPos));
        }
    }

    protected static <V> boolean sameHolder(Holder<V> first, Holder<V> second) {
        Optional<ResourceKey<V>> firstKey = first.unwrapKey();
        Optional<ResourceKey<V>> secondKey = second.unwrapKey();
        if (firstKey.isPresent() && secondKey.isPresent()) {
            return firstKey.get().equals(secondKey.get());
        }
        return first.value() == second.value();
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY, boolean original) {
        if (this.isScrollActive()) {
            int leftPos = (this.screen.width / 2) - Guidebook.PAGE_WIDTH;
            int topPos = (this.screen.height - Guidebook.PAGE_HEIGHT) / 2;
            if (button == 0) {
                float scrollbarGutterLeft = leftPos + 139.0F;
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
        return original;
    }

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
        return original;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button, boolean original) {
        return original;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button, boolean original) {
        this.scrolling = false;
        return original;
    }

    public Component getTitle() {
        return this.title;
    }

    public List<T> getEntries() {
        return this.entries;
    }

    public void setSelectedEntry(T selectedEntry) {
        this.selectedEntry = selectedEntry;
    }

    public T getSelectedEntry() {
        return this.selectedEntry;
    }

    @Nullable
    protected T getEntryFromSlot(double mouseX, double mouseY) {
        int slot = this.getSlotIndex(mouseX, mouseY);
        if (slot != -1) {
            int trueSlot = slot + this.getSlotOffset(); // Determines the true index to get from the list of Moa Skins, if there is a slot offset from scrolling.
            if (trueSlot < this.getOrderedEntries().size()) {
                return this.getOrderedEntries().get(trueSlot);
            }
        }
        return null;
    }

    protected int getSlotIndex(double mouseX, double mouseY) {
        int leftPos = ((this.screen.width + 2) / 2) - Guidebook.PAGE_WIDTH;
        int topPos = (this.screen.height - Guidebook.PAGE_HEIGHT) / 2;
        int slotLeft = leftPos + 42;
        int slotTop = topPos + 59;
        double mouseXDiff = mouseX - slotLeft;
        double mouseYDiff = mouseY - slotTop;
        int slot = ((int) (mouseXDiff / 18)) + (((int) (mouseYDiff / 18)) * 6);
        return mouseYDiff < 108 && mouseYDiff > 0 && mouseXDiff < 108 && mouseXDiff > 0 ? slot : -1;
    }

    protected int getSlotOffset() {
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

    protected int scrollIncrement() {
        return 6;
    }

    protected float scrollbarHeight() {
        return 9.0F;
    }

    protected float scrollbarGutterHeight() {
        return 106 - this.scrollbarHeight();
    }

    protected int maxSlots() {
        return 36;
    }

    protected boolean isScrollActive() {
        return this.getOrderedEntries().size() > this.maxSlots();
    }

    public boolean isUnlocked(T entry, String value) {
        if (entry.getClientValues().containsKey(value)) {
            return entry.getClientValues().get(value).isVisible();
        } else {
            return false;
        }
    }

    public boolean isViewed(T entry) {
        for (Map.Entry<String, GuidebookEntry.Info> values : entry.getClientValues().entrySet()) {
            if (values.getValue().isVisible()) {
                if (!values.getValue().isViewed()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void updateViewed(T entry) {
        for (Map.Entry<String, GuidebookEntry.Info> values : entry.getClientValues().entrySet()) {
            if (values.getValue().isVisible()) {
                values.getValue().view();
            }
        }
        ClientPacketDistributor.sendToServer(this.getViewedPacket(entry));
    }

    public boolean areAnyUnchecked() {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            return this.getOrderedEntries().stream().anyMatch((entry) -> entry.getClientValues().values().stream().anyMatch((info) -> info.isVisible() && !info.isViewed()));
        }
        return false;
    }

    public boolean areAnyUnlocked(T entry) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            return entry.getClientValues().values().stream().anyMatch(GuidebookEntry.Info::isVisible);
        }
        return false;
    }

    protected abstract AetherPacketPayload getViewedPacket(T entry);

    protected abstract List<T> getOrderedEntries();

    public ResourceLocation getRightPageTexture() {
        return GUIDEBOOK_DISCOVERY_RIGHT_PAGE_GENERAL_LOCATION;
    }
}


