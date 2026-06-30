package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.GlitterwingGlowLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.GlitterwingModel;
import com.aetherteam.aetherii.entity.passive.Glitterwing;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GlitterwingRenderer extends MobRenderer<Glitterwing, GlitterwingModel> {
    public GlitterwingRenderer(EntityRendererProvider.Context context) {
        super(context, new GlitterwingModel(context.bakeLayer(AetherIIModelLayers.GLITTERWING)), 0.25F);
        this.addLayer(new GlitterwingGlowLayer(this));
    }

    @Override
    protected void scale(Glitterwing glitterwing, PoseStack poseStack, float partialTick) {
        poseStack.translate(0.0F, -0.125F, 0.0F);
        poseStack.scale(0.725F, 0.725F, 0.725F);
    }

    @Override
    public ResourceLocation getTextureLocation(Glitterwing glitterwing) {
        return glitterwing.getVariant().value().texture();
    }
}
