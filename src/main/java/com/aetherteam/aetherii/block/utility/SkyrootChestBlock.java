package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.blockentity.SkyrootChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

import java.util.function.Supplier;

public class SkyrootChestBlock extends ChestBlock {
    public SkyrootChestBlock(BlockBehaviour.Properties properties, Supplier<BlockEntityType<? extends ChestBlockEntity>> blockEntityType) {
        super(properties, blockEntityType);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TYPE, ChestType.SINGLE).setValue(WATERLOGGED, false));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SkyrootChestBlockEntity(pos, state);
    }
}