package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.dungeon.SageChestBlock;
import com.aetherteam.aetherii.blockentity.SageChestBlockEntity;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SageChestModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

public class SageChestRenderer implements BlockEntityRenderer<SageChestBlockEntity> {
    private static final ResourceLocation SAGE_CHEST = texture("sage_chest");
    private static final ResourceLocation SAGE_CHEST_LEFT = texture("sage_chest_left");
    private static final ResourceLocation SAGE_CHEST_RIGHT = texture("sage_chest_right");

    private final SageChestModel singleModel;
    private final SageChestModel doubleLeftModel;
    private final SageChestModel doubleRightModel;

    public SageChestRenderer(BlockEntityRendererProvider.Context context) {
        this.singleModel = new SageChestModel(context.getModelSet().bakeLayer(AetherIIModelLayers.SAGE_CHEST));
        this.doubleLeftModel = new SageChestModel(context.getModelSet().bakeLayer(AetherIIModelLayers.DOUBLE_SAGE_CHEST_LEFT));
        this.doubleRightModel = new SageChestModel(context.getModelSet().bakeLayer(AetherIIModelLayers.DOUBLE_SAGE_CHEST_RIGHT));
    }

    @Override
    public void render(SageChestBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        boolean hasLevel = level != null;
        BlockState state = hasLevel
                ? blockEntity.getBlockState()
                : AetherIIBlocks.SAGE_CHEST.get().defaultBlockState().setValue(SageChestBlock.FACING, Direction.SOUTH);
        ChestType chestType = state.hasProperty(SageChestBlock.TYPE) ? state.getValue(SageChestBlock.TYPE) : ChestType.SINGLE;
        Direction facing = state.hasProperty(SageChestBlock.FACING) ? state.getValue(SageChestBlock.FACING) : Direction.SOUTH;
        Float2FloatFunction openness = blockEntity::getOpenNess;

        if (hasLevel && state.getBlock() instanceof SageChestBlock sageChestBlock) {
            DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combined = sageChestBlock.combine(state, level, blockEntity.getBlockPos(), true);
            openness = combined.apply(ChestBlock.opennessCombiner(blockEntity));
            packedLight = combined.apply(new BrightnessCombiner<>()).applyAsInt(packedLight);
        }

        renderModel(getModel(chestType, this.singleModel, this.doubleLeftModel, this.doubleRightModel), texture(chestType), easedOpen(openness.get(partialTick)), facing.toYRot(), poseStack, buffer, packedLight, packedOverlay);
    }

    public static void renderModel(SageChestModel model, ResourceLocation texture, float openness, float yRot, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        model.setupAnim(openness);
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(texture));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }

    private static SageChestModel getModel(ChestType chestType, SageChestModel singleModel, SageChestModel doubleLeftModel, SageChestModel doubleRightModel) {
        return switch (chestType) {
            case LEFT -> doubleLeftModel;
            case RIGHT -> doubleRightModel;
            case SINGLE -> singleModel;
        };
    }

    public static ResourceLocation texture(ChestType chestType) {
        return switch (chestType) {
            case LEFT -> SAGE_CHEST_LEFT;
            case RIGHT -> SAGE_CHEST_RIGHT;
            case SINGLE -> SAGE_CHEST;
        };
    }

    private static ResourceLocation texture(String name) {
        return new ResourceLocation(AetherII.MODID, "textures/entity/sage_chest/" + name + ".png");
    }

    private static float easedOpen(float openness) {
        float closed = 1.0F - openness;
        return 1.0F - closed * closed * closed;
    }
}
