package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.ArkeniumTalotonEyesLayer;
import com.aetherteam.aetherii.client.renderer.entity.layers.GravititeTalotonEyesLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.ArkeniumTalotonModel;
import com.aetherteam.aetherii.client.renderer.entity.model.GravititeTalotonModel;
import com.aetherteam.aetherii.client.renderer.entity.state.ArkeniumTalotonRenderState;
import com.aetherteam.aetherii.client.renderer.entity.state.GravititeTalotonRenderState;
import com.aetherteam.aetherii.entity.monster.ArkeniumTaloton;
import com.aetherteam.aetherii.entity.monster.GravititeTaloton;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class GravititeTalotonRenderer extends MobRenderer<GravititeTaloton, GravititeTalotonRenderState, GravititeTalotonModel> {
    private static final ResourceLocation GRAVITITE_TALOTON_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/gravitite_taloton/gravitite_taloton.png");

    public GravititeTalotonRenderer(EntityRendererProvider.Context context) {
        super(context, new GravititeTalotonModel(context.bakeLayer(AetherIIModelLayers.GRAVITITE_TALOTON)), 0.5F);
        this.addLayer(new GravititeTalotonEyesLayer(this));
    }

    @Override
    public GravititeTalotonRenderState createRenderState() {
        return new GravititeTalotonRenderState();
    }

    @Override
    public void extractRenderState(GravititeTaloton entity, GravititeTalotonRenderState renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        renderState.legRot = Mth.rotLerp(partialTick, entity.getLegRotO(), entity.getLegRot());
        renderState.debrisRot = Mth.rotLerp(partialTick, entity.getDebrisRot0(), entity.getDebrisRot());
    }

    @Override
    protected void scale(GravititeTalotonRenderState renderState, PoseStack poseStack) {
        poseStack.translate(0.0, -0.3, 0.0);
        float sin = Mth.sin((renderState.ageInTicks + renderState.partialTick) / 6);
        poseStack.translate(0.0, sin / 15, 0.0);
    }

    @Override
    protected void setupRotations(GravititeTalotonRenderState renderState, PoseStack poseStack, float bodyRot, float scale) { }

    @Override
    public ResourceLocation getTextureLocation(GravititeTalotonRenderState renderState) {
        return GRAVITITE_TALOTON_TEXTURE;
    }
}
