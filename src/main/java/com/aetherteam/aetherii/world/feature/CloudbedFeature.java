package com.aetherteam.aetherii.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import java.util.List;

public class CloudbedFeature extends Feature<CloudbedFeature.Config> {

    public static final PerlinSimplexNoise BASE_NOISE = new PerlinSimplexNoise(new XoroshiroRandomSource(42), List.of(0, 1, 2, 3, 4));
    public static final PerlinSimplexNoise Y_OFFSET = new PerlinSimplexNoise(new XoroshiroRandomSource(95), List.of(0, 1));

    public CloudbedFeature(Codec<Config> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<Config> context) {

        Config config = context.config();

        DensityFunction cloudNoise = config.cloudNoise();
        DensityFunction yOffsetNoise = config.yOffset();

        // This should be placed, once per chunk
        int chunkX = context.origin().getX() - (context.origin().getX() % 16);
        int chunkZ = context.origin().getZ() - (context.origin().getZ() % 16);
        // Place blocks across the entire chunk
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                // calculate new coords based on the for loops' values
                int xCoord = chunkX + x;
                int zCoord = chunkZ + z;
                // The main cloud noise is what is used for the distinction of gaps and non-gaps
                double cloudCalc = cloudNoise.compute(new DensityFunction.SinglePointContext(xCoord, config.yLevel(), zCoord));
                // A Y offset is then calculated and applied using a second, smoother and larger noise
                double offsetCalc = yOffsetNoise.compute(new DensityFunction.SinglePointContext(xCoord, config.yLevel(), zCoord));
                float realOffset =  cosineInterp((float) Mth.inverseLerp(offsetCalc, -0.5, 0.5), 0F, (float) config.maxYOffset());
                // We don't need to, and shouldn't, generate anything if the cloud noise value is below zero
                if (cloudCalc >= 0) {
                    // Interpolate for some extra smoothness
                    float realCloud = cosineInterp((float) Mth.clamp(cloudCalc, 0, 1), 0, 1);
                    // Calculate how many blocks up from the main y offset plane should be generated
                    float blocksUp = Mth.lerp(realCloud, 0F, (float) config.cloudRadius()) + realOffset;
                    // Calculate how many blocks down from the main y offset plane should be generated
                    float blocksDown = Mth.lerp(realCloud, 0F, (float) config.cloudRadius() - 1F) - realOffset;
                    // Floor these values and then place the blocks
                    for (int i = Mth.floor(-blocksDown); i <= Mth.floor(blocksUp); i++) {
                        int y = Mth.clamp(config.yLevel() + i, context.level().getMinBuildHeight(), context.level().getMaxBuildHeight());
                        BlockPos pos = new BlockPos(xCoord, y, zCoord);
                        if (config.predicate().test(context.level(), pos)) {
                            this.setBlock(context.level(), pos, config.block().getState(context.random(), pos));
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private static float cosineInterp(float progress, float start, float end) {
        return (-Mth.cos((float) (Math.PI * progress)) + 1F) * 0.5F * (end - start) + start;
    }

    public record Config(BlockStateProvider block, BlockPredicate predicate, int yLevel, DensityFunction cloudNoise, double cloudRadius, DensityFunction yOffset, double maxYOffset) implements FeatureConfiguration {
        public static final Codec<Config> CODEC = RecordCodecBuilder.create(
                (builder) -> builder.group(
                        BlockStateProvider.CODEC.fieldOf("block").forGetter(Config::block),
                        BlockPredicate.CODEC.fieldOf("predicate").forGetter(Config::predicate),
                        Codec.INT.fieldOf("y_level").forGetter(Config::yLevel),
                        DensityFunction.HOLDER_HELPER_CODEC.fieldOf("cloud_noise").forGetter(Config::cloudNoise),
                        Codec.DOUBLE.fieldOf("cloud_radius").forGetter(Config::cloudRadius),
                        DensityFunction.HOLDER_HELPER_CODEC.fieldOf("offset_noise").forGetter(Config::yOffset),
                        Codec.DOUBLE.fieldOf("offset_max").forGetter(Config::maxYOffset)

                ).apply(builder, Config::new));
    }
}
