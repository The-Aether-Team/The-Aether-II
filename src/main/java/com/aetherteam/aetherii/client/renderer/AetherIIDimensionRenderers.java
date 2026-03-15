package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.level.AetherSkyboxRenderer;
import com.aetherteam.aetherii.client.renderer.level.AetherWeatherEffectRender;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.neoforged.neoforge.client.event.ExtractLevelRenderStateEvent;
import net.neoforged.neoforge.client.event.RegisterCustomEnvironmentEffectRendererEvent;

public class AetherIIDimensionRenderers {
    public static final ContextKey<Float> DATA_THUNDER_KEY = new ContextKey<>(
            Identifier.fromNamespaceAndPath(AetherII.MODID, "thunder"));

    public static final Identifier AETHER_SKY_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "aether_sky");
    public static final Identifier AETHER_WEATHER_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "aether_weather");

    public static void registerDimensionEffect(RegisterCustomEnvironmentEffectRendererEvent event) {
        event.registerSkyboxRenderer(AETHER_SKY_ID, new AetherSkyboxRenderer());
        event.registerWeatherEffectRenderer(AETHER_WEATHER_ID, new AetherWeatherEffectRender());
    }

    public static void extractDimensionEffect(ExtractLevelRenderStateEvent event) {
        if (event.getLevel().dimensionTypeRegistration().is(AetherIIDimensions.AETHER_HOLY_ISLES_DIMENSION_TYPE)) {
            event.getRenderState().setRenderData(DATA_THUNDER_KEY, event.getLevel().getThunderLevel(Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false)));
        }
    }
}
