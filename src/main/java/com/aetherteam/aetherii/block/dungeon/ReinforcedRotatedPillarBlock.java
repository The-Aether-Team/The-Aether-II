package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ReinforcedRotatedPillarBlock extends RotatedPillarBlock {

    public static final BooleanProperty REINFORCED = AetherIIBlockStateProperties.REINFORCED;
    //public static final EnumProperty<Direction.Axis> AXIS = RotatedPillarBlock.AXIS;

    public ReinforcedRotatedPillarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(REINFORCED, Boolean.FALSE).setValue(AXIS, Direction.Axis.Y));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> Builder) {
        Builder.add(REINFORCED).add(AXIS);
    }
}