package com.aetherteam.aetherii.client.renderer.entity.state;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.AnimationState;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

public class BladeshroomHunterRenderState extends LivingEntityRenderState {
    public Quaternionfc prevRotations = new Quaternionf();
    public Quaternionfc rotations = new Quaternionf();

    public Direction attachDir = Direction.DOWN;
    public float attachChangeProgress;
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState buryAnimationState = new AnimationState();
    public AnimationState unburyAnimationState = new AnimationState();
    public AnimationState rustleAnimationState = new AnimationState();
}
