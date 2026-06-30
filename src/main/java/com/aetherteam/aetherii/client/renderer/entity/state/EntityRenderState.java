package com.aetherteam.aetherii.client.renderer.entity.state;

public class EntityRenderState {
    public int id;
    public float ageInTicks;
    public int lightCoords;
    public int outlineColor;
    public boolean isInvisible;
    public boolean appearsGlowing;

    public boolean appearsGlowing() {
        return this.appearsGlowing;
    }
}
