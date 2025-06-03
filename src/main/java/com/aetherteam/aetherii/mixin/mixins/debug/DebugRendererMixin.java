package com.aetherteam.aetherii.mixin.mixins.debug;

import com.aetherteam.aetherii.AetherII;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.debug.DebugRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugRenderer.class)
public class DebugRendererMixin {
    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/culling/Frustum;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;DDD)V", at = @At("HEAD"))
    private void render(PoseStack poseStack, Frustum frustum, MultiBufferSource.BufferSource bufferSource, double camX, double camY, double camZ, CallbackInfo ci) {
        DebugRenderer debugRenderer = (DebugRenderer) (Object) this;
        if (AetherII.DEBUG_MODE) {
            debugRenderer.pathfindingRenderer.render(poseStack, bufferSource, camX, camY, camZ);
        }
    }
}
