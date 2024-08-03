package com.aetherteam.aetherii.world.feature.modifier.predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;

public record ScanPredicate(Direction directionOfSearch, BlockPredicate targetCondition, int maxSteps) implements BlockPredicate {
    public static final MapCodec<ScanPredicate> CODEC = RecordCodecBuilder.mapCodec(
            p_191650_ -> p_191650_.group(
                            // Neo: Allow any direction, not just vertical. The code already handles it fine.
                            Direction.CODEC.fieldOf("direction_of_search").forGetter(p_191672_ -> p_191672_.directionOfSearch),
                            BlockPredicate.CODEC.fieldOf("target_condition").forGetter(p_191670_ -> p_191670_.targetCondition),
                            Codec.intRange(1, 32).fieldOf("max_steps").forGetter(p_191652_ -> p_191652_.maxSteps)
                    )
                    .apply(p_191650_, ScanPredicate::new)
    );

    @Override
    public boolean test(WorldGenLevel worldGenLevel, BlockPos pos) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();
        boolean flag = true;
        for (int i = 0; i < this.maxSteps; i++) {
            if (!this.targetCondition.test(worldGenLevel, blockpos$mutableblockpos)) {
                flag = false;
            }

            blockpos$mutableblockpos.move(this.directionOfSearch);
            if (worldGenLevel.isOutsideBuildHeight(blockpos$mutableblockpos.getY())) {
                flag = false;
            }
        }
        if (!this.targetCondition.test(worldGenLevel, blockpos$mutableblockpos)) {
            flag = false;
        }

        return flag;
    }

    @Override
    public BlockPredicateType<?> type() {
        return AetherIIBlockPredicateTypes.SCAN.get();
    }
}
