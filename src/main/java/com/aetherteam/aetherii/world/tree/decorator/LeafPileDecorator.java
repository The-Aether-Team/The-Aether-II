package com.aetherteam.aetherii.world.tree.decorator;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class LeafPileDecorator extends TreeDecorator {
    public static final Codec<LeafPileDecorator> CODEC = BlockStateProvider.CODEC
            .fieldOf("provider")
            .xmap(LeafPileDecorator::new, p_69327_ -> p_69327_.provider)
            .codec();
    private final BlockStateProvider provider;

    public LeafPileDecorator(BlockStateProvider p_69306_) {
        this.provider = p_69306_;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.LEAF_PILE.get();
    }

    @Override
    public void place(TreeDecorator.Context pContext) {
        List<BlockPos> list = Lists.newArrayList();
        List<BlockPos> list1 = pContext.roots();
        List<BlockPos> list2 = pContext.logs();
        if (list1.isEmpty()) {
            list.addAll(list2);
        } else if (!list2.isEmpty() && list1.get(0).getY() == list2.get(0).getY()) {
            list.addAll(list2);
            list.addAll(list1);
        } else {
            list.addAll(list1);
        }

        if (!list.isEmpty()) {
            int i = list.get(0).getY();
            list.stream().filter(p_69310_ -> p_69310_.getY() == i).forEach(p_225978_ -> {
                this.placeCircle(pContext, p_225978_.west().north());
                this.placeCircle(pContext, p_225978_.east(2).north());
                this.placeCircle(pContext, p_225978_.west().south(2));
                this.placeCircle(pContext, p_225978_.east(2).south(2));

                for(int j = 0; j < 5; ++j) {
                    int k = pContext.random().nextInt(64);
                    int l = k % 8;
                    int i1 = k / 8;
                    if (l == 0 || l == 7 || i1 == 0 || i1 == 7) {
                        this.placeCircle(pContext, p_225978_.offset(-3 + l, 0, -3 + i1));
                    }
                }
            });
        }
    }

    private void placeCircle(TreeDecorator.Context p_225971_, BlockPos p_225972_) {
        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                if (Math.abs(i) != 2 || Math.abs(j) != 2) {
                    this.placeBlockAt(p_225971_, p_225972_.offset(i, 0, j));
                }
            }
        }
    }

    private void placeBlockAt(TreeDecorator.Context p_225974_, BlockPos p_225975_) {
        BlockPos blockpos = p_225975_.above();
        if (Feature.isGrassOrDirt(p_225974_.level(), blockpos.below()) && p_225974_.isAir(blockpos)) {
            p_225974_.setBlock(blockpos, this.provider.getState(p_225974_.random(), p_225975_));
        }
    }
}
