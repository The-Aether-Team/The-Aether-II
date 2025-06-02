package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.SwetGelLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.SwetModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SwetRenderState;
import com.aetherteam.aetherii.entity.monster.Swet;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SwetRenderer extends MobRenderer<Swet, SwetRenderState, SwetModel<SwetRenderState>> {
    public SwetRenderer(EntityRendererProvider.Context context) {
        super(context, new SwetModel<>(context.bakeLayer(AetherIIModelLayers.SWET), false), 0.3F);
        this.addLayer(new SwetGelLayer(this, context.getModelSet()));
    }

    @Override
    public SwetRenderState createRenderState() {
        return new SwetRenderState();
    }

    @Override
    public void extractRenderState(Swet entity, SwetRenderState renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        renderState.jumpAnimationState.copyFrom(entity.jumpAnimationState);
        renderState.groundAnimationState.copyFrom(entity.groundAnimationState);
        renderState.texture = entity.getVariant().value().texture();
        renderState.swetScale = entity.getSwetScale();
    }

    @Override
    protected void scale(SwetRenderState renderState, PoseStack poseStack) {
        float minScale = 0.6F;
        float defaultScale = 0.95F;
        float currentScale = renderState.swetScale;
        float scaleRange = defaultScale - minScale;
        float scaleDiff = defaultScale - currentScale;

        poseStack.translate(0.0F, Mth.clamp((scaleDiff / scaleRange), 0.0F, 0.2F), 0.0F);

        super.scale(renderState, poseStack);
    }

    @Override
    public ResourceLocation getTextureLocation(SwetRenderState renderState) {
        return renderState.texture;
    }
}
