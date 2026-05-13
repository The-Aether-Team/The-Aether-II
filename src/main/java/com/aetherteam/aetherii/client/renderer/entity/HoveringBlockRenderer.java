package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.state.HoveringBlockEntityRenderState;
import com.aetherteam.aetherii.entity.block.HoveringBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.phys.Vec3;

public class HoveringBlockRenderer extends EntityRenderer<HoveringBlockEntity, HoveringBlockEntityRenderState> {
    public HoveringBlockRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
    }

    @Override
    public void submit(HoveringBlockEntityRenderState floatingBlock, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {

            poseStack.pushPose();
            poseStack.translate(-0.5, 0.0, -0.5);

        submitNodeCollector.submitMovingBlock(poseStack, floatingBlock.movingBlockRenderState);
        poseStack.popPose();
        if (floatingBlock.blockEntityDummy != null) {
            BlockEntityRenderer<BlockEntity, BlockEntityRenderState> renderer = Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(floatingBlock.blockEntityDummy);
            if (renderer != null) {
                poseStack.pushPose();
                poseStack.translate(-0.5, 0.0, -0.5);
                BlockEntityRenderState blockEntityRenderState = renderer.createRenderState();
                renderer.extractRenderState(floatingBlock.blockEntityDummy, blockEntityRenderState, floatingBlock.partialTick, Vec3.ZERO, null);
                renderer.submit(blockEntityRenderState, poseStack, submitNodeCollector, cameraRenderState);
                poseStack.popPose();
            }
        }
        super.submit(floatingBlock, poseStack, submitNodeCollector, cameraRenderState);

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
        renderState.movingBlockRenderState.randomSeedPos = floatingBlock.getStartPos();
        renderState.movingBlockRenderState.blockPos = blockpos;
        renderState.movingBlockRenderState.blockState = floatingBlock.getBlockState();
        renderState.movingBlockRenderState.biome = floatingBlock.level().getBiome(blockpos);
    }
}
