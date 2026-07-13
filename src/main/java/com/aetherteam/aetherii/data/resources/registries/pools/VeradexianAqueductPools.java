package com.aetherteam.aetherii.data.resources.registries.pools;

import com.aetherteam.aetherii.data.resources.registries.AetherIIProcessorLists;
import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesPlacedFeatures;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class VeradexianAqueductPools {
    public static final ResourceKey<StructureTemplatePool> AQUEDUCT_START = AetherIIPools.createKey("veradexian_aqueduct/aqueducts_start");
    public static final ResourceKey<StructureTemplatePool> AQUEDUCTS = AetherIIPools.createKey("veradexian_aqueduct/aqueducts");
    public static final ResourceKey<StructureTemplatePool> BRYALINN_MOSS_COVER = AetherIIPools.createKey("veradexian_aqueduct/decoration/bryalinn_moss_cover");
    public static final ResourceKey<StructureTemplatePool> AERCLOUD = AetherIIPools.createKey("veradexian_aqueduct/decoration/aercloud");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processor = processors.getOrThrow(AetherIIProcessorLists.VERADEXIAN_AQUEDUCT);

        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        context.register(AQUEDUCT_START, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolAquatic("veradexian_aqueduct/aqueduct", processor), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(AQUEDUCTS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolAquatic("veradexian_aqueduct/aqueduct", processor), 7),
                        Pair.of(StructurePoolElement.empty(), 2)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(BRYALINN_MOSS_COVER, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HolyIslesPlacedFeatures.BRYALINN_MOSS_COVER_STRUCTURE)), 1),
                        Pair.of(StructurePoolElement.empty(), 2)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(AERCLOUD, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HolyIslesPlacedFeatures.COLD_AERCLOUD_LIBRARY)), 1),
                        Pair.of(StructurePoolElement.empty(), 2)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}