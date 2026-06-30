package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.DemolitionProjectileModel;
import com.aetherteam.aetherii.entity.projectile.DemolitionProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DemolitionProjectileRenderer extends EntityRenderer<DemolitionProjectile> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/projectile/demolition_projectile.png");
    private static final ResourceLocation EMISSIVE_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/projectile/demolition_projectile_emissive.png");
    private static final RenderType EMISSIVE = RenderType.eyes(EMISSIVE_TEXTURE);
    private final DemolitionProjectileModel model;

    public DemolitionProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new DemolitionProjectileModel(context.bakeLayer(AetherIIModelLayers.DEMOLITION_PROJECTILE));
    }

    @Override
    public void render(DemolitionProjectile entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot())));
        poseStack.scale(2.0F, 2.0F, 2.0F);
        poseStack.translate(0.0F, -1.1F, 0.0F);
        this.model.setupAnim(entity, 0.0F, 0.0F, entity.tickCount + partialTick, 0.0F, 0.0F);
        VertexConsumer consumer = buffer.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        VertexConsumer emissiveConsumer = buffer.getBuffer(EMISSIVE);
        this.model.renderToBuffer(poseStack, emissiveConsumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    protected int getBlockLightLevel(DemolitionProjectile entity, BlockPos pos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(DemolitionProjectile entity) {
        return TEXTURE;
    }
}
