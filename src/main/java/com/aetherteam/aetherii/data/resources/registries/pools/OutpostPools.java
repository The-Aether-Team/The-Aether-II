package com.aetherteam.aetherii.data.resources.registries.pools;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

//TODO: Add to Datagen

public class OutpostPools {
    public static final ResourceKey<StructureTemplatePool> START = AetherIIPools.createKey("outpost/outposts");

    public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> holder = templatePools.getOrThrow(Pools.EMPTY);
    }
}