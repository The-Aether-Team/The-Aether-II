package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.SentryGolemModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SentryGolemRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;

public class SentryGolemLayer extends EyesLayer<SentryGolemRenderState, SentryGolemModel> {
    private static final RenderType GLOW = RenderTypes.eyes(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/sentry_golem/sentry_golem_emissive.png"));
    private static final RenderType RANGED_GLOW = RenderTypes.eyes(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/sentry_golem/sentry_golem_ranged_emissive.png"));

    public SentryGolemLayer(RenderLayerParent<SentryGolemRenderState, SentryGolemModel> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int p_434650_, SentryGolemRenderState sentry, float p_433542_, float p_435619_) {
        RenderType renderType = sentry.ranged ? RANGED_GLOW : GLOW;

        submitNodeCollector.order(1).submitModel(this.getParentModel(), sentry, poseStack, renderType, 15728640, OverlayTexture.NO_OVERLAY, -1, (TextureAtlasSprite) null, sentry.outlineColor, (ModelFeatureRenderer.CrumblingOverlay) null);
    }

    @Override
    public RenderType renderType() {
        return GLOW;
    }
}
