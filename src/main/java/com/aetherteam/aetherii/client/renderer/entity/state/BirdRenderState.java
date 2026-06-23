package com.aetherteam.aetherii.client.renderer.entity.state;

import com.aetherteam.aetherii.entity.passive.Bird;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class BirdRenderState extends LivingEntityRenderState {
    public Identifier texture;
    public Identifier emissiveTexture;
    public Bird.BirdType type;
    public boolean rest;
}
