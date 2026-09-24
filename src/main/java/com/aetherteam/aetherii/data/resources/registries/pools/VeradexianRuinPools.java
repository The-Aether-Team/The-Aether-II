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
    public static final ResourceKey<StructureTemplatePool> RUIN_CENTERS_TEMPERATE = AetherIIPools.createKey("veradexian_ruins/temperate/ruin_centers");
    public static final ResourceKey<StructureTemplatePool> PATHS_TEMPERATE = AetherIIPools.createKey("veradexian_ruins/temperate/paths");
    public static final ResourceKey<StructureTemplatePool> RUINS_TEMPERATE = AetherIIPools.createKey("veradexian_ruins/temperate/ruins");
    public static final ResourceKey<StructureTemplatePool> TEMPLE_BASE_TEMPERATE = AetherIIPools.createKey("veradexian_ruins/temperate/temple_base");
    public static final ResourceKey<StructureTemplatePool> TEMPLE_TEMPERATE = AetherIIPools.createKey("veradexian_ruins/temperate/temple");
    public static final ResourceKey<StructureTemplatePool> BRYALINN_MOSS_COVER = AetherIIPools.createKey("veradexian_ruins/decoration/bryalinn_moss_cover");


    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processorRuins = processors.getOrThrow(AetherIIProcessorLists.VERADEXIAN_RUINS);
        Holder<StructureProcessorList> processorRuinsDecay = processors.getOrThrow(AetherIIProcessorLists.VERADEXIAN_RUINS_DECAY);

        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        context.register(RUIN_CENTERS_TEMPERATE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/center_01", processorRuins), 1)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
        );
        context.register(PATHS_TEMPERATE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/paths/straight", processorRuins), 2),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/paths/straight_collonades", processorRuinsDecay), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/paths/straight_curved", processorRuins), 2),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/paths/curve", processorRuins), 4),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/paths/curve_left", processorRuins), 2),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/paths/curve_right", processorRuins), 2),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/paths/t_cross", processorRuins), 2)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
        );
        context.register(RUINS_TEMPERATE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/house_01", processorRuinsDecay), 2),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/house_02", processorRuinsDecay), 2),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/house_03", processorRuinsDecay), 2),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruins/house_04", processorRuinsDecay), 1),
                        Pair.of(StructurePoolElement.empty(), 8)
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
        context.register(TEMPLE_TEMPERATE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/temple", processorRuinsDecay), 1)
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
    }
}