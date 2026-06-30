package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.GravititeTalutonEyesLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.GravititeTalutonModel;
import com.aetherteam.aetherii.entity.monster.GravititeTaluton;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GravititeTalutonRenderer extends MobRenderer<GravititeTaluton, GravititeTalutonModel> {
    private static final ResourceLocation GRAVITITE_TALUTON_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/gravitite_taluton/gravitite_taluton.png");

    public GravititeTalutonRenderer(EntityRendererProvider.Context context) {
        super(context, new GravititeTalutonModel(context.bakeLayer(AetherIIModelLayers.GRAVITITE_TALUTON)), 0.5F);
        this.addLayer(new GravititeTalutonEyesLayer(this));
    }

    @Override
    protected void scale(GravititeTaluton gravititeTaluton, PoseStack poseStack, float partialTick) {
        poseStack.translate(0.0, -0.3, 0.0);
    }

    @Override
    public ResourceLocation getTextureLocation(GravititeTaluton gravititeTaluton) {
        return GRAVITITE_TALUTON_TEXTURE;
    }
}
