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

public class UndercloudMineshaftPools {
    public static final ResourceKey<StructureTemplatePool> HUB = AetherIIPools.createKey("undercloud_mineshaft/hubs");
    public static final ResourceKey<StructureTemplatePool> BRIDGE = AetherIIPools.createKey("undercloud_mineshaft/bridges");
    public static final ResourceKey<StructureTemplatePool> CORRIDOR = AetherIIPools.createKey("undercloud_mineshaft/corridors");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processorBridge = processors.getOrThrow(AetherIIProcessorLists.UNDERCLOUD_MINESHAFT_BRIDGE);
        Holder<StructureProcessorList> processorCorridor = processors.getOrThrow(AetherIIProcessorLists.UNDERCLOUD_MINESHAFT_CORRIDOR);

        context.register(HUB, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/hub", processorBridge), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(BRIDGE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/bridges/straight_01", processorBridge), 4),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/bridges/straight_02", processorBridge), 4),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/bridges/corner", processorBridge), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/bridges/t_cross", processorBridge), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDOR, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/straight_01", processorCorridor), 16),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/t_cross_01", processorCorridor), 6),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/staircase_01", processorCorridor), 2),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/staircase_02", processorCorridor), 2)

                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}