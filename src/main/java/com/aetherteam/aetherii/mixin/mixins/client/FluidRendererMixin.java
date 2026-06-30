package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.RenderChunkRegionAccessor;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LiquidBlockRenderer.class)
public class FluidRendererMixin {
    @Unique
    private static final ThreadLocal<FluidFadeContext> aether_ii$fluidFadeContext = ThreadLocal.withInitial(FluidFadeContext::inactive);

    @Inject(method = "tesselate", at = @At("HEAD"))
    private void captureAetherFluidFade(BlockAndTintGetter level, BlockPos pos, VertexConsumer builder, BlockState blockState, FluidState fluidState, CallbackInfo ci) {
        Level biomeLevel = null;
        if (level instanceof Level directLevel) {
            biomeLevel = directLevel;
        } else if (level instanceof RenderChunkRegion renderChunkRegion) {
            biomeLevel = ((RenderChunkRegionAccessor) renderChunkRegion).aether_ii$getLevel();
        }

        int minY = level.getMinBuildHeight();
        boolean fadesAtBottom = biomeLevel != null && pos.getY() < minY + 8 && biomeLevel.getBiome(pos).is(AetherIITags.Biomes.THE_AETHER);
        aether_ii$fluidFadeContext.set(fadesAtBottom ? new FluidFadeContext(pos.getY(), minY, true) : FluidFadeContext.inactive());
    }

    @Inject(method = "tesselate", at = @At("RETURN"))
    private void clearAetherFluidFade(BlockAndTintGetter level, BlockPos pos, VertexConsumer builder, BlockState blockState, FluidState fluidState, CallbackInfo ci) {
        aether_ii$fluidFadeContext.remove();
    }

    @ModifyArg(method = "vertex", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;color(FFFF)Lcom/mojang/blaze3d/vertex/VertexConsumer;"), index = 3)
    private float fadeBottomAetherFluid(float alpha, @Local(argsOnly = true, ordinal = 1) double y) {
        FluidFadeContext context = aether_ii$fluidFadeContext.get();
        if (!context.active()) {
            return alpha;
        }
        int range = 8;
        float opacityStep = 1.0F / range;
        int offsetY = context.currentY() - context.bottomY();
        boolean upperVertex = y - (context.currentY() & 15) > 0.005;
        return upperVertex ? opacityStep * (offsetY + 1) : opacityStep * offsetY;
    }

    @Unique
    private record FluidFadeContext(int currentY, int bottomY, boolean active) {
        private static FluidFadeContext inactive() {
            return new FluidFadeContext(0, 0, false);
        }
    }
}
