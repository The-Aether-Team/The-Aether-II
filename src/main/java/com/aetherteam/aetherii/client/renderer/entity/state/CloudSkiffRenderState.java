package com.aetherteam.aetherii.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.world.entity.AnimationState;

public class CloudSkiffRenderState extends BoatRenderState {
    public AnimationState unfoldAnimationState = new AnimationState();
    public int animationTick;
    public boolean animateUnfold;
    public float steering;
    public float wingLift;
}
