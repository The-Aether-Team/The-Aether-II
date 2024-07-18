package com.aetherteam.aetherii.client.gui.screen.guidebook.discovery;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.client.gui.screen.guidebook.Guidebook;
import com.aetherteam.aetherii.client.gui.screen.guidebook.GuidebookDiscoveryScreen;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBestiaryEntries;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class BestiarySection extends DiscoverySection<BestiaryEntry> {
    private static final ResourceLocation GUIDEBOOK_DISCOVERY_RIGHT_PAGE_BESTIARY_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/discovery/guidebook_discovery_right_bestiary.png");

    public BestiarySection(RegistryAccess registryAccess, GuidebookDiscoveryScreen screen, Component title) {
        super(registryAccess, AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY, screen, title);
    }

    @Override
    public void initSection() {
        super.initSection();
        int leftPos = (this.screen.width / 2) -  Guidebook.PAGE_WIDTH;
        int topPos = (this.screen.height - Guidebook.PAGE_HEIGHT) / 2;
        int i = 0;
        for (BestiaryEntry entry : this.getEntries()) {
            int x = i % 6;
            int y = i / 6;
            this.screen.addRenderableWidget(this.screen, new BestiaryEntrySlot(entry, leftPos + 30 + (x * 18), topPos + 53 + (y * 18), 16, 16));
            i++;
        }
    }

    @Override
    public void renderEntries(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderEntries(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void renderInformation(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int leftPos = (this.screen.width / 2);
        int topPos = (this.screen.height - Guidebook.PAGE_HEIGHT) / 2;
        if (this.getSelectedEntry() != null) {
            Level level = Minecraft.getInstance().level;
            if (level != null) {
                Entity entity = this.selectedEntry.entityType().value().create(level);
                if (entity instanceof LivingEntity livingEntity) {
                    int x = 26;
                    int y = 22;
                    int width = 56;
                    int height = 69;
                    InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, leftPos + x, topPos + y, leftPos + x + width, topPos + y + height, 25, 0.1625F, mouseX, mouseY, livingEntity); //todo dynamic scale
                }
            }
            guiGraphics.drawCenteredString(this.screen.getMinecraft().font, Component.translatable(this.getSelectedEntry().entityType().value().getDescriptionId()), leftPos + 90, topPos + 7, 16777215);
        }
    }

    @Override
    public ResourceLocation getRightPageTexture() {
        return GUIDEBOOK_DISCOVERY_RIGHT_PAGE_BESTIARY_LOCATION;
    }

    public class BestiaryEntrySlot extends DiscoveryEntrySlot {
        public BestiaryEntrySlot(BestiaryEntry entry, int x, int y, int width, int height) {
            super(entry, x, y, width, height, new WidgetSprites(entry.icon(), entry.icon()), (button) -> BestiarySection.this.setSelectedEntry(entry), Component.translatable(entry.entityType().value().getDescriptionId()));
            this.setTooltip(Tooltip.create(Component.translatable(entry.entityType().value().getDescriptionId())));
        }

        @Override
        public boolean isSelected() {
            return BestiarySection.this.selectedEntry != null && this.getEntry().entityType() == BestiarySection.this.selectedEntry.entityType();
        }
    }
}
