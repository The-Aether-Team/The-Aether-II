package com.aetherteam.aetherii.client.renderer.entity.state;

import net.minecraft.world.entity.AnimationState;

public class SwetRenderState extends LivingEntityRenderState {
    public AnimationState jumpAnimationState = new AnimationState();
    public AnimationState groundAnimationState = new AnimationState();
    public float swetScale = 0.95F;
}
