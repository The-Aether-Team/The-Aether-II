package com.aetherteam.aetherii.client.renderer.entity.state;

import com.aetherteam.aetherii.entity.passive.Sheepuff;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class SheepuffRenderState extends LivingEntityRenderState {
    public float headEatPositionScale;
    public float headEatAngleScale;
    public boolean isSheared;
    public Sheepuff.SheepuffColor woolColor = Sheepuff.SheepuffColor.WHITE;
    public int id;
    public boolean onGround;
    public boolean puff;
}
