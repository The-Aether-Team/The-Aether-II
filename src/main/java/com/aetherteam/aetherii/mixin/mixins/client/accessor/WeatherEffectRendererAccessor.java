package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(WeatherEffectRenderer.class)
public interface WeatherEffectRendererAccessor {
    @Accessor("rainSoundTime")
    int aether_ii$getRainSoundTime();

    @Accessor("rainSoundTime")
    void aether_ii$setRainSoundTime(int rainSoundTime);

//    @Invoker
//    void callCollectColumnInstances(Level level, int ticks, float partialTick, Vec3 cameraPosition, int radius, List<WeatherEffectRenderer.ColumnInstance> rainColumnInstances, List<WeatherEffectRenderer.ColumnInstance> snowColumnInstances);

    @Invoker("renderInstances")
    void callRenderInstances(VertexConsumer buffer, List<WeatherEffectRenderer.ColumnInstance> columnInstances, Vec3 cameraPosition, float amount, int radius, float rainLevel);
}
