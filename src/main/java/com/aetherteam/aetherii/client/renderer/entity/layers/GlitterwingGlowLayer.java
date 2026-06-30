package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.client.renderer.entity.model.GlitterwingModel;
import com.aetherteam.aetherii.entity.passive.Glitterwing;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class GlitterwingGlowLayer extends RenderLayer<Glitterwing, GlitterwingModel> {
    public GlitterwingGlowLayer(RenderLayerParent<Glitterwing, GlitterwingModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Glitterwing glitterwing, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!glitterwing.isInvisible()) {
            glitterwing.getVariant().value().emissiveTexture().ifPresent(texture -> {
                VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.eyes(texture));
                this.getParentModel().renderToBuffer(poseStack, vertexConsumer, 15728640, LivingEntityRenderer.getOverlayCoords(glitterwing, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
            });
        }
    }
}
