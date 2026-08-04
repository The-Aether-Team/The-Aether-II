package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.data.resources.builders.worldgen.AetherIIDensityFunctionBuilders;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class AetherIIDensityFunctions extends AetherIIDensityFunctionBuilders {
    public static void bootstrap(BootstrapContext<DensityFunction> context) {
        HolderGetter<DensityFunction> function = context.lookup(Registries.DENSITY_FUNCTION);
        HolderGetter<NormalNoise.NoiseParameters> noise = context.lookup(Registries.NOISE);
        DensityFunction shiftX = getFunction(function, SHIFT_X);
        DensityFunction shiftZ = getFunction(function, SHIFT_Z);

        context.register(TEMPERATURE, DensityFunctions.cacheOnce(DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.375, noise.getOrThrow(AetherIINoises.TEMPERATURE))));
        context.register(VEGETATION, DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.5, noise.getOrThrow(AetherIINoises.VEGETATION)));
        context.register(VEGETATION_RARITY_MAPPER, buildVegetationRarityMapper(function));
        context.register(VEGETATION_RARE, DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noise.getOrThrow(AetherIINoises.VEGETATION_RARE)).abs());
        context.register(CONTINENTS_HEIGHTMAP, buildContinentsHeightmap(function));
        context.register(CONTINENTS, buildContinents(function));
        context.register(CONTINENTS_RARE, DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.3, noise.getOrThrow(AetherIINoises.CONTINENTALNESS_RARE)).abs());
        context.register(CONTINENTS_RARITY_MAPPER, buildContinentsRarityMapper(function));
        context.register(EROSION, DensityFunctions.cacheOnce(DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.31, noise.getOrThrow(AetherIINoises.EROSION)).abs()));
        context.register(DEPTH, DensityFunctions.yClampedGradient(0, 384, -0.75, 1.5));
        context.register(CAVE_BIOMES, DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.375, noise.getOrThrow(AetherIINoises.CAVE_BIOMES)).abs());
        context.register(CAVE_BIOMES_RARITY_MAPPER, buildCaveBiomesRarityMapper(function));
        context.register(AMPLIFICATION, DensityFunctions.cacheOnce(DensityFunctions.weirdScaledSampler(getFunction(function, AetherIIDensityFunctions.BASE_3D_NOISE), noise.getOrThrow(AetherIINoises.AMPLIFICATION), DensityFunctions.WeirdScaledSampler.RarityValueMapper.TYPE1)));
        context.register(RIDGES, DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.85, noise.getOrThrow(AetherIINoises.RIDGES)).abs());
        context.register(BASE_3D_NOISE, DensityFunctions.cacheOnce(BlendedNoise.createUnseeded(
                0.1D, // xz scale
                0.02D, // y scale
                80D, // xz factor
                160D, // y factor
                1.0D // smear scale multiplier, capped at 8
        )));
        context.register(BASE_SUNKEN_3D_NOISE, BlendedNoise.createUnseeded(
                0.5D, // xz scale
                0.1D, // y scale
                80D, // xz factor
                160D, // y factor
                1.0D // smear scale multiplier, capped at 8
        ));

        context.register(SHATTERED_ISLANDS, buildShatteredIslands(function));
        context.register(SUNKEN_ISLANDS, buildSunkenIslands(function));
        context.register(BASE_ISLANDS, buildBaseIslands(function));
        context.register(FINAL_ISLANDS, buildFinalIslands(function));

        context.register(FACTOR, buildFactor(function));
        context.register(ELEVATION, DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.5, noise.getOrThrow(AetherIINoises.ELEVATION)).abs());
        context.register(ELEVATION_DENSE, DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.675, noise.getOrThrow(AetherIINoises.ELEVATION)).abs());
        context.register(ELEVATION_MAPPER, buildElevationMapper(function));
        context.register(TOP_SLIDE, buildTopSlide(function));
        context.register(BOTTOM_SLIDE, buildBottomSlide(function));
        context.register(SLOPER, buildSloper(function));
        context.register(BASE_TERRAIN_SHAPER, buildBaseTerrainShaper(function));
        context.register(TERRAIN_SHAPER, buildTerrainShaper(function));

        context.register(TOP_SLIDE_ARCTIC, buildTopSlideArctic(function));
        context.register(SLOPER_ARCTIC, buildSloperArctic(function));

        context.register(FACTOR_SHATTERED, buildFactorShattered(function));
        context.register(ELEVATION_SHATTERED, DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.5, noise.getOrThrow(AetherIINoises.ELEVATION_SHATTERED)).abs());
        context.register(TOP_SLIDE_SHATTERED, buildTopSlideShattered(function));
        context.register(BOTTOM_SLIDE_SHATTERED, buildBottomSlideShattered(function));

        context.register(FACTOR_SUNKEN, buildFactorSunken(function));
        context.register(ELEVATION_SUNKEN, DensityFunctions.cacheOnce(DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.5, noise.getOrThrow(AetherIINoises.ELEVATION_SUNKEN)).abs()));
        context.register(TOP_SLIDE_SUNKEN, buildTopSlideSunken(function));
        context.register(BOTTOM_SLIDE_SUNKEN, buildBottomSlideSunken(function));

        context.register(NOISE_CAVES, buildNoiseCaves(function, noise));
        context.register(UNDERGROUND_SHAPER, buildUndergroundShaper(function));

        context.register(LAKES_NOISE, DensityFunctions.add(new PerlinNoiseFunction(new NormalNoise.NoiseParameters(-8, 1.0, 1.5, 0.0, 0.0, 0.0, 0.0), 0.75D, 0.0D, 64), DensityFunctions.constant(0.1D)));
        context.register(LAKES_NOISE_SWAMP,
                DensityFunctions.cacheOnce(DensityFunctions.add(DensityFunctions.mul(new PerlinNoiseFunction(new NormalNoise.NoiseParameters(-5, 0.2, 0.35, 0.0, 0.0), 1.0D, 0.0D, 99).abs(), DensityFunctions.constant(-1.0D)),
                getFunction(function, LAKES_NOISE))));
        context.register(LAKES_FLOOR, new PerlinNoiseFunction(new NormalNoise.NoiseParameters(-6, 2.5, 1.5, 0.0, 0.0, 0.0, 0.0), 0.75D, 0.0D, 17).abs());
        context.register(LAKES_BARRIER, DensityFunctions.add(new PerlinNoiseFunction(new NormalNoise.NoiseParameters(-5, 4.0, 2.0, 0.0, 0.0, 0.0), 0.5D, 0.0D, 38).abs(), DensityFunctions.constant(6.0D)));
        context.register(LAKES_SHORE, new PerlinNoiseFunction(new NormalNoise.NoiseParameters(-5, 0.15, 0.3, 0.0, 0.0, 0.0), 1.0D, 0.0D, 80).abs());
        context.register(LAKES_WATERFALLS, new PerlinNoiseFunction(new NormalNoise.NoiseParameters(-5, 1.0, 1.0, 0.0, 0.0), 0.5D, 0.0D, 45).abs().clamp(0, 1));
        context.register(LAKES_FACTOR, buildLakeFactor(function));

        context.register(COASTS_BASE_NOISE, new PerlinNoiseFunction(new NormalNoise.NoiseParameters(-6, 8.0, 2.0, 0.0, 0.0), 1.5D, 0.0D, 16).abs());
        context.register(COASTS_HIGHFIELDS, buildCoastNoise(function, 5.0D));
        context.register(COASTS_MAGNETIC, buildCoastNoise(function,9.0D));
        context.register(COASTS_ARCTIC, buildCoastNoise(function,3.0D));
        context.register(COASTS_FERROSITE_PILLAR, buildCoastNoise(function,7.0D));

        context.register(ENVIRONMENTAL_SNOW, buildBaseEnvironmentalNoise(42));
        context.register(ENVIRONMENTAL_TREE_MOSS, buildBaseEnvironmentalNoise(53));
        context.register(ENVIRONMENTAL_CRATER, new PerlinNoiseFunction(new NormalNoise.NoiseParameters(-2, 4.0, 0.0, 3.0, 0.0, 0.0, 0.0), 1.0D, 0.0D, 53).square());

        context.register(STRATA_ICHORITE, new PerlinNoiseFunction(new NormalNoise.NoiseParameters(-5, 1.0F, 1.0F, 1.0F, 1.0F), 1.0F, 3.0, 0));
        context.register(STRATA_AGIOSITE, DensityFunctions.mul(
                DensityFunctions.add(
                        DensityFunctions.add(
                                new PerlinNoiseFunction(new NormalNoise.NoiseParameters(-5, 1.0F), 1, 3, 0).abs(),
                                new PerlinNoiseFunction(new NormalNoise.NoiseParameters(-5, 1.0F), 0.5, 2, 0).abs()
                        ),
                        DensityFunctions.constant(1.0)
                ),
                DensityFunctions.yClampedGradient(-384, 512, 0, 1)
        ));
        context.register(STRATA_MOSSY_HOLYSTONE, DensityFunctions.add(
                new PerlinNoiseFunction(new NormalNoise.NoiseParameters(-4, 1.0F, 1.0F, 1.0F, 1.0F), 4, 4, 0).abs(),
                DensityFunctions.mul(
                        new PerlinNoiseFunction(new NormalNoise.NoiseParameters(-7, 1.0F, 1.0F, 1.0F, 1.0F), 5, 5, 0),
                        DensityFunctions.constant(0.5)
                )
        ));
        context.register(STRATA_FERROSITE, new PerlinNoiseFunction(new NormalNoise.NoiseParameters(-4, 1.0F, 1.0F, 1.0F, 1.0F), 0.1F, 5, 0));
        context.register(STRATA_ARCTIC_PACKED_ICE, DensityFunctions.add(
                new PerlinNoiseFunction(new NormalNoise.NoiseParameters(-3, 1.0F), 2, 0.2, 0).abs(),
                DensityFunctions.add(
                        new PerlinNoiseFunction(new NormalNoise.NoiseParameters(-3, 1.0F), 1, 0.3, 0),
                        DensityFunctions.constant(0.625F)
                )
        ));
        context.register(STRATA_IRRADIATED_HOLYSTONE, new PerlinNoiseFunction(new NormalNoise.NoiseParameters(-5, 1.0F, 10.0F, 1.0F, -10.0F, 1.0F), 1, 1, 0).abs());

        context.register(CLOUDBED_NOISE, DensityFunctions.add(DensityFunctions.mul(new PerlinNoiseFunction(new NormalNoise.NoiseParameters(0, 1, 1, 1, 1, 1, 1), 0.005D, 0.0D, 42), DensityFunctions.constant(1.5D)), DensityFunctions.constant(0.1D)));
        context.register(CLOUDBED_Y_OFFSET, DensityFunctions.mul(new PerlinNoiseFunction(new NormalNoise.NoiseParameters(0, 1, 1), 0.001D, 0.0D, 95), DensityFunctions.constant(1.5D)));

        context.register(DUNGEONS_INFECTED_BLOCKS, DensityFunctions.add(new PerlinNoiseFunction(new NormalNoise.NoiseParameters(-4, 0.75F, 1.0, 0.75, 0.0, 0.0), 1.0D, 1.0D, 1000), DensityFunctions.constant(-0.15D)));
        context.register(DUNGEONS_STRUCTURE_COVER, DensityFunctions.add(new PerlinNoiseFunction(new NormalNoise.NoiseParameters(-3, 0.75F, 1.0, 0.75, 0.0, 0.0), 1.0D, 1.0D, 2000), DensityFunctions.constant(0.15D)));
    }
}