package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.ButterflyGlowLayer;
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
        this.addLayer(new ButterflyGlowLayer(this));
    }

    @Override
    protected void scale(ButterflyRenderState renderState, PoseStack poseStack) {
        poseStack.translate(0.0F, -0.125F, 0.0F);
        poseStack.scale(0.725F, 0.725F, 0.725F);
    }

    @Override
    public ButterflyRenderState createRenderState() {
        return new ButterflyRenderState();
    }

    @Override
    public void extractRenderState(Butterfly butterfly, ButterflyRenderState state, float partialTicks) {
        super.extractRenderState(butterfly, state, partialTicks);
        state.texture = butterfly.getVariant().value().texture();
        state.emissiveTexture = butterfly.getVariant().value().emissiveTexture().orElse(null);
        state.wingXOffset = butterfly.getVariant().value().wingXOffset();
        state.wingZRotation = butterfly.getVariant().value().wingZRotation();
    }

    @Override
    public ResourceLocation getTextureLocation(ButterflyRenderState renderState) {
        return renderState.texture;
    }
}
