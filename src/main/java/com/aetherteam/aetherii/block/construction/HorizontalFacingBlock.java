package com.aetherteam.aetherii.block.construction;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class HorizontalFacingBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<HorizontalFacingBlock> CODEC = simpleCodec(HorizontalFacingBlock::new);

    @Override
    public MapCodec<HorizontalFacingBlock> codec() {
        return CODEC;
    }

    public HorizontalFacingBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }
}

