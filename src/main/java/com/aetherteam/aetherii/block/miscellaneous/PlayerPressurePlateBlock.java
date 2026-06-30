package com.aetherteam.aetherii.block.miscellaneous;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class PlayerPressurePlateBlock extends BasePressurePlateBlock {
    public static final BooleanProperty POWERED;

    public PlayerPressurePlateBlock(BlockSetType type, BlockBehaviour.Properties properties) {
        super(properties, type);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false));
    }

    public int getSignalForState(BlockState state) {
        return state.getValue(POWERED) ? 15 : 0;
    }

    protected BlockState setSignalForState(BlockState state, int strength) {
        return state.setValue(POWERED, strength > 0);
    }

    public int getSignalStrength(Level level, BlockPos pos) {
        return getEntityCount(level, TOUCH_AABB.move(pos), Player.class) > 0 ? 15 : 0;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    static {
        POWERED = BlockStateProperties.POWERED;
    }
}
