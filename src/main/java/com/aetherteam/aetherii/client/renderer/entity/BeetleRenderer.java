package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.BeetleGlowLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.BeetleModel;
import com.aetherteam.aetherii.client.renderer.entity.state.BeetleRenderState;
import com.aetherteam.aetherii.entity.passive.Beetle;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BeetleRenderer extends MobRenderer<Beetle, BeetleRenderState, BeetleModel> {
    public BeetleRenderer(EntityRendererProvider.Context context) {
        super(context, new BeetleModel(context.bakeLayer(AetherIIModelLayers.BEETLE)), 0.25F);
        this.addLayer(new BeetleGlowLayer(this));
    }

    @Override
    protected void scale(BeetleRenderState renderState, PoseStack poseStack) {
        poseStack.scale(0.725F, 0.725F, 0.725F);
    }

    @Override
    public BeetleRenderState createRenderState() {
        return new BeetleRenderState();
    }

    @Override
    public void extractRenderState(Beetle beetle, BeetleRenderState state, float partialTicks) {
        super.extractRenderState(beetle, state, partialTicks);
        state.texture = beetle.getVariant().value().texture();
        state.emissiveTexture = beetle.getVariant().value().emissiveTexture().orElse(null);
        state.restScale = beetle.getRestAnimationScale(partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(BeetleRenderState renderState) {
        return renderState.texture;
    }
}
