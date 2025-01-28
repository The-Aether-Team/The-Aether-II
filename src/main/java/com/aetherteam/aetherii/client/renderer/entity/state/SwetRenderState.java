package com.aetherteam.aetherii.client.renderer.entity.state;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;

public class SwetRenderState extends LivingEntityRenderState {
    private static final ResourceLocation DEFAULT_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/swet/blue.png");
    public AnimationState jumpAnimationState = new AnimationState();
    public AnimationState groundAnimationState = new AnimationState();
    public ResourceLocation texture;
    public float foodSaturation = 1.0F;
    public float waterDamageScale = 0.0F;

    public SwetRenderState() {
        this.texture = DEFAULT_TEXTURE;
    }
}
