package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.ArkeniumTalutonEyesLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.ArkeniumTalutonModel;
import com.aetherteam.aetherii.entity.monster.ArkeniumTaluton;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ArkeniumTalutonRenderer extends MobRenderer<ArkeniumTaluton, ArkeniumTalutonModel> {
    private static final ResourceLocation ARKENIUM_TALUTON_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/mobs/arkenium_taluton/arkenium_taluton.png");

    public ArkeniumTalutonRenderer(EntityRendererProvider.Context context) {
        super(context, new ArkeniumTalutonModel(context.bakeLayer(AetherIIModelLayers.ARKENIUM_TALUTON)), 0.5F);
        this.addLayer(new ArkeniumTalutonEyesLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ArkeniumTaluton arkeniumTaluton) {
        return ARKENIUM_TALUTON_TEXTURE;
    }
}
