package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.GravititeDebrisShotModel;
import com.aetherteam.aetherii.entity.projectile.GravititeDebrisShot;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class GravititeDebrisShotRenderer extends EntityRenderer<GravititeDebrisShot> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(AetherII.MODID, "textures/entity/projectile/gravitite_debris_shot.png");
    private final GravititeDebrisShotModel model;

    public GravititeDebrisShotRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new GravititeDebrisShotModel(context.bakeLayer(AetherIIModelLayers.GRAVITITE_DEBRIS_SHOT));
    }

    @Override
    public void render(GravititeDebrisShot entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.25F, 0.0F);
        poseStack.mulPose(Axis.XN.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot())));
        poseStack.mulPose(Axis.YN.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot())));
        this.model.setupAnim(entity, 0.0F, 0.0F, entity.tickCount + partialTick, 0.0F, 0.0F);
        VertexConsumer consumer = buffer.getBuffer(this.model.renderType(TEXTURE_LOCATION));
        this.model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(GravititeDebrisShot entity) {
        return TEXTURE_LOCATION;
    }
}
