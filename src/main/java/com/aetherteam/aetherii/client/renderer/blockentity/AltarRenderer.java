package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.block.utility.AltarBlock;
import com.aetherteam.aetherii.blockentity.AltarBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class AltarRenderer implements BlockEntityRenderer<AltarBlockEntity> {
    private final ItemRenderer itemRenderer;
    private float ambSpinningSpeed = 0.0F;
    private float ambrosiumFinalRotation = 0.0F;
    private float bobOffs = -1.0F;
    private float inputItemRotation = 0.0F;

    public AltarRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(AltarBlockEntity altarBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay, Vec3 pos) {
        Level level = altarBlockEntity.getLevel();
        BlockState blockState = altarBlockEntity.getBlockState();
        Direction direction = blockState.getValue(AltarBlock.FACING);
        if (level != null) {
            ItemStack itemStack = !altarBlockEntity.getItem(0).isEmpty() ? altarBlockEntity.getItem(0) : altarBlockEntity.getItem(2);
            if (this.bobOffs < 0) {
                this.bobOffs = level.getRandom().nextFloat() * Mth.PI * 2.0F;
            }
            if (!itemStack.isEmpty()) {
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
                ItemStack itemstack = altarBlockEntity.getItem(0);

                if (!itemstack.isEmpty()) {
                    poseStack.scale(0.5F, 0.5F, 0.5F);
                    Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, level, 0);
                }



//                Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, Minecraft.getInstance().level, 0);


//                poseStack.translate(0.5, 1.0, 0.5);
//                altarBlockEntity.getLevel().getRandom().setSeed(ItemEntityRenderer.getSeedForItemStack(itemStack));
//                BakedModel bakedModel = this.itemRenderer.getModel(itemStack, altarBlockEntity.getLevel(), null, 0);
//                boolean flag = bakedModel.isGui3d();
//                float f1 = Mth.sin(this.inputItemRotation / 10.0F + this.bobOffs) * 0.1F + 0.1F;
//                float f2 = bakedModel.getTransforms().getTransform(ItemDisplayContext.GROUND).scale.y();
//                poseStack.translate(0.0F, f1 + 0.25F * f2, 0.0F);
//                float f3 = this.inputItemRotation / 20.0F + this.bobOffs;
//                poseStack.mulPose(Axis.YP.rotation(f3));
//                ItemEntityRenderer.renderMultipleFromCount(this.itemRenderer, poseStack, multiBufferSource, packedLight, itemStack, bakedModel, flag, altarBlockEntity.getLevel().getRandom());
//                this.inputItemRotation += 1.0F / 10.0F;
                poseStack.popPose();
            }

            int amount = 0;
            for (int i = 1; i < 9; i++) {
                if (!altarBlockEntity.getItem(i).isEmpty()) {
                    amount++;
                }
            }
            this.spin(amount);
            if (amount > 0) {
                for (int i = 0; i < amount; i++) {
                    poseStack.pushPose();
                    float radius = 1.25F;
                    float theta = 5.0F;

                    float dist = Mth.PI * i / amount * 2.0F;
                    float x = radius * Mth.cos(theta + dist);
                    float y = 0.0F;
                    float z = radius * Mth.sin(theta + dist);
                    float deltaX = z * Mth.cos(this.ambrosiumFinalRotation) - x * Mth.sin(this.ambrosiumFinalRotation);
                    float deltaZ = x * Mth.cos(this.ambrosiumFinalRotation) + z * Mth.sin(this.ambrosiumFinalRotation);
                    poseStack.translate(0.5, 1.25, 0.5);
                    poseStack.scale(0.3F, 0.3F, 0.3F);
                    poseStack.translate(deltaX, y, deltaZ);

                    this.ambrosiumFinalRotation += this.ambSpinningSpeed / 100.0F;

                    Minecraft.getInstance().getItemRenderer().renderStatic(altarBlockEntity.getItem(i + 1), ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, altarBlockEntity.getLevel(), 0);

                    poseStack.popPose();
                }
            }
        }
    }

    public void spin(int count) {
        float spinningSpeed;
//        if (count < 4) {
//            spinningSpeed = 0.2F * (count + 1) * 0.5F;
//        } else {
//        }
        spinningSpeed = 0.5F;
        this.ambSpinningSpeed = spinningSpeed;
    }
}
