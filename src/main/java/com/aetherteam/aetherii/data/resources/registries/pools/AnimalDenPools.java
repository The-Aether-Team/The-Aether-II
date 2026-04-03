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

public class AnimalDenPools {
    public static final ResourceKey<StructureTemplatePool> ANIMAL_DEN_HIGHFIELDS = AetherIIPools.createKey("animal_den/highfields");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        context.register(ANIMAL_DEN_HIGHFIELDS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("animal_den/highfields/den_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}