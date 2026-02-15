package com.aetherteam.aetherii.client.renderer.level;

import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;

public class AetherIIRenderEffects {
    public final static DimensionSpecialEffects HOLY_ISLES_RENDER_EFFECTS = new HolyIslesSpecialEffects();

    public static void registerRenderEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(AetherIIDimensions.AETHER_HOLY_ISLES_DIMENSION_TYPE.location(), HOLY_ISLES_RENDER_EFFECTS);
    }
}
