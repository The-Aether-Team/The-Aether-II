package com.aetherteam.aetherii.world;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.attribute.*;
import net.minecraft.world.attribute.modifier.ColorModifier;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;

public class AetherIIEnvironmentAttributes {
    public static final DeferredRegister<EnvironmentAttribute<?>> ENVIRONMENT_ATTRIBUTES = DeferredRegister.create(Registries.ENVIRONMENT_ATTRIBUTE, AetherII.MODID);

    public static final DeferredHolder<EnvironmentAttribute<?>, EnvironmentAttribute<Integer>> AETHER_GRASS_COLOR = ENVIRONMENT_ATTRIBUTES.register("visual/aether_grass_color", () -> EnvironmentAttribute.builder(AttributeTypes.RGB_COLOR).defaultValue(0xb5ffd0).spatiallyInterpolated().syncable().build());
    public static final DeferredHolder<EnvironmentAttribute<?>, EnvironmentAttribute<Integer>> BASE_SKY_COLOR = ENVIRONMENT_ATTRIBUTES.register("visual/base_sky_color", () -> EnvironmentAttribute.builder(AttributeTypes.RGB_COLOR).defaultValue(0xC2C0E0).spatiallyInterpolated().syncable().build());
    public static final DeferredHolder<EnvironmentAttribute<?>, EnvironmentAttribute<Integer>> TOP_SKY_GRADIENT_COLOR = ENVIRONMENT_ATTRIBUTES.register("visual/top_sky_gradient_color", () -> EnvironmentAttribute.builder(AttributeTypes.RGB_COLOR).defaultValue(0x8A81CB).spatiallyInterpolated().syncable().build());
    public static final DeferredHolder<EnvironmentAttribute<?>, EnvironmentAttribute<Integer>> CLOUD_COVER_COLOR = ENVIRONMENT_ATTRIBUTES.register("visual/cloud_color_cover", () -> EnvironmentAttribute.builder(AttributeTypes.RGB_COLOR).defaultValue(0).spatiallyInterpolated().syncable().build());

    public static class Weather {
        public static final EnvironmentAttributeMap RAIN = EnvironmentAttributeMap.builder()
                .modify(AetherIIEnvironmentAttributes.BASE_SKY_COLOR.get(), ColorModifier.BLEND_TO_GRAY, new ColorModifier.BlendToGray(0.6F, 0.75F))
                .modify(AetherIIEnvironmentAttributes.TOP_SKY_GRADIENT_COLOR.get(), ColorModifier.BLEND_TO_GRAY, new ColorModifier.BlendToGray(0.6F, 0.75F))
                .modify(AetherIIEnvironmentAttributes.CLOUD_COVER_COLOR.get(), ColorModifier.MULTIPLY_RGB, ARGB.colorFromFloat(1.0F, 0.76F, 0.77F, 0.92F))
                .build();
        public static final EnvironmentAttributeMap THUNDER = EnvironmentAttributeMap.builder()
                .modify(AetherIIEnvironmentAttributes.BASE_SKY_COLOR.get(), ColorModifier.BLEND_TO_GRAY, new ColorModifier.BlendToGray(0.24F, 0.94F))
                .modify(AetherIIEnvironmentAttributes.TOP_SKY_GRADIENT_COLOR.get(), ColorModifier.BLEND_TO_GRAY, new ColorModifier.BlendToGray(0.24F, 0.94F))
                .modify(AetherIIEnvironmentAttributes.CLOUD_COVER_COLOR.get(), ColorModifier.MULTIPLY_RGB, ARGB.colorFromFloat(1.0F, 0.29F, 0.29F, 0.38F))
                .build();
        private static final Set<EnvironmentAttribute<?>> WEATHER_ATTRIBUTES = Sets.union(RAIN.keySet(), THUNDER.keySet());

        public static void addBuiltinLayers(EnvironmentAttributeSystem.Builder system, Level level, WeatherAttributes.WeatherAccess weatherAccess) {
            if (level.dimension() == AetherIIDimensions.AETHER_HOLY_ISLES_LEVEL) {
                for (EnvironmentAttribute<?> attribute : WEATHER_ATTRIBUTES) {
                    addLayer(system, weatherAccess, attribute);
                }
            }
        }

        private static <Value> void addLayer(EnvironmentAttributeSystem.Builder system, WeatherAttributes.WeatherAccess weatherAccess, EnvironmentAttribute<Value> attribute) {
            EnvironmentAttributeMap.Entry<Value, ?> rainEntry = RAIN.get(attribute);
            EnvironmentAttributeMap.Entry<Value, ?> thunderEntry = THUNDER.get(attribute);
            system.addTimeBasedLayer(attribute, (result, cacheTickId) -> {
                float thunderLevel = weatherAccess.thunderLevel();
                float rainLevel = weatherAccess.rainLevel() - thunderLevel;
                if (rainEntry != null && rainLevel > 0.0F) {
                    Value rainValue = rainEntry.applyModifier(result);
                    result = attribute.type().stateChangeLerp().apply(rainLevel, result, rainValue);
                }
                if (thunderEntry != null && thunderLevel > 0.0F) {
                    Value thunderValue = thunderEntry.applyModifier(result);
                    result = attribute.type().stateChangeLerp().apply(thunderLevel, result, thunderValue);
                }
                return result;
            });
        }
    }

    public static class Elevation {
        public static final EnvironmentAttributeMap ELEVATION = EnvironmentAttributeMap.builder()
                .modify(EnvironmentAttributes.FOG_COLOR, ColorModifier.MULTIPLY_RGB, ARGB.colorFromFloat(1.0F, 0.18F, 0.18F, 0.24F))
                .modify(AetherIIEnvironmentAttributes.CLOUD_COVER_COLOR.get(), ColorModifier.MULTIPLY_RGB, ARGB.colorFromFloat(1.0F, 0.15F, 0.14F, 0.18F))
                .build();

        public static void addBuiltinLayers(EnvironmentAttributeSystem.Builder system, Level level) {
            if (level.dimension() == AetherIIDimensions.AETHER_HOLY_ISLES_LEVEL) {
                for (EnvironmentAttribute<?> attribute : ELEVATION.keySet()) {
                    addLayer(system, attribute);
                }
            }
        }

        private static <Value> void addLayer(EnvironmentAttributeSystem.Builder system, EnvironmentAttribute<Value> attribute) {
            EnvironmentAttributeMap.Entry<Value, ?> elevationEntry = ELEVATION.get(attribute);
            system.addTimeBasedLayer(attribute, (result, cacheTickId) -> {
                float cameraHeight = 0.03125F * (float) (Minecraft.getInstance().player.getEyePosition(Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false)).y() - 66);
                if (elevationEntry != null) {
                    Value value = elevationEntry.applyModifier(result);
                    result = attribute.type().stateChangeLerp().apply(Mth.clamp(1.0F - cameraHeight, 0.0F, 1.0F), result, value);
                }
                return result;
            });
        }
    }
}
