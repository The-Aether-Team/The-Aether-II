package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.client.renderer.entity.model.GlitterwingModel;
import com.aetherteam.aetherii.client.renderer.entity.state.GlitterwingRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class GlitterwingGlowLayer extends RenderLayer<GlitterwingRenderState, GlitterwingModel> {
    public GlitterwingGlowLayer(RenderLayerParent<GlitterwingRenderState, GlitterwingModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, GlitterwingRenderState glitterwingRenderState, float v, float v1) {
         emissiveTexture = glitterwingRenderState.emissiveTexture;
        if (emissiveTexture != null) {
            RenderType renderType = RenderType.eyes(emissiveTexture);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(renderType);
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
        }
    }
}
