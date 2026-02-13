package com.aetherteam.aetherii.client.renderer.entity.state;

import net.minecraft.world.entity.AnimationState;

public class GlitterwingRenderState extends InsectRenderState {
    public float wingXOffset;
    public float wingZRotation;
    public AnimationState landAnimationState = new AnimationState();
    public AnimationState takeOffAnimationState = new AnimationState();
}
