package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.GravititeTalutonEyesLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.GravititeTalutonModel;
import com.aetherteam.aetherii.client.renderer.entity.state.GravititeTalutonRenderState;
import com.aetherteam.aetherii.entity.monster.GravititeTaluton;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class GravititeTalutonRenderer extends MobRenderer<GravititeTaluton, GravititeTalutonRenderState, GravititeTalutonModel> {
    private static final Identifier GRAVITITE_TALUTON_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/gravitite_taluton/gravitite_taluton.png");

    public GravititeTalutonRenderer(EntityRendererProvider.Context context) {
        super(context, new GravititeTalutonModel(context.bakeLayer(AetherIIModelLayers.GRAVITITE_TALUTON)), 0.5F);
        this.addLayer(new GravititeTalutonEyesLayer(this));
    }

    @Override
    public GravititeTalutonRenderState createRenderState() {
        return new GravititeTalutonRenderState();
    }

    @Override
    public void extractRenderState(GravititeTaluton entity, GravititeTalutonRenderState renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        renderState.attackAnimationState.copyFrom(entity.attackAnimationState);
        renderState.reloadAnimationState.copyFrom(entity.reloadAnimationState);
        renderState.debrisVisible = entity.debrisVisible;
        renderState.viewYRot = entity.getViewYRot(partialTick);
    }

    @Override
    protected void scale(GravititeTalutonRenderState renderState, PoseStack poseStack) {
        poseStack.translate(0.0, -0.3, 0.0);
    }

    @Override
    protected void setupRotations(GravititeTalutonRenderState renderState, PoseStack poseStack, float bodyRot, float scale) {
        if (renderState.deathTime > 0.0F) {
            float f = (renderState.deathTime - 1.0F) / 20.0F * 1.6F;
            f = Mth.sqrt(f);
            if (f > 1.0F) {
                f = 1.0F;
            }
            poseStack.translate(0.0, f * 0.3, 0.0);
            poseStack.mulPose(Axis.ZP.rotationDegrees(f * this.getFlipDegrees()));
        } else if (renderState.isUpsideDown) {
            poseStack.translate(0.0F, (renderState.boundingBoxHeight + 0.1F) / scale, 0.0F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        }
    }

    @Override
    public Identifier getTextureLocation(GravititeTalutonRenderState renderState) {
        return GRAVITITE_TALUTON_TEXTURE;
    }
}
