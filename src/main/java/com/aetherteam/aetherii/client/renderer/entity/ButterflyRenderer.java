package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.ButterflyModel;
import com.aetherteam.aetherii.client.renderer.entity.state.ButterflyRenderState;
import com.aetherteam.aetherii.entity.passive.Butterfly;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ButterflyRenderer extends MobRenderer<Butterfly, ButterflyRenderState, ButterflyModel> {
    public ButterflyRenderer(EntityRendererProvider.Context context) {
        super(context, new ButterflyModel(context.bakeLayer(AetherIIModelLayers.BUTTERFLY)), 0.25F);
    }

    @Override
    protected void setupRotations(ButterflyRenderState renderState, PoseStack poseStack, float bodyRot, float scale) {
        super.setupRotations(renderState, poseStack, bodyRot, scale);
    }

    @Override
    protected void scale(ButterflyRenderState renderState, PoseStack poseStack) {
        poseStack.translate(0.0, -0.125, 0.0);
    }

    @Override
    public ButterflyRenderState createRenderState() {
        return new ButterflyRenderState();
    }

    @Override
    public void extractRenderState(Butterfly butterfly, ButterflyRenderState state, float partialTicks) {
        super.extractRenderState(butterfly, state, partialTicks);
        state.texture = butterfly.getVariant().value().texture();
        state.wingXOffset = butterfly.getVariant().value().wingXOffset();
        state.wingZRotation = butterfly.getVariant().value().wingZRotation();
    }

    @Override
    public ResourceLocation getTextureLocation(ButterflyRenderState renderState) {
        return renderState.texture;
    }
}
