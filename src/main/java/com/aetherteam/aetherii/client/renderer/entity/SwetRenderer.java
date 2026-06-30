package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.SwetGelLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.SwetModel;
import com.aetherteam.aetherii.entity.monster.Swet;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SwetRenderer extends MobRenderer<Swet, SwetModel<Swet>> {
    private static final ResourceLocation BLUE_SWET_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/swet/blue_swet.png");

    public SwetRenderer(EntityRendererProvider.Context context) {
        this(context, new SwetModel<>(context.bakeLayer(AetherIIModelLayers.SWET)));
        this.addLayer(new SwetGelLayer(this, context.getModelSet()));
    }

    protected SwetRenderer(EntityRendererProvider.Context context, SwetModel<Swet> model) {
        super(context, model, 0.3F);
    }

    @Override
    protected void scale(Swet swet, PoseStack poseStack, float partialTick) {
        float minScale = 0.6F;
        float defaultScale = 0.95F;
        float scaleDiff = defaultScale - swet.getSwetScale();
        float scaleRange = defaultScale - minScale;
        poseStack.translate(0.0F, Mth.clamp(scaleDiff / scaleRange, 0.0F, 0.2F), 0.0F);
        super.scale(swet, poseStack, partialTick);
    }

    @Override
    public ResourceLocation getTextureLocation(Swet swet) {
        return BLUE_SWET_TEXTURE;
    }
}
