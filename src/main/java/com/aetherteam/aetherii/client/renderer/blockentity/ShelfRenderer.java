package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.block.construction.ShelfBlock;
import com.aetherteam.aetherii.blockentity.ShelfBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Redirect;

public class ShelfRenderer implements BlockEntityRenderer<ShelfBlockEntity> {
    private static final float ITEM_SIZE = 0.25F;
    private final ItemRenderer itemRenderer;

    public ShelfRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
    }

    @Override
    public void render(ShelfBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        BlockState state = blockEntity.getBlockState();
        Direction facing = state.hasProperty(ShelfBlock.FACING) ? state.getValue(ShelfBlock.FACING) : Direction.NORTH;
        float yRot = facing.getAxis().isHorizontal() ? -facing.toYRot() : 180.0F;

        for (int slot = 0; slot < ShelfBlockEntity.SLOT_COUNT; slot++) {
            ItemStack stack = blockEntity.getItem(slot);
            if (!stack.isEmpty()) {
                this.renderItem(stack, slot, yRot, poseStack, buffer, packedLight, packedOverlay, blockEntity);
            }
        }
    }

    private void renderItem(ItemStack stack, int slot, float yRot, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, ShelfBlockEntity blockEntity) {
        float itemSlotPosition = (slot - 1) * 0.3125F;
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.75F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        poseStack.translate(itemSlotPosition, -0.25F, -0.25F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.scale(ITEM_SIZE, ITEM_SIZE, ITEM_SIZE);
        this.itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, buffer, blockEntity.getLevel(), (int) blockEntity.getBlockPos().asLong() + slot);
        poseStack.popPose();
    }
}
