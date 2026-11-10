package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.furniture.TheranGlobeBlock;
import com.aetherteam.aetherii.blockentity.TheranGlobeBlockEntity;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.TheranGlobeModel;
import com.aetherteam.aetherii.client.renderer.blockentity.state.TheranGlobeRenderState;
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

public class TheranGlobeRenderer implements BlockEntityRenderer<TheranGlobeBlockEntity, TheranGlobeRenderState> {
    private static final Identifier THERAN_GLOBE_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/theran_globe/theran_globe.png");
    private final TheranGlobeModel model;

    public TheranGlobeRenderer(BlockEntityRendererProvider.Context context) {
        this(context.entityModelSet());
    }

    public TheranGlobeRenderer(EntityModelSet modelSet) {
        this.model = new TheranGlobeModel(modelSet.bakeLayer(AetherIIModelLayers.THERAN_GLOBE));
    }

    @Override
    public void submit(TheranGlobeRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        float yRot = state.angle;
        float rotation = state.globeRotation;
        this.render(poseStack, submitNodeCollector, state, state.lightCoords, yRot, rotation);
    }


    public void render(PoseStack poseStack, SubmitNodeCollector collector, TheranGlobeRenderState state, int packedLight, float yRot, float globeRotation) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));

        this.model.setupAnim(globeRotation);
        collector.submitModel(
                this.model, globeRotation, poseStack, RenderTypes.entityCutout(THERAN_GLOBE_LOCATION), packedLight, OverlayTexture.NO_OVERLAY, -1, null, 0, state.breakProgress
        );

        poseStack.popPose();
    }

    @Override
    public TheranGlobeRenderState createRenderState() {
        return new TheranGlobeRenderState();
    }

    @Override
    public void extractRenderState(TheranGlobeBlockEntity blockEntity, TheranGlobeRenderState state, float p_446851_, Vec3 p_445788_, ModelFeatureRenderer.@Nullable CrumblingOverlay p_446944_) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, p_446851_, p_445788_, p_446944_);
        boolean flag = blockEntity.getLevel() != null;
        BlockState blockstate = flag ? blockEntity.getBlockState() : AetherIIBlocks.THERAN_GLOBE.get().defaultBlockState().setValue(TheranGlobeBlock.FACING, Direction.SOUTH);
        state.angle = blockstate.getValue(TheranGlobeBlock.FACING).toYRot();
        //state.globeRotation = blockEntity.getGlobeRotation(p_446851_);

    }
}