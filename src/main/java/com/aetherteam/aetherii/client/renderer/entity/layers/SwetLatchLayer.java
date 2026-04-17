package com.aetherteam.aetherii.client.renderer.entity.layers;

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
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, T livingEntity, float v, float v1) {
        if (this.getParentModel() instanceof PlayerModel) {
            List<SwetRenderState> swets = livingEntity.getRenderDataOrDefault(AetherIIRenderers.SWET_KEY, List.of());
            for (int i = 0; i < swets.size(); i++) {
                SwetRenderState swet = swets.get(i);
                poseStack.pushPose();
                float scale = (float) Math.cos(livingEntity.ageInTicks / 4.0F) / 20.0F;
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
                EntityRenderer<?, ? super SwetRenderState> renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(swet);

                CameraRenderState camerarenderstate = Minecraft.getInstance().gameRenderer.getLevelRenderState().cameraRenderState;
                renderer.submit(swet, poseStack, submitNodeCollector, camerarenderstate);
                poseStack.popPose();
            }
        }
    }
}
