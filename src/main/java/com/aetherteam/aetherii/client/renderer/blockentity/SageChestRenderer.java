package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.block.dungeon.SageChestBlock;
import com.aetherteam.aetherii.blockentity.SageChestBlockEntity;
import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SageChestModel;
import com.aetherteam.aetherii.client.renderer.blockentity.state.SageChestRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiblockChestResources;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SageChestRenderer implements BlockEntityRenderer<SageChestBlockEntity, SageChestRenderState> {
    public static final MultiblockChestResources<ModelLayerLocation> LAYERS = new MultiblockChestResources<>(AetherIIModelLayers.SAGE_CHEST, AetherIIModelLayers.DOUBLE_SAGE_CHEST_LEFT, AetherIIModelLayers.DOUBLE_SAGE_CHEST_RIGHT);
    private final SpriteGetter sprites;
    private final MultiblockChestResources<SageChestModel> models;

    public SageChestRenderer(BlockEntityRendererProvider.Context context) {
        this.sprites = context.sprites();
        this.models = LAYERS.map((layer) -> new SageChestModel(context.bakeLayer(layer)));
    }

    @Override
    public SageChestRenderState createRenderState() {
        return new SageChestRenderState();
    }

    @Override
    public void extractRenderState(SageChestBlockEntity blockEntity, SageChestRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combineResult = DoubleBlockCombiner.Combiner::acceptNone;
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        boolean hasLevel = blockEntity.getLevel() != null;
        BlockState blockState = hasLevel ? blockEntity.getBlockState() : Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
        state.type = blockState.hasProperty(ChestBlock.TYPE) ? blockState.getValue(ChestBlock.TYPE) : ChestType.SINGLE;
        state.facing = blockState.getValue(ChestBlock.FACING);
        if (hasLevel) {
            if (blockState.getBlock() instanceof SageChestBlock sageChestBlock) {
                combineResult = sageChestBlock.combine(blockState, blockEntity.getLevel(), blockEntity.getBlockPos(), true);
            }
        }
        state.open = combineResult.apply(SageChestBlock.opennessCombiner(blockEntity)).get(partialTicks);
        if (state.type != ChestType.SINGLE) {
            state.lightCoords = ((Int2IntFunction) combineResult.apply(new BrightnessCombiner())).applyAsInt(state.lightCoords);
        }
    }

    @Override
    public void submit(SageChestRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.mulPose(Axis.YP.rotationDegrees(state.facing.toYRot()));
        float open = state.open;
        open = 1.0F - open;
        open = 1.0F - open * open * open;
        SpriteId spriteId = AetherIIAtlases.SAGE_CHEST_RESOURCES_SPRITE.select(state.type);
        SageChestModel model = this.models.select(state.type);
        submitNodeCollector.submitModel(model, open, poseStack, state.lightCoords, OverlayTexture.NO_OVERLAY, -1, spriteId, this.sprites, 0, state.breakProgress);
        poseStack.popPose();
    }

    @Override
    public AABB getRenderBoundingBox(SageChestBlockEntity blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();
        return AABB.encapsulatingFullBlocks(pos.offset(-1, 0, -1), pos.offset(1, 1, 1));
    }
}
