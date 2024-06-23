package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.block.miscellaneous.egg.AbstractMoaEggBlock;
import com.aetherteam.aetherii.blockentity.MoaEggBlockEntity;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaEggModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MoaEggRenderer implements BlockEntityRenderer<MoaEggBlockEntity> {
    private final MoaEggModel moaEggModel;

    public MoaEggRenderer(BlockEntityRendererProvider.Context pContext) {
        this.moaEggModel = new MoaEggModel(pContext.getModelSet().bakeLayer(AetherIIModelLayers.MOA_EGG));
    }

    @Override
    public void render(MoaEggBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if (pBlockEntity.getBlockState().getBlock() instanceof AbstractMoaEggBlock moaEggBlock) {
            pPoseStack.translate(0.5F, 1.5F, 0.5F);
            pPoseStack.mulPose(Axis.XN.rotationDegrees(180F));
            if (pBlockEntity.getBlockState().getValue(AbstractMoaEggBlock.HATCH) > 0) {
                pPoseStack.mulPose(Axis.YP.rotationDegrees((float) (Math.cos((double) pBlockEntity.tickCount * 3.25) * Math.PI * 0.4F * pBlockEntity.getBlockState().getValue(AbstractMoaEggBlock.HATCH))));
            }

            String modid = moaEggBlock.getMoaType().location().getNamespace();
            String path = moaEggBlock.getMoaType().location().getPath();
            VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(ResourceLocation.fromNamespaceAndPath(modid, "textures/block/miscellaneous/" + path + "_moa_egg.png")));
            this.moaEggModel.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, pPackedOverlay, 1, 1, 1, 1F);
        }
    }
}
