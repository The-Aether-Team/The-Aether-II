package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.SentryModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SentryRenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class DetonationSentryEmissiveLayer extends EyesLayer<SentryRenderState, SentryModel> {
    private static final RenderType MARK = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/detonation_sentry/eye.png"));

    public DetonationSentryEmissiveLayer(RenderLayerParent<SentryRenderState, SentryModel> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return MARK;
    }
}
