package com.aetherteam.aetherii.client.renderer.entity.state;

import com.aetherteam.aetherii.entity.passive.Cherub;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class CherubRenderState extends LivingEntityRenderState {
    public Identifier texture;
    public Cherub.CherubType type;
    public boolean flying;
    public float flapAngle;
}
