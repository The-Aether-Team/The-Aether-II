package com.aetherteam.aetherii.mixin.mixins.client;

import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LiquidBlockRenderer.class)
public class LiquidBlockRendererMixin { //todo: make work with new render changes in 1.21
//    @WrapOperation(method = "tesselate(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/FluidState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/LiquidBlockRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;DDDFFFFFFI)V"))
//    private void customVertex(LiquidBlockRenderer instance, VertexConsumer consumer, double x, double y, double z, float r, float g, float b, float a, float u, float v, int light, Operation<Void> original,
//                              @Local(argsOnly = true) LocalRef<BlockAndTintGetter> level, @Local(argsOnly = true) LocalRef<BlockPos> pos, @Local(argsOnly = true) LocalRef<FluidState> fluidState) {
//        boolean aetherEffects = level.get() instanceof RenderChunkRegion renderChunkRegion && ((RenderChunkRegionAccessor) renderChunkRegion).aether_ii$getLevel() instanceof ClientLevel clientLevel && clientLevel.effects() instanceof HighlandsSpecialEffects;
//        if (!aetherEffects || !this.vertex(consumer, x, y, z, r, g, b, a, u, v, light, pos.get().getY(), level.get().getMinBuildHeight())) {
//            original.call(instance, consumer, x, y, z, r, g, b, a, u, v, light);
//        }
//    }
//
//    @Unique
//    private boolean vertex(VertexConsumer consumer, double x, double y, double z, float r, float g, float b, float a, float u, float v, int light, int currentY, int bottomY) {
//        int range = 8;
//        float opacityStep = 1.0F / range;
//        int max = bottomY + range;
//        boolean isUpperVertex = y - currentY > 0.005;
//        if (currentY < max) {
//            float trueAlpha = isUpperVertex ? opacityStep * (currentY + 1) : opacityStep * currentY;
//            consumer.vertex(x, y, z).color(r, g, b, trueAlpha).uv(u, v).uv2(light).normal(0.0F, 1.0F, 0.0F).endVertex();
//            return true;
//        } else {
//            return false;
//        }
//    }
}