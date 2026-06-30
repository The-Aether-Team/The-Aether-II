package com.aetherteam.aetherii.client.renderer.entity.state;

import net.minecraft.core.Direction;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

public class SkephidRenderState extends LivingEntityRenderState {
    public Quaternionfc prevRotations = new Quaternionf();
    public Quaternionfc rotations = new Quaternionf();

    public Direction attachDir = Direction.DOWN;
    public float attachChangeProgress;

}
