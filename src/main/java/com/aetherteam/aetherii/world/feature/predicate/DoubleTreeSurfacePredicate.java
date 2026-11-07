package com.aetherteam.aetherii.world.feature.predicate;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;

import java.util.ArrayList;
import java.util.List;

public record DoubleTreeSurfacePredicate(BlockPredicate predicate) implements BlockPredicate {
    public static final MapCodec<DoubleTreeSurfacePredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockPredicate.CODEC.fieldOf("predicate").forGetter(DoubleTreeSurfacePredicate::predicate)
    ).apply(instance, DoubleTreeSurfacePredicate::new));

    @Override
    public boolean test(WorldGenLevel level, BlockPos pos) {
        BlockPos[] testOffsets = new BlockPos[] { BlockPos.ZERO, BlockPos.ZERO.east(), BlockPos.ZERO.south(), BlockPos.ZERO.south().east() };
        List<BlockPos> validPos = new ArrayList<>();

        for (BlockPos offset : testOffsets) {
            if (this.predicate().test(level, pos.offset(offset))) {
                validPos.add(offset);
            } else if (this.predicate().test(level, pos.offset(offset.below()))) {
                validPos.add(offset.below());
            }
        }
        return validPos.size() == 4;
    }

    @Override
    public BlockPredicateType<?> type() {
        return AetherIIBlockPredicateTypes.DOUBLE_TREE_SURFACE.get();
    }
}
