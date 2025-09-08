package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.block.utility.ArkeniumForgeBlock;
import com.aetherteam.aetherii.blockentity.ArkeniumForgeBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ArkeniumForgeRenderer implements BlockEntityRenderer<ArkeniumForgeBlockEntity> {
    public ArkeniumForgeRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(ArkeniumForgeBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        BlockState blockState = blockEntity.getBlockState();
        Direction direction = blockState.getValue(ArkeniumForgeBlock.FACING);
        if (level != null) {
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
            ItemStack itemstack = blockEntity.getItem(0);

            if (!itemstack.isEmpty()) {
                poseStack.scale(0.5F, 0.5F, 0.5F);
                Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, level, 0);
            }
            poseStack.popPose();
        }
    }
}
