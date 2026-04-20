package com.aetherteam.aetherii.client.renderer.blockentity;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.utility.AltarBlock;
import com.aetherteam.aetherii.blockentity.AltarBlockEntity;
import com.aetherteam.aetherii.client.renderer.blockentity.state.AltarRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.state.ItemClusterRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;

public class AltarRenderer implements BlockEntityRenderer<AltarBlockEntity, AltarRenderState> {
    private final ItemModelResolver itemModelResolver;
    private final RandomSource random = RandomSource.create();
    private EntityRenderDispatcher blockEntityRenderDispatcher;

    public AltarRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
        this.blockEntityRenderDispatcher = context.entityRenderer();
    }

    @Override
    public AltarRenderState createRenderState() {
        return new AltarRenderState();
    }

    @Override
    public void extractRenderState(AltarBlockEntity altarBlockEntity, AltarRenderState renderState, float partialTick, Vec3 pos, ModelFeatureRenderer.CrumblingOverlay overlay) {
        BlockEntityRenderer.super.extractRenderState(altarBlockEntity, renderState, partialTick, pos, overlay);
        ItemStack outputStack = altarBlockEntity.getItem(9);

        renderState.facing = altarBlockEntity.getBlockState().getValue(AltarBlock.FACING);
        renderState.displayItem = new ItemClusterRenderState();
        this.itemModelResolver.updateForTopItem(renderState.displayItem.item, outputStack, ItemDisplayContext.GROUND, altarBlockEntity.getLevel(), null, 0);
        renderState.displayItem.count = ItemClusterRenderState.getRenderedAmount(outputStack.getCount());
        renderState.displayItem.seed = ItemClusterRenderState.getSeedForItemStack(outputStack);

        int i = (int) altarBlockEntity.getBlockPos().asLong();
        this.itemModelResolver
                .updateForTopItem(renderState.itemInput, altarBlockEntity.getItems().get(0), ItemDisplayContext.FIXED, altarBlockEntity.getLevel(), null, i);
        renderState.fuelItems = new ArrayList<>();

        for (int i2 = 1; i2 <= 8; i2++) {
            ItemStack fuelStack = altarBlockEntity.getItems().get(i2);
            ItemStackRenderState itemstackrenderstate = new ItemStackRenderState();
            if (fuelStack.is(AetherIITags.Items.ALTAR_FUEL)) {
                this.itemModelResolver
                        .updateForTopItem(itemstackrenderstate, fuelStack, ItemDisplayContext.FIXED, altarBlockEntity.getLevel(), null, i + i2);
                renderState.fuelItems.add(itemstackrenderstate);
            }
        }
        renderState.progress = altarBlockEntity.getProcessingProgress();
        if (renderState.bobOff < 0) {
            renderState.bobOff = altarBlockEntity.getLevel().getRandom().nextFloat() * Mth.TWO_PI;
        }
        altarBlockEntity.setInputItemRotation(altarBlockEntity.getInputItemRotation() + ((1.0F + partialTick) / 10.0F));
        altarBlockEntity.setAmbSpinningSpeed(Math.clamp(Mth.lerp(0.025F, altarBlockEntity.getAmbSpinningSpeed(), altarBlockEntity.getProcessingProgress() * 0.01F), 0.25F, 1.0F));
        altarBlockEntity.setAmbrosiumFinalRotation(altarBlockEntity.getAmbrosiumFinalRotation() + (altarBlockEntity.getAmbSpinningSpeed() / 15.0F));

        renderState.inputItemRotation = altarBlockEntity.getInputItemRotation();
        renderState.ambSpinningSpeed = altarBlockEntity.getAmbSpinningSpeed();
        renderState.ambFinalSpeed = altarBlockEntity.getAmbrosiumFinalRotation();
    }

    @Override
    public void submit(AltarRenderState alterRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        ItemStackRenderState inputStack = alterRenderState.itemInput;

        if (!inputStack.isEmpty()) {
            poseStack.pushPose();
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            poseStack.translate(0.5F, 0.5F, -1.01725F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(alterRenderState.facing.toYRot() - 180));

            if (!inputStack.isEmpty()) {
                poseStack.scale(0.5F, 0.5F, 0.5F);
                inputStack.submit(poseStack, submitNodeCollector, alterRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            }
            poseStack.popPose();
        }
        if (!alterRenderState.displayItem.item.isEmpty()) {
            poseStack.pushPose();

            poseStack.translate(0.5, 1.0, 0.5);
            AABB aabb = alterRenderState.displayItem.item.getModelBoundingBox();
            float f = -((float) aabb.minY) + 0.0625F;
            float f1 = Mth.sin(alterRenderState.inputItemRotation / 10.0F + alterRenderState.bobOff) * 0.05F + 0.05F;
            poseStack.translate(0.0F, f1 + f, 0.0F);
            float f2 = ItemEntity.getSpin(alterRenderState.inputItemRotation, alterRenderState.bobOff);
            poseStack.mulPose(Axis.YP.rotation(f2));
            ItemEntityRenderer.renderMultipleFromCount(poseStack, submitNodeCollector, alterRenderState.lightCoords, alterRenderState.displayItem, this.random);

            poseStack.popPose();
        }
        for (int i = 0; i < alterRenderState.fuelItems.size(); i++) {
            ItemStackRenderState fuelStack = alterRenderState.fuelItems.get(i);
            poseStack.pushPose();
            float radius = 1.25F;
            float theta = 5.0F;

            float dist = Mth.PI * i / alterRenderState.fuelItems.size() * 2.0F;
            float x = radius * Mth.cos(theta + dist);
            float y = 0.0F;
            float z = radius * Mth.sin(theta + dist);
            float deltaX = z * Mth.cos(alterRenderState.ambFinalSpeed) - x * Mth.sin(alterRenderState.ambFinalSpeed);
            float deltaZ = x * Mth.cos(alterRenderState.ambFinalSpeed) + z * Mth.sin(alterRenderState.ambFinalSpeed);
            poseStack.translate(0.5, 1.25, 0.5);
            poseStack.scale(0.3F, 0.3F, 0.3F);
            poseStack.translate(deltaX, y, deltaZ);
            poseStack.mulPose(Axis.YN.rotationDegrees(this.blockEntityRenderDispatcher.camera.yRot()));
            fuelStack.submit(poseStack, submitNodeCollector, alterRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);

            poseStack.popPose();
        }
    }
}
