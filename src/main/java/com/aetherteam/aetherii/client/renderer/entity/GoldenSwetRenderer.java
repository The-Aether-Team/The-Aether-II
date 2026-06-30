package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.GoldenSwetGelLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.SwetModel;
import com.aetherteam.aetherii.entity.monster.Swet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class GoldenSwetRenderer extends SwetRenderer {
    public static final ResourceLocation GOLDEN_SWET_LOCATION = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/swet/golden_swet.png");

    public GoldenSwetRenderer(EntityRendererProvider.Context context) {
        super(context, new SwetModel<>(context.bakeLayer(AetherIIModelLayers.GOLDEN_SWET)));
        this.addLayer(new GoldenSwetGelLayer(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(Swet swet) {
        return GOLDEN_SWET_LOCATION;
    }
}
