package com.aetherteam.aetherii.client.gui.screen.guidebook.discovery;

import com.aetherteam.aetherii.api.guidebook.ExplorationEntry;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.client.gui.screen.guidebook.GuidebookDiscoveryScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.List;

public class ExplorationSection extends DiscoverySection<ExplorationEntry, ExplorationEntry.Mutable> {
    public ExplorationSection(RegistryAccess registryAccess, GuidebookDiscoveryScreen screen, Component title) {
        super(registryAccess, AetherIIRegistries.EXPLORATION_ENTRY, screen, title);
    }

    @Override
    public void renderBg(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {

    }

    @Override
    public void renderFoward(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {

    }

    @Override
    public void renderInformation(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {

    }

    @Override
    protected CustomPacketPayload getViewedPacket(ExplorationEntry.Mutable entry) {
        return null;
    }

    @Override
    protected List<ExplorationEntry.Mutable> getOrderedEntries() {
        return List.of();
    }
}
