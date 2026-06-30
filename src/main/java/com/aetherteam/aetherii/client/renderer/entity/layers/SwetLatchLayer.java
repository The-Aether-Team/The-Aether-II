package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.entity.monster.Swet;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class SwetLatchLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    public SwetLatchLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (livingEntity instanceof Player player && this.getParentModel() instanceof PlayerModel<?>) {
            List<Swet> swets = AetherIIDataAttachments.get(player, AetherIIDataAttachments.SWET_LATCH).getLatchedSwets();
            EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
            for (int i = 0; i < swets.size(); i++) {
                Swet swet = swets.get(i);
                poseStack.pushPose();
                float yRot = swet.getYRot();
                float xRot = swet.getXRot();
                float yRotO = swet.yRotO;
                float xRotO = swet.xRotO;
                float yBodyRot = swet.yBodyRot;
                float yBodyRotO = swet.yBodyRotO;
                float yHeadRot = swet.yHeadRot;
                float yHeadRotO = swet.yHeadRotO;
                swet.setYRot(0.0F);
                swet.setXRot(0.0F);
                swet.yRotO = 0.0F;
                swet.xRotO = 0.0F;
                swet.yBodyRot = 0.0F;
                swet.yBodyRotO = 0.0F;
                swet.yHeadRot = 0.0F;
                swet.yHeadRotO = 0.0F;
                float scale = (float) Math.cos(ageInTicks / 4.0F) / 20.0F;
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
                poseStack.scale(1.0F + scale, 1.0F + scale, 1.0F + scale);
                dispatcher.render(swet, 0.0, 0.0, 0.0, 0.0F, partialTick, poseStack, bufferSource, packedLight);
                swet.setYRot(yRot);
                swet.setXRot(xRot);
                swet.yRotO = yRotO;
                swet.xRotO = xRotO;
                swet.yBodyRot = yBodyRot;
                swet.yBodyRotO = yBodyRotO;
                swet.yHeadRot = yHeadRot;
                swet.yHeadRotO = yHeadRotO;
                poseStack.popPose();
            }
        }
    }
}
