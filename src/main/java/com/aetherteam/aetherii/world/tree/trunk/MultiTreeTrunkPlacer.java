package com.aetherteam.aetherii.world.tree.trunk;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class MultiTreeTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<MultiTreeTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.intRange(0, 32).fieldOf("base_height").forGetter((trunkPlacer) -> trunkPlacer.baseHeight),
            Codec.intRange(0, 24).fieldOf("height_rand_a").forGetter((trunkPlacer) -> trunkPlacer.heightRandA),
            Codec.intRange(0, 24).fieldOf("height_rand_b").forGetter((trunkPlacer) -> trunkPlacer.heightRandB),
            IntProvider.CODEC.fieldOf("radius").forGetter((trunkPlacer) -> trunkPlacer.radius),
            Codec.intRange(2, 10).fieldOf("amount").forGetter((trunkPlacer) -> trunkPlacer.amount)
    ).apply(instance, MultiTreeTrunkPlacer::new));

    private final IntProvider radius;
    private final int amount;

    public MultiTreeTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, IntProvider radius, int amount) {
        super(baseHeight, heightRandA, heightRandB);
        this.radius = radius;
        this.amount = amount;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int freeTreeHeight, BlockPos pos, TreeConfiguration config) {
        List<FoliagePlacer.FoliageAttachment> foliageAttachments = new ArrayList<>();
        int radius = this.radius.sample(random);
        BlockPos min = pos.offset(-radius, -radius, -radius);
        BlockPos max = pos.offset(radius, radius, radius);
        Iterable<BlockPos> aroundPos = BlockPos.randomBetweenClosed(random, this.amount, min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
        for (BlockPos origin : aroundPos) {
            BlockPos heightmapPos = level.getHeightmapPos(Heightmap.Types.OCEAN_FLOOR_WG, origin.above());
            boolean noAdjacentTrees = BlockPos.betweenClosedStream(-1, -1, -1, 1, 1, 1)
                    .map(BlockPos::immutable).filter((e) -> Vector3i.length(e.getX(), e.getY(), e.getZ()) != 0)
                    .toList().stream().noneMatch((offset) -> level.isStateAtPosition(heightmapPos.offset(offset), state -> state.is(BlockTags.LOGS)));

            if (this.isFree(level, heightmapPos) && noAdjacentTrees) {
                if (level.isStateAtPosition(heightmapPos.below(), (state) -> state.is(BlockTags.DIRT) && !state.is(Blocks.GRASS_BLOCK) && !state.is(Blocks.MYCELIUM))) {
                    setDirtAt(level, blockSetter, random, heightmapPos.below(), config);

                    for (int i = 0; i < freeTreeHeight; ++i) {
                        this.placeLog(level, blockSetter, random, heightmapPos.above(i), config);
                    }

                    foliageAttachments.add(new FoliagePlacer.FoliageAttachment(heightmapPos.above(freeTreeHeight), 0, false));
                }
            }
        }
        return foliageAttachments;
    }

    @Override
    protected boolean validTreePos(LevelSimulatedReader level, BlockPos pos) {
        return super.validTreePos(level, pos) || level.isStateAtPosition(pos, BlockBehaviour.BlockStateBase::canBeReplaced);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return AetherIITrunkPlacerTypes.MULTI_TREE_TRUNK_PLACER.get();
    }
}
