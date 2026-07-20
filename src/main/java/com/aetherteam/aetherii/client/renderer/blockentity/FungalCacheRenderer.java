package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.blockentity.FungalCacheBlockEntity;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.blockentity.model.FungalCacheModel;
import com.aetherteam.aetherii.client.renderer.blockentity.state.FungalCacheRenderState;
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
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class FungalCacheRenderer implements BlockEntityRenderer<FungalCacheBlockEntity, FungalCacheRenderState> {
    private static final Identifier FUNGAL_CACHE_LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/fungal_cache/fungal_cache.png");
    private final FungalCacheModel model;

    public FungalCacheRenderer(BlockEntityRendererProvider.Context context) {
        this(context.entityModelSet());
    }

    public FungalCacheRenderer(EntityModelSet modelSet) {
        this.model = new FungalCacheModel(modelSet.bakeLayer(AetherIIModelLayers.FUNGAL_CACHE));
    }


    @Override
    public void submit(FungalCacheRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        float openNess = state.open;
        openNess = 1.0F - openNess;
        openNess = 1.0F - openNess * openNess * openNess;
        this.render(poseStack, submitNodeCollector, state, state.lightCoords, openNess);
    }


    public void render(PoseStack poseStack, SubmitNodeCollector collector, FungalCacheRenderState state, int packedLight, float openness) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        this.model.setupAnim(openness);
        collector.submitModel(
                this.model, openness, poseStack, RenderTypes.entityCutout(FUNGAL_CACHE_LOCATION), packedLight, OverlayTexture.NO_OVERLAY, -1, null, 0, state.breakProgress
        );

        poseStack.popPose();
    }

    @Override
    public FungalCacheRenderState createRenderState() {
        return new FungalCacheRenderState();
    }

    @Override
    public void extractRenderState(FungalCacheBlockEntity blockEntity, FungalCacheRenderState state, float p_446851_, Vec3 p_445788_, ModelFeatureRenderer.@Nullable CrumblingOverlay p_446944_) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, p_446851_, p_445788_, p_446944_);
        state.open = blockEntity.getOpenNess(p_446851_);
    }
}