package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.TempestModel;
import com.aetherteam.aetherii.client.renderer.entity.state.TempestRenderState;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class TempestEmissiveLayer extends EyesLayer<TempestRenderState, TempestModel> {
    private static final RenderType TEMPEST_MARKINGS = RenderTypes.eyes(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/tempest/tempest_emissive.png"));

    public TempestEmissiveLayer(RenderLayerParent<TempestRenderState, TempestModel> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return TEMPEST_MARKINGS;
    }
}
