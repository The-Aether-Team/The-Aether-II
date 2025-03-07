package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.miscellaneous.FacingPillarBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ReinforcedFacingPillarBlock extends FacingPillarBlock {

    public static final BooleanProperty REINFORCED = AetherIIBlockStateProperties.REINFORCED;

    public ReinforcedFacingPillarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(REINFORCED, Boolean.FALSE).setValue(FACING, Direction.UP));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> Builder) {
        Builder.add(REINFORCED).add(FACING);
    }
}