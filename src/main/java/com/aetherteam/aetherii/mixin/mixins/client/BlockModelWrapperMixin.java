package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.mixin.wrappers.common.LayerRenderStateWrapper;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockModelWrapper.class)
public class BlockModelWrapperMixin {
    @Inject(method = "lambda$update$0(Lnet/minecraft/client/renderer/item/ItemStackRenderState;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/item/ItemStackRenderState$FoilType;[ILnet/minecraft/client/resources/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState$LayerRenderState;setupBlockModel(Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/client/renderer/RenderType;)V", shift = At.Shift.AFTER))
    private static void update(ItemStackRenderState renderState, ItemStack stack, ItemStackRenderState.FoilType foilType, int[] tints, BakedModel pass, CallbackInfo ci, @Local ItemStackRenderState.LayerRenderState layerRenderState) {
        ((LayerRenderStateWrapper) layerRenderState).aether_ii$setIrradiated(!stack.hasFoil() && stack.is(AetherIITags.Items.IRRADIATED_ITEM));
    }
}
