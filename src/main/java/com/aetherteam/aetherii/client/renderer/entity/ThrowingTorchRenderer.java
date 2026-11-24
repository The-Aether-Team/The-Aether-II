package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.state.ThrowingTorchRenderState;
import com.aetherteam.aetherii.entity.projectile.ThrowingTorch;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;

public class ThrowingTorchRenderer extends EntityRenderer<ThrowingTorch, ThrowingTorchRenderState> {
    private final ItemModelResolver itemModelResolver;

    public ThrowingTorchRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemModelResolver = context.getItemModelResolver();
    }

    @Override
    public void submit(ThrowingTorchRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        float yRot = state.yRot;
        if (state.thrownHand == InteractionHand.MAIN_HAND) {
            yRot += 105.0F;
        } else {
            yRot += 75.0F;
        }
        Quaternionf quaternion = Axis.YP.rotationDegrees(yRot);
        quaternion.mul(Axis.ZP.rotationDegrees(state.ageInTicks * 40));
        poseStack.mulPose(quaternion);
        poseStack.translate(0.075F, -0.05F, 0.0F);
        state.item.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        poseStack.popPose();
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    public ThrowingTorchRenderState createRenderState() {
        return new ThrowingTorchRenderState();
    }

    @Override
    public void extractRenderState(ThrowingTorch entity, ThrowingTorchRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        ItemStack itemStack = entity.getItem();
        itemStack.set(DataComponents.ITEM_MODEL, Identifier.fromNamespaceAndPath(AetherII.MODID, "ambrosium_throwing_torch_emissive"));
        this.itemModelResolver.updateForNonLiving(state.item, itemStack, ItemDisplayContext.GROUND, entity);
        state.yRot = entity.getYRot();
        state.thrownHand = entity.getThrownHand();
    }
}
