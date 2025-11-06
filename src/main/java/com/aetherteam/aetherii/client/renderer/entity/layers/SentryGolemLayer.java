package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.SentryGolemModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SentryGolemRenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class SentryGolemLayer extends EyesLayer<SentryGolemRenderState, SentryGolemModel> {
    private static final RenderType MARK = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/sentry_golem/sentry_golem_glow.png"));

    public SentryGolemLayer(RenderLayerParent<SentryGolemRenderState, SentryGolemModel> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return MARK;
    }
}
