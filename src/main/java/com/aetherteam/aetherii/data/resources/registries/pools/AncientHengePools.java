package com.aetherteam.aetherii.data.resources.registries.pools;

import com.aetherteam.aetherii.data.resources.registries.AetherIIProcessorLists;
import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesPlacedFeatures;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class AncientHengePools {
    public static final ResourceKey<StructureTemplatePool> CENTER = AetherIIPools.createKey("ancient_henge/centers");
    public static final ResourceKey<StructureTemplatePool> PATH = AetherIIPools.createKey("ancient_henge/paths");
    public static final ResourceKey<StructureTemplatePool> HENGE = AetherIIPools.createKey("ancient_henge/henges");
    public static final ResourceKey<StructureTemplatePool> HENGE_MOSS = AetherIIPools.createKey("ancient_henge/moss");

    public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processor = processors.getOrThrow(AetherIIProcessorLists.ANCIENT_HENGE);

        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        context.register(CENTER, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("ancient_henge/center", processor), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(PATH, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("ancient_henge/path", processor), 1)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
        );
        context.register(HENGE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("ancient_henge/henges/henge_01", processor), 1),
                        Pair.of(AetherIIPools.aetherPool("ancient_henge/henges/henge_02", processor), 1),
                        Pair.of(AetherIIPools.aetherPool("ancient_henge/henges/henge_03", processor), 1),
                        Pair.of(AetherIIPools.aetherPool("ancient_henge/henges/henge_04", processor), 1),
                        Pair.of(AetherIIPools.aetherPool("ancient_henge/henges/henge_05", processor), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(HENGE_MOSS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HolyIslesPlacedFeatures.BRYALINN_MOSS_COVER_STRUCTURE)), 1),
                        Pair.of(StructurePoolElement.empty(), 2)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}