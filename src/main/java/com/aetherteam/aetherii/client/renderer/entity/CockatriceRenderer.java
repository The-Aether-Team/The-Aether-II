package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.CockatriceEmissiveLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.CockatriceModel;
import com.aetherteam.aetherii.entity.monster.Cockatrice;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EnderEyesLayer;
import net.minecraft.resources.ResourceLocation;

public class CockatriceRenderer extends MobRenderer<Cockatrice, CockatriceModel> {
    private static final ResourceLocation COCKATRICE_PLANT_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/cockatrice/cockatrice.png");

    public CockatriceRenderer(EntityRendererProvider.Context context) {
        super(context, new CockatriceModel(context.bakeLayer(AetherIIModelLayers.COCKATRICE)), 0.3F);
        this.addLayer(new CockatriceEmissiveLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Cockatrice aechorPlant) {
        return COCKATRICE_PLANT_TEXTURE;
    }
}
