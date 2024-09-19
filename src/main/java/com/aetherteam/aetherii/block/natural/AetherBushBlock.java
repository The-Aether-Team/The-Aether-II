package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.AetherIITags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class AetherBushBlock extends BushBlock {
    public static final MapCodec<AetherBushBlock> CODEC = simpleCodec(AetherBushBlock::new);

    public AetherBushBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(AetherIITags.Blocks.AETHER_DIRT) || super.mayPlaceOn(state, level, pos);
    }
}