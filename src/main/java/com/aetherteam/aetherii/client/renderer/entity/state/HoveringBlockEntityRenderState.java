package com.aetherteam.aetherii.client.renderer.entity.state;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class HoveringBlockEntityRenderState extends EntityRenderState {
    public BlockEntity blockEntityDummy;
    public BlockState movingBlockState = Blocks.AIR.defaultBlockState();
}
