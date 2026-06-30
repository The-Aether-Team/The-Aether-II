package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesBiomes;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.Biome;

public class AetherIIBiomes {
    public static void bootstrap(BootstapContext<Biome> context) {
        HolyIslesBiomes.bootstrap(context);
    }
}