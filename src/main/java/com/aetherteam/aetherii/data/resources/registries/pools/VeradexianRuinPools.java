package com.aetherteam.aetherii.data.resources.registries.pools;

import com.aetherteam.aetherii.data.resources.registries.AetherIIProcessorLists;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class VeradexianRuinPools {
    public static final ResourceKey<StructureTemplatePool> RUIN_CENTERS_TEMPERATE = AetherIIPools.createKey("veradexian_ruins/temperate/ruin_centers");
    public static final ResourceKey<StructureTemplatePool> SMALL_RUINS_TEMPERATE = AetherIIPools.createKey("veradexian_ruins/temperate/small_ruins");
    public static final ResourceKey<StructureTemplatePool> LARGE_RUINS_TEMPERATE = AetherIIPools.createKey("veradexian_ruins/temperate/large_ruins");
    public static final ResourceKey<StructureTemplatePool> RUIN_CENTERS_ARCTIC = AetherIIPools.createKey("veradexian_ruins/arctic/ruin_centers");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processorRuins = processors.getOrThrow(AetherIIProcessorLists.VERADEXIAN_RUINS);

        context.register(RUIN_CENTERS_TEMPERATE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/center/center_01", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/center/center_02", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/center/center_03", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/center/center_04", processorRuins), 1)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
        );
        context.register(SMALL_RUINS_TEMPERATE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/small/ruin_01", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/small/ruin_02", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/small/ruin_03", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/small/ruin_04", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/small/ruin_05", processorRuins), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LARGE_RUINS_TEMPERATE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/large/ruin_01", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/large/ruin_02", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/large/ruin_03", processorRuins), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(RUIN_CENTERS_ARCTIC, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruin_small", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruin_medium", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/arctic/ruin_large", processorRuins), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}