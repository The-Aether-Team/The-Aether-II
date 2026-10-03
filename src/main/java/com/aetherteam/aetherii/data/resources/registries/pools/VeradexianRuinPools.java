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

public class VeradexianRuinPools {
    public static final ResourceKey<StructureTemplatePool> RUIN_CENTERS_HIGHFIELDS = AetherIIPools.createKey("veradexian_ruins/highfields/ruin_centers");
    public static final ResourceKey<StructureTemplatePool> PATHS_HIGHFIELDS = AetherIIPools.createKey("veradexian_ruins/highfields/paths");
    public static final ResourceKey<StructureTemplatePool> RUINS_HIGHFIELDS = AetherIIPools.createKey("veradexian_ruins/highfields/ruins");
    public static final ResourceKey<StructureTemplatePool> RUINS_SMALL_HIGHFIELDS = AetherIIPools.createKey("veradexian_ruins/highfields/ruins_small");

    public static final ResourceKey<StructureTemplatePool> RUIN_CENTERS_MAGNETIC = AetherIIPools.createKey("veradexian_ruins/magnetic/ruin_centers");
    public static final ResourceKey<StructureTemplatePool> PATHS_MAGNETIC = AetherIIPools.createKey("veradexian_ruins/magnetic/paths");
    public static final ResourceKey<StructureTemplatePool> RUINS_MAGNETIC = AetherIIPools.createKey("veradexian_ruins/magnetic/ruins");
    public static final ResourceKey<StructureTemplatePool> RUINS_SMALL_MAGNETIC = AetherIIPools.createKey("veradexian_ruins/magnetic/ruins_small");

    public static final ResourceKey<StructureTemplatePool> TEMPLE_BASE_TEMPERATE = AetherIIPools.createKey("veradexian_ruins/temperate/temple_base");
    public static final ResourceKey<StructureTemplatePool> TEMPLE_BASE_50_TEMPERATE = AetherIIPools.createKey("veradexian_ruins/temperate/temple_base_50");
    public static final ResourceKey<StructureTemplatePool> TEMPLE_TEMPERATE = AetherIIPools.createKey("veradexian_ruins/temperate/temple");

    public static final ResourceKey<StructureTemplatePool> RUIN_CENTERS_ARCTIC = AetherIIPools.createKey("veradexian_ruins/arctic/ruin_centers");
    public static final ResourceKey<StructureTemplatePool> PATHS_ARCTIC = AetherIIPools.createKey("veradexian_ruins/arctic/paths");
    public static final ResourceKey<StructureTemplatePool> RUINS_ARCTIC = AetherIIPools.createKey("veradexian_ruins/arctic/ruins");
    public static final ResourceKey<StructureTemplatePool> RUINS_SMALL_ARCTIC = AetherIIPools.createKey("veradexian_ruins/arctic/ruins_small");
    public static final ResourceKey<StructureTemplatePool> TEMPLE_BASE_ARCTIC = AetherIIPools.createKey("veradexian_ruins/arctic/temple_base");
    public static final ResourceKey<StructureTemplatePool> TEMPLE_BASE_50_ARCTIC = AetherIIPools.createKey("veradexian_ruins/arctic/temple_base_50");
    public static final ResourceKey<StructureTemplatePool> TEMPLE_ARCTIC = AetherIIPools.createKey("veradexian_ruins/arctic/temple");

    public static final ResourceKey<StructureTemplatePool> BRYALINN_MOSS_COVER = AetherIIPools.createKey("veradexian_ruins/decoration/bryalinn_moss_cover");
    public static final ResourceKey<StructureTemplatePool> SHAYELINN_MOSS_COVER = AetherIIPools.createKey("veradexian_ruins/decoration/shayelinn_moss_cover");


    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processorRuins = processors.getOrThrow(AetherIIProcessorLists.VERADEXIAN_RUINS);
        Holder<StructureProcessorList> processorRuinsDecay = processors.getOrThrow(AetherIIProcessorLists.VERADEXIAN_RUINS_DECAY);
        Holder<StructureProcessorList> processorRuinsTerrainMatching = processors.getOrThrow(AetherIIProcessorLists.VERADEXIAN_RUINS_TERRAIN_MATCHING);

        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        context.register(RUIN_CENTERS_HIGHFIELDS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/highfields/center_01", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/highfields/small_center_01", processorRuins), 1)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
        );
        context.register(PATHS_HIGHFIELDS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/highfields/paths/straight", processorRuins), 2),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/highfields/paths/straight_collonades", processorRuinsDecay), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/highfields/paths/straight_curved", processorRuins), 2),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/highfields/paths/curve", processorRuins), 4),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/highfields/paths/curve_left", processorRuins), 2),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/highfields/paths/curve_right", processorRuins), 2),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/highfields/paths/t_cross", processorRuins), 2)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
        );
        context.register(RUINS_HIGHFIELDS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/small_ruin_01", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/small_ruin_02", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/small_ruin_03", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/small_ruin_04", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/small_ruin_05", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/large_ruin_01", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/large_ruin_02", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/large_ruin_03", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/holystone_ruin_01", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/holystone_ruin_02", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/holystone_ruin_03", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/house_01", processorRuinsDecay), 5),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/house_02", processorRuinsDecay), 5),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/house_03", processorRuinsDecay), 5),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/highfields/ruins/house_tent", processorRuins), 3),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/highfields/ruins/house_watchtower_short", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/highfields/ruins/house_watchtower_tall", processorRuins), 1),
                        Pair.of(StructurePoolElement.empty(), 9)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(RUINS_SMALL_HIGHFIELDS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/small_ruin_01", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/small_ruin_02", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/small_ruin_03", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/small_ruin_04", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/small_ruin_05", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/large_ruin_01", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/large_ruin_02", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/large_ruin_03", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/holystone_ruin_01", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/holystone_ruin_02", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/holystone_ruin_03", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/house_01", processorRuinsDecay), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/house_02", processorRuinsDecay), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/house_03", processorRuinsDecay), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/highfields/ruins/house_tent", processorRuins), 4),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/highfields/ruins/house_watchtower_short", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/highfields/ruins/house_watchtower_tall", processorRuins), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(RUIN_CENTERS_MAGNETIC, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/magnetic/center_01", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/magnetic/small_center_01", processorRuins), 1)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
        );
        context.register(PATHS_MAGNETIC, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/magnetic/paths/straight", processorRuins), 2),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/magnetic/paths/straight_collonades", processorRuinsDecay), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/magnetic/paths/straight_curved", processorRuins), 2),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/magnetic/paths/curve", processorRuins), 4),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/magnetic/paths/curve_left", processorRuins), 2),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/magnetic/paths/curve_right", processorRuins), 2),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/magnetic/paths/t_cross", processorRuins), 2)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
        );
        context.register(RUINS_MAGNETIC, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/small_ruin_01", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/small_ruin_02", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/small_ruin_03", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/small_ruin_04", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/small_ruin_05", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/large_ruin_01", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/large_ruin_02", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/large_ruin_03", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/holystone_ruin_01", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/holystone_ruin_02", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/holystone_ruin_03", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/house_01", processorRuinsDecay), 5),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/house_02", processorRuinsDecay), 5),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/house_03", processorRuinsDecay), 5),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/magnetic/ruins/house_tent", processorRuins), 3),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/magnetic/ruins/house_watchtower_short", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/magnetic/ruins/house_watchtower_tall", processorRuins), 1),
                        Pair.of(StructurePoolElement.empty(), 9)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(RUINS_SMALL_MAGNETIC, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/small_ruin_01", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/small_ruin_02", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/small_ruin_03", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/small_ruin_04", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/small_ruin_05", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/large_ruin_01", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/large_ruin_02", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/large_ruin_03", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/holystone_ruin_01", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/holystone_ruin_02", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/holystone_ruin_03", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/house_01", processorRuinsDecay), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/house_02", processorRuinsDecay), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/house_03", processorRuinsDecay), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/magnetic/ruins/house_tent", processorRuins), 4),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/magnetic/ruins/house_watchtower_short", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/magnetic/ruins/house_watchtower_tall", processorRuins), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(TEMPLE_BASE_TEMPERATE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/temple_base", processorRuins), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(TEMPLE_BASE_50_TEMPERATE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/temple_base", processorRuins), 1),
                        Pair.of(StructurePoolElement.empty(), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(TEMPLE_TEMPERATE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/temple", processorRuinsDecay), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(RUIN_CENTERS_ARCTIC, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/center_01", processorRuins), 2),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/small_center_01", processorRuins), 1)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
        );
        context.register(PATHS_ARCTIC, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/paths/straight", processorRuins), 2),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/paths/straight_collonades", processorRuinsDecay), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/paths/straight_curved", processorRuins), 2),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/paths/curve", processorRuins), 4),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/paths/curve_left", processorRuins), 2),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/paths/curve_right", processorRuins), 2),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/paths/t_cross", processorRuins), 2)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
        );
        context.register(RUINS_ARCTIC, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/small_ruin_01", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/small_ruin_02", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/small_ruin_03", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/small_ruin_04", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/small_ruin_05", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/large_ruin_01", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/large_ruin_02", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/large_ruin_03", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/holystone_ruin_01", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/holystone_ruin_02", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/holystone_ruin_03", processorRuinsTerrainMatching), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/house_01", processorRuinsDecay), 5),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/house_02", processorRuinsDecay), 5),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/house_03", processorRuinsDecay), 5),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/house_tent", processorRuins), 3),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/house_watchtower_short", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/house_watchtower_tall", processorRuins), 1),
                        Pair.of(StructurePoolElement.empty(), 9)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(RUINS_SMALL_ARCTIC, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/small_ruin_01", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/small_ruin_02", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/small_ruin_03", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/small_ruin_04", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/small_ruin_05", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/large_ruin_01", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/large_ruin_02", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/large_ruin_03", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/holystone_ruin_01", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/holystone_ruin_02", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/holystone_ruin_03", processorRuinsTerrainMatching), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/house_01", processorRuinsDecay), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/house_02", processorRuinsDecay), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/house_03", processorRuinsDecay), 6),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/house_tent", processorRuins), 4),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/house_watchtower_short", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruins/house_watchtower_tall", processorRuins), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(TEMPLE_BASE_ARCTIC, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/temple_base", processorRuins), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(TEMPLE_BASE_50_ARCTIC, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/temple_base", processorRuins), 1),
                        Pair.of(StructurePoolElement.empty(), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(TEMPLE_ARCTIC, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/temple", processorRuinsDecay), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(BRYALINN_MOSS_COVER, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HolyIslesPlacedFeatures.BRYALINN_MOSS_COVER_STRUCTURE)), 1),
                        Pair.of(StructurePoolElement.empty(), 4)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(SHAYELINN_MOSS_COVER, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HolyIslesPlacedFeatures.SHAYELINN_MOSS_COVER_STRUCTURE)), 1),
                        Pair.of(StructurePoolElement.empty(), 4)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}