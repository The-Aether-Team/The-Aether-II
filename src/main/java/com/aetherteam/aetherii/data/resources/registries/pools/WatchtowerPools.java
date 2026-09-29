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

public class WatchtowerPools {
    public static final ResourceKey<StructureTemplatePool> WATCHTOWER_HIGHFIELDS = AetherIIPools.createKey("watchtower/watchtowers_highfields");
    public static final ResourceKey<StructureTemplatePool> WATCHTOWER_MAGNETIC = AetherIIPools.createKey("watchtower/watchtowers_magnetic");
    public static final ResourceKey<StructureTemplatePool> WATCHTOWER_ARCTIC = AetherIIPools.createKey("watchtower/watchtowers_arctic");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        context.register(WATCHTOWER_HIGHFIELDS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("watchtower/highfields/watchtower_01"), 1),
                        Pair.of(AetherIIPools.aetherPool("watchtower/highfields/watchtower_02"), 1),
                        Pair.of(AetherIIPools.aetherPool("watchtower/highfields/watchtower_03"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(WATCHTOWER_MAGNETIC, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("watchtower/magnetic/watchtower_01"), 1),
                        Pair.of(AetherIIPools.aetherPool("watchtower/magnetic/watchtower_02"), 1),
                        Pair.of(AetherIIPools.aetherPool("watchtower/magnetic/watchtower_03"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(WATCHTOWER_ARCTIC, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("watchtower/arctic/watchtower_01"), 1),
                        Pair.of(AetherIIPools.aetherPool("watchtower/arctic/watchtower_02"), 1),
                        Pair.of(AetherIIPools.aetherPool("watchtower/arctic/watchtower_03"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}