package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.client.renderer.entity.model.GlitterwingModel;
import com.aetherteam.aetherii.client.renderer.entity.state.GlitterwingRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class GlitterwingGlowLayer extends RenderLayer<GlitterwingRenderState, GlitterwingModel> {
    public GlitterwingGlowLayer(RenderLayerParent<GlitterwingRenderState, GlitterwingModel> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, GlitterwingRenderState glitterwingRenderState, float v, float v1) {
        Identifier emissiveTexture = glitterwingRenderState.emissiveTexture;
        if (emissiveTexture != null) {
            RenderType renderType = RenderTypes.eyes(emissiveTexture);
            submitNodeCollector.submitModel(this.getParentModel(), glitterwingRenderState, poseStack, renderType, i, OverlayTexture.NO_OVERLAY, glitterwingRenderState.outlineColor, null);
        }
    }
}
