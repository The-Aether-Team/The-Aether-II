package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.client.renderer.AetherIIRenderTypes;
import com.mojang.blaze3d.resource.ResourceHandle;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.FogParameters;
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
	@Inject(method = "lambda$addMainPass$2(Lnet/minecraft/client/renderer/FogParameters;Lnet/minecraft/client/DeltaTracker;Lnet/minecraft/client/Camera;Lnet/minecraft/util/profiling/ProfilerFiller;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lnet/minecraft/client/renderer/culling/Frustum;ZLcom/mojang/blaze3d/resource/ResourceHandle;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderBuffers;crumblingBufferSource()Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;", shift = At.Shift.BEFORE))
	private void endRedFoilBatch(FogParameters p_363661_, DeltaTracker p_360931_, Camera p_363453_, ProfilerFiller p_362234_, Matrix4f p_362420_, Matrix4f p_361272_, ResourceHandle resourcehandle2, ResourceHandle resourcehandle, ResourceHandle resourcehandle3, ResourceHandle resourcehandle4, Frustum p_366590_, boolean p_363964_, ResourceHandle resourcehandle1, CallbackInfo ci) {
		MultiBufferSource.BufferSource bufferSource = this.renderBuffers.bufferSource();

		bufferSource.endBatch(AetherIIRenderTypes.irradiatedGlint());
	}
}
