package com.aetherteam.aetherii.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class MimicRenderState extends LivingEntityRenderState {
    public final AnimationState spawnAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
}
