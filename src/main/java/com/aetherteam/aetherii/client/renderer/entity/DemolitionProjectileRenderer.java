package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.DemolitionProjectileModel;
import com.aetherteam.aetherii.client.renderer.entity.state.DemolitionProjectileenderState;
import com.aetherteam.aetherii.entity.projectile.DemolitionProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class DemolitionProjectileRenderer extends EntityRenderer<DemolitionProjectile, DemolitionProjectileenderState> {
    private static final RenderType DEMOLITION_PROJECTILE = RenderType.entityCutoutNoCull(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/demolition_projectile.png"));
    private static final RenderType DEMOLITION_PROJECTILE_EMISSIVE = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/projectile/demolition_projectile_emissive.png"));
    private final DemolitionProjectileModel projectile;

    public DemolitionProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.projectile = new DemolitionProjectileModel(context.bakeLayer(AetherIIModelLayers.DEMOLITION_PROJECTILE));
    }

    @Override
    public DemolitionProjectileenderState createRenderState() {
        return new DemolitionProjectileenderState();
    }

    @Override
    public void extractRenderState(DemolitionProjectile entity, DemolitionProjectileenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.xRot = entity.getXRot(partialTick);
        reusedState.yRot = entity.getYRot(partialTick);
    }

    @Override
    public void render(DemolitionProjectileenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot));
        poseStack.scale(2.0F, 2.0F, 2.0F);
        poseStack.translate(0.0F, -1.1F, 0.0F);
        this.projectile.setupAnim(renderState);
        this.projectile.renderToBuffer(poseStack, bufferSource.getBuffer(DEMOLITION_PROJECTILE), packedLight, OverlayTexture.NO_OVERLAY);
        this.projectile.renderToBuffer(poseStack, bufferSource.getBuffer(DEMOLITION_PROJECTILE_EMISSIVE), packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();

        super.render(renderState, poseStack, bufferSource, packedLight);
    }
}
