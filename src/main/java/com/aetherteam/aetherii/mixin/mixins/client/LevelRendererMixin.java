package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.client.renderer.AetherIIRenderTypes;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.resource.ResourceHandle;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.util.profiling.ProfilerFiller;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
	@Shadow @Final private RenderBuffers renderBuffers;

	// Injects after buffersource.endBatch(Sheets.chestSheet()), and before this.renderBuffers.crumblingBufferSource().endBatch()
	// If you have MinecraftModDev IJ plugin, it will link you to the wrong invocation of crumblingBufferSource()
	@Inject(method = "lambda$addMainPass$3", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderBuffers;crumblingBufferSource()Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;", shift = At.Shift.BEFORE))
	private void endRedFoilBatch(GpuBufferSlice shaderFog, DeltaTracker deltaTracker, Camera camera, ProfilerFiller profiler, Matrix4f frustumMatrix, Frustum frustum, ResourceHandle resourcehandle2, ResourceHandle resourcehandle3, boolean renderBlockOutline, ResourceHandle resourcehandle1, ResourceHandle resourcehandle, CallbackInfo ci) {
		MultiBufferSource.BufferSource bufferSource = this.renderBuffers.bufferSource();

		bufferSource.endBatch(AetherIIRenderTypes.irradiatedGlint());
	}
}
