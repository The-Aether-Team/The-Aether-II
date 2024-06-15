package com.aetherteam.aetherii.client.renderer.entity;

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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;

public class HoveringBlockRenderer extends EntityRenderer<HoveringBlockEntity> {
    private BlockEntity blockEntityDummy;

    public HoveringBlockRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
    }

    @Override
    public void render(HoveringBlockEntity floatingBlock, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLightIn) {
        BlockState blockState = floatingBlock.getBlockState();
        if (blockState.hasBlockEntity() && blockState.getBlock() instanceof BaseEntityBlock baseEntityBlock) {
            if (this.blockEntityDummy == null) {
                this.blockEntityDummy = baseEntityBlock.newBlockEntity(BlockPos.ZERO, blockState);
            }
        }
        if (blockState.getRenderShape() == RenderShape.MODEL) {
            Level world = floatingBlock.level();
            if (blockState != world.getBlockState(floatingBlock.blockPosition()) && blockState.getRenderShape() != RenderShape.INVISIBLE) {
                poseStack.pushPose();
                BlockPos blockPos = BlockPos.containing(floatingBlock.getX(), floatingBlock.getBoundingBox().maxY, floatingBlock.getZ());
                poseStack.translate(-0.5, 0.0, -0.5);
                BlockRenderDispatcher blockRenderDispatcher = Minecraft.getInstance().getBlockRenderer();
                BakedModel model = blockRenderDispatcher.getBlockModel(blockState);
                for (RenderType renderType : model.getRenderTypes(blockState, RandomSource.create(blockState.getSeed(floatingBlock.getStartPos())), ModelData.EMPTY)) {
                    blockRenderDispatcher.getModelRenderer().tesselateBlock(world, model, blockState, blockPos, poseStack, buffer.getBuffer(renderType), false, RandomSource.create(), blockState.getSeed(floatingBlock.getStartPos()), OverlayTexture.NO_OVERLAY, ModelData.EMPTY, renderType);
                }
                poseStack.popPose();
                super.render(floatingBlock, entityYaw, partialTicks, poseStack, buffer, packedLightIn);
            }
        } else if (this.blockEntityDummy != null) {
            BlockEntityRenderer<BlockEntity> renderer = Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(this.blockEntityDummy);
            if (renderer != null) {
                poseStack.pushPose();
                poseStack.translate(-0.5, 0.0, -0.5);
                renderer.render(this.blockEntityDummy, partialTicks, poseStack, buffer, packedLightIn, OverlayTexture.NO_OVERLAY);
                poseStack.popPose();
                super.render(floatingBlock, entityYaw, partialTicks, poseStack, buffer, packedLightIn);
            }
        }
    }

    @Override
    public ResourceLocation getTextureLocation(HoveringBlockEntity block) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
