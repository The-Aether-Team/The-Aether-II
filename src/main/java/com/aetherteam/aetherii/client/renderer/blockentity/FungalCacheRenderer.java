package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.blockentity.FungalCacheBlockEntity;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.FungalCacheModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FungalCacheRenderer implements BlockEntityRenderer<FungalCacheBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/fungal_cache/fungal_cache.png");
    private final FungalCacheModel model;

    public FungalCacheRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new FungalCacheModel(context.getModelSet().bakeLayer(AetherIIModelLayers.FUNGAL_CACHE));
    }

    @Override
    public void render(FungalCacheBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        renderModel(this.model, easedOpen(blockEntity.getOpenNess(partialTick)), poseStack, buffer, packedLight, packedOverlay);
    }

    public static void renderModel(FungalCacheModel model, float openness, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        model.setupAnim(openness);
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }

    private static float easedOpen(float openness) {
        float closed = 1.0F - openness;
        return 1.0F - closed * closed * closed;
    }
}
