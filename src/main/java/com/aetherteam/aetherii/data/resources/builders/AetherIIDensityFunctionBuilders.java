package com.aetherteam.aetherii.data.resources.builders;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDensityFunctions;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.*;

public class AetherIIDensityFunctionBuilders {
    public static final ResourceKey<DensityFunction> TEMPERATURE = createKey("highlands/temperature");
    public static final ResourceKey<DensityFunction> CONTINENTS = createKey("highlands/continents");
    public static final ResourceKey<DensityFunction> EROSION = createKey("highlands/erosion");
    public static final ResourceKey<DensityFunction> DEPTH = createKey("highlands/depth");
    public static final ResourceKey<DensityFunction> ELEVATION = createKey("highlands/elevation");
    public static final ResourceKey<DensityFunction> FACTOR = createKey("highlands/factor"); //TODO: Add to Datagen
    public static final ResourceKey<DensityFunction> BOTTOM_SLIDE = createKey("highlands/bottom_slide"); //TODO: Add to Datagen
    public static final ResourceKey<DensityFunction> TOP_SLIDE = createKey("highlands/top_slide"); //TODO: Add to Datagen
    public static final ResourceKey<DensityFunction> TOP_SLIDE_ARCTIC = createKey("highlands/top_slide_arctic"); //TODO: Add to Datagen
    public static final ResourceKey<DensityFunction> SLOPER = createKey("highlands/sloper"); //TODO: Add to Datagen
    public static final ResourceKey<DensityFunction> SLOPER_ARCTIC = createKey("highlands/sloper_arctic"); //TODO: Add to Datagen
    public static final ResourceKey<DensityFunction> BASE_3D_NOISE = createKey("highlands/base_3d_noise");
    public static final ResourceKey<DensityFunction> AMPLIFICATION = createKey("highlands/amplification"); //TODO: Add to Datagen
    public static final ResourceKey<DensityFunction> LAKES_NOISE = createKey("highlands/lakes/noise");
    public static final ResourceKey<DensityFunction> LAKES_FACTOR = createKey("highlands/lakes/factor"); //TODO: Add to Datagen
    public static final ResourceKey<DensityFunction> LAKES_ISLAND_CHECKER = createKey("highlands/lakes/island_checker"); //TODO: Add to Datagen
    public static final ResourceKey<DensityFunction> TERRAIN_SHAPER = createKey("highlands/terrain_shaper"); //TODO: Add to Datagen

    public static final ResourceKey<DensityFunction> FINAL_DENSITY = createKey("highlands/final_density");

    public static final ResourceKey<DensityFunction> CLOUDBED_NOISE = createKey("cloudbed_noise");
    public static final ResourceKey<DensityFunction> CLOUDBED_Y_OFFSET = createKey("cloudbed_y_offset");

    private static ResourceKey<DensityFunction> createKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(AetherII.MODID, name));
    }

    public static final ResourceKey<DensityFunction> SHIFT_X = createVanillaKey("shift_x");
    public static final ResourceKey<DensityFunction> SHIFT_Z = createVanillaKey("shift_z");
    public static final ResourceKey<DensityFunction> Y = createVanillaKey("y");

    private static ResourceKey<DensityFunction> createVanillaKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(name));
    }

    public static DensityFunction factorize(HolderGetter<DensityFunction> function, double value) {
        DensityFunction density = getFunction(function, AetherIIDensityFunctions.FACTOR);
        density = DensityFunctions.mul(density, DensityFunctions.constant(value));
        density = DensityFunctions.mul(density, getFunction(function, AetherIIDensityFunctions.TERRAIN_SHAPER));
        return density;
    }

    public static DensityFunction selectSlide(HolderGetter<DensityFunction> function) {
        return DensityFunctions.rangeChoice(getFunction(function, TEMPERATURE), -0.4, 1.5, getFunction(function, TOP_SLIDE), getFunction(function, TOP_SLIDE_ARCTIC));
    }

    public static DensityFunction selectSloper(HolderGetter<DensityFunction> function) {
        return DensityFunctions.rangeChoice(getFunction(function, TEMPERATURE), -0.4, 1.5, getFunction(function, SLOPER), getFunction(function, SLOPER_ARCTIC));
    }

    public static DensityFunction makeTerrainShaperBase(HolderGetter<DensityFunction> function) {
        DensityFunction density = getFunction(function, AMPLIFICATION);
        density = DensityFunctions.add(density, DensityFunctions.yClampedGradient(96, 128, 0.75, 0.35));
        density = DensityFunctions.mul(density, selectSloper(function));
        return density.clamp(0, 1);
    }

    public static DensityFunction makeTerrainShaperFinal(HolderGetter<DensityFunction> function) {
        DensityFunction base = makeTerrainShaperBase(function);
        DensityFunction density = base;
        density = DensityFunctions.rangeChoice(getFunction(function, Y), DimensionType.MIN_Y * 2, 128, density, DensityFunctions.mul(density, getFunction(function, LAKES_FACTOR)));
        density = DensityFunctions.rangeChoice(getFunction(function, TEMPERATURE), -0.4, 0.55, density, base);
        density = DensityFunctions.rangeChoice(getFunction(function, EROSION), 0.0, 0.55, density, base);
        return density;
    }

    public static DensityFunction buildFinalDensity(HolderGetter<DensityFunction> function) {
        DensityFunction density = getFunction(function, AetherIIDensityFunctions.BASE_3D_NOISE);
        density = DensityFunctions.add(density, DensityFunctions.constant(-0.03));
        density = DensityFunctions.add(density, DensityFunctions.constant(0.2));
        density = DensityFunctions.mul(density, selectSlide(function));
        density = DensityFunctions.add(density, factorize(function, -0.18));
        density = DensityFunctions.add(density, DensityFunctions.constant(0.1));
        density = DensityFunctions.mul(density, getFunction(function, AetherIIDensityFunctions.BOTTOM_SLIDE));
        density = DensityFunctions.add(density, factorize(function, -0.18));
        density = DensityFunctions.blendDensity(density);
        density = DensityFunctions.interpolated(density);
        density = density.squeeze();
        return density;
    }

    public static DensityFunction getFunction(HolderGetter<DensityFunction> function, ResourceKey<DensityFunction> key) {
        return new DensityFunctions.HolderHolder(function.getOrThrow(key));
    }
}