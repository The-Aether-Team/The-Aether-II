package com.aetherteam.aetherii.client.gui.screen.guidebook.discovery;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.api.guidebook.GuidebookEntry;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.GuidebookDiscoveryAttachment;
import com.aetherteam.aetherii.client.gui.AetherIIGuiGraphics;
import com.aetherteam.aetherii.client.gui.component.guidebook.DescriptionButton;
import com.aetherteam.aetherii.client.gui.screen.guidebook.Guidebook;
import com.aetherteam.aetherii.client.gui.screen.guidebook.GuidebookDiscoveryScreen;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBestiaryEntries;
import com.aetherteam.aetherii.entity.attributes.EffectResistanceAttribute;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import com.aetherteam.aetherii.network.packet.serverbound.CheckBestiaryEntryPacket;
import com.google.common.collect.ImmutableList;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Quaternionf;

import java.text.DecimalFormat;
import java.util.*;

public class BestiarySection extends DiscoverySection<BestiaryEntry, BestiaryEntry.Mutable> {
    private static final ResourceLocation GUIDEBOOK_DISCOVERY_RIGHT_PAGE_BESTIARY_LOCATION = new ResourceLocation(AetherII.MODID, "textures/gui/guidebook/discovery/guidebook_discovery_right_bestiary.png");
    private static final ResourceLocation SLASH_SPRITE = new ResourceLocation(AetherII.MODID, "guidebook/stats/slash");
    private static final ResourceLocation IMPACT_SPRITE = new ResourceLocation(AetherII.MODID, "guidebook/stats/impact");
    private static final ResourceLocation PIERCE_SPRITE = new ResourceLocation(AetherII.MODID, "guidebook/stats/pierce");
    private static final ResourceLocation UNDISCOVERED_ENTRY_SPRITE = new ResourceLocation(AetherII.MODID, "guidebook/bestiary/undiscovered");
    private static final ResourceLocation DISCOVERED_ENTRY_FALLBACK_SPRITE = new ResourceLocation(AetherII.MODID, "guidebook/bestiary/default");
    private static final DecimalFormat PERCENTAGE = new DecimalFormat("##.##%");
    private final List<BestiaryEntry.Mutable> orderedEntries = new ArrayList<>();
    private float rotation = 0.0F;
    private final List<Holder<Item>> currentFoods = new ArrayList<>();
    private int switchFoodItemCounter = 0;

    public BestiarySection(RegistryAccess registryAccess, GuidebookDiscoveryScreen screen, Component title) {
        super(registryAccess, AetherIIRegistries.BESTIARY_ENTRY, screen, title);
    }

    @Override
    public void initSection() {
        this.entries.clear();
        this.getOrderedEntries().clear();
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            GuidebookDiscoveryAttachment attachment = AetherIIDataAttachments.get(player, AetherIIDataAttachments.GUIDEBOOK_DISCOVERY);
            attachment.getBestiaryEntries().forEach((mutable) -> this.registryAccess.registryOrThrow(this.registryKey).asHolderIdMap().forEach((entry) -> {
                if (sameHolder(entry.value().getEntityType(), mutable.getEntityType())) {
                    this.entries.add(mutable);
                }
            }));
            AetherIIBestiaryEntries.ENTRY_ORDER.forEach((entityTypeHolder) -> this.entries.forEach((entry) -> {
                if (sameHolder(entry.getEntityType(), entityTypeHolder)) {
                    this.getOrderedEntries().add(entry);
                }
            }));
            for (BestiaryEntry.Mutable bestiaryEntry : attachment.getBestiaryEntries()) {
                Optional<BestiaryEntry.Mutable> matchingEntry = this.getOrderedEntries().stream().filter((mutable) -> sameHolder(mutable.getEntityType(), bestiaryEntry.getEntityType())).findFirst();
                if (matchingEntry.isPresent()) {
                    for (Map.Entry<String, GuidebookEntry.Info> bestiaryClientValue : bestiaryEntry.getClientValues().entrySet()) {
                        if (matchingEntry.get().getClientValues().containsKey(bestiaryClientValue.getKey())) {
                            if (bestiaryClientValue.getValue().isVisible() && !matchingEntry.get().getClientValues().get(bestiaryClientValue.getKey()).isVisible()) {
                                matchingEntry.get().getClientValues().get(bestiaryClientValue.getKey()).reveal();
                            }
                            if (bestiaryClientValue.getValue().isViewed() && !matchingEntry.get().getClientValues().get(bestiaryClientValue.getKey()).isViewed()) {
                                matchingEntry.get().getClientValues().get(bestiaryClientValue.getKey()).view();
                            }
                        }
                    }
                }
            }
        }

        super.initSection();

        this.screen.addRenderableWidget(this.screen, new DescriptionButton(this.screen, (this.screen.width / 2) + 155, (this.screen.height / 2) + 40, Guidebook.MAGNIFYING_GLASS));

        this.rotation = 0.0F;
    }

    @Override
    public void renderFoward(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int rightPagePos = (this.screen.width / 2);
        int topPos = (this.screen.height - Guidebook.PAGE_HEIGHT) / 2;
        if (this.getSelectedEntry() != null && this.isUnlocked(this.getSelectedEntry(), BestiaryEntry.ENTITY_TYPE.id())) {
            Level level = Minecraft.getInstance().level;
            if (level != null) {
                Entity entity = this.getSelectedEntry().getEntityType().value().create(level);
                if (entity instanceof LivingEntity livingEntity) {
                    int x = 24;
                    int y = 28;
                    int width = 125;
                    int height = 69;
                    this.rotation = Mth.wrapDegrees(Mth.lerp(partialTick, this.rotation, this.rotation + 0.85F));
                    int scale = (int) ((30 / livingEntity.getBoundingBox().getSize()) * this.getSelectedEntry().getScaleMultiplier().orElse(1.0));
                    this.renderRotatingEntity(guiGraphics, rightPagePos + x, topPos + y, rightPagePos + x + width, topPos + y + height, scale, 0.2225F, this.rotation, -15.0F, livingEntity);
                }
            }
        }
    }

    @Override
    public void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

    }

    public void renderRotatingEntity(GuiGraphics guiGraphics, int startX, int startY, int endX, int endY, int scale, float yOffset, float angleXComponent, float angleYComponent, LivingEntity livingEntity) {
        Quaternionf xQuaternion = new Quaternionf().rotateZ(Mth.PI);
        Quaternionf zQuaternion = new Quaternionf().rotateX(angleYComponent * Mth.DEG_TO_RAD);
        xQuaternion.mul(zQuaternion);
        float yBodyRot = livingEntity.yBodyRot;
        float yRot = livingEntity.getYRot();
        float xRot = livingEntity.getXRot();
        float yHeadRotO = livingEntity.yHeadRotO;
        float yHeadRot = livingEntity.yHeadRot;
        livingEntity.tickCount = -2;
        livingEntity.yBodyRot = 180.0F + angleXComponent;
        livingEntity.setYRot(180.0F + angleXComponent);
        livingEntity.setXRot(-angleYComponent);
        livingEntity.yHeadRot = livingEntity.getYRot();
        livingEntity.yHeadRotO = livingEntity.getYRot();

        InventoryScreen.renderEntityInInventory(guiGraphics, (startX + endX) / 2, (int) (endY - ((endY - startY) * yOffset)), scale, xQuaternion, zQuaternion, livingEntity);

        livingEntity.yBodyRot = yBodyRot;
        livingEntity.setYRot(yRot);
        livingEntity.setXRot(xRot);
        livingEntity.yHeadRotO = yHeadRotO;
        livingEntity.yHeadRot = yHeadRot;
    }

    @Override
    public void renderEntries(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderEntries(guiGraphics, mouseX, mouseY, partialTick);
        BestiaryEntry.Mutable hoveredEntry = this.getEntryFromSlot(mouseX, mouseY);
        int leftPos = 43;
        int topPos = 59;
        int i = 0;

        List<BestiaryEntry.Mutable> visibleEntries = this.getOrderedEntries().size() > this.maxSlots() ? this.getOrderedEntries().subList(Math.max(0, this.getSlotOffset()), Math.min(this.getSlotOffset() + this.maxSlots(), this.getOrderedEntries().size())) : this.getOrderedEntries();
        for (BestiaryEntry.Mutable entry : visibleEntries) {
            ResourceLocation sprite;
            if (this.isUnlocked(entry, BestiaryEntry.ICON.id())) {
                sprite = entry.getIcon();
                if (Minecraft.getInstance().getResourceManager().getResource(AetherIIGuiGraphics.spriteTexture(sprite)).isEmpty()) {
                    sprite = DISCOVERED_ENTRY_FALLBACK_SPRITE;
                }
            } else {
                sprite = UNDISCOVERED_ENTRY_SPRITE;
            }

            int x = i % 6;
            int y = i / 6;
            int slotX = leftPos + (x * 18);
            int slotY = topPos + (y * 18);
            AetherIIGuiGraphics.blitSprite(guiGraphics, sprite, slotX, slotY, 16, 16);

            boolean isHovered = hoveredEntry != null && sameHolder(entry.getEntityType(), hoveredEntry.getEntityType());
            boolean isSelected = this.selectedEntry != null && sameHolder(entry.getEntityType(), this.selectedEntry.getEntityType());

            if (isHovered || isSelected) {
                guiGraphics.fillGradient(slotX, slotY, slotX + 16, slotY + 16, -2130706433, -2130706433);
            }

            if (!this.isViewed(entry)) {
                AetherIIGuiGraphics.blitSprite(guiGraphics, Guidebook.EXCLAMATION, slotX, slotY, 3, 8);
            }

            i++;
        }
        this.renderScrollbar(guiGraphics);
        this.renderSlotTooltips(guiGraphics, mouseX, mouseY);
    }

    private void renderSlotTooltips(GuiGraphics guiGraphics, double mouseX, double mouseY) {
        BestiaryEntry.Mutable entry = this.getEntryFromSlot(mouseX, mouseY);
        if (entry != null) {
            int leftPagePos = ((this.screen.width + 2) / 2) - Guidebook.PAGE_WIDTH;
            int topPos = (this.screen.height - Guidebook.PAGE_HEIGHT) / 2;
            Component name = Component.translatable("gui.aether_ii.guidebook.discovery.entry.unknown");
            if (this.isUnlocked(entry, BestiaryEntry.SLOT_NAME.id())) {
                name = Component.translatable(entry.getSlotName());
            }
            List<Component> components = new ArrayList<>(List.of(name));
            if (this.isUnlocked(entry, BestiaryEntry.SLOT_SUBTITLE.id()) && entry.getSlotSubtitle().isPresent()) {
                components.add(Component.translatable(entry.getSlotSubtitle().get()).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
            }
            guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, components, (int) (mouseX - leftPagePos), (int) (mouseY - topPos));
        }
    }

    @Override
    public void renderInformation(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        BestiaryEntry.Mutable entry = this.getSelectedEntry();
        if (entry != null) {
            Level level = Minecraft.getInstance().level;
            Font font = Minecraft.getInstance().font;
            if (level != null) {
                Entity entity = entry.getEntityType().value().create(level);
                if (entity instanceof LivingEntity livingEntity) {
                    if (this.isUnlocked(entry, BestiaryEntry.NAME.id())) {
                        guiGraphics.drawCenteredString(font, Component.translatable(entry.getName()), 88, 13, 0xffffffff);
                    }

                    int x = 27;
                    int y = 29;

                    if (this.isUnlocked(entry, BestiaryEntry.HEALTH.id())) {
                        AetherIIGuiGraphics.blitSprite(guiGraphics, Guidebook.HEARTS_SPRITE, x, y, 16, 16);
                        this.renderIconValue(guiGraphics, x, y, (int) livingEntity.getMaxHealth());
                        this.renderTooltipOverIcon(font, guiGraphics, mouseX, mouseY, x, y, Component.translatable("gui.aether_ii.guidebook.discovery.bestiary.stat.health", livingEntity.getMaxHealth()));
                    }

                    y += 17;
                    if (this.isUnlocked(entry, BestiaryEntry.SLASH_DEFENSE.id())) {
                        AetherIIGuiGraphics.blitSprite(guiGraphics, SLASH_SPRITE, x, y, 16, 16);
                        int slashDefense = entry.getSlashDefense();
                        Component slashTooltip = this.getDamageTypeComponent(slashDefense, "slash");
                        this.renderDefenseIconValue(guiGraphics, x, y, -slashDefense);
                        this.renderTooltipOverIcon(font, guiGraphics, mouseX, mouseY, x, y, slashTooltip);
                    }

                    y += 17;
                    if (this.isUnlocked(entry, BestiaryEntry.IMPACT_DEFENSE.id())) {
                        AetherIIGuiGraphics.blitSprite(guiGraphics, IMPACT_SPRITE, x, y, 16, 16);
                        int impactDefense = entry.getImpactDefense();
                        Component impactTooltip = this.getDamageTypeComponent(impactDefense, "impact");
                        this.renderDefenseIconValue(guiGraphics, x, y, -impactDefense);
                        this.renderTooltipOverIcon(font, guiGraphics, mouseX, mouseY, x, y, impactTooltip);
                    }

                    y += 17;
                    if (this.isUnlocked(entry, BestiaryEntry.PIERCE_DEFENSE.id())) {
                        AetherIIGuiGraphics.blitSprite(guiGraphics, PIERCE_SPRITE, x, y, 16, 16);
                        int pierceDefense = entry.getPierceDefense();
                        Component pierceTooltip = this.getDamageTypeComponent(pierceDefense, "pierce");
                        this.renderDefenseIconValue(guiGraphics, x, y, -pierceDefense);
                        this.renderTooltipOverIcon(font, guiGraphics, mouseX, mouseY, x, y, pierceTooltip);
                    }

                    x = 132;
                    y = 29;

                    List<BestiaryEntry.EffectResistanceDisplay> effectResistances = entry.getEffectResistances();
                    if (!effectResistances.isEmpty()) {
                        for (int i = 0; i < effectResistances.size(); i++) {
                            BestiaryEntry.EffectResistanceDisplay effectResistanceDisplay = effectResistances.get(i);
                            if (effectResistanceDisplay.attribute().value() instanceof EffectResistanceAttribute effectResistanceAttribute) {
                                if (entry.getClientValues().containsKey(BestiaryEntry.EFFECT_RESISTANCE.id() + "_" + i) && this.isUnlocked(entry, BestiaryEntry.EFFECT_RESISTANCE.id() + "_" + i)) {
                                    MobEffect effect = effectResistanceAttribute.getEffect().get();
                                    guiGraphics.blit(x, y, 0, 18, 18, Minecraft.getInstance().getMobEffectTextures().get(effect));
                                    int effectValue = effectResistanceDisplay.value();
                                    Component effectTooltip = Component.literal(effectValue * 100 + "%")
                                            .append(CommonComponents.space())
                                            .append(Component.translatable(effectResistanceAttribute.getDescriptionId(), Component.translatable(effect.getDescriptionId()).withStyle((style) -> style.withColor(effect.getColor()))));
                                    this.renderDefenseIconValue(guiGraphics, x, y, effectValue);
                                    this.renderTooltipOverIcon(font, guiGraphics, mouseX, mouseY, x, y, effectTooltip);
                                    y += 17;
                                }
                            }
                        }
                    }

                    int dropsTextX = 101;
                    int dropsTextY = 156;

                    Optional<TagKey<Item>> food = entry.getFood();
                    if (food.isPresent()) {
                        if (this.isUnlocked(entry, BestiaryEntry.FOOD.id())) {
                            Registry<Item> itemRegistry = this.registryAccess.registryOrThrow(Registries.ITEM);
                            List<Holder<Item>> tag = new ArrayList<>(ImmutableList.copyOf(itemRegistry.getTagOrEmpty(food.get())));
                            if (this.currentFoods.isEmpty()) {
                                this.currentFoods.addAll(tag);
                            }
                            if (!this.currentFoods.isEmpty()) {
                                guiGraphics.drawString(font, Component.translatable("gui.aether_ii.guidebook.discovery.bestiary.info.eats"), 17, 156, -1);
                                ItemStack itemStack = this.currentFoods.get(0).value().getDefaultInstance();
                                this.renderFakeSlot(guiGraphics, font, List.of(itemStack.getHoverName()), itemStack, mouseX, mouseY, 44, 151);

                                if (this.switchFoodItemCounter++ >= 90) {
                                    Collections.rotate(this.currentFoods, 1);
                                    this.switchFoodItemCounter = 0;
                                }
                            }
                        }
                    } else {
                        dropsTextX = 60;
                    }

                    List<BestiaryEntry.LootDisplay> loot = entry.getLoot();
                    if (!loot.isEmpty()) {
                        boolean renderTitle = false;
                        for (int i = 0; i < loot.size(); i++) {
                            BestiaryEntry.LootDisplay lootDisplay = loot.get(i);
                            if (entry.getClientValues().containsKey(BestiaryEntry.LOOT.id() + "_" + i) && this.isUnlocked(entry, BestiaryEntry.LOOT.id() + "_" + i)) {
                                int slotX = dropsTextX + (10 * (3 - loot.size())) + (20 * i);
                                ItemStack itemStack = new ItemStack(lootDisplay.getItemLike());
                                List<Component> components = new ArrayList<>();
                                components.add(itemStack.getHoverName());
                                if (lootDisplay.minCount() != lootDisplay.maxCount()) {
                                    components.add(Component.literal(lootDisplay.minCount() + "-" + lootDisplay.maxCount()).withStyle(ChatFormatting.GRAY));
                                } else {
                                    components.add(Component.literal(String.valueOf(lootDisplay.minCount())).withStyle(ChatFormatting.GRAY));
                                }
                                components.add(Component.literal(PERCENTAGE.format(lootDisplay.chance())).withStyle(ChatFormatting.GRAY));
                                this.renderFakeSlot(guiGraphics, font, components, itemStack, mouseX, mouseY, slotX, dropsTextY - 5);
                                renderTitle = true;
                            }
                        }
                        if (renderTitle) {
                            Component drops = Component.translatable("gui.aether_ii.guidebook.discovery.bestiary.info.drops");
                            guiGraphics.drawString(font, drops, dropsTextX - (font.width(drops) + 3) + (10 * (3 - loot.size())), dropsTextY, -1);
                        }
                    }

                    if (this.isUnlocked(entry, BestiaryEntry.DESCRIPTION_KEY.id())) {
                        this.drawDescriptionString(guiGraphics, Minecraft.getInstance().font, Component.translatable(entry.getDescriptionKey()));
                    }
                }
            }
        }
    }

    private void renderDefenseIconValue(GuiGraphics guiGraphics, int x, int y, double value) {
        Font font = Minecraft.getInstance().font;
        String name = String.valueOf(Math.abs((int) value));
        if (value > 0) {
            name = "₊" + name;
        } else if (value < 0) {
            name = "₋" + name;
        }
        guiGraphics.drawString(font, name, x + 19 - 2 - font.width(name), y + 6 + 3, 0xffffffff, true);
    }

    private void renderIconValue(GuiGraphics guiGraphics, int x, int y, double value) {
        Font font = Minecraft.getInstance().font;
        String name = String.valueOf(Math.abs((int) value));
        guiGraphics.drawString(font, name, x + 19 - 2 - font.width(name), y + 6 + 3, 0xffffffff, true);
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

    private void renderTooltipOverIcon(Font font, GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y, Component component) {
        int rightPagePos = (this.screen.width / 2);
        int topPos = (this.screen.height - Guidebook.PAGE_HEIGHT) / 2;
        double mouseXDiff = (mouseX - rightPagePos) - x;
        double mouseYDiff = (mouseY - topPos) - y;
        if (mouseYDiff <= 15 && mouseYDiff >= 0 && mouseXDiff <= 15 && mouseXDiff >= 0) {
            guiGraphics.renderTooltip(font, component, mouseX - rightPagePos, mouseY - topPos);
        }
    }

    private void drawDescriptionString(GuiGraphics guiGraphics, Font font, Component component) {
        int x = 21;
        int y = 103;
        int lineHeight = 9;
        MultiLineLabel label = MultiLineLabel.create(font, component, 135, 5);
        label.renderLeftAligned(guiGraphics, x, y, lineHeight, 0xffffffff);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button, boolean original) {
        BestiaryEntry.Mutable entry = this.getEntryFromSlot(mouseX, mouseY);
        if (entry != null && (this.getSelectedEntry() == null || !sameHolder(entry.getEntityType(), this.getSelectedEntry().getEntityType())) && this.areAnyUnlocked(entry)) {
            this.selectedEntry = entry;
            this.updateViewed(entry);
            this.currentFoods.clear();
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button, original);
    }

    @Override
    protected AetherPacketPayload getViewedPacket(BestiaryEntry.Mutable entry) {
        return new CheckBestiaryEntryPacket(entry.getEntityType().value());
    }

    @Override
    public List<BestiaryEntry.Mutable> getOrderedEntries() {
        return this.orderedEntries;
    }

    @Override
    public ResourceLocation getRightPageTexture() {
        return GUIDEBOOK_DISCOVERY_RIGHT_PAGE_BESTIARY_LOCATION;
    }
}


