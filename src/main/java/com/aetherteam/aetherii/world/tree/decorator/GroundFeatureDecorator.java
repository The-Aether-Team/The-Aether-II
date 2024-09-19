package com.aetherteam.aetherii.world.tree.decorator;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class GroundFeatureDecorator extends TreeDecorator {
    public static final MapCodec<GroundFeatureDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(decorator -> decorator.blockProvider),
                    Codec.INT.fieldOf("chance").forGetter(decorator -> decorator.chance)
            ).apply(instance, GroundFeatureDecorator::new)
    );
    protected final BlockStateProvider blockProvider;
    protected final int chance;

    public GroundFeatureDecorator(BlockStateProvider blockProvider, int chance) {
        this.blockProvider = blockProvider;
        this.chance = chance;
    }

    @Override
    public void place(TreeDecorator.Context context) {
        RandomSource randomSource = context.random();
        List<BlockPos> list = Lists.newArrayList();
        List<BlockPos> logs = context.logs();
        list.addAll(logs);

        if (!list.isEmpty()) {
            int i = list.get(0).getY();
            for (int y = -2; y < 2; y++) {
                int finalY = y;
                list.stream().filter(pos -> pos.getY() == i).forEach(pos -> {
                    pos = pos.offset(0, finalY, 0);
                    this.placeCircle(context, pos.west().north(), randomSource);
                    this.placeCircle(context, pos.east(2).north(), randomSource);
                    this.placeCircle(context, pos.west().south(2), randomSource);
                    this.placeCircle(context, pos.east(2).south(2), randomSource);

                    for (int j = 0; j < 5; ++j) {
                        int k = context.random().nextInt(64);
                        int l = k % 8;
                        int i1 = k / 8;
                        if (l == 0 || l == 7 || i1 == 0 || i1 == 7) {
                            this.placeCircle(context, pos.offset(-3 + l, 0, -3 + i1), randomSource);
                        }
                    }
                });
            }
        }
    }

    private void placeCircle(TreeDecorator.Context context, BlockPos pos, RandomSource random) {
        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                if (Math.abs(i) != 2 || Math.abs(j) != 2) {
                    if (random.nextInt(this.chance) == 0) {
                        this.placeBlockAt(context, pos.offset(i, 0, j));
                    }
                }
            }
        }
    }

    private void placeBlockAt(TreeDecorator.Context context, BlockPos pos) {
        BlockPos blockpos = pos.above();
        if (Feature.isGrassOrDirt(context.level(), blockpos.below()) && context.isAir(blockpos)) {
            context.setBlock(blockpos, this.blockProvider.getState(context.random(), pos));
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.GROUND_FEATURE.get();
    }
}