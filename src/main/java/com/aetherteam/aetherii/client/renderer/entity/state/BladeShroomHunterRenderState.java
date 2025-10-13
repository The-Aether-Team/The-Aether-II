package com.aetherteam.aetherii.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.AnimationState;
import org.joml.Quaternionf;

public class BladeShroomHunterRenderState extends LivingEntityRenderState {
    public Quaternionf prevRotations = new Quaternionf();
    public Quaternionf rotations = new Quaternionf();

    public Direction attachDir = Direction.DOWN;
    public float attachChangeProgress;
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState burryAnimationState = new AnimationState();
    public AnimationState unburryAnimationState = new AnimationState();
    public AnimationState rustleAnimationState = new AnimationState();
}
