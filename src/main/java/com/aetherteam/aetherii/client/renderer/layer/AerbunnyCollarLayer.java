package com.aetherteam.aetherii.client.renderer.layer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherModelLayers;
import com.aetherteam.aetherii.client.renderer.model.AerbunnyModel;
import com.aetherteam.aetherii.entity.passive.Aerbunny;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;

public class AerbunnyCollarLayer extends TamableCollarLayer<Aerbunny, AerbunnyModel> {
    private static final ResourceLocation COLLAR_LOCATION = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/aerbunny/aerbunny_collar.png");

    public AerbunnyCollarLayer(RenderLayerParent<Aerbunny, AerbunnyModel> renderer, EntityModelSet modelSet) {
        super(renderer, new AerbunnyModel(modelSet.bakeLayer(AetherModelLayers.AERBUNNY_COLLAR)), COLLAR_LOCATION);
    }

    @Override
    public float[] getColor(Aerbunny aerbunny) {
        return aerbunny.getCollarColor().getTextureDiffuseColors();
    }
}
