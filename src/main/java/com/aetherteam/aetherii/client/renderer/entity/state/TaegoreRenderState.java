package com.aetherteam.aetherii.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class TaegoreRenderState extends LivingEntityRenderState {
    public AnimationState digAnimationState = new AnimationState();
    public AnimationState digStartAnimationState = new AnimationState();
    public AnimationState digEndAnimationState = new AnimationState();
}
