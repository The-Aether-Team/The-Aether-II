package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.client.renderer.AetherIIRenderTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.*;
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
	@Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderBuffers;crumblingBufferSource()Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;", ordinal = 2, shift = At.Shift.BEFORE))
	private void endRedFoilBatch(DeltaTracker deltaTracker, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f frustumMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
		MultiBufferSource.BufferSource bufferSource = this.renderBuffers.bufferSource();

		bufferSource.endBatch(AetherIIRenderTypes.irradiatedGlint());
	}
}
