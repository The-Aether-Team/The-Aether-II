package com.aetherteam.aetherii.client.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

public class ArkeniumForgeRenderState extends BlockEntityRenderState {
    public ItemStackRenderState item = new ItemStackRenderState();
    public Direction facing;

}
