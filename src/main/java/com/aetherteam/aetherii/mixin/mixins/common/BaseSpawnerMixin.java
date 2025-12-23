package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.entity.ConditionalSpawner;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseSpawner.class)
public class BaseSpawnerMixin {
    @Inject(method = "delay", at = @At(value = "TAIL"))
    private void delay(Level level, BlockPos pos, CallbackInfo ci) {
        BaseSpawner spawner = (BaseSpawner) (Object) this;
        if (spawner instanceof ConditionalSpawner conditionalSpawner) {
            conditionalSpawner.markSyncDelay();
        }
    }
}
