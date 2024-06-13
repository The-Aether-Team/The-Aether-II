package com.aetherteam.aetherii.client.event.listeners;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.event.hooks.DimensionRenderEffectHooks;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.SectionPos;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.event.TickEvent;
import org.apache.commons.lang3.tuple.Triple;

public class DimensionRenderEffectListeners {
    public static void listen(IEventBus bus) {
        bus.addListener(DimensionRenderEffectListeners::onRenderFogColor);
        bus.addListener(DimensionRenderEffectListeners::renderTick);
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

    public static void renderTick(final TickEvent.RenderTickEvent event) { //todo temp
        if (event.phase == TickEvent.Phase.START && Minecraft.getInstance().level != null) {
            AetherII.cachedDynamicLightPoints.columnMap().keySet().forEach((pos) -> {
                SectionPos sectionPos = SectionPos.of(pos);
                Minecraft.getInstance().levelRenderer.setSectionDirtyWithNeighbors(sectionPos.getX(), sectionPos.getY(), sectionPos.getZ());
            });
        }
    }
}
