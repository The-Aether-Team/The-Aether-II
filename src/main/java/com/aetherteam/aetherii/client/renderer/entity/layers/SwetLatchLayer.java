package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.attachment.player.SwetLatchAttachment;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderers;
import com.aetherteam.aetherii.client.renderer.entity.state.SwetRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;

import java.util.List;

public class SwetLatchLayer<T extends LivingEntityRenderState, M extends EntityModel<? super T>> extends RenderLayer<T, M> {
    public SwetLatchLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, T parentState, float yRot, float xRot) {
        if (this.getParentModel() instanceof PlayerModel) {
            List<SwetLatchAttachment.LatchedSwetData> latchedSwetData = parentState.getRenderDataOrDefault(AetherIIRenderers.LATCHED_SWETS_KEY, List.of());
            for (int i = 0; i < latchedSwetData.size(); i++) {
                SwetLatchAttachment.LatchedSwetData data = latchedSwetData.get(i);
                poseStack.pushPose();
                SwetRenderState swetState = new SwetRenderState();
                swetState.entityType = data.type;
                swetState.swetScale = data.scale;
                swetState.lightCoords = parentState.lightCoords;
                swetState.outlineColor = parentState.outlineColor;
                float scale = (float) Math.cos(parentState.ageInTicks / 4.0F) / 20.0F;
                poseStack.scale(0.3F, 0.3F, 0.3F);
                if (i == 0) {
                    poseStack.mulPose(Axis.XN.rotationDegrees(90.0F));
                    poseStack.mulPose(Axis.YP.rotationDegrees(30.0F));
                    poseStack.translate(-0.4F, 0.2F, 1.3F);
                } else if (i == 1) {
                    poseStack.mulPose(Axis.XN.rotationDegrees(90.0F));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(30.0F));
                    poseStack.translate(-0.3F, 0.3F, 0.7F);
                } else if (i == 2) {
                    poseStack.mulPose(Axis.XN.rotationDegrees(-90.0F));
                    poseStack.mulPose(Axis.YP.rotationDegrees(-160.0F));
                    poseStack.translate(-0.2F, 0.3F, 1.0F);
                }
                poseStack.scale(1 + scale, 1 + scale, 1 + scale);
                EntityRenderer<?, ? super SwetRenderState> renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(swetState);
                CameraRenderState camerarenderstate = Minecraft.getInstance().gameRenderer.getGameRenderState().levelRenderState.cameraRenderState;
                renderer.submit(swetState, poseStack, submitNodeCollector, camerarenderstate);
                poseStack.popPose();
            }
        }
    }
}
