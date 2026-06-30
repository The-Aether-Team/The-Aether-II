package com.aetherteam.aetherii.client.renderer.entity.state;

import net.minecraft.world.entity.AnimationState;

public class GravititeTalutonRenderState extends LivingEntityRenderState {
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState reloadAnimationState = new AnimationState();
    public boolean debrisVisible;
    public float viewYRot;
}
