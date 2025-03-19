package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ReinforcedBlock extends Block {

    public static final BooleanProperty REINFORCED = AetherIIBlockStateProperties.REINFORCED;

    public ReinforcedBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(REINFORCED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> Builder) {
        Builder.add(REINFORCED);
    }
}