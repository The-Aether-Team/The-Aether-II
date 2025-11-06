package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.DetonationProjectileModel;
import com.aetherteam.aetherii.client.renderer.entity.state.DetonationProjectileRenderState;
import com.aetherteam.aetherii.entity.projectile.DetonationProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class DetonationProjectileRenderer extends EntityRenderer<DetonationProjectile, DetonationProjectileRenderState> {
    private static final ResourceLocation SENTRY_LIT_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/detonation_sentry/detonation_sentry_lit.png");
    private static final RenderType SENTRY_EYE = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/detonation_sentry/eye.png"));
    private final DetonationProjectileModel projectile;

    public DetonationProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.projectile = new DetonationProjectileModel(context.bakeLayer(AetherIIModelLayers.DETONATION_PROJECTILE));
    }

    @Override
    public DetonationProjectileRenderState createRenderState() {
        return new DetonationProjectileRenderState();
    }

    @Override
    public void extractRenderState(DetonationProjectile entity, DetonationProjectileRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.xRot = entity.getXRot(partialTick);
        reusedState.yRot = entity.getYRot(partialTick);
    }

    @Override
    public void render(DetonationProjectileRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot));
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        this.projectile.setupAnim(renderState);
        this.projectile.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(renderState))), packedLight, OverlayTexture.NO_OVERLAY);
        this.projectile.renderToBuffer(poseStack, bufferSource.getBuffer(SENTRY_EYE), packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();


        super.render(renderState, poseStack, bufferSource, packedLight);
    }

    public ResourceLocation getTextureLocation(DetonationProjectileRenderState detonationProjectile) {
        return SENTRY_LIT_TEXTURE;
    }
}
