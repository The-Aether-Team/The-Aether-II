package com.aetherteam.aetherii.block.construction;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public interface SentryBlockUpdating {
//    default void checkSignal(BlockState state, Level level, BlockPos pos) {
//        if (level instanceof ServerLevel serverlevel) {
//            this.checkAndFlip(state, serverlevel, pos);
//        }
//    }
//
//    default void checkAndFlip(BlockState state, ServerLevel level, BlockPos pos) {
//        boolean flag = level.hasNeighborSignal(pos);
//        if (flag != state.getValue(BlockStateProperties.POWERED)) {
//            BlockState blockstate = state;
//            if (!state.getValue(BlockStateProperties.POWERED)) {
//                blockstate = state.cycle(BlockStateProperties.LIT);
////                level.playSound((Entity)null, pos, (Boolean)blockstate.getValue(LIT) ? SoundEvents.COPPER_BULB_TURN_ON : SoundEvents.COPPER_BULB_TURN_OFF, SoundSource.BLOCKS); //todo
//            }
//            level.setBlock(pos, blockstate.setValue(BlockStateProperties.POWERED, flag), 3);
//        }
//    }
//
//    default BlockState changeLit(BlockState state, BlockState neighborState) {
//        if (state.is(neighborState.getBlock())) {
//            if (!state.getValue(BlockStateProperties.POWERED) && neighborState.getValue(BlockStateProperties.POWERED)) {
//                return state.setValue(BlockStateProperties.LIT, neighborState.getValue(BlockStateProperties.LIT)).setValue(BlockStateProperties.POWERED, true);
//            }
//        }
//        return state;
//    }
}
