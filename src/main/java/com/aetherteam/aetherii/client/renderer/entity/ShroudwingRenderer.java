package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.ShroudwingGlowLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.ShroudwingModel;
import com.aetherteam.aetherii.entity.passive.Shroudwing;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ShroudwingRenderer extends MobRenderer<Shroudwing, ShroudwingModel> {
    public ShroudwingRenderer(EntityRendererProvider.Context context) {
        super(context, new ShroudwingModel(context.bakeLayer(AetherIIModelLayers.SHROUDWING)), 0.25F);
        this.addLayer(new ShroudwingGlowLayer(this));
    }

    @Override
    protected void scale(Shroudwing shroudwing, PoseStack poseStack, float partialTick) {
        poseStack.scale(0.725F, 0.725F, 0.725F);
        if (!shroudwing.isRest()) {
            poseStack.translate(0.0F, -0.25F, 0.0F);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(Shroudwing shroudwing) {
        return shroudwing.getVariant().value().texture();
    }
}
