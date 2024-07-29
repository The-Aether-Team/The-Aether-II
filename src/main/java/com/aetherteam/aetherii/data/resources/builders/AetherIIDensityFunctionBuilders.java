package com.aetherteam.aetherii.data.resources.builders;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDensityFunctions;
import com.aetherteam.aetherii.data.resources.registries.AetherIINoises;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CubicSpline;
import net.minecraft.util.ToFloatFunction;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class AetherIIDensityFunctionBuilders {
    public static final ResourceKey<DensityFunction> TEMPERATURE = createKey("highlands/temperature");
    public static final ResourceKey<DensityFunction> VEGETATION = createKey("highlands/vegetation");
    public static final ResourceKey<DensityFunction> VEGETATION_RARE = createKey("highlands/vegetation_rare");
    public static final ResourceKey<DensityFunction> VEGETATION_RARITY_MAPPER = createKey("highlands/vegetation_rarity_mapper");
    public static final ResourceKey<DensityFunction> CONTINENTS = createKey("highlands/continents");
    public static final ResourceKey<DensityFunction> CONTINENTS_FACTOR = createKey("highlands/continents_factor");
    public static final ResourceKey<DensityFunction> CONTINENTS_FINAL = createKey("highlands/continents_final");
    public static final ResourceKey<DensityFunction> EROSION = createKey("highlands/erosion");
    public static final ResourceKey<DensityFunction> DEPTH = createKey("highlands/depth");
    public static final ResourceKey<DensityFunction> AMPLIFICATION = createKey("highlands/amplification");
    public static final ResourceKey<DensityFunction> RIDGES = createKey("highlands/ridges");
    public static final ResourceKey<DensityFunction> BASE_3D_NOISE = createKey("highlands/base_3d_noise");

    public static final ResourceKey<DensityFunction> SHATTERED_ISLANDS = createKey("highlands/terrain/shattered_islands");
    public static final ResourceKey<DensityFunction> FINAL_ISLANDS = createKey("highlands/terrain/final_islands");

    public static final ResourceKey<DensityFunction> FACTOR = createKey("highlands/terrain/base/factor");
    public static final ResourceKey<DensityFunction> ELEVATION = createKey("highlands/terrain/base/elevation");
    public static final ResourceKey<DensityFunction> BOTTOM_SLIDE = createKey("highlands/terrain/base/bottom_slide");
    public static final ResourceKey<DensityFunction> TOP_SLIDE = createKey("highlands/terrain/base/top_slide");
    public static final ResourceKey<DensityFunction> SLOPER = createKey("highlands/terrain/base/sloper");
    public static final ResourceKey<DensityFunction> BASE_TERRAIN_SHAPER = createKey("highlands/terrain/base/base_terrain_shaper");
    public static final ResourceKey<DensityFunction> TERRAIN_SHAPER = createKey("highlands/terrain/base/terrain_shaper");

    public static final ResourceKey<DensityFunction> TOP_SLIDE_ARCTIC = createKey("highlands/terrain/arctic/top_slide_arctic");
    public static final ResourceKey<DensityFunction> SLOPER_ARCTIC = createKey("highlands/terrain/arctic/sloper_arctic");

    public static final ResourceKey<DensityFunction> FACTOR_SHATTERED = createKey("highlands/terrain/shattered/factor_shattered");
    public static final ResourceKey<DensityFunction> ELEVATION_SHATTERED = createKey("highlands/terrain/shattered/elevation_shattered");
    public static final ResourceKey<DensityFunction> BOTTOM_SLIDE_SHATTERED = createKey("highlands/terrain/shattered/bottom_slide_shattered");
    public static final ResourceKey<DensityFunction> TOP_SLIDE_SHATTERED = createKey("highlands/terrain/shattered/top_slide_shattered");

    public static final ResourceKey<DensityFunction> NOISE_CAVES = createKey("highlands/caves/noise_caves");
    public static final ResourceKey<DensityFunction> UNDERGROUND_SHAPER = createKey("highlands/caves/underground_shaper");

    public static final ResourceKey<DensityFunction> LAKES_NOISE = createKey("highlands/lakes/noise");
    public static final ResourceKey<DensityFunction> LAKES_FACTOR = createKey("highlands/lakes/factor");
    public static final ResourceKey<DensityFunction> LAKES_FLOOR = createKey("highlands/lakes/lake_floor");
    public static final ResourceKey<DensityFunction> LAKES_BARRIER = createKey("highlands/lakes/lake_barrier");

    public static final ResourceKey<DensityFunction> COASTS_BASE_NOISE = createKey("highlands/coasts/base_noise");
    public static final ResourceKey<DensityFunction> COASTS_HIGHFIELDS = createKey("highlands/coasts/highfields");
    public static final ResourceKey<DensityFunction> COASTS_MAGNETIC = createKey("highlands/coasts/magnetic");
    public static final ResourceKey<DensityFunction> COASTS_ARCTIC = createKey("highlands/coasts/arctic");

    public static final ResourceKey<DensityFunction> CLOUDBED_NOISE = createKey("highlands/cloudbed/noise");
    public static final ResourceKey<DensityFunction> CLOUDBED_Y_OFFSET = createKey("highlands/cloudbed/y_offset");

    private static ResourceKey<DensityFunction> createKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static final ResourceKey<DensityFunction> SHIFT_X = createVanillaKey("shift_x");
    public static final ResourceKey<DensityFunction> SHIFT_Z = createVanillaKey("shift_z");
    public static final ResourceKey<DensityFunction> Y = createVanillaKey("y");

    private static ResourceKey<DensityFunction> createVanillaKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, ResourceLocation.withDefaultNamespace(name));
    }

    // Noises
    public static DensityFunction makeVegetationRarityMapper(HolderGetter<DensityFunction> function) {
        DensityFunction vegetation = getFunction(function, VEGETATION);
        DensityFunction density = vegetation;
        density = DensityFunctions.rangeChoice(getFunction(function, VEGETATION_RARE), -1.5, 0.5, density, DensityFunctions.constant(2.0));
        density = DensityFunctions.rangeChoice(getFunction(function, TEMPERATURE), -0.4, 0.3, density, vegetation);
        density = DensityFunctions.rangeChoice(getFunction(function, EROSION), 0.0, 0.55, density, vegetation);
        return density;
    }


    public static DensityFunction buildContinentsFactor(HolderGetter<DensityFunction> function) {
        DensityFunctions.Spline.Coordinate continents = new DensityFunctions.Spline.Coordinate(function.getOrThrow(CONTINENTS));
        DensityFunctions.Spline.Coordinate temperature = new DensityFunctions.Spline.Coordinate(function.getOrThrow(TEMPERATURE));
        DensityFunctions.Spline.Coordinate erosion = new DensityFunctions.Spline.Coordinate(function.getOrThrow(EROSION));
        return DensityFunctions.spline(continentsFactor(continents, temperature, erosion));
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> continentsFactor(I continents, I temperature, I erosion) {
        CubicSpline<C, I> continentsSpline = CubicSpline.builder(continents)
                .addPoint(0.35F, 1.0F)
                .addPoint(0.55F, 7.5F)
                .build();

        CubicSpline<C, I> temperatureSpline = CubicSpline.builder(temperature)
                .addPoint(-0.45F, 1.0F)
                .addPoint(-0.4F, continentsSpline)
                .addPoint(-0.275F, continentsSpline)
                .addPoint(-0.225F, 1.0F)

                .addPoint(0.475F, 1.0F)
                .addPoint(0.525F, continentsSpline)
                .addPoint(0.65F, continentsSpline)
                .addPoint(0.725F, 1.0F)
                .build();

        return CubicSpline.builder(erosion)
                .addPoint(0.55F, temperatureSpline)
                .addPoint(0.625F, 1.0F)
                .build();
    }

    public static DensityFunction buildContinentsFinal(HolderGetter<DensityFunction> function) {
        DensityFunction density = getFunction(function, AetherIIDensityFunctions.CONTINENTS_FACTOR);
        density = DensityFunctions.mul(density, DensityFunctions.constant(0.1));
        density = DensityFunctions.add(density, DensityFunctions.constant(-0.1));
        return density;
    }

    // Terrain
    public static DensityFunction buildShatteredIslands(HolderGetter<DensityFunction> function) {
        DensityFunction density = getFunction(function, AetherIIDensityFunctions.BASE_3D_NOISE);
        density = DensityFunctions.add(density, DensityFunctions.constant(-0.1));
        density = DensityFunctions.add(density, DensityFunctions.constant(0.2));
        density = DensityFunctions.mul(density, getFunction(function, AetherIIDensityFunctions.TOP_SLIDE_SHATTERED));
        density = DensityFunctions.add(density, factorizeShattered(function, -0.3));
        density = DensityFunctions.add(density, DensityFunctions.constant(0.1));
        density = DensityFunctions.mul(density, getFunction(function, AetherIIDensityFunctions.BOTTOM_SLIDE_SHATTERED));
        density = DensityFunctions.add(density, factorizeShattered(function, -0.3));
        return density;
    }

    public static DensityFunction buildFinalIslands(HolderGetter<DensityFunction> function) {
        DensityFunction density = getFunction(function, AetherIIDensityFunctions.BASE_3D_NOISE);
        density = DensityFunctions.add(density, DensityFunctions.constant(-0.03));
        density = DensityFunctions.add(density, DensityFunctions.constant(0.2));
        density = DensityFunctions.mul(density, selectSlide(function));
        density = DensityFunctions.add(density, factorize(function, -0.19));
        density = DensityFunctions.add(density, DensityFunctions.constant(0.1));
        density = DensityFunctions.mul(density, getFunction(function, AetherIIDensityFunctions.BOTTOM_SLIDE));
        density = DensityFunctions.add(density, factorize(function, -0.19));
        density = DensityFunctions.min(density, getFunction(function, NOISE_CAVES));
        density = DensityFunctions.max(density, DensityFunctions.rangeChoice(getFunction(function, Y), DimensionType.MIN_Y * 2, 130, DensityFunctions.constant(-1), getFunction(function, AetherIIDensityFunctions.SHATTERED_ISLANDS)));
        density = DensityFunctions.blendDensity(density);
        density = DensityFunctions.interpolated(density);
        density = density.squeeze();
        return density;
    }

    // Base Islands
    public static DensityFunction buildFactor(HolderGetter<DensityFunction> function) {
        DensityFunctions.Spline.Coordinate ridges = new DensityFunctions.Spline.Coordinate(function.getOrThrow(RIDGES));
        DensityFunctions.Spline.Coordinate temperature = new DensityFunctions.Spline.Coordinate(function.getOrThrow(TEMPERATURE));
        DensityFunctions.Spline.Coordinate erosion = new DensityFunctions.Spline.Coordinate(function.getOrThrow(EROSION));
        return DensityFunctions.spline(factor(ridges, temperature, erosion));
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> factor(I ridges, I temperature, I erosion) {
        CubicSpline<C, I> continentsSpline = CubicSpline.builder(ridges)
                .addPoint(0.0F, 3.0F)
                .addPoint(0.2F, 1.0F)
                .build();

        CubicSpline<C, I> temperatureSpline = CubicSpline.builder(temperature)
                .addPoint(-0.525F, 1.0F)
                .addPoint(-0.45F, 1.5F)
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

    public static DensityFunction factorize(HolderGetter<DensityFunction> function, double value) {
        DensityFunction density = getFunction(function, AetherIIDensityFunctions.FACTOR);
        density = DensityFunctions.mul(density, DensityFunctions.constant(value));
        density = DensityFunctions.mul(density, getFunction(function, AetherIIDensityFunctions.TERRAIN_SHAPER));
        density = DensityFunctions.mul(density, getFunction(function, AetherIIDensityFunctions.CONTINENTS_FACTOR));
        return density;
    }


    public static DensityFunction buildTopSlide(HolderGetter<DensityFunction> function) {
        DensityFunctions.Spline.Coordinate y = new DensityFunctions.Spline.Coordinate(function.getOrThrow(Y));
        DensityFunctions.Spline.Coordinate elevation = new DensityFunctions.Spline.Coordinate(function.getOrThrow(ELEVATION));
        return DensityFunctions.spline(topSlide(y, elevation, 0.0F));
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> topSlide(I y, I elevation, float value) {
        return CubicSpline.builder(elevation)
                .addPoint(0.1F, slidePiece(y, 128, 184, 1, value))
                .addPoint(0.15F, slidePiece(y, 136, 192, 1, value))
                .addPoint(0.2F, slidePiece(y, 144, 200, 1, value))
                .addPoint(0.25F, slidePiece(y, 152, 208, 1, value))
                .addPoint(0.3F, slidePiece(y, 160, 216, 1, value))
                .addPoint(0.35F, slidePiece(y, 168, 224, 1, value))
                .addPoint(0.4F, slidePiece(y, 176, 232, 1, value))
                .addPoint(0.45F, slidePiece(y, 184, 240, 1, value))
                .addPoint(0.5F, slidePiece(y, 192, 248, 1, value))
                .addPoint(0.55F, slidePiece(y, 200, 256, 1, value))
                .addPoint(0.6F, slidePiece(y, 208, 264, 1, value))
                .addPoint(0.65F, slidePiece(y, 216, 272, 1, value))
                .addPoint(0.7F, slidePiece(y, 224, 280, 1, value))
                .addPoint(0.75F, slidePiece(y, 232, 288, 1, value))
                .build();
    }

    public static DensityFunction buildBottomSlide(HolderGetter<DensityFunction> function) {
        DensityFunctions.Spline.Coordinate y = new DensityFunctions.Spline.Coordinate(function.getOrThrow(Y));
        DensityFunctions.Spline.Coordinate elevation = new DensityFunctions.Spline.Coordinate(function.getOrThrow(ELEVATION));
        return DensityFunctions.spline(bottomSlide(y, elevation));
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> bottomSlide(I y, I elevation) {
        return CubicSpline.builder(elevation)
                .addPoint(0.1F, slidePiece(y, -64, 128, 0, 1))
                .addPoint(0.15F, slidePiece(y, -56, 136, 0, 1))
                .addPoint(0.2F, slidePiece(y, -48, 144, 0, 1))
                .addPoint(0.25F, slidePiece(y, -40, 152, 0, 1))
                .addPoint(0.3F, slidePiece(y, -32, 160, 0, 1))
                .addPoint(0.35F, slidePiece(y, -24, 168, 0, 1))
                .addPoint(0.4F, slidePiece(y, -16, 176, 0, 1))
                .addPoint(0.45F, slidePiece(y, -8, 184, 0, 1))
                .addPoint(0.5F, slidePiece(y, 0, 192, 0, 1))
                .addPoint(0.55F, slidePiece(y, 8, 200, 0, 1))
                .addPoint(0.6F, slidePiece(y, 16, 208, 0, 1))
                .addPoint(0.65F, slidePiece(y, 24, 216, 0, 1))
                .addPoint(0.7F, slidePiece(y, 32, 224, 0, 1))
                .addPoint(0.75F, slidePiece(y, 40, 232, 0, 1))
                .build();
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> slidePiece(I y, float locationFrom, float locationTo, float valueFrom, float valueTo) {
        return CubicSpline.builder(y)
                .addPoint(locationFrom, valueFrom)
                .addPoint(locationTo, valueTo)
                .build();
    }

    public static DensityFunction selectSlide(HolderGetter<DensityFunction> function) {
        return DensityFunctions.rangeChoice(getFunction(function, TEMPERATURE), -0.4, 1.5, getFunction(function, TOP_SLIDE), getFunction(function, TOP_SLIDE_ARCTIC));
    }


    public static DensityFunction buildSloper(HolderGetter<DensityFunction> function) {
        DensityFunctions.Spline.Coordinate y = new DensityFunctions.Spline.Coordinate(function.getOrThrow(Y));
        DensityFunctions.Spline.Coordinate elevation = new DensityFunctions.Spline.Coordinate(function.getOrThrow(ELEVATION));
        return DensityFunctions.spline(topSlide(y, elevation, 2.0F));
    }

    public static DensityFunction selectSloper(HolderGetter<DensityFunction> function) {
        DensityFunction density = DensityFunctions.rangeChoice(getFunction(function, TEMPERATURE), -0.4, 1.5, getFunction(function, SLOPER), getFunction(function, SLOPER_ARCTIC));
        density = DensityFunctions.mul(density, getFunction(function, UNDERGROUND_SHAPER));
        return density;
    }


    public static DensityFunction buildBaseTerrainShaper(HolderGetter<DensityFunction> function) {
        DensityFunction density = getFunction(function, AMPLIFICATION);
        density = DensityFunctions.add(density, DensityFunctions.yClampedGradient(96, 128, 0.65, 0.35));
        density = DensityFunctions.mul(density, selectSloper(function));
        return density.clamp(0, 1);
    }

    public static DensityFunction buildTerrainShaper(HolderGetter<DensityFunction> function) {
        DensityFunction base = getFunction(function, BASE_TERRAIN_SHAPER);
        DensityFunction density = base;
        density = DensityFunctions.rangeChoice(getFunction(function, Y), DimensionType.MIN_Y * 2, 128, density, DensityFunctions.mul(density, getFunction(function, LAKES_FACTOR)));
        density = DensityFunctions.rangeChoice(getFunction(function, TEMPERATURE), -1.5, 0.65, density, base);
        density = DensityFunctions.rangeChoice(getFunction(function, EROSION), 0.0, 0.55, density, base);
        return density;
    }

    // Arctic Islands
    public static DensityFunction buildTopSlideArctic(HolderGetter<DensityFunction> function) {
        DensityFunctions.Spline.Coordinate y = new DensityFunctions.Spline.Coordinate(function.getOrThrow(Y));
        DensityFunctions.Spline.Coordinate elevation = new DensityFunctions.Spline.Coordinate(function.getOrThrow(ELEVATION));
        return DensityFunctions.spline(topSlideArctic(y, elevation, 0.0F));
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> topSlideArctic(I y, I elevation, float value) {
        return CubicSpline.builder(elevation)
                .addPoint(0.0F, slidePiece(y, 128, 200, 1, value))
                .addPoint(0.05F, slidePiece(y, 136, 212, 1, value))
                .addPoint(0.1F, slidePiece(y, 144, 224, 1, value))
                .addPoint(0.15F, slidePiece(y, 152, 236, 1, value))
                .addPoint(0.2F, slidePiece(y, 160, 248, 1, value))
                .addPoint(0.25F, slidePiece(y, 168, 260, 1, value))
                .addPoint(0.3F, slidePiece(y, 176, 272, 1, value))
                .addPoint(0.33F, slidePiece(y, 184, 284, 1, value))
                .addPoint(0.36F, slidePiece(y, 192, 296, 1, value))
                .addPoint(0.4F, slidePiece(y, 200, 308, 1, value))
                .addPoint(0.425F, slidePiece(y, 208, 320, 1, value))
                .addPoint(0.45F, slidePiece(y, 216, 332, 1, value))
                .addPoint(0.575F, slidePiece(y, 224, 344, 1, value))
                .build();
    }


    public static DensityFunction buildSloperArctic(HolderGetter<DensityFunction> function) {
        DensityFunctions.Spline.Coordinate y = new DensityFunctions.Spline.Coordinate(function.getOrThrow(Y));
        DensityFunctions.Spline.Coordinate elevation = new DensityFunctions.Spline.Coordinate(function.getOrThrow(ELEVATION));
        return DensityFunctions.spline(topSlideArctic(y, elevation, 2.0F));
    }

    // Shattered Islands
    public static DensityFunction buildFactorShattered(HolderGetter<DensityFunction> function) {
        DensityFunctions.Spline.Coordinate ridges = new DensityFunctions.Spline.Coordinate(function.getOrThrow(RIDGES));
        DensityFunctions.Spline.Coordinate temperature = new DensityFunctions.Spline.Coordinate(function.getOrThrow(TEMPERATURE));
        DensityFunctions.Spline.Coordinate erosion = new DensityFunctions.Spline.Coordinate(function.getOrThrow(EROSION));
        return DensityFunctions.spline(factorShattered(ridges, temperature, erosion));
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> factorShattered(I ridges, I temperature, I erosion) {
        CubicSpline<C, I> continentsSpline = CubicSpline.builder(ridges)
                .addPoint(0.0F, 3.0F)
                .addPoint(0.1F, 1.25F)
                .addPoint(0.2F, 1.0F)
                .build();

        CubicSpline<C, I> temperatureSpline = CubicSpline.builder(temperature)
                .addPoint(-0.425F, 1.0F)
                .addPoint(-0.4F, 7.5F)
                .addPoint(-0.375F, continentsSpline)
                .addPoint(0.625F, continentsSpline)
                .addPoint(0.65F, 7.5F)
                .addPoint(0.675F, 1.0F)
                .build();

        return CubicSpline.builder(erosion)
                .addPoint(0.525F, temperatureSpline)
                .addPoint(0.55F, 7.5F)
                .addPoint(0.575F, 1.0F)
                .build();
    }

    public static DensityFunction factorizeShattered(HolderGetter<DensityFunction> function, double value) {
        DensityFunction density = getFunction(function, AetherIIDensityFunctions.FACTOR_SHATTERED);
        density = DensityFunctions.mul(density, DensityFunctions.constant(value));
        density = DensityFunctions.mul(density, DensityFunctions.add(DensityFunctions.constant(0.5D), getFunction(function, AetherIIDensityFunctions.AMPLIFICATION)));
        return density;
    }


    public static DensityFunction buildTopSlideShattered(HolderGetter<DensityFunction> function) {
        DensityFunctions.Spline.Coordinate y = new DensityFunctions.Spline.Coordinate(function.getOrThrow(Y));
        DensityFunctions.Spline.Coordinate elevation = new DensityFunctions.Spline.Coordinate(function.getOrThrow(ELEVATION_SHATTERED));
        return DensityFunctions.spline(topSlideShattered(y, elevation));
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> topSlideShattered(I y, I elevation) {
        return CubicSpline.builder(elevation)
                .addPoint(0.05F, slidePiece(y, 162, 182, 1, 0.0F))
                .addPoint(0.1F, slidePiece(y, 166, 186, 1, 0.0F))
                .addPoint(0.15F, slidePiece(y, 170, 190, 1, 0.0F))
                .addPoint(0.2F, slidePiece(y, 174, 194, 1, 0.0F))
                .addPoint(0.25F, slidePiece(y, 178, 198, 1, 0.0F))
                .addPoint(0.3F, slidePiece(y, 182, 202, 1, 0.0F))
                .addPoint(0.35F, slidePiece(y, 186, 206, 1, 0.0F))
                .addPoint(0.4F, slidePiece(y, 190, 210, 1, 0.0F))
                .addPoint(0.45F, slidePiece(y, 194, 214, 1, 0.0F))
                .addPoint(0.5F, slidePiece(y, 198, 218, 1, 0.0F))
                .build();
    }

    public static DensityFunction buildBottomSlideShattered(HolderGetter<DensityFunction> function) {
        DensityFunctions.Spline.Coordinate y = new DensityFunctions.Spline.Coordinate(function.getOrThrow(Y));
        DensityFunctions.Spline.Coordinate elevation = new DensityFunctions.Spline.Coordinate(function.getOrThrow(ELEVATION_SHATTERED));
        return DensityFunctions.spline(bottomSlideShattered(y, elevation));
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> bottomSlideShattered(I y, I elevation) {
        return CubicSpline.builder(elevation)
                .addPoint(0.05F, slidePiece(y, 130, 162, 0, 1))
                .addPoint(0.1F, slidePiece(y, 134, 166, 0, 1))
                .addPoint(0.15F, slidePiece(y, 138, 170, 0, 1))
                .addPoint(0.2F, slidePiece(y, 142, 174, 0, 1))
                .addPoint(0.25F, slidePiece(y, 146, 178, 0, 1))
                .addPoint(0.3F, slidePiece(y, 150, 182, 0, 1))
                .addPoint(0.35F, slidePiece(y, 154, 186, 0, 1))
                .addPoint(0.4F, slidePiece(y, 158, 190, 0, 1))
                .addPoint(0.45F, slidePiece(y, 162, 194, 0, 1))
                .addPoint(0.5F, slidePiece(y, 166, 198, 0, 1))
                .build();
    }

    // Underground
    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> caveGradient(I y) {
        return CubicSpline.builder(y)
                .addPoint(96, 0.0F)
                .addPoint(112, 0.175F)
                .build();
    }

    public static DensityFunction buildNoiseCaves(HolderGetter<DensityFunction> function, HolderGetter<NormalNoise.NoiseParameters> noise) {
        DensityFunction density = DensityFunctions.weirdScaledSampler(getFunction(function, AetherIIDensityFunctions.BASE_3D_NOISE), noise.getOrThrow(AetherIINoises.CAVE_THICKNESS), DensityFunctions.WeirdScaledSampler.RarityValueMapper.TYPE1);
        density = DensityFunctions.add(density, DensityFunctions.yClampedGradient(16, 32, 0.05, 0.0));
        density = DensityFunctions.add(density, DensityFunctions.constant(-0.125));
        density = DensityFunctions.add(density, DensityFunctions.weirdScaledSampler(getFunction(function, AetherIIDensityFunctions.BASE_3D_NOISE), noise.getOrThrow(AetherIINoises.CAVES), DensityFunctions.WeirdScaledSampler.RarityValueMapper.TYPE2));
        DensityFunctions.Spline.Coordinate y = new DensityFunctions.Spline.Coordinate(function.getOrThrow(Y));
        density = DensityFunctions.add(density, DensityFunctions.spline(caveGradient(y)));
        return density;
    }


    public static DensityFunction buildUndergroundShaper(HolderGetter<DensityFunction> function) {
        DensityFunctions.Spline.Coordinate y = new DensityFunctions.Spline.Coordinate(function.getOrThrow(Y));
        return DensityFunctions.spline(undergroundShaper(y));
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> undergroundShaper(I y) {
        return CubicSpline.builder(y)
                .addPoint(0, 3.0F)
                .addPoint(8, 1.0F)
                .addPoint(16, 0.7F)
                .addPoint(32, 0.5F)
                .addPoint(64, 0.55F)
                .addPoint(72, 0.6F)
                .addPoint(88, 0.8F)
                .addPoint(96, 0.9F)
                .addPoint(112, 1.0F)
                .build();
    }

    // Lakes
    public static DensityFunction buildLakeFactor(HolderGetter<DensityFunction> function) {
        DensityFunctions.Spline.Coordinate lakes = new DensityFunctions.Spline.Coordinate(function.getOrThrow(LAKES_NOISE));
        DensityFunctions.Spline.Coordinate temperature = new DensityFunctions.Spline.Coordinate(function.getOrThrow(TEMPERATURE));
        DensityFunctions.Spline.Coordinate erosion = new DensityFunctions.Spline.Coordinate(function.getOrThrow(EROSION));
        return DensityFunctions.spline(lakeFactor(lakes, temperature, erosion));
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> lakeFactor(I lakes, I temperature, I erosion) {

        CubicSpline<C, I> lakeSpline = CubicSpline.builder(lakes)
                .addPoint(0.4F, 1.0F)
                .addPoint(0.45F, 5.0F)
                .addPoint(0.5F, 25.0F)
                .build();

        CubicSpline<C, I> temperatureSpline = CubicSpline.builder(temperature)
                .addPoint(0.575F, lakeSpline)
                .addPoint(0.65F, 1.0F)
                .build();

        return CubicSpline.builder(erosion)
                .addPoint(0.425F, temperatureSpline)
                .addPoint(0.55F, 1.0F)
                .build();
    }

    // Coasts
    public static DensityFunction buildCoastNoise(HolderGetter<DensityFunction> function, double value) {
        return DensityFunctions.add(getFunction(function, AetherIIDensityFunctions.COASTS_BASE_NOISE), DensityFunctions.constant(value));
    }

    public static DensityFunction getFunction(HolderGetter<DensityFunction> function, ResourceKey<DensityFunction> key) {
        return new DensityFunctions.HolderHolder(function.getOrThrow(key));
    }
}