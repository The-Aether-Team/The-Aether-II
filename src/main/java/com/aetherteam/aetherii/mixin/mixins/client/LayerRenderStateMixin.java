package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.mixin.mixins.client.accessor.ItemStackRenderStateAccessor;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.SubmitNodeCollectionAccessor;
import com.aetherteam.aetherii.mixin.wrappers.client.IrradiatedDataWrapper;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemStackRenderState.LayerRenderState.class)
public class LayerRenderStateMixin implements IrradiatedDataWrapper {
    @Final
    @Shadow
    ItemStackRenderState this$0;

    @Shadow
    @Final
    private List<BakedQuad> quads;
    @Shadow
    private ItemStackRenderState.FoilType foilType;
    @Unique
    private boolean aether_ii$isIrradiated;

    @Inject(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;III)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitItem(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/item/ItemDisplayContext;III[ILjava/util/List;Lnet/minecraft/client/renderer/item/ItemStackRenderState$FoilType;)V"))
    private void render(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, int outlineColor, CallbackInfo ci, @Local int[] tints) {
        if (this.aether_ii$getIrradiated()) {
            if (submitNodeCollector instanceof SubmitNodeStorage submitNodeStorage) {
                SubmitNodeStorage.ItemSubmit submit = new SubmitNodeStorage.ItemSubmit(poseStack.last().copy(), ((ItemStackRenderStateAccessor) this.this$0).aether_ii$getDisplayContext(), lightCoords, overlayCoords, outlineColor, tints, this.quads, this.foilType);
                ((IrradiatedDataWrapper) (Object) submit).aether_ii$setIrradiated(true);
                ((SubmitNodeCollectionAccessor) submitNodeStorage.order(0)).aether_ii$setWasUsed(true);
                submitNodeStorage.order(0).getItemSubmits().add(submit);
            }
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
