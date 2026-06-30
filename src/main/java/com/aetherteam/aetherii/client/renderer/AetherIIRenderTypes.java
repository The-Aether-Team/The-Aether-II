package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class AetherIIRenderTypes {
    public static final ResourceLocation IRRADIATED_GLINT_ITEM = new ResourceLocation(AetherII.MODID, "textures/misc/irradiated_glint_item.png");
    private static final RenderType CLOUD_COVER = new RenderType("aether_ii:cloud_cover", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLE_FAN, 256, false, false, AetherIIRenderTypes::setupCloudCover, AetherIIRenderTypes::clearCloudCover) {
    };
    private static final RenderType IRRADIATED_GLINT = new RenderType("aether_ii:irradiated_glint", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, AetherIIRenderTypes::setupIrradiatedGlint, AetherIIRenderTypes::clearIrradiatedGlint) {
    };

    public static RenderType entityDitherNoCull(ResourceLocation location) {
        return entityDitherNoCull(location, true);
    }

    public static RenderType entityDitherNoCull(ResourceLocation location, boolean outline) {
        if (ShaderCompatibility.areShadersActive()) {
            return RenderType.entityTranslucent(location, outline);
        }
        return RenderType.entityTranslucent(location, outline);
    }

    public static RenderType cloudCover() {
        return CLOUD_COVER;
    }

    public static RenderType irradiatedGlint() {
        return IRRADIATED_GLINT;
    }

    private static void setupCloudCover() {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.disableCull();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }

    private static void clearCloudCover() {
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
    }

    private static void setupIrradiatedGlint() {
        RenderSystem.setShader(GameRenderer::getRendertypeGlintShader);
        Minecraft.getInstance().getTextureManager().getTexture(IRRADIATED_GLINT_ITEM).setFilter(true, false);
        RenderSystem.setShaderTexture(0, IRRADIATED_GLINT_ITEM);
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(514);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
        setupGlintTexturing();
    }

    private static void clearIrradiatedGlint() {
        RenderSystem.resetTextureMatrix();
        RenderSystem.disableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.depthFunc(515);
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
    }

    private static void setupGlintTexturing() {
        long millis = (long) ((double) Util.getMillis() * Minecraft.getInstance().options.glintSpeed().get() * 8.0D);
        float u = (float) (millis % 110000L) / 110000.0F;
        float v = (float) (millis % 30000L) / 30000.0F;
        Matrix4f matrix = new Matrix4f().translation(-u, v, 0.0F);
        matrix.rotateZ(0.17453292F).scale(8.0F);
        RenderSystem.setTextureMatrix(matrix);
    }
}
