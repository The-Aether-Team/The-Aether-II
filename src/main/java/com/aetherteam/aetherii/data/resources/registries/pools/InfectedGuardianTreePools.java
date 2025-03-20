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

public class InfectedGuardianTreePools {
    public static final ResourceKey<StructureTemplatePool> START_ROOM = AetherIIPools.createKey("infected_guardian_tree/start_room");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        context.register(START_ROOM, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/start_room"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}