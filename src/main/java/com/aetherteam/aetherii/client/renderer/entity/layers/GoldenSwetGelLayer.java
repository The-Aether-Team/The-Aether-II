package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.GoldenSwetRenderer;
import com.aetherteam.aetherii.client.renderer.entity.model.SwetModel;
import com.aetherteam.aetherii.entity.monster.Swet;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;

public class GoldenSwetGelLayer extends SwetGelLayer {
    public GoldenSwetGelLayer(RenderLayerParent<Swet, SwetModel<Swet>> renderer, EntityModelSet modelSet) {
        super(renderer, modelSet, new SwetModel<>(modelSet.bakeLayer(AetherIIModelLayers.GOLDEN_SWET), true));
    }

    @Override
    public ResourceLocation getTextureLocation(Swet swet) {
        return GoldenSwetRenderer.GOLDEN_SWET_LOCATION;
    }
}
