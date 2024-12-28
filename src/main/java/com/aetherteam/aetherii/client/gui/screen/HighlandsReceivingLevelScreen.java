package com.aetherteam.aetherii.client.gui.screen;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.renderer.RenderType;

import java.util.function.BooleanSupplier;

public class HighlandsReceivingLevelScreen extends ReceivingLevelScreen {
    private boolean isInAetherPortal;
    private float portalIntensity;
    private float oPortalIntensity;

    public HighlandsReceivingLevelScreen(BooleanSupplier levelReceived, Reason reason) {
        super(levelReceived, reason);
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.portalProcess != null && Minecraft.getInstance().player.portalProcess.isSamePortal(AetherIIBlocks.AETHER_PORTAL.get())) {
            var data = Minecraft.getInstance().player.getData(AetherIIDataAttachments.PLAYER);
            this.isInAetherPortal = true;
            this.portalIntensity = data.getPortalIntensity();
            this.oPortalIntensity = data.getOldPortalIntensity();
        }
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.isInAetherPortal) {
            guiGraphics.blitSprite(RenderType::guiOpaqueTexturedBackground, Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getParticleIcon(AetherIIBlocks.AETHER_PORTAL.get().defaultBlockState()), 0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight());
        }
    }

    @Override
    public void onClose() {
        if (Minecraft.getInstance().player != null && this.isInAetherPortal) {
            var data = Minecraft.getInstance().player.getData(AetherIIDataAttachments.PLAYER);
            data.portalIntensity = this.portalIntensity;
            data.oPortalIntensity = this.oPortalIntensity;
        }
        super.onClose();
    }
}