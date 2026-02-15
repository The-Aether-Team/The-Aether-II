package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesBiomes;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;

public class AetherIIBiomes {
    public static void bootstrap(BootstrapContext<Biome> context) {
        HolyIslesBiomes.bootstrap(context);
    }
}