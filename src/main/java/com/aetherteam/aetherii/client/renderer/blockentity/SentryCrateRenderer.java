package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.dungeon.SentryCrateBlock;
import com.aetherteam.aetherii.blockentity.SentryCrateBlockEntity;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentryCrateModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

public class SentryCrateRenderer implements BlockEntityRenderer<SentryCrateBlockEntity> {
    private static final ResourceLocation[][] TEXTURES = new ResourceLocation[][] {
            textureSet("single"),
            textureSet("left"),
            textureSet("right")
    };
    private static final ResourceLocation[] EMISSIVE_TEXTURES = new ResourceLocation[] {
            emissiveTexture("single"),
            emissiveTexture("left"),
            emissiveTexture("right")
    };

    private final SentryCrateModel singleModel;
    private final SentryCrateModel doubleLeftModel;
    private final SentryCrateModel doubleRightModel;

    public SentryCrateRenderer(BlockEntityRendererProvider.Context context) {
        this.singleModel = new SentryCrateModel(context.getModelSet().bakeLayer(AetherIIModelLayers.SENTRY_CRATE));
        this.doubleLeftModel = new SentryCrateModel(context.getModelSet().bakeLayer(AetherIIModelLayers.DOUBLE_SENTRY_CRATE_LEFT));
        this.doubleRightModel = new SentryCrateModel(context.getModelSet().bakeLayer(AetherIIModelLayers.DOUBLE_SENTRY_CRATE_RIGHT));
    }

    @Override
    public void render(SentryCrateBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        boolean hasLevel = level != null;
        BlockState state = hasLevel ? blockEntity.getBlockState() : AetherIIBlocks.SENTRY_CRATE.get().defaultBlockState().setValue(SentryCrateBlock.FACING, Direction.SOUTH);
        ChestType chestType = state.hasProperty(SentryCrateBlock.TYPE) ? state.getValue(SentryCrateBlock.TYPE) : ChestType.SINGLE;
        Direction facing = state.hasProperty(SentryCrateBlock.FACING) ? state.getValue(SentryCrateBlock.FACING) : Direction.SOUTH;

        if (hasLevel && state.getBlock() instanceof SentryCrateBlock sentryCrateBlock) {
            DoubleBlockCombiner.NeighborCombineResult<SentryCrateBlockEntity> combined = sentryCrateBlock.combine(state, level, blockEntity.getBlockPos(), true);
            packedLight = combined.apply(new BrightnessCombiner<>()).applyAsInt(packedLight);
        }

        float openness = blockEntity.chestLidController.getOpenness(partialTick);
        int frame = Math.max(0, Math.min(3, (int) Math.ceil(openness * 4.0F) - 1));
        SentryCrateModel model = this.getModel(chestType);
        int textureIndex = textureIndex(chestType);

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(-facing.toYRot()));
        poseStack.translate(-0.5F, -0.5F, -0.5F);

        this.renderModel(model, poseStack, buffer, TEXTURES[textureIndex][frame], packedLight);
        if (openness > 0.0F) {
            this.renderModel(model, poseStack, buffer, EMISSIVE_TEXTURES[textureIndex], LightTexture.FULL_BRIGHT);
        }
        poseStack.popPose();
    }

    private SentryCrateModel getModel(ChestType chestType) {
        return switch (chestType) {
            case LEFT -> this.doubleLeftModel;
            case RIGHT -> this.doubleRightModel;
            case SINGLE -> this.singleModel;
        };
    }

    private void renderModel(SentryCrateModel model, PoseStack poseStack, MultiBufferSource buffer, ResourceLocation texture, int packedLight) {
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(texture));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    private static int textureIndex(ChestType chestType) {
        return switch (chestType) {
            case LEFT -> 1;
            case RIGHT -> 2;
            case SINGLE -> 0;
        };
    }

    private static ResourceLocation[] textureSet(String type) {
        return new ResourceLocation[] {
                texture(type, 0),
                texture(type, 1),
                texture(type, 2),
                texture(type, 3)
        };
    }

    private static ResourceLocation texture(String type, int frame) {
        return new ResourceLocation(AetherII.MODID, "textures/entity/sentry_crate/" + type + "/sentry_crate_" + frame + ".png");
    }

    private static ResourceLocation emissiveTexture(String type) {
        return new ResourceLocation(AetherII.MODID, "textures/entity/sentry_crate/" + type + "/sentry_crate_emissive.png");
    }
}
