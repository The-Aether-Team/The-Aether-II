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
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class BrexallenRuinPools {
    public static final ResourceKey<StructureTemplatePool> RUIN_CENTERS = AetherIIPools.createKey("brexallen_ruins/ruin_centers");
    public static final ResourceKey<StructureTemplatePool> SMALL_RUINS = AetherIIPools.createKey("brexallen_ruins/small_ruins");
    public static final ResourceKey<StructureTemplatePool> LARGE_RUINS = AetherIIPools.createKey("brexallen_ruins/large_ruins");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processorCenter = processors.getOrThrow(AetherIIProcessorLists.BREXALLEN_RUINS_CENTER);
        Holder<StructureProcessorList> processorRuins = processors.getOrThrow(AetherIIProcessorLists.BREXALLEN_RUINS);

        context.register(RUIN_CENTERS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolCaves("brexallen_ruins/center/center_01", processorCenter), 1),
                        Pair.of(AetherIIPools.aetherPoolCaves("brexallen_ruins/center/center_02", processorCenter), 1),
                        Pair.of(AetherIIPools.aetherPoolCaves("brexallen_ruins/center/center_03", processorCenter), 1)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
        );
        context.register(LARGE_RUINS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolCaves("brexallen_ruins/large/temple_01", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPoolCaves("brexallen_ruins/large/temple_02", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPoolCaves("brexallen_ruins/large/temple_03", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPoolCaves("brexallen_ruins/large/house_01", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPoolCaves("brexallen_ruins/large/house_02", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPoolCaves("brexallen_ruins/large/house_03", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPoolCaves("brexallen_ruins/large/large_house_01", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPoolCaves("brexallen_ruins/large/large_house_02", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPoolCaves("brexallen_ruins/large/large_house_03", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPoolCaves("brexallen_ruins/large/bath_01", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPoolCaves("brexallen_ruins/large/bath_02", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPoolCaves("brexallen_ruins/large/bath_03", processorRuins), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(SMALL_RUINS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolCaves("brexallen_ruins/small/ruin_01", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPoolCaves("brexallen_ruins/small/ruin_02", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPoolCaves("brexallen_ruins/small/ruin_03", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPoolCaves("brexallen_ruins/small/ruin_04", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPoolCaves("brexallen_ruins/small/ruin_05", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPoolCaves("brexallen_ruins/small/ruin_06", processorRuins), 1),
                        Pair.of(StructurePoolElement.empty(), 3)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}