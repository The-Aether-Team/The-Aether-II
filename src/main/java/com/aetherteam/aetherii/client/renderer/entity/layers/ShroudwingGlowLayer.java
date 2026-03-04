package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.client.renderer.entity.model.ShroudwingModel;
import com.aetherteam.aetherii.client.renderer.entity.state.ShroudwingRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class ShroudwingGlowLayer extends RenderLayer<ShroudwingRenderState, ShroudwingModel> {
    public ShroudwingGlowLayer(RenderLayerParent<ShroudwingRenderState, ShroudwingModel> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, ShroudwingRenderState shroudwingRenderState, float v, float v1) {
        Identifier emissiveTexture = shroudwingRenderState.emissiveTexture;
        if (emissiveTexture != null) {
            RenderType renderType = RenderTypes.eyes(emissiveTexture);
            submitNodeCollector.order(1).submitModel(this.getParentModel(), shroudwingRenderState, poseStack, renderType, 15728640, OverlayTexture.NO_OVERLAY, -1, null, shroudwingRenderState.outlineColor, null);
        }
    }
}
