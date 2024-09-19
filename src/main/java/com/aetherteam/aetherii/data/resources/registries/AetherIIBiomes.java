package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.data.resources.registries.highlands.HighlandsBiomes;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;

public class AetherIIBiomes {
    public static void bootstrap(BootstrapContext<Biome> context) {
        HighlandsBiomes.bootstrap(context);
    }
}