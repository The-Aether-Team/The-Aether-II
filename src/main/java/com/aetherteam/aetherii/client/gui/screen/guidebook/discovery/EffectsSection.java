package com.aetherteam.aetherii.client.gui.screen.guidebook.discovery;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.EffectsEntry;
import com.aetherteam.aetherii.api.guidebook.GuidebookEntry;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.GuidebookDiscoveryAttachment;
import com.aetherteam.aetherii.client.gui.component.guidebook.DescriptionButton;
import com.aetherteam.aetherii.client.gui.screen.guidebook.Guidebook;
import com.aetherteam.aetherii.client.gui.screen.guidebook.GuidebookDiscoveryScreen;
import com.aetherteam.aetherii.data.resources.registries.AetherIIEffectsEntries;
import com.aetherteam.aetherii.network.packet.serverbound.CheckEffectsEntryPacket;
import com.mojang.blaze3d.platform.cursor.CursorTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EffectsSection extends DiscoverySection<EffectsEntry, EffectsEntry.Mutable> {
    private static final Identifier GUIDEBOOK_DISCOVERY_RIGHT_PAGE_EFFECTS_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/discovery/guidebook_discovery_right_effects.png");
    private static final Identifier UNDISCOVERED_ENTRY_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/effects/undiscovered");
    private final List<EffectsEntry.Mutable> orderedEntries = new ArrayList<>();

    public EffectsSection(RegistryAccess registryAccess, GuidebookDiscoveryScreen screen, Component title) {
        super(registryAccess, AetherIIRegistries.EFFECTS_ENTRY, screen, title);
    }

    @Override
    public void initSection() {
        this.entries.clear();
        this.getOrderedEntries().clear();
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            GuidebookDiscoveryAttachment attachment = player.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY);
            attachment.getEffectsEntries().forEach((mutable) -> this.registryAccess.lookupOrThrow(this.registryKey).asHolderIdMap().forEach((entry) -> {
                if (entry.value().getEffect().value() == mutable.getEffect().value()) {
                    this.entries.add(mutable);
                }
            }));
            AetherIIEffectsEntries.ENTRY_ORDER.forEach((mobEffectHolder) -> this.entries.forEach((entry) -> {
                if (entry.getEffect().value() == mobEffectHolder.value()) {
                    this.getOrderedEntries().add(entry);
                }
            }));
            for (EffectsEntry.Mutable effectsEntry : attachment.getEffectsEntries()) {
                Optional<EffectsEntry.Mutable> matchingEntry = this.getOrderedEntries().stream().filter((mutable) -> mutable.getEffect().is(effectsEntry.getEffect())).findFirst();
                if (matchingEntry.isPresent()) {
                    for (Map.Entry<String, GuidebookEntry.Info> effectsClientValue : effectsEntry.getClientValues().entrySet()) {
                        if (matchingEntry.get().getClientValues().containsKey(effectsClientValue.getKey())) {
                            if (effectsClientValue.getValue().isVisible() && !matchingEntry.get().getClientValues().get(effectsClientValue.getKey()).isVisible()) {
                                matchingEntry.get().getClientValues().get(effectsClientValue.getKey()).reveal();
                            }
                            if (effectsClientValue.getValue().isViewed() && !matchingEntry.get().getClientValues().get(effectsClientValue.getKey()).isViewed()) {
                                matchingEntry.get().getClientValues().get(effectsClientValue.getKey()).view();
                            }
                        }
                    }
                }
            }
        }

        super.initSection();

        this.screen.addRenderableWidget(this.screen, new DescriptionButton(this.screen, (this.screen.width / 2) + 155, (this.screen.height / 2) + 35, Guidebook.MAGNIFYING_GLASS));
    }

    @Override
    public void renderBg(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {

    }

    @Override
    public void renderFoward(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {

    }

    @Override
    public void renderEntries(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderEntries(guiGraphics, mouseX, mouseY, partialTick);
        EffectsEntry.Mutable hoveredEntry = this.getEntryFromSlot(mouseX, mouseY);
        int leftPos = 43;
        int topPos = 59;
        int i = 0;

        List<EffectsEntry.Mutable> visibleEntries = this.getOrderedEntries().size() > this.maxSlots() ? this.getOrderedEntries().subList(Math.max(0, this.getSlotOffset()), Math.min(this.getSlotOffset() + this.maxSlots(), this.getOrderedEntries().size())) : this.getOrderedEntries();
        for (EffectsEntry.Mutable entry : visibleEntries) {
            int x = i % 6;
            int y = i / 6;
            int slotX = leftPos + (x * 18);
            int slotY = topPos + (y * 18);

            Identifier location = Gui.getMobEffectSprite(entry.getEffect());

            if (this.isUnlocked(entry, EffectsEntry.ICON.id())) {
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, location, slotX, slotY, 16, 16);
            } else {
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, UNDISCOVERED_ENTRY_SPRITE, slotX, slotY, 16, 16);
            }
            
            boolean isHovered = hoveredEntry != null && entry.getEffect() == hoveredEntry.getEffect();
            boolean isSelected = this.selectedEntry != null && entry.getEffect() == this.selectedEntry.getEffect();

            if (isHovered || isSelected) {
                guiGraphics.fillGradient(slotX, slotY, slotX + 16, slotY + 16, -2130706433, -2130706433);
            }

            if (!this.isViewed(entry)) {
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, Guidebook.EXCLAMATION, slotX, slotY, 3, 8);
            }

            i++;
        }
        this.renderScrollbar(guiGraphics);
        this.renderSlotTooltips(guiGraphics, mouseX, mouseY);
    }

    private void renderSlotTooltips(GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        EffectsEntry.Mutable entry = this.getEntryFromSlot(mouseX, mouseY);
        if (entry != null) {
            int leftPagePos = ((this.screen.width + 2) / 2) - Guidebook.PAGE_WIDTH;
            int topPos = (this.screen.height - Guidebook.PAGE_HEIGHT) / 2;
            Component name = Component.translatable("gui.aether_ii.guidebook.discovery.entry.unknown");
            if (this.isUnlocked(entry, EffectsEntry.SLOT_NAME.id())) {
                name = Component.translatable(entry.getSlotName());
            }
            List<Component> components = new ArrayList<>(List.of(name));
            if (this.isUnlocked(entry, EffectsEntry.SLOT_SUBTITLE.id()) && entry.getSlotSubtitle().isPresent()) {
                components.add(Component.translatable(entry.getSlotSubtitle().get()).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
            }
            guiGraphics.setComponentTooltipForNextFrame(Minecraft.getInstance().font, components, (int) mouseX, (int) mouseY);
            if (this.areAnyUnlocked(entry)) {
                guiGraphics.requestCursor(CursorTypes.POINTING_HAND);
            }
        }
    }

    @Override
    public void renderInformation(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        EffectsEntry.Mutable entry = this.getSelectedEntry();
        if (entry != null) {
            Font font = Minecraft.getInstance().font;
            if (this.isUnlocked(entry, EffectsEntry.NAME.id())) {
                guiGraphics.centeredText(font, Component.translatable(entry.getName()), 88, 13, 0xffffffff);
            }

            if (this.isUnlocked(this.getSelectedEntry(), EffectsEntry.EFFECT.id())) {
                Identifier location = Gui.getMobEffectSprite(entry.getEffect());

                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, location, 72, 30, 32, 32);
            }

            List<Holder<Item>> items = entry.getItems();
            for (int i = 0; i < 6; i++) {
                int slotX = 19 + (10) + (20 * i);
                List<Component> components = new ArrayList<>();
                ItemStack itemStack = ItemStack.EMPTY;
                if (entry.getClientValues().containsKey(EffectsEntry.ITEM.id() + "_" + i) && this.isUnlocked(entry, EffectsEntry.ITEM.id() + "_" + i)) {
                    Holder<Item> item = items.get(i);
                    itemStack = new ItemStack(item.value());
                    components.add(itemStack.getHoverName());
                }
                this.renderFakeSlot(guiGraphics, font, components, itemStack, mouseX, mouseY, slotX, 149);
            }

            if (this.isUnlocked(entry, EffectsEntry.DESCRIPTION_KEY.id())) {
                this.drawDescriptionString(guiGraphics, Minecraft.getInstance().font, Component.translatable(entry.getDescriptionKey()));
            }
        }
    }

    private void drawDescriptionString(GuiGraphicsExtractor guiGraphics, Font font, Component component) {
        int x = 21;
        int y = 71;
        int lineHeight = 9;
        int color = 0xffffffff;
        ActiveTextCollector textCollector = guiGraphics.textRenderer();
        MultiLineLabel label = MultiLineLabel.create(font, 135, 8, component);
        label.visitLines(TextAlignment.LEFT, x, y, lineHeight, textCollector);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean held, boolean original) {
        EffectsEntry.Mutable entry = this.getEntryFromSlot(event.x(), event.y());
        if (entry != null && (this.getSelectedEntry() == null || (entry.getEffect().value() != this.getSelectedEntry().getEffect().value())) && this.areAnyUnlocked(entry)) {
            this.selectedEntry = entry;
            this.updateViewed(entry);
            return true;
        }
        return super.mouseClicked(event, held, original);
    }

    @Override
    protected CustomPacketPayload getViewedPacket(EffectsEntry.Mutable entry) {
        return new CheckEffectsEntryPacket(entry.getEffect().value());
    }

    @Override
    protected List<EffectsEntry.Mutable> getOrderedEntries() {
        return this.orderedEntries;
    }

    @Override
    public Identifier getRightPageTexture() {
        return GUIDEBOOK_DISCOVERY_RIGHT_PAGE_EFFECTS_LOCATION;
    }
}
