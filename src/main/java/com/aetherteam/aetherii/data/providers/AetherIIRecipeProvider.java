package com.aetherteam.aetherii.data.providers;

import com.aetherteam.nitrogen.data.providers.NitrogenRecipeProvider;
import net.minecraft.data.PackOutput;

public abstract class AetherIIRecipeProvider extends NitrogenRecipeProvider {
    public AetherIIRecipeProvider(PackOutput output, String id) {
        super(output, id);
    }
}
