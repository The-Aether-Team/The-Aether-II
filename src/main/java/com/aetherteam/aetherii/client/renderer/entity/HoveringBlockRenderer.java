package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.client.renderer.entity.state.HoveringBlockEntityRenderState;
import com.aetherteam.aetherii.entity.block.HoveringBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;

public class HoveringBlockRenderer extends EntityRenderer<HoveringBlockEntity, HoveringBlockEntityRenderState> {


    public HoveringBlockRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
    }

    @Override
    public void render(HoveringBlockEntityRenderState floatingBlock, PoseStack poseStack, MultiBufferSource buffer, int packedLightIn) {
        BlockState blockState = floatingBlock.blockState;

        if (blockState.getRenderShape() == RenderShape.MODEL) {
            BlockAndTintGetter world = floatingBlock.level;
            poseStack.pushPose();
            poseStack.translate(-0.5, 0.0, -0.5);
            BlockRenderDispatcher blockRenderDispatcher = Minecraft.getInstance().getBlockRenderer();
            BakedModel model = blockRenderDispatcher.getBlockModel(blockState);
            for (RenderType renderType : model.getRenderTypes(blockState, RandomSource.create(blockState.getSeed(floatingBlock.startBlockPos)), ModelData.EMPTY)) {
                blockRenderDispatcher.getModelRenderer().tesselateBlock(world, model, blockState, floatingBlock.blockPos, poseStack, buffer.getBuffer(renderType), false, RandomSource.create(), blockState.getSeed(floatingBlock.startBlockPos), OverlayTexture.NO_OVERLAY, ModelData.EMPTY, renderType);
            }
            poseStack.popPose();
            super.render(floatingBlock, poseStack, buffer, packedLightIn);
        } else if (floatingBlock.blockEntityDummy != null) {
            BlockEntityRenderer<BlockEntity> renderer = Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(floatingBlock.blockEntityDummy);
            if (renderer != null) {
                poseStack.pushPose();
                poseStack.translate(-0.5, 0.0, -0.5);
                renderer.render(floatingBlock.blockEntityDummy, floatingBlock.partialTick, poseStack, buffer, packedLightIn, OverlayTexture.NO_OVERLAY);
                poseStack.popPose();
                super.render(floatingBlock, poseStack, buffer, packedLightIn);
            }
        }
    }

    @Override
    public HoveringBlockEntityRenderState createRenderState() {
        return new HoveringBlockEntityRenderState();
    }


    @Override
    public void extractRenderState(HoveringBlockEntity floatingBlock, HoveringBlockEntityRenderState renderState, float p_362204_) {
        super.extractRenderState(floatingBlock, renderState, p_362204_);
        BlockState blockState = floatingBlock.getBlockState();

        if (blockState.hasBlockEntity() && blockState.getBlock() instanceof BaseEntityBlock baseEntityBlock) {
            if (renderState.blockEntityDummy == null) {
                renderState.blockEntityDummy = baseEntityBlock.newBlockEntity(BlockPos.ZERO, blockState);
            }
        }

        BlockPos blockpos = BlockPos.containing(floatingBlock.getX(), floatingBlock.getBoundingBox().maxY, floatingBlock.getZ());
        renderState.startBlockPos = floatingBlock.getStartPos();
        renderState.blockPos = blockpos;
        renderState.blockState = floatingBlock.getBlockState();
        renderState.biome = floatingBlock.level().getBiome(blockpos);
        renderState.level = floatingBlock.level();
    }
}
