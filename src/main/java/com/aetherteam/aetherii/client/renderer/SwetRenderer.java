package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.layers.SwetGelLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.SwetModel;
import com.aetherteam.aetherii.entity.monster.Swet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SwetRenderer extends MobRenderer<Swet, SwetModel<Swet>> {
    private static final ResourceLocation SWET_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/swet/blue_swet.png");

    public SwetRenderer(EntityRendererProvider.Context context) {
        super(context, new SwetModel<>(context.bakeLayer(AetherIIModelLayers.SWET)), 0.3F);
        this.addLayer(new SwetGelLayer(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(Swet aechorPlant) {
        return SWET_LOCATION;
    }
}
