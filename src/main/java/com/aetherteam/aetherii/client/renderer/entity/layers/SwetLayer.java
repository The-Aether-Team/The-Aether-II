package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.client.renderer.AetherIIRenderers;
import com.aetherteam.aetherii.entity.monster.Swet;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

import java.util.List;

public class SwetLayer<T extends LivingEntityRenderState, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final EntityRenderDispatcher dispatcher;

    public SwetLayer(EntityRendererProvider.Context context, RenderLayerParent<T, M> renderer) {
        super(renderer);
        this.dispatcher = context.getEntityRenderDispatcher();
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float netHeadYaw, float headPitch) {
        if (this.getParentModel() instanceof PlayerModel) {
            List<Swet> swets = livingEntity.getRenderDataOrDefault(AetherIIRenderers.SWET_KEY, List.of());
            for (int i = 0; i < swets.size(); i++) {
                Swet swet = swets.get(i);
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
                this.dispatcher.render(swet, 0.0, 0.0, 0.0, livingEntity.partialTick, poseStack, buffer, packedLight);
                poseStack.popPose();
            }
        }
    }
}
