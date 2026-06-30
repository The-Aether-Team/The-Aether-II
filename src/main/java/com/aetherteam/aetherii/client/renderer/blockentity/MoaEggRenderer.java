package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.miscellaneous.MoaEggBlock;
import com.aetherteam.aetherii.blockentity.MoaEggBlockEntity;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaEggModel;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class MoaEggRenderer implements BlockEntityRenderer<MoaEggBlockEntity> {
    private final MoaEggModel moaEggModel;

    public MoaEggRenderer(BlockEntityRendererProvider.Context context) {
        this.moaEggModel = new MoaEggModel(context.getModelSet().bakeLayer(AetherIIModelLayers.MOA_EGG));
    }

    @Override
    public void render(MoaEggBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        BlockState state = blockEntity.getBlockState();
        if (!(state.getBlock() instanceof MoaEggBlock)) {
            return;
        }

        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XN.rotationDegrees(180.0F));
        int hatch = state.getValue(MoaEggBlock.HATCH);
        if (hatch > 0) {
            poseStack.mulPose(Axis.YP.rotationDegrees((float) (Math.cos((double) blockEntity.tickCount * 3.25) * Math.PI * 0.4F * hatch)));
        }

        this.renderLayer(poseStack, buffer, packedLight, packedOverlay, texture("moa_egg_feather_" + state.getValue(MoaEggBlock.FEATHER_SHAPE).getSerializedName() + "_" + state.getValue(MoaEggBlock.FEATHERS).getSerializedName()));
        this.renderLayer(poseStack, buffer, packedLight, packedOverlay, texture("moa_egg_eyes_" + state.getValue(MoaEggBlock.EYES).getSerializedName()));
        this.renderLayer(poseStack, buffer, packedLight, packedOverlay, texture("moa_egg_keratin_" + state.getValue(MoaEggBlock.KERATIN).getSerializedName()));
        poseStack.popPose();
    }

    private void renderLayer(PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, ResourceLocation texture) {
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(texture));
        this.moaEggModel.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay == OverlayTexture.NO_OVERLAY ? OverlayTexture.NO_OVERLAY : packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    private static ResourceLocation texture(String name) {
        return new ResourceLocation(AetherII.MODID, "textures/entity/moa_egg/" + name + ".png");
    }
}
