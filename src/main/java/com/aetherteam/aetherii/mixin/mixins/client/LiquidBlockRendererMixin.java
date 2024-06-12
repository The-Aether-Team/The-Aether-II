package com.aetherteam.aetherii.mixin.mixins.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LiquidBlockRenderer.class)
public class LiquidBlockRendererMixin {
    @Unique
    private boolean isSource;
    @Unique
    private int currentY;
    @Unique
    private int bottomY;

    @Inject(method = "tesselate(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/FluidState;)V", at = @At(value = "HEAD"))
    private void tesselate(BlockAndTintGetter level, BlockPos pos, VertexConsumer consumer, BlockState blockState, FluidState fluidState, CallbackInfo ci) { //todo level effects check
        this.isSource = fluidState.isSource();
        this.currentY = pos.getY();
        this.bottomY = level.getMinBuildHeight();
    }

    @Inject(method = "vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;DDDFFFFFFI)V", at = @At(value = "HEAD"), cancellable = true)
    private void vertex(VertexConsumer consumer, double x, double y, double z, float r, float g, float b, float a, float u, float v, int light, CallbackInfo ci) {
        int range = 8;
        float opacityStep = 1.0F / range;
        int current = this.currentY;
        int min = this.bottomY;
        int max = min + range;
        boolean isUpperVertex = y - current > 0.005;
        if (!this.isSource && this.currentY < max) {
            float trueAlpha = isUpperVertex ? opacityStep * (current + 1) : opacityStep * current;
            consumer.vertex(x, y, z).color(r, g, b, trueAlpha).uv(u, v).uv2(light).normal(0.0F, 1.0F, 0.0F).endVertex();
            ci.cancel();
        }
    }
}
