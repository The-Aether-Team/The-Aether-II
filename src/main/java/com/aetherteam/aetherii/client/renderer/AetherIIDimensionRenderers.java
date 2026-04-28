package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.level.HolyIslesCloudsRenderer;
import com.aetherteam.aetherii.client.renderer.level.HolyIslesSkyboxRenderer;
import com.aetherteam.aetherii.client.renderer.level.HolyIslesWeatherEffectRenderer;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.context.ContextKey;
import net.neoforged.neoforge.client.event.ExtractLevelRenderStateEvent;
import net.neoforged.neoforge.client.event.RegisterCustomEnvironmentEffectRendererEvent;

public class AetherIIDimensionRenderers {
    public static final ContextKey<Float> DATA_THUNDER_KEY = new ContextKey<>(
            Identifier.fromNamespaceAndPath(AetherII.MODID, "thunder"));
    public static final ContextKey<Float> DATA_TIME_OF_DAY_KEY = new ContextKey<>(
            Identifier.fromNamespaceAndPath(AetherII.MODID, "time_of_day"));

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
            event.getRenderState().setRenderData(DATA_THUNDER_KEY, event.getLevel().getThunderLevel(Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false)));
            event.getRenderState().setRenderData(DATA_TIME_OF_DAY_KEY, timeOfDay(event.getLevel().getDefaultClockTime()));
        }
    }

    public static float timeOfDay(long dayTime) {
        double d0 = Mth.frac((double) dayTime / (double) 24000.0F - (double) 0.25F);
        double d1 = (double) 0.5F - Math.cos(d0 * Math.PI) / (double) 2.0F;
        return (float) (d0 * (double) 2.0F + d1) / 3.0F;
    }
}
