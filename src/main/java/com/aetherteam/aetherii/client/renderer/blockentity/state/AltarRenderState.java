package com.aetherteam.aetherii.client.renderer.blockentity.state;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class AltarRenderState {
    public float inputItemRotation;
    public ItemStack displayItem = ItemStack.EMPTY;
    public float bobOff;
    public int progress;
    public float ambSpinningSpeed = 0.025F;
    public double ambFinalSpeed;

    public List<ItemStack> fuelItems = Collections.emptyList();
    public ItemStack itemInput = ItemStack.EMPTY;
    public Direction facing = Direction.NORTH;
}
