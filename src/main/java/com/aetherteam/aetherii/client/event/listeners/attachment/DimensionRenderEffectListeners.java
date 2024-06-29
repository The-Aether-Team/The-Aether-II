package com.aetherteam.aetherii.client.event.listeners.attachment;

import com.aetherteam.aetherii.client.event.hooks.attachment.DimensionRenderEffectHooks;
import net.minecraft.client.Camera;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ViewportEvent;
import org.apache.commons.lang3.tuple.Triple;

public class DimensionRenderEffectListeners {
    public static void listen(IEventBus bus) {
        bus.addListener(DimensionRenderEffectListeners::onRenderFogColor);
    }

    public static void onRenderFogColor(ViewportEvent.ComputeFogColor event) {
        Camera camera = event.getCamera();
        Triple<Float, Float, Float> renderFogColors = DimensionRenderEffectHooks.renderFogColors(camera, event.getRed(), event.getGreen(), event.getBlue());
        if (renderFogColors != null) {
            event.setRed(renderFogColors.getLeft());
            event.setGreen(renderFogColors.getMiddle());
            event.setBlue(renderFogColors.getRight());
        }
        Triple<Float, Float, Float> adjustWeatherFogColors = DimensionRenderEffectHooks.adjustWeatherFogColors(camera, event.getRed(), event.getGreen(), event.getBlue());
        if (adjustWeatherFogColors != null) {
            event.setRed(adjustWeatherFogColors.getLeft());
            event.setGreen(adjustWeatherFogColors.getMiddle());
            event.setBlue(adjustWeatherFogColors.getRight());
        }
    }
}
