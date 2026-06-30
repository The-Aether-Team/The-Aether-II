package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.ArkeniumTalutonModel;
import com.aetherteam.aetherii.entity.monster.ArkeniumTaluton;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class ArkeniumTalutonEyesLayer extends EyesLayer<ArkeniumTaluton, ArkeniumTalutonModel> {
    private static final RenderType ARKENIUM_TALUTON_EYES = RenderType.entityTranslucentEmissive(new ResourceLocation(AetherII.MODID, "textures/entity/mobs/arkenium_taluton/arkenium_taluton_eyes.png"));

    public ArkeniumTalutonEyesLayer(RenderLayerParent<ArkeniumTaluton, ArkeniumTalutonModel> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return ARKENIUM_TALUTON_EYES;
    }
}
