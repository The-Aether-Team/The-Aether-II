package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

public class AetherIICarvers {
    public static final ResourceKey<ConfiguredWorldCarver<?>> HIGHLANDS_CAVE = createKey("highlands_cave");

    private static ResourceKey<ConfiguredWorldCarver<?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_CARVER, new ResourceLocation(AetherII.MODID, name));
    }

    public static void bootstrap(BootstapContext<ConfiguredWorldCarver<?>> context) {
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
        context.register(HIGHLANDS_CAVE, WorldCarver.CAVE.configured(new CaveCarverConfiguration(
                                0.175F,
                                UniformHeight.of(VerticalAnchor.aboveBottom(16), VerticalAnchor.absolute(256)),
                                UniformFloat.of(0.1F, 0.9F),
                                VerticalAnchor.aboveBottom(-64),
                                blocks.getOrThrow(AetherIITags.Blocks.AETHER_CARVER_REPLACEABLES),
                                UniformFloat.of(0.7F, 1.4F),
                                UniformFloat.of(0.8F, 1.3F),
                                UniformFloat.of(-1.0F, -0.4F)
                        )
                )
        );
    }
}