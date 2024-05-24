package com.aetherteam.aetherii.data.resources.registries.features;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.builders.AetherIIFeatureBuilders;
import com.aetherteam.aetherii.world.feature.AetherIIFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class AetherIIMiscFeatures extends AetherIIFeatureBuilders {
    public static final ResourceKey<ConfiguredFeature<?, ?>> COAST_QUICKSOIL = AetherIIFeatureUtils.registerKey("coast_quicksoil");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        AetherIIFeatureUtils.register(context, COAST_QUICKSOIL, AetherIIFeatures.COAST.get(), createCoast(AetherIIBlocks.QUICKSOIL.get().defaultBlockState(), 32, 96));
    }
}