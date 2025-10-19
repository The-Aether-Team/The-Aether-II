package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.mixin.MixinHooks;
import com.aetherteam.aetherii.mixin.wrappers.common.LayerRenderStateWrapper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(ItemStackRenderState.LayerRenderState.class)
public class LayerRenderStateMixin implements LayerRenderStateWrapper {
    @Shadow
    private int[] tintLayers;
    @Shadow
    @Final
    private List<BakedQuad> quads;
    @Shadow
    @Nullable
    private RenderType renderType;
    @Unique
    private boolean aether_ii$isIrradiated;

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderItem(Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II[ILjava/util/List;Lnet/minecraft/client/renderer/RenderType;Lnet/minecraft/client/renderer/item/ItemStackRenderState$FoilType;)V"))
    private void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, CallbackInfo ci) {
        if (this.aether_ii$getIrradiated()) {
            MixinHooks.renderIrradiated(poseStack, bufferSource, packedLight, packedOverlay, tintLayers, quads, renderType);
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
