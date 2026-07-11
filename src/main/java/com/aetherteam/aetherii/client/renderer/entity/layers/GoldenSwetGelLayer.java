package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.GoldenSwetRenderer;
import com.aetherteam.aetherii.client.renderer.entity.model.SwetModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SwetRenderState;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.Identifier;

public class GoldenSwetGelLayer extends SwetGelLayer {
    public GoldenSwetGelLayer(RenderLayerParent<SwetRenderState, SwetModel> renderer, EntityModelSet modelSet) {
        super(renderer, new SwetModel(modelSet.bakeLayer(AetherIIModelLayers.GOLDEN_SWET), true));
    }

    @Override
    public Identifier getTextureLocation(SwetRenderState swetRenderState) {
        return GoldenSwetRenderer.GOLDEN_SWET_LOCATION;
    }
}
