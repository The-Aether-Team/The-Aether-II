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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class BestiarySection extends DiscoverySection<BestiaryEntry> {
    private static final ResourceLocation GUIDEBOOK_DISCOVERY_RIGHT_PAGE_BESTIARY_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/guidebook/discovery/guidebook_discovery_right_bestiary.png");
    private float rotation = 0.0F;

    public BestiarySection(RegistryAccess registryAccess, GuidebookDiscoveryScreen screen, Component title) {
        super(registryAccess, AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY, screen, title);
    }

    @Override
    public void initSection() {
        super.initSection();
        int leftPos = (this.screen.width / 2) - (Guidebook.PAGE_WIDTH / 2);
        int topPos = (this.screen.height / 2) - (Guidebook.PAGE_HEIGHT / 2);
        int i = 0;
        for (BestiaryEntry entry : this.getEntries()) {
            int x = i % 6;
            int y = i / 6;
            this.screen.addRenderableWidget(this.screen, new BestiaryEntrySlot(entry, leftPos - 58 + (x * 18), topPos + 53 + (y * 18), 16, 16));
            i++;
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

    @Override
    public void renderEntries(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderEntries(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void renderInformation(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.getSelectedEntry() != null) {
            guiGraphics.drawCenteredString(this.screen.getMinecraft().font, Component.translatable(this.getSelectedEntry().entityType().value().getDescriptionId()), 88, 13, 16777215);
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
