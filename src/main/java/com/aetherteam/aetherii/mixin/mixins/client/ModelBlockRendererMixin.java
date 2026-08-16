package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.block.natural.AetherLeavesBlock;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderers;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModelBlockRenderer.class)
public class ModelBlockRendererMixin {
    @Inject(method = "forceOpaque(ZLnet/minecraft/world/level/block/state/BlockState;)Z", at = @At(value = "HEAD"), cancellable = true)
    private static void forceOpaque(boolean cutoutLeaves, BlockState blockState, CallbackInfoReturnable<Boolean> cir) {
        if (!cutoutLeaves && blockState.getBlock() instanceof AetherLeavesBlock) {
            cir.setReturnValue(false);
        }
        if (!cutoutLeaves && AetherIIRenderers.isFastBlock(blockState)) {
            cir.setReturnValue(true);
        }
    }
}
