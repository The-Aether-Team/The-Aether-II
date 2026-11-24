package com.aetherteam.aetherii.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.object.projectile.ArrowModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;

public abstract class EmissiveArrowRenderer<T extends AbstractArrow, S extends ArrowRenderState> extends ArrowRenderer<T, S> {
    public EmissiveArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void submit(S renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(renderState.xRot));
        submitNodeCollector.submitModel(this.getModel(renderState), renderState, poseStack, RenderTypes.eyes(this.getEmissiveTextureLocation(renderState)), renderState.lightCoords, OverlayTexture.NO_OVERLAY, renderState.outlineColor, null);
        poseStack.popPose();
        super.submit(renderState, poseStack, submitNodeCollector, cameraRenderState);
    }

    public abstract Identifier getEmissiveTextureLocation(ArrowRenderState renderState);

    public abstract ArrowModel getModel(ArrowRenderState renderState);
}
