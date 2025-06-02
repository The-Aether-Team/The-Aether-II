package com.aetherteam.aetherii.client.renderer.entity.state;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;

public class SwetRenderState extends LivingEntityRenderState {
    public AnimationState jumpAnimationState = new AnimationState();
    public AnimationState groundAnimationState = new AnimationState();
    public ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/swet/blue.png");
    public float swetScale = 0.95F;
}
