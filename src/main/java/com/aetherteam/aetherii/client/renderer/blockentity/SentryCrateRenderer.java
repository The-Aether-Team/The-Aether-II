package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.dungeon.SentryCrateBlock;
import com.aetherteam.aetherii.blockentity.SentryCrateBlockEntity;
import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentryCrateModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class SentryCrateRenderer implements BlockEntityRenderer<SentryCrateBlockEntity> {
    private final SentryCrateModel singleModel;
    private final SentryCrateModel doubleLeftModel;
    private final SentryCrateModel doubleRightModel;

    public SentryCrateRenderer(BlockEntityRendererProvider.Context context) {
        this.singleModel = new SentryCrateModel(context.bakeLayer(AetherIIModelLayers.SENTRY_CRATE));
        this.doubleLeftModel = new SentryCrateModel(context.bakeLayer(AetherIIModelLayers.DOUBLE_SENTRY_CRATE_LEFT));
        this.doubleRightModel = new SentryCrateModel(context.bakeLayer(AetherIIModelLayers.DOUBLE_SENTRY_CRATE_RIGHT));
    }

    public void render(SentryCrateBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, Vec3 cameraPos) {
        Level level = blockEntity.getLevel();
        boolean flag = level != null;
        BlockState state = flag ? blockEntity.getBlockState() : AetherIIBlocks.SENTRY_CRATE.get().defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
        ChestType type = state.hasProperty(SentryCrateBlock.TYPE) ? state.getValue(SentryCrateBlock.TYPE) : ChestType.SINGLE;
        if (state.getBlock() instanceof SentryCrateBlock crate) {
            boolean doubleChest = type != ChestType.SINGLE;
            poseStack.pushPose();
            float yRot = state.getValue(SentryCrateBlock.FACING).toYRot();
            poseStack.translate(0.5F, 0.5F, 0.5F);
            poseStack.mulPose(Axis.YP.rotationDegrees(-yRot));
            poseStack.translate(-0.5F, -0.5F, -0.5F);

            DoubleBlockCombiner.NeighborCombineResult<SentryCrateBlockEntity> combined;
            if (flag) {
                combined = crate.combine(state, level, blockEntity.getBlockPos(), true);
            } else {
                combined = DoubleBlockCombiner.Combiner::acceptNone;
            }
            int i = combined.apply(new BrightnessCombiner<>()).applyAsInt(packedLight);

            int frame = Math.max(0, (int) Math.ceil(blockEntity.chestLidController.getOpenness(partialTick) * 4) - 1);
            Material material = new ArrayList<>(AetherIIAtlases.SENTRY_CRATE_MATERIALS.get(type)).get(frame);
            Material emissive = chooseMaterial(type, AetherIIAtlases.SENTRY_CRATE_SINGLE_EMISSIVE_LOCATION, AetherIIAtlases.SENTRY_CRATE_LEFT_EMISSIVE_LOCATION, AetherIIAtlases.SENTRY_CRATE_RIGHT_EMISSIVE_LOCATION);

            VertexConsumer vertexConsumer = material.buffer(buffer, RenderType::entityCutout);
            VertexConsumer emissiveConsumer = emissive.buffer(buffer, RenderType::entityCutout);
            if (doubleChest) {
                if (type == ChestType.LEFT) {
                    this.renderModel(this.doubleLeftModel, state, poseStack, vertexConsumer, emissiveConsumer, i, packedOverlay);
                } else {
                    this.renderModel(this.doubleRightModel, state, poseStack, vertexConsumer, emissiveConsumer, i, packedOverlay);
                }
            } else {
                this.renderModel(this.singleModel, state, poseStack, vertexConsumer, emissiveConsumer, i, packedOverlay);
            }
            poseStack.popPose();
        }
    }

    private void renderModel(SentryCrateModel model, BlockState state, PoseStack poseStack, VertexConsumer vertexConsumer, VertexConsumer emissiveConsumer, int i, int packedOverlay) {
        model.renderToBuffer(poseStack, vertexConsumer, i, packedOverlay);
        if (state.getValue(SentryCrateBlock.OPEN)) {
            model.renderToBuffer(poseStack, emissiveConsumer, LightTexture.FULL_BRIGHT, packedOverlay);
        }
    }

    private static Material chooseMaterial(ChestType chestType, Material doubleMaterial, Material leftMaterial, Material rightMaterial) {
        return switch (chestType) {
            case LEFT -> leftMaterial;
            case RIGHT -> rightMaterial;
            default -> doubleMaterial;
        };
    }
}
