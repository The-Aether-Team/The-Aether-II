package com.aetherteam.aetherii.client.renderer.entity.state;

import com.aetherteam.aetherii.entity.passive.Kirrid;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;

public class KirridRenderState extends LivingEntityRenderState {

    public boolean plate;
    public boolean wool;
    public AnimationState jumpAnimationState = new AnimationState();
    public AnimationState ramAnimationState = new AnimationState();
    public AnimationState eatAnimationState = new AnimationState();
    public EntityType<?> entityType;
    public int id;
    public Kirrid.KirridColor woolColor = Kirrid.KirridColor.WHITE;
}
