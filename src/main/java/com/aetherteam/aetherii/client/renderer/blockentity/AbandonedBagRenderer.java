package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.dungeon.AbandonedBagBlock;
import com.aetherteam.aetherii.blockentity.AbandonedBagBlockEntity;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.AbandonedBagModel;
import com.aetherteam.aetherii.client.renderer.blockentity.state.AbandonedBagRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class AbandonedBagRenderer implements BlockEntityRenderer<AbandonedBagBlockEntity, AbandonedBagRenderState> {
    private static final Identifier ABANDONED_BAG_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/abandoned_bag/abandoned_bag.png");
    private final AbandonedBagModel model;

    public AbandonedBagRenderer(BlockEntityRendererProvider.Context context) {
        this(context.entityModelSet());
    }

    public AbandonedBagRenderer(EntityModelSet modelSet) {
        this.model = new AbandonedBagModel(modelSet.bakeLayer(AetherIIModelLayers.ABANDONED_BAG));
    }


    @Override
    public void submit(AbandonedBagRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        float yRot = state.angle;
        float openNess = state.open;
        openNess = 1.0F - openNess;
        openNess = 1.0F - openNess * openNess * openNess;
        this.render(poseStack, submitNodeCollector, state, state.lightCoords, yRot, openNess);
    }


    public void render(PoseStack poseStack, SubmitNodeCollector collector, AbandonedBagRenderState state, int packedLight, float yRot, float openness) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));

        this.model.setupAnim(openness);
        collector.submitModel(
                this.model, openness, poseStack, RenderTypes.entityCutout(ABANDONED_BAG_LOCATION), packedLight, OverlayTexture.NO_OVERLAY, -1, null, 0, state.breakProgress
        );

        poseStack.popPose();
    }

    @Override
    public AbandonedBagRenderState createRenderState() {
        return new AbandonedBagRenderState();
    }

    @Override
    public void extractRenderState(AbandonedBagBlockEntity blockEntity, AbandonedBagRenderState state, float p_446851_, Vec3 p_445788_, ModelFeatureRenderer.@Nullable CrumblingOverlay p_446944_) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, p_446851_, p_445788_, p_446944_);
        boolean flag = blockEntity.getLevel() != null;
        BlockState blockstate = flag ? blockEntity.getBlockState() : AetherIIBlocks.ABANDONED_BAG.get().defaultBlockState().setValue(AbandonedBagBlock.FACING, Direction.SOUTH);
        state.angle = blockstate.getValue(AbandonedBagBlock.FACING).toYRot();
        state.open = blockEntity.getOpenNess(p_446851_);

    }
}