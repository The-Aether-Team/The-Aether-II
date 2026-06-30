package com.aetherteam.aetherii.client.renderer.entity.state;

import com.aetherteam.aetherii.entity.passive.Kirrid;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;

import java.util.Optional;

public class KirridRenderState extends LivingEntityRenderState {
    public boolean plate;
    public boolean wool;
    public AnimationState jumpAnimationState = new AnimationState();
    public AnimationState ramAnimationState = new AnimationState();
    public AnimationState eatAnimationState = new AnimationState();
    public EntityType<?> entityType;
    public int id;
    public Optional<Integer> woolColor;

    public KirridRenderState() {
        this.woolColor = Optional.empty();
    }
}
