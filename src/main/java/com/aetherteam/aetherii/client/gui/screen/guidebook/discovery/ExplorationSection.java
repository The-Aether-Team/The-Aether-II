package com.aetherteam.aetherii.client.gui.screen.guidebook.discovery;

import com.aetherteam.aetherii.api.guidebook.ExplorationEntry;
import com.aetherteam.aetherii.client.gui.screen.guidebook.GuidebookDiscoveryScreen;
import com.aetherteam.aetherii.data.resources.registries.AetherIIExplorationEntries;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.List;

public class ExplorationSection extends DiscoverySection<ExplorationEntry, ExplorationEntry.Mutable> {
    public ExplorationSection(RegistryAccess registryAccess, GuidebookDiscoveryScreen screen, Component title) {
        super(registryAccess, AetherIIExplorationEntries.EXPLORATION_ENTRY_REGISTRY_KEY, screen, title);
    }

    @Override
    public void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

    }

    @Override
    public void renderInformation(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

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
