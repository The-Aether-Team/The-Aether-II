package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.AerbunnyModel;
import com.aetherteam.aetherii.client.renderer.entity.state.AerbunnyRenderState;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;

public class AerbunnyCollarLayer extends TamableCollarLayer<AerbunnyRenderState, AerbunnyModel> {
    private static final ResourceLocation COLLAR_LOCATION = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/aerbunny/aerbunny_collar.png");

    public AerbunnyCollarLayer(RenderLayerParent<AerbunnyRenderState, AerbunnyModel> renderer, EntityModelSet modelSet) {
        super(renderer, new AerbunnyModel(modelSet.bakeLayer(AetherIIModelLayers.AERBUNNY_COLLAR)), COLLAR_LOCATION);
    }

    @Override
    public int getColor(AerbunnyRenderState aerbunny) {
        return aerbunny.collarColor.getTextureDiffuseColor();
    }
}
