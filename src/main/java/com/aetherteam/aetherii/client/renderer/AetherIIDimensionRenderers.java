package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.level.HolyIslesCloudsRenderer;
import com.aetherteam.aetherii.client.renderer.level.HolyIslesSkyboxRenderer;
import com.aetherteam.aetherii.client.renderer.level.HolyIslesWeatherEffectRenderer;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import com.aetherteam.aetherii.world.AetherIIEnvironmentAttributes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.neoforged.neoforge.client.event.ExtractLevelRenderStateEvent;
import net.neoforged.neoforge.client.event.RegisterCustomEnvironmentEffectRendererEvent;

public class AetherIIDimensionRenderers {
    public static final ContextKey<Integer> DATA_BASE_SKY_COLOR_KEY = new ContextKey<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "base_sky_color"));
    public static final ContextKey<Integer> DATA_TOP_SKY_GRADIENT_COLOR_KEY = new ContextKey<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "top_sky_gradient_color"));
    public static final ContextKey<Integer> DATA_CLOUD_COVER_COLOR_KEY = new ContextKey<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "cloud_cover_color"));

    public static final Identifier HOLY_ISLES_SKY_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "holy_isles_sky");
    public static final Identifier HOLY_ISLES_WEATHER_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "holy_isles_weather");
    public static final Identifier HOLY_ISLES_CLOUDS_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "holy_isles_clouds");

    public static void registerDimensionEffect(RegisterCustomEnvironmentEffectRendererEvent event) {
        event.registerSkyboxRenderer(HOLY_ISLES_SKY_ID, new HolyIslesSkyboxRenderer());
        event.registerWeatherEffectRenderer(HOLY_ISLES_WEATHER_ID, new HolyIslesWeatherEffectRenderer());
        event.registerCloudRenderer(HOLY_ISLES_CLOUDS_ID, new HolyIslesCloudsRenderer());
    }

    public static void extractDimensionEffect(ExtractLevelRenderStateEvent event) {
        if (event.getLevel().dimensionTypeRegistration().is(AetherIIDimensions.AETHER_HOLY_ISLES_DIMENSION_TYPE)) {
            event.getRenderState().setRenderData(DATA_BASE_SKY_COLOR_KEY, event.getCamera().attributeProbe().getValue(AetherIIEnvironmentAttributes.BASE_SKY_COLOR.get(), event.getDeltaTracker().getGameTimeDeltaPartialTick(false)));
            event.getRenderState().setRenderData(DATA_TOP_SKY_GRADIENT_COLOR_KEY, event.getCamera().attributeProbe().getValue(AetherIIEnvironmentAttributes.TOP_SKY_GRADIENT_COLOR.get(), event.getDeltaTracker().getGameTimeDeltaPartialTick(false)));
            event.getRenderState().setRenderData(DATA_CLOUD_COVER_COLOR_KEY, event.getCamera().attributeProbe().getValue(AetherIIEnvironmentAttributes.CLOUD_COVER_COLOR.get(), event.getDeltaTracker().getGameTimeDeltaPartialTick(false)));
        }
    }
}
