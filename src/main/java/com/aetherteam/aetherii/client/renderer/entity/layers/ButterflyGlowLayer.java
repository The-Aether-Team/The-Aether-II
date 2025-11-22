package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.client.renderer.entity.model.ButterflyModel;
import com.aetherteam.aetherii.client.renderer.entity.state.ButterflyRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ButterflyGlowLayer extends RenderLayer<ButterflyRenderState, ButterflyModel> {
    public ButterflyGlowLayer(RenderLayerParent<ButterflyRenderState, ButterflyModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, ButterflyRenderState butterflyRenderState, float v, float v1) {
        ResourceLocation emissiveTexture = butterflyRenderState.emissiveTexture;
        if (emissiveTexture != null) {
            RenderType renderType = RenderType.eyes(emissiveTexture);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(renderType);
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
        }
    }
}
