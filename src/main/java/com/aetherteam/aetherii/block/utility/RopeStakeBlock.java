package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class RopeStakeBlock extends Block {
    public static final EnumProperty<Direction> CONNECTION = BlockStateProperties.FACING;
    public static final EnumProperty<AetherIIBlockStateProperties.StakeSpoolState> SPOOL = AetherIIBlockStateProperties.STAKE_SPOOL;

    public RopeStakeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CONNECTION, Direction.NORTH).setValue(SPOOL, AetherIIBlockStateProperties.StakeSpoolState.NONE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CONNECTION, SPOOL);
    }
}
