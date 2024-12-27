package com.aetherteam.aetherii.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class SwetRenderState extends LivingEntityRenderState {
    public AnimationState jumpAnimationState = new AnimationState();
    public AnimationState groundAnimationState = new AnimationState();
    public float foodSaturation = 1.0F;
    public float waterDamageScale = 0.0F;
}
