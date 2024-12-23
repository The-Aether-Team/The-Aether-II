package com.aetherteam.aetherii.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

public class SkephidRenderState extends LivingEntityRenderState {
    public float attachChangeProgress;
    public float prevAttachChangeProgress;
    public Direction attachDir = Direction.DOWN;
    public double yO;
    public Vec3 deltaMovement = Vec3.ZERO;
}
