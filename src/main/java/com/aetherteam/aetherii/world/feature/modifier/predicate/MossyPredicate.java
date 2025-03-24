package com.aetherteam.aetherii.world.feature.modifier.predicate;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.minecraft.world.level.levelgen.blockpredicates.StateTestingPredicate;

public class MossyPredicate extends StateTestingPredicate {
    public static final MapCodec<MossyPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> stateTestingCodec(instance).apply(instance, MossyPredicate::new));

    public MossyPredicate(Vec3i offset) {
        super(offset);
    }

    @Override
    protected boolean test(BlockState blockState) {
        return blockState.getOptionalValue(AetherIIBlockStateProperties.MOSSY).isPresent() && blockState.getValue(AetherIIBlockStateProperties.MOSSY) != AetherIIBlockStateProperties.Mossy.NONE;
    }

    @Override
    public BlockPredicateType<?> type() {
        return AetherIIBlockPredicateTypes.MOSSY.get();
    }
}
