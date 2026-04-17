package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.dungeon.SentryCrateBlock;
import com.aetherteam.aetherii.blockentity.SentryCrateBlockEntity;
import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentryCrateModel;
import com.aetherteam.aetherii.client.renderer.blockentity.state.SentryCrateRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.Direction;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.util.Unit;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;

public class SentryCrateRenderer implements BlockEntityRenderer<SentryCrateBlockEntity, SentryCrateRenderState> {
    private final SpriteGetter sprites;

    private final SentryCrateModel singleModel;
    private final SentryCrateModel doubleLeftModel;
    private final SentryCrateModel doubleRightModel;

    public SentryCrateRenderer(BlockEntityRendererProvider.Context context) {
        this.sprites = context.sprites();

        this.singleModel = new SentryCrateModel(context.bakeLayer(AetherIIModelLayers.SENTRY_CRATE));
        this.doubleLeftModel = new SentryCrateModel(context.bakeLayer(AetherIIModelLayers.DOUBLE_SENTRY_CRATE_LEFT));
        this.doubleRightModel = new SentryCrateModel(context.bakeLayer(AetherIIModelLayers.DOUBLE_SENTRY_CRATE_RIGHT));
    }


    @Override
    public void submit(SentryCrateRenderState sentryCrateRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        ChestType type = sentryCrateRenderState.type;
        boolean doubleChest = type != ChestType.SINGLE;
        poseStack.pushPose();
        float yRot = sentryCrateRenderState.facing.toYRot();
            poseStack.translate(0.5F, 0.5F, 0.5F);
            poseStack.mulPose(Axis.YP.rotationDegrees(-yRot));
            poseStack.translate(-0.5F, -0.5F, -0.5F);

        int frame = Math.max(0, (int) Math.ceil(sentryCrateRenderState.open * 4) - 1);
            SpriteId spriteId = new ArrayList<>(AetherIIAtlases.SENTRY_CRATE_MATERIALS.get(type)).get(frame);
            SpriteId emissiveId = chooseMaterial(type, AetherIIAtlases.SENTRY_CRATE_SINGLE_EMISSIVE_LOCATION, AetherIIAtlases.SENTRY_CRATE_LEFT_EMISSIVE_LOCATION, AetherIIAtlases.SENTRY_CRATE_RIGHT_EMISSIVE_LOCATION);

        if (doubleChest) {
                if (type == ChestType.LEFT) {
                    this.renderModel(this.doubleLeftModel, sentryCrateRenderState, poseStack, submitNodeCollector, spriteId, emissiveId, sentryCrateRenderState.lightCoords);
                } else {
                    this.renderModel(this.doubleRightModel, sentryCrateRenderState, poseStack, submitNodeCollector, spriteId, emissiveId, sentryCrateRenderState.lightCoords);
                }
            } else {
            this.renderModel(this.singleModel, sentryCrateRenderState, poseStack, submitNodeCollector, spriteId, emissiveId, sentryCrateRenderState.lightCoords);
            }
            poseStack.popPose();

    }

    private void renderModel(SentryCrateModel model, SentryCrateRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, SpriteId spriteId, SpriteId emissiveId, int i) {
        RenderType renderType = spriteId.renderType(RenderTypes::entityCutout);
        RenderType emissiveRenderType = emissiveId.renderType(RenderTypes::entityCutout);


        submitNodeCollector.submitModel(model, Unit.INSTANCE, poseStack, renderType, i, OverlayTexture.NO_OVERLAY,
                -1,
                sprites.get(spriteId),
                0,
                state.breakProgress);

        if (state.open > 0) {
            submitNodeCollector.submitModel(model, Unit.INSTANCE, poseStack, emissiveRenderType, LightCoordsUtil.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,
                    -1,
                    sprites.get(emissiveId),
                    0,
                    state.breakProgress);
        }
    }

    private static SpriteId chooseMaterial(ChestType chestType, SpriteId doubleId, SpriteId leftId, SpriteId rightId) {
        return switch (chestType) {
            case LEFT -> leftId;
            case RIGHT -> rightId;
            default -> doubleId;
        };
    }

    @Override
    public SentryCrateRenderState createRenderState() {
        return new SentryCrateRenderState();
    }

    @Override
    public void extractRenderState(SentryCrateBlockEntity blockEntity, SentryCrateRenderState state, float p_446851_, Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay p_446944_) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, p_446851_, vec3, p_446944_);
        boolean flag = blockEntity.getLevel() != null;
        BlockState blockstate = flag ? blockEntity.getBlockState() : AetherIIBlocks.SENTRY_CRATE.get().defaultBlockState().setValue(SentryCrateBlock.FACING, Direction.SOUTH);
        state.facing = blockstate.getValue(SentryCrateBlock.FACING);
        ChestType type = blockstate.hasProperty(SentryCrateBlock.TYPE) ? blockstate.getValue(SentryCrateBlock.TYPE) : ChestType.SINGLE;
        state.type = type;


        DoubleBlockCombiner.NeighborCombineResult<? extends SentryCrateBlockEntity> neighborcombineresult;
        if (flag && blockstate.getBlock() instanceof SentryCrateBlock chestblock) {
            neighborcombineresult = chestblock.combine(blockstate, blockEntity.getLevel(), blockEntity.getBlockPos(), true);
        } else {
            neighborcombineresult = DoubleBlockCombiner.Combiner::acceptNone;
        }

        state.open = blockEntity.chestLidController.getOpenness(p_446851_);

        state.lightCoords = neighborcombineresult.apply(new BrightnessCombiner<>()).applyAsInt(state.lightCoords);

    }

}
