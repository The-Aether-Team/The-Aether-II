package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightmapRenderStateExtractor;
import net.minecraft.client.renderer.state.LightmapRenderState;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightmapRenderStateExtractor.class)
public class LightmapRenderStateExtractorMixin {
    @Inject(method = "extract(Lnet/minecraft/client/renderer/state/LightmapRenderState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V", shift = At.Shift.BEFORE))
    private void render(LightmapRenderState renderState, float partialTicks, CallbackInfo ci) {
        if (Minecraft.getInstance().player != null) {
            float cameraHeight = 0.0175F * (float) (Minecraft.getInstance().player.getEyePosition(partialTicks).y() - 48);
//            AetherII.LOGGER.info(cameraHeight + " " + Mth.clamp(cameraHeight, 0.0F, 1.0F));
            renderState.brightness = Mth.clamp(renderState.brightness * cameraHeight, -0.1F, 1.0F);
        }
    }
}
