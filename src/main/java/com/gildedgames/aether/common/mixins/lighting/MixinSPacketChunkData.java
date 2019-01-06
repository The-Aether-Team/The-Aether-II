package com.gildedgames.aether.common.mixins.lighting;

import com.gildedgames.aether.api.lighting.ILightingEngineProvider;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SPacketChunkData.class)
public class MixinSPacketChunkData
{
	@Inject(method = "<init>(Lnet/minecraft/world/chunk/Chunk;I)V", at = @At("HEAD"))
	private void onConstructed(Chunk chunkIn, int changedSectionFilter, CallbackInfo ci)
	{
		((ILightingEngineProvider) chunkIn).getLightingEngine().procLightUpdates();
	}
}
