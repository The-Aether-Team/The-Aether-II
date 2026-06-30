package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.GravititeTalutonModel;
import com.aetherteam.aetherii.entity.monster.GravititeTaluton;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class GravititeTalutonEyesLayer extends EyesLayer<GravititeTaluton, GravititeTalutonModel> {
    private static final RenderType GRAVITITE_TALUTON_EYES = RenderType.entityTranslucentEmissive(new ResourceLocation(AetherII.MODID, "textures/entity/mobs/gravitite_taluton/gravitite_taluton_eyes.png"));

    public GravititeTalutonEyesLayer(RenderLayerParent<GravititeTaluton, GravititeTalutonModel> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return GRAVITITE_TALUTON_EYES;
    }
}
