package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.client.renderer.entity.model.SwetModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SwetRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public abstract class SwetGelLayer extends RenderLayer<SwetRenderState, SwetModel> {
    private final SwetModel model;

    public SwetGelLayer(RenderLayerParent<SwetRenderState, SwetModel> renderer, SwetModel model) {
        super(renderer);
        this.model = model;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, SwetRenderState swetRenderState, float v, float v1) {
        boolean flag = swetRenderState.appearsGlowing() && swetRenderState.isInvisible;
        if (!swetRenderState.isInvisible || flag) {
            this.model.setupAnim(swetRenderState);
            RenderType renderType = flag
                    ? RenderTypes.outline(this.getTextureLocation(swetRenderState))
                    : RenderTypes.entityTranslucent(this.getTextureLocation(swetRenderState));
            submitNodeCollector.submitModel(this.model, swetRenderState, poseStack, renderType, swetRenderState.lightCoords, OverlayTexture.NO_OVERLAY, swetRenderState.outlineColor, null);
        }
    }

    public abstract Identifier getTextureLocation(SwetRenderState swetRenderState);
}
