package com.aetherteam.aetherii.data.resources.registries.pools;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.world.structure.pool.AetherPoolElement;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;
import java.util.function.Function;

public class AetherIIPools {

    private static final Holder<StructureProcessorList> EMPTY = Holder.direct(new StructureProcessorList(List.of()));

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);
        context.register(Pools.EMPTY, new StructureTemplatePool(fallback, ImmutableList.of(), StructureTemplatePool.Projection.RIGID));

        OutpostPools.bootstrap(context);
        CampHighfieldsPools.bootstrap(context);
        InfectedGuardianTreePools.bootstrap(context);
    }

    public static ResourceKey<StructureTemplatePool> createKey(String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void register(BootstrapContext<StructureTemplatePool> context, String name, StructureTemplatePool value) {
        context.register(createKey(name), value);
    }

    public static Function<StructureTemplatePool.Projection, AetherPoolElement> aetherPool(String id) {
        return pool -> new AetherPoolElement(Either.left(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, id)), EMPTY, pool, false);
    }

    public static Function<StructureTemplatePool.Projection, AetherPoolElement> aetherPool(String id, Holder<StructureProcessorList> processor) {
        return pool -> new AetherPoolElement(Either.left(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, id)), processor, pool, false);
    }

    public static Function<StructureTemplatePool.Projection, AetherPoolElement> aetherPoolReplace(String id) {
        return pool -> new AetherPoolElement(Either.left(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, id)), EMPTY, pool, true);
    }

    public static Function<StructureTemplatePool.Projection, AetherPoolElement> aetherPoolReplace(String id, Holder<StructureProcessorList> processor) {
        return pool -> new AetherPoolElement(Either.left(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, id)), processor, pool, true);
    }
}