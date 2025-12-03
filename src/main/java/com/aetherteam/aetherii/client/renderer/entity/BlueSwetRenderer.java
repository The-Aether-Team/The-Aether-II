package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.BlueSwetGelLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.SwetModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SwetRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BlueSwetRenderer extends SwetRenderer {
    public static ResourceLocation BLUE_SWET_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/swet/blue_swet.png");

    public BlueSwetRenderer(EntityRendererProvider.Context context) {
        super(context, new SwetModel<>(context.bakeLayer(AetherIIModelLayers.BLUE_SWET), false));
        this.addLayer(new BlueSwetGelLayer(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(SwetRenderState swetRenderState) {
        return BLUE_SWET_LOCATION;
    }
}
