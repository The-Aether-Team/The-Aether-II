package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.SentryGolemModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SentryGolemRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class SentryGolemLayer extends EyesLayer<SentryGolemRenderState, SentryGolemModel> {
    private static final RenderType GLOW = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/sentry_golem/sentry_golem_emissive.png"));
    private static final RenderType RANGED_GLOW = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/sentry_golem/sentry_golem_ranged_emissive.png"));

    public SentryGolemLayer(RenderLayerParent<SentryGolemRenderState, SentryGolemModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, SentryGolemRenderState sentry, float netHeadYaw, float headPitch) {
        VertexConsumer vertexconsumer = buffer.getBuffer(sentry.ranged ? RANGED_GLOW : GLOW);
        this.getParentModel().renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
    }

    @Override
    public RenderType renderType() {
        return GLOW;
    }
}
