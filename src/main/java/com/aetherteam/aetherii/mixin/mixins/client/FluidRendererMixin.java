package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.RenderSectionRegionAccessor;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.FluidRenderer;
import net.minecraft.client.renderer.chunk.RenderSectionRegion;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.material.FluidState;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FluidRenderer.class)
public class FluidRendererMixin {
    @WrapOperation(method = "tesselate(Lnet/minecraft/client/renderer/block/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/client/renderer/block/FluidRenderer$Output;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/FluidState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/FluidRenderer;addFace(Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFFFFFFFFFFFFFIIZ)V"))
    private void tesselate(FluidRenderer instance, VertexConsumer builder, float x0, float y0, float z0, float u0, float v0, float x1, float y1, float z1, float u1, float v1, float x2, float y2, float z2, float u2, float v2, float x3, float y3, float z3, float u3, float v3, int color, int lightCoords, boolean addBackFace, Operation<Void> original, @Local(argsOnly = true) LocalRef<BlockAndTintGetter> level, @Local(argsOnly = true) LocalRef<BlockPos> pos, @Local(argsOnly = true) LocalRef<FluidState> fluidState) {
        boolean aether = level.get() instanceof RenderSectionRegion renderSectionRegion && ((RenderSectionRegionAccessor) renderSectionRegion).aether_ii$getLevel() instanceof ClientLevel clientLevel && clientLevel.getBiome(pos.get()).is(AetherIITags.Biomes.THE_AETHER);
        if (!aether || !this.addCustomFace(builder, x0, y0, z0, u0, v0, x1, y1, z1, u1, v1, x2, y2, z2, u2, v2, x3, y3, z3, u3, v3, color, lightCoords, addBackFace, pos.get().getY(), level.get().getMinY())) {
            original.call(instance, builder, x0, y0, z0, u0, v0, x1, y1, z1, u1, v1, x2, y2, z2, u2, v2, x3, y3, z3, u3, v3, color, lightCoords, addBackFace);
        }
    }

    private boolean addCustomFace(
            VertexConsumer builder,
            float x0,
            float y0,
            float z0,
            float u0,
            float v0,
            float x1,
            float y1,
            float z1,
            float u1,
            float v1,
            float x2,
            float y2,
            float z2,
            float u2,
            float v2,
            float x3,
            float y3,
            float z3,
            float u3,
            float v3,
            int color,
            int lightCoords,
            boolean addBackFace,
            int currentY, int bottomY
    ) {
        int range = 8;
        int max = bottomY + range;
        if (currentY < max) {
            this.customVertex(builder, x0, y0, z0, color, u0, v0, lightCoords, currentY, bottomY);
            this.customVertex(builder, x1, y1, z1, color, u1, v1, lightCoords, currentY, bottomY);
            this.customVertex(builder, x2, y2, z2, color, u2, v2, lightCoords, currentY, bottomY);
            this.customVertex(builder, x3, y3, z3, color, u3, v3, lightCoords, currentY, bottomY);
            if (addBackFace) {
                this.customVertex(builder, x3, y3, z3, color, u3, v3, lightCoords, currentY, bottomY);
                this.customVertex(builder, x2, y2, z2, color, u2, v2, lightCoords, currentY, bottomY);
                this.customVertex(builder, x1, y1, z1, color, u1, v1, lightCoords, currentY, bottomY);
                this.customVertex(builder, x0, y0, z0, color, u0, v0, lightCoords, currentY, bottomY);
            }
            return true;
        }
        return false;
    }

    @Unique
    private void customVertex(VertexConsumer builder, float x, float y, float z, int color, float u, float v, int lightCoords, int currentY, int bottomY) {
        int range = 8;
        float opacityStep = 1.0F / range;
        float offsetY = currentY - bottomY;
        boolean isUpperVertex = y - offsetY > 0.005;
        float trueAlpha = isUpperVertex ? opacityStep * (offsetY + 1) : opacityStep * offsetY;
        Vector4f vector4f = ARGB.vector4fFromARGB32(color);
        int trueColor = ARGB.colorFromFloat(trueAlpha, vector4f.x(), vector4f.y(), vector4f.z());
        builder.addVertex(x, y, z, trueColor, u, v, OverlayTexture.NO_OVERLAY, lightCoords, 0.0F, 1.0F, 0.0F);
    }
}