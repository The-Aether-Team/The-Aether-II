package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.utility.AltarBlock;
import com.aetherteam.aetherii.blockentity.AltarBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class AltarRenderer implements BlockEntityRenderer<AltarBlockEntity> {
    private final ItemRenderer itemRenderer;
    private final EntityRenderDispatcher entityRenderDispatcher;

    public AltarRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
        this.entityRenderDispatcher = context.getEntityRenderer();
    }

    @Override
    public void render(AltarBlockEntity altar, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Direction facing = altar.getBlockState().getValue(AltarBlock.FACING);
        int seed = (int) altar.getBlockPos().asLong();

        this.renderInputItem(altar.getItem(0), facing, altar, seed, poseStack, buffer, packedLight);
        this.renderOutputItem(altar, partialTick, seed + 9, poseStack, buffer, packedLight);
        this.renderFuelItems(altar, seed, poseStack, buffer, packedLight);
    }

    private void renderInputItem(ItemStack stack, Direction facing, AltarBlockEntity altar, int seed, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (stack.isEmpty()) {
            return;
        }

        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        poseStack.translate(0.5F, 0.5F, -1.01725F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(facing.toYRot() - 180.0F));
        poseStack.scale(0.5F, 0.5F, 0.5F);
        this.itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, altar.getLevel(), seed);
        poseStack.popPose();
    }

    private void renderOutputItem(AltarBlockEntity altar, float partialTick, int seed, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        ItemStack stack = altar.getItem(9);
        if (stack.isEmpty()) {
            return;
        }

        if (altar.getBobOffs() < 0.0F && altar.getLevel() != null) {
            altar.setBobOffs(altar.getLevel().getRandom().nextFloat() * Mth.TWO_PI);
        }
        float inputRotation = altar.getInputItemRotation() + ((1.0F + partialTick) / 10.0F);
        altar.setInputItemRotation(inputRotation);

        poseStack.pushPose();
        poseStack.translate(0.5F, 1.0F, 0.5F);
        float bob = Mth.sin(inputRotation / 10.0F + altar.getBobOffs()) * 0.05F + 0.15F;
        poseStack.translate(0.0F, bob, 0.0F);
        poseStack.mulPose(Axis.YP.rotation(inputRotation / 20.0F + altar.getBobOffs()));
        this.itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, altar.getLevel(), seed);
        poseStack.popPose();
    }

    private void renderFuelItems(AltarBlockEntity altar, int seed, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        int fuelCount = 0;
        for (int slot = 1; slot <= 8; slot++) {
            if (altar.getItem(slot).is(AetherIITags.Items.ALTAR_FUEL)) {
                fuelCount++;
            }
        }
        if (fuelCount == 0) {
            return;
        }

        altar.setAmbSpinningSpeed(Mth.clamp(Mth.lerp(0.025F, altar.getAmbSpinningSpeed(), altar.getProcessingProgress() * 0.01F), 0.25F, 1.0F));
        altar.setAmbrosiumFinalRotation(altar.getAmbrosiumFinalRotation() + (altar.getAmbSpinningSpeed() / 15.0F));

        int renderedIndex = 0;
        for (int slot = 1; slot <= 8; slot++) {
            ItemStack stack = altar.getItem(slot);
            if (!stack.is(AetherIITags.Items.ALTAR_FUEL)) {
                continue;
            }

            poseStack.pushPose();
            float radius = 1.25F;
            float theta = 5.0F;
            float dist = Mth.PI * renderedIndex / fuelCount * 2.0F;
            float x = radius * Mth.cos(theta + dist);
            float z = radius * Mth.sin(theta + dist);
            float deltaX = z * Mth.cos(altar.getAmbrosiumFinalRotation()) - x * Mth.sin(altar.getAmbrosiumFinalRotation());
            float deltaZ = x * Mth.cos(altar.getAmbrosiumFinalRotation()) + z * Mth.sin(altar.getAmbrosiumFinalRotation());
            poseStack.translate(0.5F, 1.25F, 0.5F);
            poseStack.scale(0.3F, 0.3F, 0.3F);
            poseStack.translate(deltaX, 0.0F, deltaZ);
            poseStack.mulPose(Axis.YN.rotationDegrees(this.entityRenderDispatcher.camera.getYRot()));
            this.itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, altar.getLevel(), seed + slot);
            poseStack.popPose();
            renderedIndex++;
        }
    }
}
