package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.TempestModel;
import com.aetherteam.aetherii.entity.monster.Tempest;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class TempestEmissiveLayer extends EyesLayer<Tempest, TempestModel> {
    private static final RenderType TEMPEST_MARKINGS = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/tempest/tempest_emissive.png"));

    public TempestEmissiveLayer(RenderLayerParent<Tempest, TempestModel> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return TEMPEST_MARKINGS;
    }
}
