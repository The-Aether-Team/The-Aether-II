package com.aetherteam.aetherii.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
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
        // This should be placed, once per chunk
        int chunkX = context.origin().getX() - (context.origin().getX() % 16);
        int chunkZ = context.origin().getZ() - (context.origin().getZ() % 16);
        // Place blocks across the entire chunk
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                // Multiplied so the default value can be 1
                double scale = context.config().scaleXZ() * 0.00375;
                // calculate new coords based on the for loops' values
                int xCoord = chunkX + x;
                int zCoord = chunkZ + z;
                // Base noise is what is used for the distinction of gaps and non-gaps
                double base = BASE_NOISE.getValue(xCoord * scale, zCoord * scale, false);
                // This is then modified based on the feature config
                double main = (base * context.config().scaleY) + context.config().noiseOffset;
                // A Y offset is then calculated and applied
                double yOffset = Y_OFFSET.getValue(xCoord * scale * 0.75D, zCoord * scale * 0.75D, false);
                float offs = (float) Mth.lerp(Mth.inverseLerp(yOffset, -0.5, 0.5), 0D, 10D);
                // We don't need to, and shouldn't, generate anything if the noise value is below zero
                if (main >= 0) {
                    // Interpolate for some extra smoothness
                    float delta = cosineInterp((float) Mth.clamp(main, 0, 1), 0, 1);
                    // Calculate how many blocks up from the main y offset plane should be generated
                    float blocksUp = Mth.lerp(delta, 0F, 5F) + offs;
                    // Calculate how many blocks down from the main y offset plane should be generated
                    float blocksDown = Mth.lerp(delta, 0F, 4F) - offs;
                    // Floor these values and then place the blocks
                    for (int i = Mth.floor(-blocksDown); i <= Mth.floor(blocksUp); i++) {
                        int y = Mth.clamp(context.config().yLevel() + i, context.level().getMinBuildHeight(), context.level().getMaxBuildHeight());
                        BlockPos pos = new BlockPos(xCoord, y, zCoord);
                        if (context.config().predicate().test(context.level(), pos)) {
                            this.setBlock(context.level(), pos, context.config().block().getState(context.random(), pos));
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

    public record Config(BlockStateProvider block, BlockPredicate predicate, int yLevel, double scaleXZ, double scaleY, double noiseOffset) implements FeatureConfiguration {
        public static final Codec<Config> CODEC = RecordCodecBuilder.create(
                (builder) -> builder.group(
                        BlockStateProvider.CODEC.fieldOf("block").forGetter(Config::block),
                        BlockPredicate.CODEC.fieldOf("predicate").forGetter(Config::predicate),
                        Codec.INT.fieldOf("base_height").forGetter(Config::yLevel),
                        Codec.DOUBLE.fieldOf("xz_scale").forGetter(Config::scaleXZ),
                        Codec.DOUBLE.fieldOf("y_scale").forGetter(Config::scaleY),
                        Codec.DOUBLE.fieldOf("noise_offset").forGetter(Config::noiseOffset)

                ).apply(builder, Config::new));
    }
}
