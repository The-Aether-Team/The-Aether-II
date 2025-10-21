package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.utility.AltarBlock;
import com.aetherteam.aetherii.blockentity.AltarBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.state.ItemClusterRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class AltarRenderer implements BlockEntityRenderer<AltarBlockEntity> {
    private final ItemClusterRenderState renderState = new ItemClusterRenderState();
    private final ItemModelResolver itemModelResolver;
    private float ambSpinningSpeed = 0.0F;
    private float ambrosiumFinalRotation = 0.0F;
    private float bobOffs = -1.0F;
    private float inputItemRotation = 0.0F;

    public AltarRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.getItemModelResolver();
    }

    @Override
    public void render(AltarBlockEntity altarBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay, Vec3 pos) {
        Level level = altarBlockEntity.getLevel();
        BlockState blockState = altarBlockEntity.getBlockState();
        Direction direction = blockState.getValue(AltarBlock.FACING);
        if (level != null) {
            ItemStack inputStack = altarBlockEntity.getItem(0);
            ItemStack outputStack = altarBlockEntity.getItem(9);

            if (this.bobOffs < 0) {
                this.bobOffs = level.getRandom().nextFloat() * Mth.TWO_PI;
            }
            if (!inputStack.isEmpty()) {
                poseStack.pushPose();
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                poseStack.translate(0.5F, 0.5F, -1.01725F);
                poseStack.mulPose(Axis.ZN.rotationDegrees(direction.toYRot() - 180));

                if (!inputStack.isEmpty()) {
                    poseStack.scale(0.5F, 0.5F, 0.5F);
                    Minecraft.getInstance().getItemRenderer().renderStatic(inputStack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, level, 0);
                }
                poseStack.popPose();
            }
            if (!outputStack.isEmpty()) {
                this.itemModelResolver.updateForTopItem(this.renderState.item, outputStack, ItemDisplayContext.GROUND, level, null, 0);
                this.renderState.count = ItemClusterRenderState.getRenderedAmount(outputStack.getCount());
                this.renderState.seed = ItemClusterRenderState.getSeedForItemStack(outputStack);

                poseStack.pushPose();

                poseStack.translate(0.5, 1.0, 0.5);
                AABB aabb = this.renderState.item.getModelBoundingBox();
                float f = -((float) aabb.minY) + 0.0625F;
                float f1 = Mth.sin(this.inputItemRotation / 10.0F + this.bobOffs) * 0.05F + 0.05F;
                poseStack.translate(0.0F, f1 + f, 0.0F);
                float f2 = ItemEntity.getSpin(this.inputItemRotation, this.bobOffs);
                poseStack.mulPose(Axis.YP.rotation(f2));
                ItemEntityRenderer.renderMultipleFromCount(poseStack, multiBufferSource, packedLight, this.renderState, level.getRandom());
                this.inputItemRotation += (1.0F + partialTick) / 10.0F;

                poseStack.popPose();
            }

            this.ambSpinningSpeed = Math.clamp(Mth.lerp(0.025F, this.ambSpinningSpeed, altarBlockEntity.getProcessingProgress() * 0.01F) , 0.25F, 1.0F);

            List<ItemStack> fuelStacks = new ArrayList<>();
            for (int i = 1; i <= 8; i++) {
                ItemStack fuelStack = altarBlockEntity.getItem(i);
                if (fuelStack.is(AetherIITags.Items.ALTAR_FUEL)) {
                    fuelStacks.add(fuelStack);
                }
            }
            for (int i = 0; i < fuelStacks.size(); i++) {
                ItemStack fuelStack = fuelStacks.get(i);
                poseStack.pushPose();
                float radius = 1.25F;
                float theta = 5.0F;

                float dist = Mth.PI * i / fuelStacks.size() * 2.0F;
                float x = radius * Mth.cos(theta + dist);
                float y = 0.0F;
                float z = radius * Mth.sin(theta + dist);
                float deltaX = z * Mth.cos(this.ambrosiumFinalRotation) - x * Mth.sin(this.ambrosiumFinalRotation);
                float deltaZ = x * Mth.cos(this.ambrosiumFinalRotation) + z * Mth.sin(this.ambrosiumFinalRotation);
                poseStack.translate(0.5, 1.25, 0.5);
                poseStack.scale(0.3F, 0.3F, 0.3F);
                poseStack.translate(deltaX, y, deltaZ);

                Minecraft.getInstance().getItemRenderer().renderStatic(fuelStack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, altarBlockEntity.getLevel(), 0);

                poseStack.popPose();
            }

            this.ambrosiumFinalRotation += this.ambSpinningSpeed / 15.0F;
        }
    }
}
