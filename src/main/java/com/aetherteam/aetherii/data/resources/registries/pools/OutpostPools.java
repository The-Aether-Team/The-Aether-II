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

public class OutpostPools {
    public static final ResourceKey<StructureTemplatePool> ENTRANCE_PEDESTAL = AetherIIPools.createKey("outpost/entrance_pedestal");
    public static final ResourceKey<StructureTemplatePool> OUTPOST = AetherIIPools.createKey("outpost/outposts");
    public static final ResourceKey<StructureTemplatePool> FOUNDATION = AetherIIPools.createKey("outpost/foundation");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processorFoundation = processors.getOrThrow(AetherIIProcessorLists.OUTPOST_FOUNDATION);

        context.register(ENTRANCE_PEDESTAL, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("outpost/entrance_pedestal"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(OUTPOST, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("outpost/outpost"), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("outpost/tall_outpost"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(FOUNDATION, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("outpost/foundation", processorFoundation), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}