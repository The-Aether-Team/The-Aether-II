package com.aetherteam.aetherii.data.resources.registries.pools;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.world.structure.pool.AetherPoolElement;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;
import java.util.function.Function;

public class AetherIIPools {

    private static final Holder<StructureProcessorList> EMPTY = Holder.direct(new StructureProcessorList(List.of()));

    public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> holderGetter = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = holderGetter.getOrThrow(Pools.EMPTY);

        context.register(Pools.EMPTY, new StructureTemplatePool(fallback, ImmutableList.of(), StructureTemplatePool.Projection.RIGID));
        OutpostPools.bootstrap(context);
    }

    public static ResourceKey<StructureTemplatePool> createKey(String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, new ResourceLocation(AetherII.MODID, name));
    }

    public static void register(BootstapContext<StructureTemplatePool> context, String name, StructureTemplatePool value) {
        context.register(createKey(name), value);
    }

    public static Function<StructureTemplatePool.Projection, AetherPoolElement> aetherPool(String id) {
        return pool -> new AetherPoolElement(Either.left(new ResourceLocation(AetherII.MODID, id)), EMPTY, pool);
    }
}