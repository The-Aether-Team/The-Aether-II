package com.aetherteam.aetherii.client.renderer.level;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;

public class HighlandsSpecialEffects extends DimensionSpecialEffects {
    private final DimensionSpecialEffects OVERWORLD = new DimensionSpecialEffects.OverworldEffects();

    public HighlandsSpecialEffects() {
        super(256.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false);
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 color, float brightness) {
        return OVERWORLD.getBrightnessDependentFogColor(color, brightness);
    }

    @Override
    public boolean isFoggyAt(int x, int z) {
        return OVERWORLD.isFoggyAt(x, z);
    }
}
