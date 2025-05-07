package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.ArkeniumTalotonModel;
import com.aetherteam.aetherii.client.renderer.entity.state.ArkeniumTalotonRenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class ArkeniumTalotonEyesLayer extends EyesLayer<ArkeniumTalotonRenderState, ArkeniumTalotonModel> {
    private static final RenderType ARKENIUM_TALOTON_EYES = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/arkenium_taloton/arkenium_taloton_eyes.png"));

    public ArkeniumTalotonEyesLayer(RenderLayerParent<ArkeniumTalotonRenderState, ArkeniumTalotonModel> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return ARKENIUM_TALOTON_EYES;
    }
}
