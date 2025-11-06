package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.layers.SentryGolemLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.DetonationProjectileModel;
import com.aetherteam.aetherii.client.renderer.entity.model.SentryGolemModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SentryGolemRenderState;
import com.aetherteam.aetherii.entity.monster.dungeon.SentryGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class SentryGolemRenderer extends MobRenderer<SentryGolem, SentryGolemRenderState, SentryGolemModel> {
    private static final ResourceLocation SENTRY_GOLEM_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/sentry_golem/sentry_golem.png");
    private static final ResourceLocation SENTRY_LIT_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/detonation_sentry/detonation_sentry_lit.png");
    private static final RenderType SENTRY_EYE = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/detonation_sentry/eye.png"));
    private final DetonationProjectileModel projectile;

    public SentryGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new SentryGolemModel(context.bakeLayer(AetherIIModelLayers.SENTRY_GOLEM)), 0.7F);
        this.addLayer(new SentryGolemLayer(this));
        this.projectile = new DetonationProjectileModel(context.bakeLayer(AetherIIModelLayers.DETONATION_PROJECTILE));
    }

    @Override
    public SentryGolemRenderState createRenderState() {
        return new SentryGolemRenderState();
    }

    @Override
    public void extractRenderState(SentryGolem p_362733_, SentryGolemRenderState p_360515_, float p_361157_) {
        super.extractRenderState(p_362733_, p_360515_, p_361157_);
        p_360515_.armState = p_362733_.getHandState();
        p_360515_.progress = p_362733_.progress;
        p_360515_.fireTime = p_362733_.getFireTime();
    }

    @Override
    public void render(SentryGolemRenderState renderState, PoseStack p_115311_, MultiBufferSource p_115312_, int p_115313_) {
        super.render(renderState, p_115311_, p_115312_, p_115313_);
        if (renderState.deathTime <= 0.0F) {
            this.renderBomb(renderState, renderState.partialTick, p_115311_, p_115312_, p_115313_);
        }
    }

    public void renderBomb(SentryGolemRenderState golem, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        float progress = golem.progress;
        float scale = 1.0F;
        if (golem.armState == 3) {
            return;
        }

        if (golem.armState == 2) {
            if (!((double) progress < 0.5)) {
                return;
            }

            scale = Math.min(1.0F - (float) (golem.fireTime - 30) / 30.0F, 0.9F);
            scale *= 1.1F;
        }

        poseStack.pushPose();
        poseStack.translate(0.0F, 4.2F, 0.0F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(golem.bodyRot));
        poseStack.translate(0.0, Math.sin(1.0F - progress) * 2.4 + 1.65, Math.sin(1.0F - progress) * -1.4);
        poseStack.scale(scale, scale, scale);
        this.projectile.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityCutoutNoCull(SENTRY_LIT_TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY);
        this.projectile.renderToBuffer(poseStack, buffer.getBuffer(SENTRY_EYE), packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(SentryGolemRenderState golem) {
        return SENTRY_GOLEM_TEXTURE;
    }
}
