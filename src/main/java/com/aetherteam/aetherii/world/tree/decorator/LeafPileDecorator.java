package com.aetherteam.aetherii.world.tree.decorator;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class LeafPileDecorator extends TreeDecorator {
    public static final MapCodec<LeafPileDecorator> CODEC = BlockStateProvider.CODEC
            .fieldOf("provider")
            .xmap(LeafPileDecorator::new, p_69327_ -> p_69327_.provider);
    private final BlockStateProvider provider;

    public LeafPileDecorator(BlockStateProvider provider) {
        this.provider = provider;
    }

    @Override
    public void place(TreeDecorator.Context context) {
        List<BlockPos> list = Lists.newArrayList();
        List<BlockPos> roots = context.roots();
        List<BlockPos> logs = context.logs();
        if (roots.isEmpty()) {
            list.addAll(logs);
        } else if (!logs.isEmpty() && roots.get(0).getY() == logs.get(0).getY()) {
            list.addAll(logs);
            list.addAll(roots);
        } else {
            list.addAll(roots);
        }

        if (!list.isEmpty()) {
            int i = list.get(0).getY();
            list.stream().filter(pos -> pos.getY() == i).forEach(pos -> {
                this.placeCircle(context, pos.west().north());
                this.placeCircle(context, pos.east(2).north());
                this.placeCircle(context, pos.west().south(2));
                this.placeCircle(context, pos.east(2).south(2));

                for(int j = 0; j < 5; ++j) {
                    int k = context.random().nextInt(64);
                    int l = k % 8;
                    int i1 = k / 8;
                    if (l == 0 || l == 7 || i1 == 0 || i1 == 7) {
                        this.placeCircle(context, pos.offset(-3 + l, 0, -3 + i1));
                    }
                }
            });
        }
    }

    private void placeCircle(TreeDecorator.Context context, BlockPos pos) {
        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                if (Math.abs(i) != 2 || Math.abs(j) != 2) {
                    this.placeBlockAt(context, pos.offset(i, 0, j));
                }
            }
        }
    }

    private void placeBlockAt(TreeDecorator.Context context, BlockPos pos) {
        BlockPos blockpos = pos.above();
        if (Feature.isGrassOrDirt(context.level(), blockpos.below()) && context.isAir(blockpos)) {
            context.setBlock(blockpos, this.provider.getState(context.random(), pos));
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.LEAF_PILE.get();
    }
}