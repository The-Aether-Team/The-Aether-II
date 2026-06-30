package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.dungeon.AbandonedBagBlock;
import com.aetherteam.aetherii.blockentity.AbandonedBagBlockEntity;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.AbandonedBagModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class AbandonedBagRenderer implements BlockEntityRenderer<AbandonedBagBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AetherII.MODID, "textures/entity/abandoned_bag/abandoned_bag.png");
    private final AbandonedBagModel model;

    public AbandonedBagRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new AbandonedBagModel(context.getModelSet().bakeLayer(AetherIIModelLayers.ABANDONED_BAG));
    }

    @Override
    public void render(AbandonedBagBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        BlockState state = blockEntity.getLevel() != null
                ? blockEntity.getBlockState()
                : AetherIIBlocks.ABANDONED_BAG.get().defaultBlockState().setValue(AbandonedBagBlock.FACING, Direction.SOUTH);
        Direction facing = state.hasProperty(AbandonedBagBlock.FACING) ? state.getValue(AbandonedBagBlock.FACING) : Direction.SOUTH;
        float openness = easedOpen(blockEntity.getOpenNess(partialTick));

        renderModel(this.model, openness, facing.toYRot(), poseStack, buffer, packedLight, packedOverlay);
    }

    public static void renderModel(AbandonedBagModel model, float openness, float yRot, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        model.setupAnim(openness);
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(TEXTURE));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }

    private static float easedOpen(float openness) {
        float closed = 1.0F - openness;
        return 1.0F - closed * closed * closed;
    }
}
