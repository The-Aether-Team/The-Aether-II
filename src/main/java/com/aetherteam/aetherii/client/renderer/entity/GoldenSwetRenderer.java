package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.GoldenSwetGelLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.SwetModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SwetRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class GoldenSwetRenderer extends SwetRenderer {
    public static Identifier GOLDEN_SWET_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/swet/golden_swet.png");

    public GoldenSwetRenderer(EntityRendererProvider.Context context) {
        super(context, new SwetModel(context.bakeLayer(AetherIIModelLayers.BLUE_SWET), false));
        this.addLayer(new GoldenSwetGelLayer(this, context.getModelSet()));
    }

    @Override
    public Identifier getTextureLocation(SwetRenderState swetRenderState) {
        return GOLDEN_SWET_LOCATION;
    }
}
