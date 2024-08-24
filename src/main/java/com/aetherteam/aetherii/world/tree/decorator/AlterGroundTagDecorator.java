package com.aetherteam.aetherii.world.tree.decorator;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.level.AlterGroundEvent;

import java.util.List;

public class AlterGroundTagDecorator extends TreeDecorator {
    public static final MapCodec<AlterGroundTagDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("provider").forGetter(decorator -> decorator.provider),
                    TagKey.codec(Registries.BLOCK).fieldOf("tag").forGetter(decorator -> decorator.replaceTag)
            ).apply(instance, AlterGroundTagDecorator::new));

    private final BlockStateProvider provider;
    private final TagKey<Block> replaceTag;

    public AlterGroundTagDecorator(BlockStateProvider provider, TagKey<Block> replaceTag) {
        this.provider = provider;
        this.replaceTag = replaceTag;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.ALTER_GROUND_TAG.get();
    }

    @Override
    public void place(TreeDecorator.Context context) {
        List<BlockPos> list = Lists.newArrayList();
        List<BlockPos> list1 = context.roots();
        List<BlockPos> list2 = context.logs();
        if (list1.isEmpty()) {
            list.addAll(list2);
        } else if (!list2.isEmpty() && list1.get(0).getY() == list2.get(0).getY()) {
            list.addAll(list2);
            list.addAll(list1);
        } else {
            list.addAll(list1);
        }

        if (!list.isEmpty()) {
            var eventProvider = EventHooks.alterGround(context, list, this.provider::getState);
            int i = list.get(0).getY();
            list.stream().filter(p_69310_ -> p_69310_.getY() == i).forEach(p_225978_ -> {
                this.placeCircle(context, p_225978_.west().north(), eventProvider);
                this.placeCircle(context, p_225978_.east(2).north(), eventProvider);
                this.placeCircle(context, p_225978_.west().south(2), eventProvider);
                this.placeCircle(context, p_225978_.east(2).south(2), eventProvider);

                for (int j = 0; j < 5; j++) {
                    int k = context.random().nextInt(64);
                    int l = k % 8;
                    int i1 = k / 8;
                    if (l == 0 || l == 7 || i1 == 0 || i1 == 7) {
                        this.placeCircle(context, p_225978_.offset(-3 + l, 0, -3 + i1), eventProvider);
                    }
                }
            });
        }
    }

    private void placeCircle(TreeDecorator.Context context, BlockPos pos, AlterGroundEvent.StateProvider eventProvider) {
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (Math.abs(i) != 2 || Math.abs(j) != 2) {
                    this.placeBlockAt(context, pos.offset(i, 0, j), eventProvider);
                }
            }
        }
    }

    private void placeBlockAt(TreeDecorator.Context context, BlockPos pos, AlterGroundEvent.StateProvider eventProvider) {
        for (int i = 2; i >= -3; i--) {
            BlockPos abovePos = pos.above(i);
            if (context.level().isStateAtPosition(abovePos, (state) -> state.is(this.replaceTag))) {
                context.setBlock(abovePos, eventProvider.getState(context.random(), pos));
                break;
            }

            if (!context.isAir(abovePos) && i < 0) {
                break;
            }
        }
    }
}
