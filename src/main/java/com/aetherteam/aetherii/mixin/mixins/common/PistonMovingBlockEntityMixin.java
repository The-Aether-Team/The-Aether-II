package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonMovingBlockEntity.class)
public class PistonMovingBlockEntityMixin {
    @Shadow
    private BlockState movedState;

    @Inject(method = "isStickyForEntities()Z", at = @At(value = "HEAD"), cancellable = true)
    private void isStickyForEntities(CallbackInfoReturnable<Boolean> cir) {
        if (this.movedState.is(AetherIIBlocks.GEL_BLOCK.get())) {
            cir.setReturnValue(true);
        }
    }
}
