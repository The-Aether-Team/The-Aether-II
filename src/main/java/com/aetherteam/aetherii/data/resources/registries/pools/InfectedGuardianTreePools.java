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
    public static final ResourceKey<StructureTemplatePool> ENTRANCE = AetherIIPools.createKey("infected_guardian_tree/entrance");
    public static final ResourceKey<StructureTemplatePool> ENTRANCE_WALLS = AetherIIPools.createKey("infected_guardian_tree/entrance_walls");
    public static final ResourceKey<StructureTemplatePool> ENTRANCE_CORNERS = AetherIIPools.createKey("infected_guardian_tree/entrance_corners");
    public static final ResourceKey<StructureTemplatePool> START_ROOM = AetherIIPools.createKey("infected_guardian_tree/start_room");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processorTrunk = processors.getOrThrow(AetherIIProcessorLists.INFECTED_GUARDIAN_TREE_TRUNK);

        context.register(ENTRANCE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/entrance/entrance", processorTrunk), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(ENTRANCE_WALLS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/entrance/entrance_wall_01", processorTrunk), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(ENTRANCE_CORNERS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/entrance/entrance_corner_01", processorTrunk), 1)
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