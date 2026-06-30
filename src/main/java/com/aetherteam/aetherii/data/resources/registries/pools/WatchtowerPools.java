package com.aetherteam.aetherii.data.resources.registries.pools;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class WatchtowerPools {
    public static final ResourceKey<StructureTemplatePool> WATCHTOWER = AetherIIPools.createKey("watchtower/watchtowers");

    public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        context.register(WATCHTOWER, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("watchtower/watchtower_skyroot_01"), 1),
                        Pair.of(AetherIIPools.aetherPool("watchtower/watchtower_skyroot_02"), 1),
                        Pair.of(AetherIIPools.aetherPool("watchtower/watchtower_skyroot_03"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}