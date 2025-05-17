package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.GravititeDebrisShotModel;
import com.aetherteam.aetherii.client.renderer.entity.state.GravititeDebrisShotRenderState;
import com.aetherteam.aetherii.client.renderer.entity.state.GravititeTalutonRenderState;
import com.aetherteam.aetherii.entity.monster.GravititeTaluton;
import com.aetherteam.aetherii.entity.projectile.GravititeDebrisShot;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class GravititeDebrisShotRenderer extends EntityRenderer<GravititeDebrisShot, GravititeDebrisShotRenderState> {
    private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/gravitite_debris_shot.png");
    private final GravititeDebrisShotModel model;

    public GravititeDebrisShotRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new GravititeDebrisShotModel(context.bakeLayer(AetherIIModelLayers.GRAVITITE_DEBRIS_SHOT));
    }

    @Override
    public GravititeDebrisShotRenderState createRenderState() {
        return new GravititeDebrisShotRenderState();
    }

    @Override
    public void extractRenderState(GravititeDebrisShot entity, GravititeDebrisShotRenderState renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        renderState.xRot = entity.getXRot(partialTick);
        renderState.yRot = entity.getYRot(partialTick);
    }

    @Override
    public void render(GravititeDebrisShotRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int partialTick) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F - renderState.xRot));
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.ageInTicks * -75.0F));

        this.model.setupAnim(renderState);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(this.model.renderType(TEXTURE_LOCATION));
        this.model.renderToBuffer(poseStack, vertexconsumer, partialTick, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(renderState, poseStack, bufferSource, partialTick);
    }
}
