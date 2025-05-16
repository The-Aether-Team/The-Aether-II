package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.GravititeTalutonEyesLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.GravititeTalutonModel;
import com.aetherteam.aetherii.client.renderer.entity.state.GravititeTalutonRenderState;
import com.aetherteam.aetherii.entity.monster.GravititeTaluton;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class GravititeTalutonRenderer extends MobRenderer<GravititeTaluton, GravititeTalutonRenderState, GravititeTalutonModel> {
    private static final ResourceLocation GRAVITITE_TALUTON_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/gravitite_taluton/gravitite_taluton.png");

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
        renderState.legRot = Mth.rotLerp(partialTick, entity.getLegRotO(), entity.getLegRot());
        renderState.debrisRot = Mth.rotLerp(partialTick, entity.getDebrisRot0(), entity.getDebrisRot());
    }

    @Override
    protected void scale(GravititeTalutonRenderState renderState, PoseStack poseStack) {
        poseStack.translate(0.0, -0.3, 0.0);
        float sin = Mth.sin((renderState.ageInTicks + renderState.partialTick) / 6);
        poseStack.translate(0.0, sin / 15, 0.0);
    }

    @Override
    protected void setupRotations(GravititeTalutonRenderState renderState, PoseStack poseStack, float bodyRot, float scale) { }

    @Override
    public ResourceLocation getTextureLocation(GravititeTalutonRenderState renderState) {
        return GRAVITITE_TALUTON_TEXTURE;
    }
}
