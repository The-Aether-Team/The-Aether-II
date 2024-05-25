package com.aetherteam.aetherii.data.resources.registries.pools;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.PillagerOutpostPools;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;



public class OutpostPools extends PillagerOutpostPools {
    public static final ResourceKey<StructureTemplatePool> START = Pools.createKey("outpost/outposts");

    public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> holder = templatePools.getOrThrow(Pools.EMPTY);

        context.register(START, new StructureTemplatePool(
                        holder, ImmutableList.of(Pair.of(
                                        StructurePoolElement.list(
                                                ImmutableList.of(
                                                        StructurePoolElement.legacy("outpost/outpost"),
                                                        StructurePoolElement.legacy("outpost/tall_outpost")
                                                )
                                        ),
                                        1
                                )
                        ),
                        StructureTemplatePool.Projection.RIGID
                )
        );
    }
}