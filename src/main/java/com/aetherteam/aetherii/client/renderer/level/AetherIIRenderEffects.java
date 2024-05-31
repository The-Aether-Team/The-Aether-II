package com.aetherteam.aetherii.client.renderer.level;

import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;

public class AetherIIRenderEffects {
    public final static DimensionSpecialEffects HIGHLANDS_RENDER_EFFECTS = new HighlandsSpecialEffects();

    public static void registerRenderEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(AetherIIDimensions.AETHER_HIGHLANDS_DIMENSION_TYPE.location(), HIGHLANDS_RENDER_EFFECTS);
    }
}
