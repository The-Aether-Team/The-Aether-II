package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.SentryGolemModel;
import com.aetherteam.aetherii.entity.monster.dungeon.SentryGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class SentryGolemLayer extends RenderLayer<SentryGolem, SentryGolemModel> {
    private static final RenderType GLOW = RenderType.entityTranslucentEmissive(new ResourceLocation(AetherII.MODID, "textures/entity/mobs/sentry_golem/sentry_golem_emissive.png"));
    private static final RenderType RANGED_GLOW = RenderType.entityTranslucentEmissive(new ResourceLocation(AetherII.MODID, "textures/entity/mobs/sentry_golem/sentry_golem_ranged_emissive.png"));

    public SentryGolemLayer(RenderLayerParent<SentryGolem, SentryGolemModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, SentryGolem sentry, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        RenderType renderType = sentry.isRanged() ? RANGED_GLOW : GLOW;
        VertexConsumer vertexConsumer = buffer.getBuffer(renderType);
        this.getParentModel().renderToBuffer(poseStack, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
