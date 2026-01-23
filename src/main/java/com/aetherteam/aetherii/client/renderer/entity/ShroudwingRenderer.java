package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.ShroudwingGlowLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.ShroudwingModel;
import com.aetherteam.aetherii.client.renderer.entity.state.ShroudwingRenderState;
import com.aetherteam.aetherii.entity.passive.Shroudwing;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ShroudwingRenderer extends MobRenderer<Shroudwing, ShroudwingRenderState, ShroudwingModel> {
    public ShroudwingRenderer(EntityRendererProvider.Context context) {
        super(context, new ShroudwingModel(context.bakeLayer(AetherIIModelLayers.SHROUDWING)), 0.25F);
        this.addLayer(new ShroudwingGlowLayer(this));
    }

    @Override
    protected void scale(ShroudwingRenderState renderState, PoseStack poseStack) {
        poseStack.scale(0.725F, 0.725F, 0.725F);
    }

    @Override
    public ShroudwingRenderState createRenderState() {
        return new ShroudwingRenderState();
    }

    @Override
    public void extractRenderState(Shroudwing shroudwing, ShroudwingRenderState state, float partialTicks) {
        super.extractRenderState(shroudwing, state, partialTicks);
        state.texture = shroudwing.getVariant().value().texture();
        state.emissiveTexture = shroudwing.getVariant().value().emissiveTexture().orElse(null);
        state.restScale = shroudwing.getRestAnimationScale(partialTicks);
        state.landAnimationState.copyFrom(shroudwing.landAnimationState);
        state.takeOffAnimationState.copyFrom(shroudwing.takeOffAnimationState);
    }

    @Override
    public ResourceLocation getTextureLocation(ShroudwingRenderState renderState) {
        return renderState.texture;
    }
}
