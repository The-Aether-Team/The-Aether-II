package com.aetherteam.aetherii.client.renderer.entity.state;

import com.aetherteam.aetherii.entity.passive.Sheepuff;

public class SheepuffRenderState extends LivingEntityRenderState {
    public float headEatPositionScale;
    public float headEatAngleScale;
    public boolean isSheared;
    public int woolColor;
    public int id;
    public boolean onGround;
    public boolean puff;

    public SheepuffRenderState() {
       this.woolColor = Sheepuff.getDecimalColor(Sheepuff.SheepuffColor.WHITE);
    }
}
