package com.aetherteam.aetherii.block.construction;

import com.aetherteam.aetherii.blockentity.AetherSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class AetherWallSignBlock extends WallSignBlock {
    public AetherWallSignBlock(WoodType type, Properties properties) {
        super(type, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AetherSignBlockEntity(pos, state);
    }
}
