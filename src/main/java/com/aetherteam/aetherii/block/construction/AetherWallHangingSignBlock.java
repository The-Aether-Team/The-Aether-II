package com.aetherteam.aetherii.block.construction;

import com.aetherteam.aetherii.blockentity.AetherHangingSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class AetherWallHangingSignBlock extends WallHangingSignBlock {
    public AetherWallHangingSignBlock(WoodType type, Properties properties) {
        super(type, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AetherHangingSignBlockEntity(pos, state);
    }
}

