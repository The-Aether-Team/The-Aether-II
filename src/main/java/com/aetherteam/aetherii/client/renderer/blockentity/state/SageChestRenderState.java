package com.aetherteam.aetherii.client.renderer.blockentity.state;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.ChestType;

public class SageChestRenderState {
    public ChestType type;
    public float open;
    public Direction facing;

    public SageChestRenderState() {
        this.type = ChestType.SINGLE;
        this.facing = Direction.SOUTH;
    }
}
