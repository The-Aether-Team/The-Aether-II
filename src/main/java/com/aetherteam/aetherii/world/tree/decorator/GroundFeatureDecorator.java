package com.aetherteam.aetherii.world.tree.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class GroundFeatureDecorator extends TreeDecorator {
    public static final MapCodec<GroundFeatureDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    ConfiguredFeature.CODEC.fieldOf("feature").forGetter(decorator -> decorator.feature),
                    Codec.INT.fieldOf("chance").forGetter(decorator -> decorator.chance)
            ).apply(instance, GroundFeatureDecorator::new)
    );
    protected final Holder<ConfiguredFeature<?, ?>> feature;
    protected final int chance;

    public GroundFeatureDecorator(Holder<ConfiguredFeature<?, ?>> feature, int chance) {
        this.feature = feature;
        this.chance = chance;
    }

    @Override
    public void place(TreeDecorator.Context context) { //todo need to have a distance limit away from the source as well
        WorldGenLevel level = context.level();
        ChunkGenerator generator = level.getLevel().getChunkSource().getGenerator();
        RandomSource random = context.random();
        List<BlockPos> logs = context.logs();

        if (!logs.isEmpty()) {
            BlockPos pos = logs.getFirst();
            for (int i = -5; i <= 5; ++i) {
                for (int j = -5; j <= 5; ++j) {
                    if ((Math.abs(i) != 5 || Math.abs(j) != 5) && (i * i) + (j * j) > 4) {
                        if (random.nextInt(this.chance) == 0) {
                            this.feature.value().place(level, generator, random, pos.offset(i, 0, j).relative(Direction.UP));
                        }
                    }
                }
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.GROUND_FEATURE.get();
    }
}