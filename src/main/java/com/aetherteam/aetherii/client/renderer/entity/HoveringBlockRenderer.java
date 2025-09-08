package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.state.HoveringBlockEntityRenderState;
import com.aetherteam.aetherii.entity.block.HoveringBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.phys.Vec3;

import java.util.List;

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
            List<BlockModelPart> list = blockRenderDispatcher
                    .getBlockModel(blockState)
                    .collectParts(world, floatingBlock.blockPos, blockState, RandomSource.create(blockState.getSeed(floatingBlock.startBlockPos)));
            blockRenderDispatcher.getModelRenderer()
                    .tesselateBlock(
                            world, list, blockState, floatingBlock.blockPos, poseStack,
                            renderType -> buffer.getBuffer(net.neoforged.neoforge.client.RenderTypeHelper.getMovingBlockRenderType(renderType)),
                            false,
                            OverlayTexture.NO_OVERLAY
                    );
            poseStack.popPose();
            super.render(floatingBlock, poseStack, buffer, packedLightIn);
        }
        if (floatingBlock.blockEntityDummy != null) {
            BlockEntityRenderer<BlockEntity> renderer = Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(floatingBlock.blockEntityDummy);
            if (renderer != null) {
                poseStack.pushPose();
                poseStack.translate(-0.5, 0.0, -0.5);
                renderer.render(floatingBlock.blockEntityDummy, floatingBlock.partialTick, poseStack, buffer, packedLightIn, OverlayTexture.NO_OVERLAY, Vec3.ZERO);
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
            renderState.blockEntityDummy = baseEntityBlock.newBlockEntity(BlockPos.ZERO, blockState);
            if (renderState.blockEntityDummy != null) {
                renderState.blockEntityDummy.setLevel(floatingBlock.level());
                if (floatingBlock.getBlockEntityData() != null) {
                    try (ProblemReporter.ScopedCollector problems = new ProblemReporter.ScopedCollector(floatingBlock.problemPath(), AetherII.LOGGER)) {
                        renderState.blockEntityDummy.loadWithComponents(TagValueInput.create(problems, floatingBlock.level().registryAccess(), floatingBlock.getBlockEntityData()));
                    }
                }
            }
        } else {
            renderState.blockEntityDummy = null;
        }

        BlockPos blockpos = BlockPos.containing(floatingBlock.getX(), floatingBlock.getBoundingBox().maxY, floatingBlock.getZ());
        renderState.startBlockPos = floatingBlock.getStartPos();
        renderState.blockPos = blockpos;
        renderState.blockState = floatingBlock.getBlockState();
        renderState.biome = floatingBlock.level().getBiome(blockpos);
        renderState.level = floatingBlock.level();
    }
}
