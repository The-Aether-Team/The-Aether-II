package com.aetherteam.aetherii.data.resources.builders;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDensityFunctions;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CubicSpline;
import net.minecraft.util.ToFloatFunction;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.*;

public class AetherIIDensityFunctionBuilders {
    public static final ResourceKey<DensityFunction> TEMPERATURE = createKey("highlands/temperature");
    public static final ResourceKey<DensityFunction> VEGETATION = createKey("highlands/vegetation");
    public static final ResourceKey<DensityFunction> VEGETATION_RARE = createKey("highlands/vegetation_rare");
    public static final ResourceKey<DensityFunction> VEGETATION_RARITY_MAPPER = createKey("highlands/vegetation_rarity_mapper");
    public static final ResourceKey<DensityFunction> CONTINENTS = createKey("highlands/continents");
    public static final ResourceKey<DensityFunction> EROSION = createKey("highlands/erosion");
    public static final ResourceKey<DensityFunction> DEPTH = createKey("highlands/depth");
    public static final ResourceKey<DensityFunction> ELEVATION = createKey("highlands/elevation");
    public static final ResourceKey<DensityFunction> FACTOR = createKey("highlands/factor");
    public static final ResourceKey<DensityFunction> BOTTOM_SLIDE = createKey("highlands/islands/bottom_slide");
    public static final ResourceKey<DensityFunction> TOP_SLIDE = createKey("highlands/islands/top_slide");
    public static final ResourceKey<DensityFunction> TOP_SLIDE_ARCTIC = createKey("highlands/islands/top_slide_arctic"); //TODO: Add to Datagen
    public static final ResourceKey<DensityFunction> SLOPER = createKey("highlands/islands/sloper");
    public static final ResourceKey<DensityFunction> SLOPER_ARCTIC = createKey("highlands/islands/sloper_arctic"); //TODO: Add to Datagen
    public static final ResourceKey<DensityFunction> BASE_3D_NOISE = createKey("highlands/base_3d_noise");
    public static final ResourceKey<DensityFunction> AMPLIFICATION = createKey("highlands/amplification");
    public static final ResourceKey<DensityFunction> ISLAND_DENSITY = createKey("highlands/island_density");
    public static final ResourceKey<DensityFunction> LAKES_NOISE = createKey("highlands/lakes/noise");
    public static final ResourceKey<DensityFunction> LAKES_FACTOR = createKey("highlands/lakes/factor"); //TODO: Add to Datagen
    public static final ResourceKey<DensityFunction> LAKES_FLOOR = createKey("highlands/lakes/lake_floor");
    public static final ResourceKey<DensityFunction> LAKES_BARRIER = createKey("highlands/lakes/lake_barrier");
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
        density = DensityFunctions.mul(density, getFunction(function, AetherIIDensityFunctions.ISLAND_DENSITY));
        return density;
    }

    public static DensityFunction makeVegetationRarityMapper(HolderGetter<DensityFunction> function) {
        DensityFunction vegetation = getFunction(function, VEGETATION);
        DensityFunction density = vegetation;
        density = DensityFunctions.rangeChoice(getFunction(function, VEGETATION_RARE), 0, 0.4, density, DensityFunctions.constant(2.0));
        density = DensityFunctions.rangeChoice(getFunction(function, TEMPERATURE), -0.4, 0.3, density, vegetation);
        density = DensityFunctions.rangeChoice(getFunction(function, EROSION), 0.0, 0.55, density, vegetation);
        return density;
    }

    public static DensityFunction selectSlide(HolderGetter<DensityFunction> function) {
        return DensityFunctions.rangeChoice(getFunction(function, TEMPERATURE), -0.4, 1.5, getFunction(function, TOP_SLIDE), getFunction(function, TOP_SLIDE_ARCTIC));
    }

    public static DensityFunction selectSloper(HolderGetter<DensityFunction> function) {
        return DensityFunctions.rangeChoice(getFunction(function, TEMPERATURE), -0.4, 1.5, getFunction(function, SLOPER), getFunction(function, SLOPER_ARCTIC));
    }

    public static DensityFunction makeBaseTerrainShaper(HolderGetter<DensityFunction> function) {
        DensityFunction density = getFunction(function, AMPLIFICATION);
        density = DensityFunctions.add(density, DensityFunctions.yClampedGradient(96, 128, 0.75, 0.35));
        density = DensityFunctions.mul(density, selectSloper(function));
        return density.clamp(0, 1);
    }

    public static DensityFunction finalizeTerrainShaper(HolderGetter<DensityFunction> function) {
        DensityFunction base = makeBaseTerrainShaper(function);
        DensityFunction density = base;
        density = DensityFunctions.rangeChoice(getFunction(function, Y), DimensionType.MIN_Y * 2, 128, density, DensityFunctions.mul(density, getFunction(function, LAKES_FACTOR)));
        density = DensityFunctions.rangeChoice(getFunction(function, TEMPERATURE), -1.5, 0.65, density, base);
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

    public static DensityFunction buildFactor(HolderGetter<DensityFunction> function) {
        DensityFunctions.Spline.Coordinate continents = new DensityFunctions.Spline.Coordinate(function.getOrThrow(CONTINENTS));
        DensityFunctions.Spline.Coordinate temperature = new DensityFunctions.Spline.Coordinate(function.getOrThrow(TEMPERATURE));
        DensityFunctions.Spline.Coordinate erosion = new DensityFunctions.Spline.Coordinate(function.getOrThrow(EROSION));
        return DensityFunctions.spline(factor(continents, temperature, erosion));
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> factor(I continents, I temperature, I erosion) {
        CubicSpline<C, I> continentsSpline = CubicSpline.builder(continents)
                .addPoint(0.05F, 5.0F)
                .addPoint(0.2F, 1.0F)
                .build();

        CubicSpline<C, I> temperatureSpline = CubicSpline.builder(temperature)
                .addPoint(-0.475F, 1.0F)
                .addPoint(-0.4F, 7.5F)
                .addPoint(-0.325F, continentsSpline)
                .addPoint(0.575F, continentsSpline)
                .addPoint(0.65F, 7.5F)
                .addPoint(0.725F, 1.0F)
                .build();

        return CubicSpline.builder(erosion)
                .addPoint(0.475F, temperatureSpline)
                .addPoint(0.55F, 7.5F)
                .addPoint(0.625F, 1.0F)
                .build();
    }

    public static DensityFunction buildIslandDensity(HolderGetter<DensityFunction> function) {
        DensityFunctions.Spline.Coordinate lakes = new DensityFunctions.Spline.Coordinate(function.getOrThrow(LAKES_NOISE));
        DensityFunctions.Spline.Coordinate continents = new DensityFunctions.Spline.Coordinate(function.getOrThrow(CONTINENTS));
        DensityFunctions.Spline.Coordinate temperature = new DensityFunctions.Spline.Coordinate(function.getOrThrow(TEMPERATURE));
        DensityFunctions.Spline.Coordinate erosion = new DensityFunctions.Spline.Coordinate(function.getOrThrow(EROSION));
        DensityFunctions.Spline.Coordinate elevation = new DensityFunctions.Spline.Coordinate(function.getOrThrow(ELEVATION));
        return DensityFunctions.spline(islandDensity(lakes, continents, temperature, erosion, elevation));
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> islandDensity(I lakes, I continents, I temperature, I erosion, I elevation) {

        CubicSpline<C, I> lakeSpline = CubicSpline.builder(lakes)
                .addPoint(0.45F, 1.0F)
                .addPoint(0.575F, 0.5F)
                .build();

        CubicSpline<C, I> continentsSpline = CubicSpline.builder(continents)
                .addPoint(0.15F, 1.0F)
                .addPoint(0.225F, lakeSpline)
                .build();

        CubicSpline<C, I> temperatureSpline = CubicSpline.builder(temperature)
                .addPoint(0.575F, continentsSpline)
                .addPoint(0.6F, 1.0F)
                .build();

        CubicSpline<C, I> erosionSpline = CubicSpline.builder(erosion)
                .addPoint(0.45F, temperatureSpline)
                .addPoint(0.5F, 1.0F)
                .build();

        return CubicSpline.builder(elevation)
                .addPoint(0.4F, erosionSpline)
                .addPoint(0.5F, 1.0F)
                .build();
    }

    public static DensityFunction buildTopSlide(HolderGetter<DensityFunction> function) {
        DensityFunctions.Spline.Coordinate y = new DensityFunctions.Spline.Coordinate(function.getOrThrow(Y));
        DensityFunctions.Spline.Coordinate elevation = new DensityFunctions.Spline.Coordinate(function.getOrThrow(ELEVATION));
        return DensityFunctions.spline(topSlide(y, elevation, 0.0F));
    }

    public static DensityFunction buildSloper(HolderGetter<DensityFunction> function) {
        DensityFunctions.Spline.Coordinate y = new DensityFunctions.Spline.Coordinate(function.getOrThrow(Y));
        DensityFunctions.Spline.Coordinate elevation = new DensityFunctions.Spline.Coordinate(function.getOrThrow(ELEVATION));
        return DensityFunctions.spline(topSlide(y, elevation, 2.0F));
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> topSlide(I y, I elevation, float value) {
        return CubicSpline.builder(elevation)
                .addPoint(0.15F, slidePiece(y, 128, 184, 1, value))
                .addPoint(0.2F, slidePiece(y, 136, 192, 1, value))
                .addPoint(0.25F, slidePiece(y, 144, 200, 1, value))
                .addPoint(0.3F, slidePiece(y, 152, 208, 1, value))
                .addPoint(0.35F, slidePiece(y, 160, 216, 1, value))
                .addPoint(0.4F, slidePiece(y, 168, 224, 1, value))
                .addPoint(0.45F, slidePiece(y, 176, 232, 1, value))
                .addPoint(0.5F, slidePiece(y, 184, 240, 1, value))
                .addPoint(0.55F, slidePiece(y, 192, 248, 1, value))
                .addPoint(0.6F, slidePiece(y, 200, 246, 1, value))
                .addPoint(0.65F, slidePiece(y, 208, 264, 1, value))
                .addPoint(0.7F, slidePiece(y, 216, 272, 1, value))
                .addPoint(0.75F, slidePiece(y, 224, 280, 1, value))
                .addPoint(0.8F, slidePiece(y, 232, 288, 1, value))
                .build();
    }

    public static DensityFunction buildBottomSlide(HolderGetter<DensityFunction> function) {
        DensityFunctions.Spline.Coordinate y = new DensityFunctions.Spline.Coordinate(function.getOrThrow(Y));
        DensityFunctions.Spline.Coordinate elevation = new DensityFunctions.Spline.Coordinate(function.getOrThrow(ELEVATION));
        return DensityFunctions.spline(bottomSlide(y, elevation));
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> bottomSlide(I y, I elevation) {
        return CubicSpline.builder(elevation)
                .addPoint(0.15F, slidePiece(y, -64, 128, 0, 1))
                .addPoint(0.2F, slidePiece(y, -56, 136, 0, 1))
                .addPoint(0.25F, slidePiece(y, -48, 144, 0, 1))
                .addPoint(0.3F, slidePiece(y, -40, 152, 0, 1))
                .addPoint(0.35F, slidePiece(y, -32, 160, 0, 1))
                .addPoint(0.4F, slidePiece(y, -24, 168, 0, 1))
                .addPoint(0.45F, slidePiece(y, -16, 176, 0, 1))
                .addPoint(0.5F, slidePiece(y, -8, 184, 0, 1))
                .addPoint(0.55F, slidePiece(y, 0, 192, 0, 1))
                .addPoint(0.6F, slidePiece(y, 8, 200, 0, 1))
                .addPoint(0.65F, slidePiece(y, 16, 208, 0, 1))
                .addPoint(0.7F, slidePiece(y, 24, 216, 0, 1))
                .addPoint(0.75F, slidePiece(y, 32, 224, 0, 1))
                .addPoint(0.8F, slidePiece(y, 40, 232, 0, 1))
                .build();
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> slidePiece(I y, float locationFrom, float locationTo, float valueFrom, float valueTo) {
        return CubicSpline.builder(y)
                .addPoint(locationFrom, valueFrom)
                .addPoint(locationTo, valueTo)
                .build();
    }

    public static DensityFunction getFunction(HolderGetter<DensityFunction> function, ResourceKey<DensityFunction> key) {
        return new DensityFunctions.HolderHolder(function.getOrThrow(key));
    }
}