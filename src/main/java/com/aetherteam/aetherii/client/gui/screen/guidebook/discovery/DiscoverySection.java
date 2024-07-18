package com.aetherteam.aetherii.client.gui.screen.guidebook.discovery;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.screen.guidebook.Guidebook;
import com.aetherteam.aetherii.client.gui.screen.guidebook.GuidebookDiscoveryScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public abstract class DiscoverySection<T> {
    private static final ResourceLocation GUIDEBOOK_DISCOVERY_RIGHT_PAGE_GENERAL_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/discovery/guidebook_discovery_right_general.png");
    protected final RegistryAccess registryAccess;
    protected final ResourceKey<Registry<T>> registryKey;
    protected final GuidebookDiscoveryScreen screen;
    protected final Component title;
    protected final List<T> entries = new ArrayList<>();
    protected T selectedEntry;

    public DiscoverySection(RegistryAccess registryAccess, ResourceKey<Registry<T>> registryKey, GuidebookDiscoveryScreen screen, Component title) {
        this.registryAccess = registryAccess;
        this.registryKey = registryKey;
        this.screen = screen;
        this.title = title;
    }

    public void initSection() {
        this.constructEntries();
        this.selectedEntry = null;
    }

    public void renderEntries(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int x = (this.screen.width - Guidebook.PAGE_WIDTH) / 2;
        int y = (this.screen.height - Guidebook.PAGE_HEIGHT) / 2;
        guiGraphics.drawString(this.screen.getMinecraft().font, this.getTitle(), x - 60, y + 42, 16777215, true);
    }

    public abstract void renderInformation(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);

    protected void constructEntries() {
        this.entries.clear();
        this.registryAccess.registryOrThrow(this.registryKey).iterator().forEachRemaining(this.entries::add);
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

    public ResourceLocation getRightPageTexture() {
        return GUIDEBOOK_DISCOVERY_RIGHT_PAGE_GENERAL_LOCATION;
    }

    public abstract class DiscoveryEntrySlot extends ImageButton {
        private final T entry;

        public DiscoveryEntrySlot(T entry, int x, int y, int width, int height, WidgetSprites sprites, OnPress onPress, Component message) {
            super(x, y, width, height, sprites, onPress, message);
            this.entry = entry;
        }

        public T getEntry() {
            return this.entry;
        }

        @Override
        public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
            if (this.isHovered() || this.isSelected()) {
                guiGraphics.fillGradient(RenderType.guiOverlay(), this.getX() + 1, this.getY(), this.getX() + 17, this.getY() + 16, -2130706433, -2130706433, 0);
            }
        }

        public abstract boolean isSelected();
    }
}
