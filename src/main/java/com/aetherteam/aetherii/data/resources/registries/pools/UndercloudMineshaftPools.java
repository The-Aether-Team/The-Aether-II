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

public class UndercloudMineshaftPools {
    public static final ResourceKey<StructureTemplatePool> HUB = AetherIIPools.createKey("undercloud_mineshaft/hubs");
    public static final ResourceKey<StructureTemplatePool> BRIDGE = AetherIIPools.createKey("undercloud_mineshaft/bridges");
    public static final ResourceKey<StructureTemplatePool> CORRIDOR = AetherIIPools.createKey("undercloud_mineshaft/corridors");
    public static final ResourceKey<StructureTemplatePool> AERCLOUD = AetherIIPools.createKey("undercloud_mineshaft/decoration/aercloud");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processorHub = processors.getOrThrow(AetherIIProcessorLists.UNDERCLOUD_MINESHAFT_HUB);
        Holder<StructureProcessorList> processorBridge = processors.getOrThrow(AetherIIProcessorLists.UNDERCLOUD_MINESHAFT_BRIDGE);
        Holder<StructureProcessorList> processorCorridor = processors.getOrThrow(AetherIIProcessorLists.UNDERCLOUD_MINESHAFT_CORRIDOR);

        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        context.register(HUB, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/hub", processorHub), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(BRIDGE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/bridges/straight_01", processorBridge), 3),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/bridges/straight_02", processorBridge), 3),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/bridges/corner", processorBridge), 2),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/bridges/t_cross", processorBridge), 1),
                        Pair.of(StructurePoolElement.empty(), 2)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDOR, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/straight_basic", processorCorridor), 12),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/straight_cockatrice_nest_01", processorCorridor), 2),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/straight_cockatrice_nest_02", processorCorridor), 2),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/straight_cockatrice_nest_03", processorCorridor), 2),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/straight_elevation", processorCorridor), 4),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/straight_storage_01", processorCorridor), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/straight_storage_02", processorCorridor), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/straight_storage_03", processorCorridor), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/straight_storage_04", processorCorridor), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/straight_storage_03", processorCorridor), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/straight_alkahest", processorCorridor), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/straight_sleeping_barracks", processorCorridor), 2),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/straight_pit", processorCorridor), 3),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/straight_platform", processorCorridor), 2),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/t_cross", processorCorridor), 12),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/staircase_01", processorCorridor), 4),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/staircase_02", processorCorridor), 4),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/junction_01", processorCorridor), 3),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/junction_02", processorCorridor), 2),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/junction_03", processorCorridor), 1)

                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(AERCLOUD, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HolyIslesPlacedFeatures.COLD_AERCLOUD_STRUCTURE)), 2),
                        Pair.of(StructurePoolElement.empty(), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}