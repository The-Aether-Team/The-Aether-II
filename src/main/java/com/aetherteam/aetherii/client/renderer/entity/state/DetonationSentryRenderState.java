package com.aetherteam.aetherii.client.renderer.entity.state;

import net.minecraft.world.entity.AnimationState;

public class DetonationSentryRenderState extends LivingEntityRenderState {
    public boolean awake;
    public float timer;
    public AnimationState explosionAnimationState = new AnimationState();
}
