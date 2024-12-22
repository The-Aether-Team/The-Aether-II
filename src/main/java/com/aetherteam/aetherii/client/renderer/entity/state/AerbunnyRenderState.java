package com.aetherteam.aetherii.client.renderer.entity.state;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.Vec3;

public class AerbunnyRenderState extends TamableRenderState {

    public DyeColor collarColor = DyeColor.RED;
    public boolean isSitting;
    public float puffiness;
    public boolean onGround;
    public Vec3 deltaMovement;
}
