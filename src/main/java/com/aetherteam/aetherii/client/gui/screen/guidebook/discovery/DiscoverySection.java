package com.aetherteam.aetherii.client.gui.screen.guidebook.discovery;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.screen.guidebook.GuidebookDiscoveryScreen;
import net.minecraft.client.gui.GuiGraphics;
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
    public T selectedEntry;

    public DiscoverySection(RegistryAccess registryAccess, ResourceKey<Registry<T>> registryKey, GuidebookDiscoveryScreen screen, Component title) {
        this.registryAccess = registryAccess;
        this.registryKey = registryKey;
        this.screen = screen;
        this.title = title;
    }

    public void initSection() {
        this.constructEntries();
    }

    protected void constructEntries() {
        this.entries.clear();
        this.registryAccess.registryOrThrow(this.registryKey).iterator().forEachRemaining(this.entries::add);
    }

    public abstract void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);

    public void renderEntries(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.drawString(this.screen.getMinecraft().font, this.getTitle(), 28, 48, 16777215, true);
    }

    public abstract void renderInformation(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);

    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY, boolean original) {
        return original;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY, boolean original) {
        return original;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button, boolean original) {
        return original;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button, boolean original) {
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

    public boolean areAnyUnchecked() {
        return false;
    }

    public ResourceLocation getRightPageTexture() {
        return GUIDEBOOK_DISCOVERY_RIGHT_PAGE_GENERAL_LOCATION;
    }
}
