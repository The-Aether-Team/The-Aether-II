package com.aetherteam.aetherii.client.renderer.entity.state;

import net.minecraft.world.entity.AnimationState;

public class CloudSkiffRenderState extends BoatRenderState {
    public AnimationState unfoldAnimationState = new AnimationState();
    public AnimationState foldAnimationState = new AnimationState();
    public int animationTick;
    public float steering;
    public float wingLift;
}
