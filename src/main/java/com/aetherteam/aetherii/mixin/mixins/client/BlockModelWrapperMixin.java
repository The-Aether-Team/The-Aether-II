package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.mixin.wrappers.client.LayerRenderStateWrapper;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockModelWrapper.class)
public class BlockModelWrapperMixin {
    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState$LayerRenderState;setExtents(Ljava/util/function/Supplier;)V"))
    private static void update(ItemStackRenderState itemStackRenderState, ItemStack stack, ItemModelResolver modelResolver, ItemDisplayContext context, ClientLevel clientLevel, LivingEntity living, int i, CallbackInfo ci, @Local ItemStackRenderState.LayerRenderState layerRenderState) {
        if (!stack.hasFoil() && stack.is(AetherIITags.Items.IRRADIATED_ITEM)) {
            ((LayerRenderStateWrapper) layerRenderState).aether_ii$setIrradiated(true);
            itemStackRenderState.setAnimated();
        } else {
            ((LayerRenderStateWrapper) layerRenderState).aether_ii$setIrradiated(false);
        }
    }
}
