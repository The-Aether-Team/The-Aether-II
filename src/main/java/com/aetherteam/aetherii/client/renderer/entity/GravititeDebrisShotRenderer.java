package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.GravititeDebrisShotModel;
import com.aetherteam.aetherii.client.renderer.entity.state.GravititeDebrisShotRenderState;
import com.aetherteam.aetherii.entity.projectile.GravititeDebrisShot;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class GravititeDebrisShotRenderer extends EntityRenderer<GravititeDebrisShot, GravititeDebrisShotRenderState> {
    private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/gravitite_debris_shot.png");
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
        renderState.xRot = entity.getViewXRot(partialTick);
        renderState.yRot = entity.getViewYRot(partialTick);
    }

    @Override
    public void submit(GravititeDebrisShotRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState p_451076_) {
        poseStack.pushPose();
        poseStack.translate(0, 0.25, 0);
        poseStack.mulPose(Axis.XN.rotationDegrees(renderState.xRot));
        poseStack.mulPose(Axis.YN.rotationDegrees(renderState.yRot));
        this.model.setupAnim(renderState);

        submitNodeCollector.submitModel(this.model, renderState, poseStack, this.model.renderType(TEXTURE_LOCATION), renderState.lightCoords, OverlayTexture.NO_OVERLAY, renderState.outlineColor, null);
        poseStack.popPose();
        super.submit(renderState, poseStack, submitNodeCollector, p_451076_);
    }
}
