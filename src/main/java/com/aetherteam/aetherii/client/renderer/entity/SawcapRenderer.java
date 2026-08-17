package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.SawcapModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SawcapRenderState;
import com.aetherteam.aetherii.entity.projectile.Sawcap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class SawcapRenderer extends EntityRenderer<Sawcap, SawcapRenderState> {
    private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/sawcap_projectile.png");
    private final SawcapModel model;

    public SawcapRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SawcapModel(context.bakeLayer(AetherIIModelLayers.SAWCAP));
    }

    @Override
    public SawcapRenderState createRenderState() {
        return new SawcapRenderState();
    }

    @Override
    public void extractRenderState(Sawcap entity, SawcapRenderState renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        renderState.xRot = entity.getViewXRot(partialTick);
        renderState.yRot = entity.getViewYRot(partialTick);
    }

    @Override
    public void submit(SawcapRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState p_451076_) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.5F / 16F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot - 180F));
        poseStack.mulPose(Axis.XP.rotationDegrees(renderState.xRot));
        poseStack.translate(0.0F, -1.0F + 0.5F / 16F, 0F);
        this.model.setupAnim(renderState);

        submitNodeCollector.submitModel(this.model, renderState, poseStack, this.model.renderType(TEXTURE_LOCATION), renderState.lightCoords, OverlayTexture.NO_OVERLAY, renderState.outlineColor, null);
        poseStack.popPose();
        super.submit(renderState, poseStack, submitNodeCollector, p_451076_);
    }
}
