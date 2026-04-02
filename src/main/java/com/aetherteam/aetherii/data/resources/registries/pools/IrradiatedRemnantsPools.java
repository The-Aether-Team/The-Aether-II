package com.aetherteam.aetherii.data.resources.registries.pools;

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

public class IrradiatedRemnantsPools {
    public static final ResourceKey<StructureTemplatePool> IRRADIATED_REMNANTS = AetherIIPools.createKey("irradiated_remnants/remnants");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        // Holder<StructureProcessorList> processorRuins = processors.getOrThrow(AetherIIProcessorLists.VERADEXIAN_RUINS);

        context.register(IRRADIATED_REMNANTS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("irradiated_remnants/bunker"), 1),
                        Pair.of(AetherIIPools.aetherPool("irradiated_remnants/research_bunker"), 1),
                        Pair.of(AetherIIPools.aetherPool("irradiated_remnants/outpost"), 1),
                        Pair.of(AetherIIPools.aetherPool("irradiated_remnants/village"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}