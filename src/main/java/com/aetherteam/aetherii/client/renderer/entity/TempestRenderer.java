package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.TempestEmissiveLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.TempestModel;
import com.aetherteam.aetherii.client.renderer.entity.state.TempestRenderState;
import com.aetherteam.aetherii.entity.monster.Tempest;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/**
 * [CODE COPY] - {@link com.aetherteam.aether.client.renderer.entity.ZephyrRenderer}.
 */
public class TempestRenderer extends MobRenderer<Tempest, TempestRenderState, TempestModel> {
    private static final ResourceLocation TEMPEST_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/tempest/tempest.png");

    public TempestRenderer(EntityRendererProvider.Context context) {
        super(context, new TempestModel(context.bakeLayer(AetherIIModelLayers.TEMPEST)), 0.5F);
        this.addLayer(new TempestEmissiveLayer(this));
    }

    @Override
    protected void scale(TempestRenderState tempest, PoseStack poseStack) {
        poseStack.translate(0.0, 0.27, 0.0);
        poseStack.translate(0.0, -0.1, 0.0);
        float sin = Mth.sin((tempest.ageInTicks) / 6);
        poseStack.translate(0.0, sin / 15, 0.0);
    }

    @Override
    public void extractRenderState(Tempest p_362733_, TempestRenderState p_360515_, float p_361157_) {
        super.extractRenderState(p_362733_, p_360515_, p_361157_);
        p_360515_.attackAnimationState.copyFrom(p_362733_.attackAnimationState);
        p_360515_.hideAnimationState.copyFrom(p_362733_.hideAnimationState);
    }

    @Override
    public TempestRenderState createRenderState() {
        return new TempestRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(TempestRenderState renderState) {
        return TEMPEST_TEXTURE;
    }
}