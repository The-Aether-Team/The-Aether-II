package com.aetherteam.aetherii.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class CarrionSproutRenderState extends LivingEntityRenderState {
    public AnimationState trapActiveAnimationState = new AnimationState();
    public AnimationState trapDeactiveAnimationState = new AnimationState();

}
