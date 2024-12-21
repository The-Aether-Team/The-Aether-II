package com.aetherteam.aetherii.block.construction;

import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class SecretDoorBlock extends DoorBlock {
    public SecretDoorBlock(BlockSetType blockSetType, Properties properties) {
        super(blockSetType, properties);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }
}
