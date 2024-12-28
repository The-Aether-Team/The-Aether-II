package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.SwetGelLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.SwetModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SwetRenderState;
import com.aetherteam.aetherii.entity.monster.Swet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SwetRenderer extends MobRenderer<Swet, SwetRenderState, SwetModel<SwetRenderState>> {
    public static final ResourceLocation SWET_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/swet/blue_swet.png");

    public SwetRenderer(EntityRendererProvider.Context context) {
        super(context, new SwetModel<>(context.bakeLayer(AetherIIModelLayers.SWET)), 0.3F);
        this.addLayer(new SwetGelLayer(this, context.getModelSet()));
    }

    @Override
    public SwetRenderState createRenderState() {
        return new SwetRenderState();
    }

    @Override
    public void extractRenderState(Swet p_362733_, SwetRenderState p_360515_, float p_361157_) {
        super.extractRenderState(p_362733_, p_360515_, p_361157_);
        p_360515_.waterDamageScale = p_362733_.getWaterDamageScale();
        p_360515_.foodSaturation = p_362733_.getFoodSaturation();
        p_360515_.groundAnimationState.copyFrom(p_362733_.groundAnimationState);
        p_360515_.jumpAnimationState.copyFrom(p_362733_.jumpAnimationState);
    }

    @Override
    public ResourceLocation getTextureLocation(SwetRenderState aechorPlant) {
        return SWET_LOCATION;
    }

}
