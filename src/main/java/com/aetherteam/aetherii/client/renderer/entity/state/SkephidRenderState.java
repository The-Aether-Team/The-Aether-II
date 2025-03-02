package com.aetherteam.aetherii.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.Direction;
import org.joml.Quaternionf;

public class SkephidRenderState extends LivingEntityRenderState {
    public Quaternionf prevRotations = new Quaternionf();
    public Quaternionf rotations = new Quaternionf();

    public Direction attachDir = Direction.DOWN;
    public float attachChangeProgress;

}
