package com.aetherteam.aetherii.client.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.ChestType;

public class SentryCrateRenderState extends BlockEntityRenderState {
    public float open;
    public Direction facing = Direction.NORTH;
    public ChestType type;
}
