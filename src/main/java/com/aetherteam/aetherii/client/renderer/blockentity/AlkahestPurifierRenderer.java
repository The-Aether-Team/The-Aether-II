package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.utility.AlkahestPurifierBlock;
import com.aetherteam.aetherii.blockentity.AlkahestPurifierBlockEntity;
import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.AlkahestPurifierModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class AlkahestPurifierRenderer implements BlockEntityRenderer<AlkahestPurifierBlockEntity> {
    private final AlkahestPurifierModel model;

    public AlkahestPurifierRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new AlkahestPurifierModel(context.getModelSet().bakeLayer(AetherIIModelLayers.ALKAHEST_PURIFIER));
    }

    @Override
    public void render(AlkahestPurifierBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        boolean hasLevel = blockEntity.getLevel() != null;
        BlockState state = hasLevel ? blockEntity.getBlockState() : AetherIIBlocks.ALKAHEST_PURIFIER.get().defaultBlockState().setValue(AlkahestPurifierBlock.FACING, Direction.SOUTH);

        int level = state.getValue(AlkahestPurifierBlock.LEVEL);
        float openness = blockEntity.getOpenNess(partialTick);
        openness = 1.0F - openness;
        openness = 1.0F - openness * openness * openness;

        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(state.getValue(AlkahestPurifierBlock.FACING).toYRot()));

        this.model.setupAnim(openness);
        VertexConsumer vertexConsumer = AetherIIAtlases.getAlkahestPurifierMaterial(level).buffer(buffer, RenderType::entityCutout);
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}
