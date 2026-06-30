package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.blockentity.VaseBlockEntity;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.VaseModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class VaseRenderer implements BlockEntityRenderer<VaseBlockEntity> {
    private static final float WOBBLE_AMPLITUDE = 0.1F;

    private final VaseModel model;

    public VaseRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new VaseModel(context.getModelSet().bakeLayer(AetherIIModelLayers.VASE));
    }

    @Override
    public void render(VaseBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - blockEntity.getDirection().toYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));

        VaseBlockEntity.WobbleStyle wobbleStyle = blockEntity.lastWobbleStyle;
        if (wobbleStyle != null && blockEntity.getLevel() != null) {
            float wobbleProgress = ((float) (blockEntity.getLevel().getGameTime() - blockEntity.wobbleStartedAtTick) + partialTick) / wobbleStyle.duration;
            if (wobbleProgress >= 0.0F && wobbleProgress <= 1.0F) {
                if (wobbleStyle == VaseBlockEntity.WobbleStyle.POSITIVE) {
                    float f = 0.015625F;
                    float f1 = wobbleProgress * ((float) Math.PI * 2.0F);
                    float f2 = -1.5F * (Mth.cos(f1) + 0.5F) * Mth.sin(f1 / 2.0F);
                    poseStack.rotateAround(Axis.XP.rotation(f2 * f), 0.0F, 0.0F, 0.0F);
                    float f3 = Mth.sin(f1);
                    poseStack.rotateAround(Axis.ZP.rotation(f3 * f), 0.0F, 0.0F, 0.0F);
                } else {
                    float f4 = Mth.sin(-wobbleProgress * 3.0F * (float) Math.PI) * WOBBLE_AMPLITUDE;
                    float f5 = 1.0F - wobbleProgress;
                    poseStack.rotateAround(Axis.YP.rotation(f4 * f5), 0.0F, 0.0F, 0.0F);
                }
            }
        }

        ResourceLocation texture = texture(BuiltInRegistries.BLOCK.getKey(blockEntity.getBlockState().getBlock()).getPath());
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(texture));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }

    private static ResourceLocation texture(String name) {
        return new ResourceLocation(AetherII.MODID, "textures/entity/vases/" + name + ".png");
    }
}
