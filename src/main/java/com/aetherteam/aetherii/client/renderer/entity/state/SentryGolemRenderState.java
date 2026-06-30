package com.aetherteam.aetherii.client.renderer.entity.state;

import net.minecraft.world.entity.AnimationState;

public class SentryGolemRenderState extends ArmedEntityRenderState {
    public boolean ranged;

    public final AnimationState checkSelfAnimationState = new AnimationState();
    public final AnimationState lookAroundAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState attackReadyAnimationState = new AnimationState();
    public final AnimationState attackRangeReadyAnimationState = new AnimationState();
    public final AnimationState attackRangeAnimationState = new AnimationState();
}
