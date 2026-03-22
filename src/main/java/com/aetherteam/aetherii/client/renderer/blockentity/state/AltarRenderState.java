package com.aetherteam.aetherii.client.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.entity.state.ItemClusterRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

import java.util.Collections;
import java.util.List;

public class AltarRenderState extends BlockEntityRenderState {
    public float inputItemRotation;
    public ItemClusterRenderState displayItem;
    public float bobOff;
    public int progress;
    public float ambSpinningSpeed = 0.025F;
    public double ambFinalSpeed;

    public List<ItemStackRenderState> fuelItems = Collections.emptyList();
    public ItemStackRenderState itemInput = new ItemStackRenderState();
    public Direction facing = Direction.NORTH;

}
