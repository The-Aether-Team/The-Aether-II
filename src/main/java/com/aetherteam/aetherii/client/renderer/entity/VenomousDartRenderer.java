package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.projectile.VenomousDart;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.ArrowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class VenomousDartRenderer extends ArrowRenderer<VenomousDart, ArrowRenderState> {
    private static final ResourceLocation VENOMOUS_DART_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/venomous_dart.png");
    private static final ResourceLocation VENOMOUS_DART_EMISSIVE_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/venomous_dart_emissive.png");

    private final ArrowModel model;

    public VenomousDartRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ArrowModel(context.bakeLayer(ModelLayers.ARROW));
    }

    @Override
    public void render(ArrowRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int partialTick) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(renderState.xRot));
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.eyes(this.getEmissiveTextureLocation(renderState)));
        this.model.setupAnim(renderState);
        this.model.renderToBuffer(poseStack, vertexconsumer, partialTick, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(renderState, poseStack, bufferSource, partialTick);
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(ArrowRenderState renderState) {
        return VENOMOUS_DART_TEXTURE;
    }

    public ResourceLocation getEmissiveTextureLocation(ArrowRenderState renderState) {
        return VENOMOUS_DART_EMISSIVE_TEXTURE;
    }
}
