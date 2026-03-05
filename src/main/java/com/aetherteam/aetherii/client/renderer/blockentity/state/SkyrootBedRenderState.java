package com.aetherteam.aetherii.client.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.BedPart;

public class SkyrootBedRenderState extends BlockEntityRenderState {
    public Direction angle;
    public Identifier bedTexture;
    public BedPart bedPart;
}
