package com.aetherteam.aetherii.client.gui.screen;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.AetherIIPlayerAttachment;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;

public class HolyIslesReceivingLevelScreen extends ReceivingLevelScreen {
    private static final Component DOWNLOADING_TERRAIN_TEXT = Component.translatable("multiplayer.downloadingTerrain");
    private boolean isInAetherPortal;
    private float portalIntensity;
    private float oPortalIntensity;

    public HolyIslesReceivingLevelScreen() {
        super();
        if (Minecraft.getInstance().player != null) {
            AetherIIPlayerAttachment data = AetherIIDataAttachments.get(Minecraft.getInstance().player, AetherIIDataAttachments.PLAYER);
            if (data.getPortalIntensity() <= 0.0F && data.getOldPortalIntensity() <= 0.0F) {
                return;
            }
            this.isInAetherPortal = true;
            this.portalIntensity = data.getPortalIntensity();
            this.oPortalIntensity = data.getOldPortalIntensity();
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.isInAetherPortal) {
            TextureAtlasSprite sprite = Minecraft.getInstance().getBlockRenderer().getBlockModel(AetherIIBlocks.AETHER_PORTAL.get().defaultBlockState()).getParticleIcon();
            com.aetherteam.aetherii.client.gui.AetherIIGuiGraphics.blitSprite(guiGraphics, sprite, 0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight());
        } else {
            this.renderDirtBackground(guiGraphics);
        }
        guiGraphics.drawCenteredString(this.font, DOWNLOADING_TERRAIN_TEXT, this.width / 2, this.height / 2 - 50, 16777215);
    }

    @Override
    public void onClose() {
        if (Minecraft.getInstance().player != null && this.isInAetherPortal) {
            AetherIIPlayerAttachment data = AetherIIDataAttachments.get(Minecraft.getInstance().player, AetherIIDataAttachments.PLAYER);
            data.portalIntensity = this.portalIntensity;
            data.oPortalIntensity = this.oPortalIntensity;
        }
        super.onClose();
    }
}

