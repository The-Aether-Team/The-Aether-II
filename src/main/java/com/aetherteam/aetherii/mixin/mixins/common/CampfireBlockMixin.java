package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {

    @Inject(method = "isSmokeSource", at = @At("HEAD"), cancellable = true)
    private void aetherII$isSmokeSource(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(AetherIIBlocks.BRETTL_GRASS_BUNDLE.get())) {
            cir.setReturnValue(true);
        }
    }
}
