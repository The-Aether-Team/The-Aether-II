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
    public static final ResourceKey<StructureTemplatePool> RUINS_TEMPERATE = AetherIIPools.createKey("veradexian_ruins/ruins_temperate");
    public static final ResourceKey<StructureTemplatePool> RUINS_ARCTIC = AetherIIPools.createKey("veradexian_ruins/ruins_arctic");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processorRuins = processors.getOrThrow(AetherIIProcessorLists.VERADEXIAN_RUINS);

        context.register(RUINS_TEMPERATE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruin_small", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruin_medium", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_ruins/temperate/ruin_large", processorRuins), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(RUINS_ARCTIC, new StructureTemplatePool(
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