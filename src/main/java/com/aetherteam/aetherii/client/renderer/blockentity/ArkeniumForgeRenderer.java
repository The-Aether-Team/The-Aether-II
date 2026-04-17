package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.block.utility.ArkeniumForgeBlock;
import com.aetherteam.aetherii.blockentity.ArkeniumForgeBlockEntity;
import com.aetherteam.aetherii.client.renderer.blockentity.state.ArkeniumForgeRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class ArkeniumForgeRenderer implements BlockEntityRenderer<ArkeniumForgeBlockEntity, ArkeniumForgeRenderState> {
    private final ItemModelResolver itemModelResolver;

    public ArkeniumForgeRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public void submit(ArkeniumForgeRenderState arkeniumForgeRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        Direction direction = arkeniumForgeRenderState.facing;

            poseStack.pushPose();
            float rotation;
            switch (direction) {
                case NORTH -> rotation = 135.0F;
                case SOUTH -> rotation = -45.0F;
                case EAST -> rotation = 45.0F;
                default -> rotation = -135.0F;
            }
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            poseStack.translate(0.5F, 0.5F, -1.01725F);
            poseStack.mulPose(Axis.ZN.rotationDegrees(rotation));

        if (!arkeniumForgeRenderState.item.isEmpty()) {
                poseStack.scale(0.5F, 0.5F, 0.5F);
            arkeniumForgeRenderState.item.submit(poseStack, submitNodeCollector, arkeniumForgeRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            }
            poseStack.popPose();

    }


    @Override
    public ArkeniumForgeRenderState createRenderState() {
        return new ArkeniumForgeRenderState();
    }


    @Override
    public void extractRenderState(ArkeniumForgeBlockEntity blockEntity, ArkeniumForgeRenderState arkeniumForgeRenderState, float p_446851_, Vec3 p_445788_, ModelFeatureRenderer.@Nullable CrumblingOverlay p_446944_) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, arkeniumForgeRenderState, p_446851_, p_445788_, p_446944_);
        arkeniumForgeRenderState.facing = blockEntity.getBlockState().getValue(ArkeniumForgeBlock.FACING);
        int i = (int) blockEntity.getBlockPos().asLong();

        this.itemModelResolver
                .updateForTopItem(arkeniumForgeRenderState.item, blockEntity.getItem(0), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, i);

    }
}
