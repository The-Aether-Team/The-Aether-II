package com.aetherteam.aetherii.client.gui.screen.guidebook.discovery;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.api.guidebook.EffectsEntry;
import com.aetherteam.aetherii.api.guidebook.GuidebookEntry;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.GuidebookDiscoveryAttachment;
import com.aetherteam.aetherii.client.gui.screen.guidebook.Guidebook;
import com.aetherteam.aetherii.client.gui.screen.guidebook.GuidebookDiscoveryScreen;
import com.aetherteam.aetherii.data.resources.registries.AetherIIEffectsEntries;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.network.packet.serverbound.CheckEffectsEntryPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.GuiSpriteManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EffectsSection extends DiscoverySection<EffectsEntry, EffectsEntry.Mutable> {
    private static final ResourceLocation GUIDEBOOK_DISCOVERY_RIGHT_PAGE_EFFECTS_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/discovery/guidebook_discovery_right_effects.png");
    private final List<EffectsEntry.Mutable> orderedEntries = new ArrayList<>();

    public EffectsSection(RegistryAccess registryAccess, GuidebookDiscoveryScreen screen, Component title) {
        super(registryAccess, AetherIIEffectsEntries.EFFECTS_ENTRY_REGISTRY_KEY, screen, title);
    }

    @Override
    public void initSection() {
        this.entries.clear();
        this.registryAccess.lookupOrThrow(this.registryKey).asHolderIdMap().forEach((entry) -> this.entries.add(new EffectsEntry.Mutable(entry)));
        this.getOrderedEntries().clear();
        AetherIIEffectsEntries.EFFECTS.values().forEach((entityTypeHolder) -> this.entries.forEach((entry) -> {
            if (entry.getEffect().value() == entityTypeHolder.value()) {
                this.getOrderedEntries().add(entry);
            }
        }));

        Player player = Minecraft.getInstance().player;
        if (player != null) {
            GuidebookDiscoveryAttachment attachment = player.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY);
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
    }

    @Override
    public void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

    }

    @Override
    public void renderEntries(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderEntries(guiGraphics, mouseX, mouseY, partialTick);
        EffectsEntry.Mutable hoveredEntry = this.getEntryFromSlot(mouseX, mouseY);
        int leftPos = 43;
        int topPos = 59;
        int i = 0;

        List<EffectsEntry.Mutable> visibleEntries = this.getOrderedEntries().size() > this.maxSlots() ? this.getOrderedEntries().subList(Math.max(0, this.getSlotOffset()), Math.min(this.getSlotOffset() + this.maxSlots(), this.getOrderedEntries().size())) : this.getOrderedEntries();
        for (EffectsEntry.Mutable entry : visibleEntries) {
            GuiSpriteManager guiSpriteManager = Minecraft.getInstance().getGuiSprites();

            ResourceLocation sprite;
//            if (this.isUnlocked(entry, BestiaryEntry.ICON.id())) {
                sprite = entry.getIcon();
                if (guiSpriteManager.getSprite(sprite).equals(guiSpriteManager.getSprite(MissingTextureAtlasSprite.getLocation()))) {
//                    sprite = DISCOVERED_ENTRY_FALLBACK_SPRITE;
                }
//            } else {
//                sprite = UNDISCOVERED_ENTRY_SPRITE;
//            }
            TextureAtlasSprite effectAtlasSprite = Minecraft.getInstance().getMobEffectTextures().get(entry.getEffect());

            int x = i % 6;
            int y = i / 6;
            int slotX = leftPos + (x * 18);
            int slotY = topPos + (y * 18);
            guiGraphics.blitSprite(RenderType::guiTextured, effectAtlasSprite, slotX, slotY, 16, 16);

            boolean isHovered = hoveredEntry != null && entry.getEffect() == hoveredEntry.getEffect();
            boolean isSelected = this.selectedEntry != null && entry.getEffect() == this.selectedEntry.getEffect();

            if (isHovered || isSelected) {
                guiGraphics.fillGradient(RenderType.guiOverlay(), slotX, slotY, slotX + 16, slotY + 16, -2130706433, -2130706433, 0);
            }

            if (!this.isViewed(entry)) {
                guiGraphics.blitSprite(RenderType::guiTextured, Guidebook.EXCLAMATION, slotX, slotY, 3, 8);
            }

            i++;
        }
        this.renderScrollbar(guiGraphics);
        this.renderSlotTooltips(guiGraphics, mouseX, mouseY);
    }

    private void renderSlotTooltips(GuiGraphics guiGraphics, double mouseX, double mouseY) {
        EffectsEntry.Mutable entry = this.getEntryFromSlot(mouseX, mouseY);
        if (entry != null) {
            int leftPagePos = ((this.screen.width + 2) / 2) - Guidebook.PAGE_WIDTH;
            int topPos = (this.screen.height - Guidebook.PAGE_HEIGHT) / 2;
            Component name = Component.translatable("gui.aether_ii.guidebook.discovery.entry.unknown");
            if (this.isUnlocked(entry, EffectsEntry.SLOT_NAME.id())) {
                if (entry.getSlotName().isPresent()) {
                    name = Component.translatable(entry.getSlotName().get());
                } else {
                    name = Component.translatable(entry.getEffect().value().getDescriptionId());
                }
            }
            List<Component> components = new ArrayList<>(List.of(name));
            if (this.isUnlocked(entry, EffectsEntry.SLOT_SUBTITLE.id()) && entry.getSlotSubtitle().isPresent()) {
                components.add(Component.translatable(entry.getSlotSubtitle().get()).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
            }
            guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, components, (int) (mouseX - leftPagePos), (int) (mouseY - topPos));
        }
    }

    @Override
    public void renderInformation(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        EffectsEntry.Mutable entry = this.getSelectedEntry();
        if (entry != null) {
            Font font = Minecraft.getInstance().font;
            if (this.isUnlocked(this.getSelectedEntry(), EffectsEntry.EFFECT.id())) {
                TextureAtlasSprite effectAtlasSprite = Minecraft.getInstance().getMobEffectTextures().get(entry.getEffect());

                guiGraphics.blitSprite(RenderType::guiTextured, effectAtlasSprite, 72, 30, 32, 32);
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

    private void drawDescriptionString(GuiGraphics guiGraphics, Font font, Component component) {
        int x = 21;
        int y = 89;
        int width = 140;
        for (FormattedCharSequence formattedcharsequence : font.split(component, width)) {
            guiGraphics.drawString(font, formattedcharsequence, x, y, -1, true);
            y += 9;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button, boolean original) {
        EffectsEntry.Mutable entry = this.getEntryFromSlot(mouseX, mouseY);
        if (entry != null && (this.getSelectedEntry() == null || (entry.getEffect().value() != this.getSelectedEntry().getEffect().value())) && this.areAnyUnlocked(entry)) {
            this.selectedEntry = entry;
            this.updateViewed(entry);
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button, original);
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
    public ResourceLocation getRightPageTexture() {
        return GUIDEBOOK_DISCOVERY_RIGHT_PAGE_EFFECTS_LOCATION;
    }
}
