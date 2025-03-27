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

public class InfectedGuardianTreePools {
    public static final ResourceKey<StructureTemplatePool> TRUNK_BASE = AetherIIPools.createKey("infected_guardian_tree/trunk/base");
    public static final ResourceKey<StructureTemplatePool> TRUNK_WALLS = AetherIIPools.createKey("infected_guardian_tree/trunk/walls");
    public static final ResourceKey<StructureTemplatePool> TRUNK_CORNERS = AetherIIPools.createKey("infected_guardian_tree/trunk/corners");
    public static final ResourceKey<StructureTemplatePool> TRUNK_TOP = AetherIIPools.createKey("infected_guardian_tree/trunk/top");
    public static final ResourceKey<StructureTemplatePool> START_ROOM = AetherIIPools.createKey("infected_guardian_tree/start_room");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processorTrunk = processors.getOrThrow(AetherIIProcessorLists.INFECTED_GUARDIAN_TREE_TRUNK);

        context.register(TRUNK_BASE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/base", processorTrunk), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(TRUNK_WALLS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/wall_01", processorTrunk), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(TRUNK_CORNERS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/corner_01", processorTrunk), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(TRUNK_TOP, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/top_01", processorTrunk), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(START_ROOM, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/start_room"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}