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

public class UndercloudMineshaftPools {
    public static final ResourceKey<StructureTemplatePool> HUB = AetherIIPools.createKey("undercloud_mineshaft/hubs");
    public static final ResourceKey<StructureTemplatePool> CORRIDOR = AetherIIPools.createKey("undercloud_mineshaft/corridors");

    public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processor = processors.getOrThrow(AetherIIProcessorLists.UNDERCLOUD_MINESHAFT);

        context.register(HUB, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/hub", processor), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDOR, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/corridor", processor), 4),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/corridor_slanted", processor), 3),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/t_cross", processor), 3),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/junction", processor), 3),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/ladder_junction", processor), 3),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/storage_room", processor), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("undercloud_mineshaft/corridors/material_deposit", processor), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}