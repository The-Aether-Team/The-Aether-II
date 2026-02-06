package com.aetherteam.aetherii.client.renderer.entity.state;

import net.minecraft.world.entity.AnimationState;

public class ShroudwingRenderState extends InsectRenderState {
    public AnimationState flyingAnimationState = new AnimationState();
    public AnimationState landAnimationState = new AnimationState();
    public AnimationState walkAnimationState = new AnimationState();
    public AnimationState takeoffAnimationState = new AnimationState();
}
