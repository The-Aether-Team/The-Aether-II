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

public class VeradexianAqueductPools {
    public static final ResourceKey<StructureTemplatePool> AQUEDUCT = AetherIIPools.createKey("veradexian_aqueduct/aqueducts");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        context.register(AQUEDUCT, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexain_aqueduct/small_aqueduct_01"), 2),
                        Pair.of(AetherIIPools.aetherPool("veradexian_aqueduct/large_aqueduct_01"), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_aqueduct/large_aqueduct_02"), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_aqueduct/large_aqueduct_03"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}