package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import net.minecraft.client.renderer.WeatherEffectRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WeatherEffectRenderer.class)
public interface WeatherEffectRendererAccessor {
    @Accessor("rainSoundTime")
    int aether_ii$getRainSoundTime();

    @Accessor("rainSoundTime")
    void aether_ii$setRainSoundTime(int rainSoundTime);
}
