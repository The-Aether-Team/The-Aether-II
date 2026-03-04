package com.aetherteam.aetherii.client.renderer.entity.state;

import net.minecraft.client.renderer.block.MovingBlockRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.level.block.entity.BlockEntity;

public class HoveringBlockEntityRenderState extends EntityRenderState {
    public BlockEntity blockEntityDummy;
    public MovingBlockRenderState movingBlockRenderState = new MovingBlockRenderState();
}
