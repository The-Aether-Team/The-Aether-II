package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.block.utility.ArkeniumForgeBlock;
import com.aetherteam.aetherii.blockentity.ArkeniumForgeBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ArkeniumForgeRenderer implements BlockEntityRenderer<ArkeniumForgeBlockEntity> {
    private final ItemRenderer itemRenderer;

    public ArkeniumForgeRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(ArkeniumForgeBlockEntity forge, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        ItemStack stack = forge.getItem(0);
        if (stack.isEmpty()) {
            return;
        }

        Direction direction = forge.getBlockState().getValue(ArkeniumForgeBlock.FACING);
        float rotation = switch (direction) {
            case NORTH -> 135.0F;
            case SOUTH -> -45.0F;
            case EAST -> 45.0F;
            default -> -135.0F;
        };

        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        poseStack.translate(0.5F, 0.5F, -1.01725F);
        poseStack.mulPose(Axis.ZN.rotationDegrees(rotation));
        poseStack.scale(0.5F, 0.5F, 0.5F);
        this.itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, forge.getLevel(), (int) forge.getBlockPos().asLong());
        poseStack.popPose();
    }
}
