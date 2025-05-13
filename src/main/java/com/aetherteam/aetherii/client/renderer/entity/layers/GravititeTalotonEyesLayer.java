package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.GravititeTalotonModel;
import com.aetherteam.aetherii.client.renderer.entity.state.GravititeTalotonRenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class GravititeTalotonEyesLayer extends EyesLayer<GravititeTalotonRenderState, GravititeTalotonModel> {
    private static final RenderType GRAVITITE_TALOTON_EYES = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/gravitite_taloton/gravitite_taloton_eyes.png"));

    public GravititeTalotonEyesLayer(RenderLayerParent<GravititeTalotonRenderState, GravititeTalotonModel> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return GRAVITITE_TALOTON_EYES;
    }
}
