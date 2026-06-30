package com.aetherteam.aetherii.client.renderer.blockentity.state;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BedPart;

public class SkyrootBedRenderState {
    public Direction angle = Direction.NORTH;
    public ResourceLocation bedTexture;
    public BedPart bedPart;
}
