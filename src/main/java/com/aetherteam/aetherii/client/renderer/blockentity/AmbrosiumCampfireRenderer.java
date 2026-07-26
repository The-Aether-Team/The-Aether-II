package com.aetherteam.aetherii.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.CampfireRenderer;
import net.minecraft.client.renderer.blockentity.state.CampfireRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;

import java.util.List;

public class AmbrosiumCampfireRenderer extends CampfireRenderer {
    public AmbrosiumCampfireRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void submit(CampfireRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        Direction facing = state.facing;
        List<ItemStackRenderState> items = state.items;

        for (int slot = 0; slot < items.size(); ++slot) {
            ItemStackRenderState itemState = items.get(slot);
            if (!itemState.isEmpty()) {
                poseStack.pushPose();
                poseStack.translate(0.5F, 0.2F, 0.5F);
                Direction direction = Direction.from2DDataValue((slot + facing.get2DDataValue()) % 4);
                float angle = -direction.toYRot();
                poseStack.mulPose(Axis.YP.rotationDegrees(angle));
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                poseStack.translate(0.0F, -0.4125F, 0.0F);
                poseStack.scale(0.375F, 0.375F, 0.375F);
                itemState.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
                poseStack.popPose();
            }
        }
    }
}
