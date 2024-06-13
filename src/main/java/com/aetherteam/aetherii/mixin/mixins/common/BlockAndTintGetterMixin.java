package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.LightLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Optional;

@Mixin(BlockAndTintGetter.class)
public interface BlockAndTintGetterMixin {
    @Inject(at = @At(value = "HEAD"), method = "getBrightness(Lnet/minecraft/world/level/LightLayer;Lnet/minecraft/core/BlockPos;)I", cancellable = true)
    default void getBrightness(LightLayer lightLayer, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        BlockAndTintGetter getter = (BlockAndTintGetter) this;
        if (lightLayer == LightLayer.BLOCK) {
            int defaultLevel = getter.getLightEngine().getLayerListener(lightLayer).getLightValue(pos);
            int newLevel = 0;
            for (Map.Entry<BlockPos, Map<Entity, Integer>> entry : AetherII.cachedDynamicLightPoints.columnMap().entrySet()) {
                BlockPos lightPos = entry.getKey();
                int lightAtPos = entry.getValue().values().stream().reduce(Math::max).orElse(0);
                int dist = (int) pos.distSqr(lightPos);
                if (dist <= lightAtPos) {
                    newLevel = lightAtPos - dist;
                }
            }
            if (newLevel > defaultLevel) {
                cir.setReturnValue(newLevel);
            }
        }
    }
}
