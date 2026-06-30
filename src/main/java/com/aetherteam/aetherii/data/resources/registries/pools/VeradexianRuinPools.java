package com.aetherteam.aetherii.data.resources.registries.pools;

import com.aetherteam.aetherii.data.resources.registries.AetherIIProcessorLists;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class VeradexianRuinPools {
    public static final ResourceKey<StructureTemplatePool> RUIN_CENTERS_TEMPERATE = AetherIIPools.createKey("veradexian_ruins/temperate/ruin_centers");
    public static final ResourceKey<StructureTemplatePool> SMALL_RUINS_TEMPERATE = AetherIIPools.createKey("veradexian_ruins/temperate/small_ruins");
    public static final ResourceKey<StructureTemplatePool> LARGE_RUINS_TEMPERATE = AetherIIPools.createKey("veradexian_ruins/temperate/large_ruins");
    public static final ResourceKey<StructureTemplatePool> RUIN_CENTERS_ARCTIC = AetherIIPools.createKey("veradexian_ruins/arctic/ruin_centers");
    public static final ResourceKey<StructureTemplatePool> SMALL_RUINS_ARCTIC = AetherIIPools.createKey("veradexian_ruins/arctic/small_ruins");
    public static final ResourceKey<StructureTemplatePool> LARGE_RUINS_ARCTIC = AetherIIPools.createKey("veradexian_ruins/arctic/large_ruins");

    public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processorRuinsTemperate = processors.getOrThrow(AetherIIProcessorLists.VERADEXIAN_RUINS_TEMPERATE);
        Holder<StructureProcessorList> processorRuinsArctic = processors.getOrThrow(AetherIIProcessorLists.VERADEXIAN_RUINS_ARCTIC);

        context.register(RUIN_CENTERS_TEMPERATE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/center/temperate/center_01", processorRuinsTemperate), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/center/temperate/center_02", processorRuinsTemperate), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/center/temperate/center_03", processorRuinsTemperate), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/center/temperate/center_04", processorRuinsTemperate), 1)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
        );
        context.register(SMALL_RUINS_TEMPERATE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/small/ruin_01", processorRuinsTemperate), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/small/ruin_02", processorRuinsTemperate), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/small/ruin_03", processorRuinsTemperate), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/small/ruin_04", processorRuinsTemperate), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/small/ruin_05", processorRuinsTemperate), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LARGE_RUINS_TEMPERATE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/large/ruin_01", processorRuinsTemperate), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/large/ruin_02", processorRuinsTemperate), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/large/ruin_03", processorRuinsTemperate), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(RUIN_CENTERS_ARCTIC, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/center/arctic/center_01", processorRuinsArctic), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/center/arctic/center_02", processorRuinsArctic), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/center/arctic/center_03", processorRuinsArctic), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/center/arctic/center_04", processorRuinsArctic), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(SMALL_RUINS_ARCTIC, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/small/ruin_01", processorRuinsArctic), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/small/ruin_02", processorRuinsArctic), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/small/ruin_03", processorRuinsArctic), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/small/ruin_04", processorRuinsArctic), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/small/ruin_05", processorRuinsArctic), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LARGE_RUINS_ARCTIC, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/large/ruin_01", processorRuinsArctic), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/large/ruin_02", processorRuinsArctic), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/large/ruin_03", processorRuinsArctic), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}