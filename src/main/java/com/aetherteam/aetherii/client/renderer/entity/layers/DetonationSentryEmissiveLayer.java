package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.SentryModel;
import com.aetherteam.aetherii.client.renderer.entity.state.DetonationSentryRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class DetonationSentryEmissiveLayer extends EyesLayer<DetonationSentryRenderState, SentryModel> {
    private static final RenderType MARK = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/detonation_sentry/eye.png"));

    public DetonationSentryEmissiveLayer(RenderLayerParent<DetonationSentryRenderState, SentryModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack p_116983_, MultiBufferSource p_116984_, int p_116985_, DetonationSentryRenderState p_363277_, float p_116987_, float p_116988_) {
        if (p_363277_.awake) {
            super.render(p_116983_, p_116984_, p_116985_, p_363277_, p_116987_, p_116988_);
        }
    }

    @Override
    public RenderType renderType() {
        return MARK;
    }
}
