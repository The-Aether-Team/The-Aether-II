package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.mixin.MixinHooks;
import com.aetherteam.aetherii.mixin.wrappers.common.LayerRenderStateWrapper;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStackRenderState.LayerRenderState.class)
public class LayerRenderStateMixin implements LayerRenderStateWrapper {
    @Unique
    private boolean aether_ii$isIrradiated;

    @WrapOperation(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderItem(Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II[ILnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/client/renderer/RenderType;Lnet/minecraft/client/renderer/item/ItemStackRenderState$FoilType;)V"))
    private void render(ItemDisplayContext context, PoseStack poseStack, MultiBufferSource bufferSource, int i, int j, int[] tints, BakedModel model, RenderType renderType, ItemStackRenderState.FoilType foil, Operation<Void> original) {
        if (this.aether_ii$getIrradiated()) {
            MixinHooks.renderIrradiated(poseStack, bufferSource, i, j, tints, model, renderType);
        } else {
            original.call(context, poseStack, bufferSource, i, j, tints, model, renderType, foil);
        }
    }

    @Override
    public void aether_ii$setIrradiated(boolean irradiated) {
        this.aether_ii$isIrradiated = irradiated;
    }

    @Override
    public boolean aether_ii$getIrradiated() {
        return this.aether_ii$isIrradiated;
    }
}
