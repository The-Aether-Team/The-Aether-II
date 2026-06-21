package com.aetherteam.aetherii.data.resources.registries.pools;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.world.structure.pool.AetherPoolElement;
import com.aetherteam.aetherii.world.structure.pool.DebugPoolElement;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class AetherIIPools {

    private static final Holder<StructureProcessorList> EMPTY = Holder.direct(new StructureProcessorList(List.of()));

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);
        context.register(Pools.EMPTY, new StructureTemplatePool(fallback, ImmutableList.of(), StructureTemplatePool.Projection.RIGID));

        OutpostPools.bootstrap(context);
        CampPools.bootstrap(context);
        WatchtowerPools.bootstrap(context);
        AnimalDenPools.bootstrap(context);
        VeradexianRuinPools.bootstrap(context);
        VeradexianLibraryPools.bootstrap(context);
        VeradexianAqueductPools.bootstrap(context);
        BrexallenRuinPools.bootstrap(context);
        UndercloudMineshaftPools.bootstrap(context);
        AncientHengePools.bootstrap(context);
        IrradiatedRemnantsPools.bootstrap(context);
        InfectedGuardianTreePools.bootstrap(context);
    }

    public static ResourceKey<StructureTemplatePool> createKey(String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void register(BootstrapContext<StructureTemplatePool> context, String name, StructureTemplatePool value) {
        context.register(createKey(name), value);
    }

    public static Function<StructureTemplatePool.Projection, AetherPoolElement> aetherPool(String id) {
        return pool -> new AetherPoolElement(Either.left(Identifier.fromNamespaceAndPath(AetherII.MODID, id)), EMPTY, pool, Optional.of(LiquidSettings.IGNORE_WATERLOGGING), 96, 384, true);
    }
    public static Function<StructureTemplatePool.Projection, AetherPoolElement> aetherPool(String id, Holder<StructureProcessorList> processor) {
        return pool -> new AetherPoolElement(Either.left(Identifier.fromNamespaceAndPath(AetherII.MODID, id)), processor, pool, Optional.of(LiquidSettings.IGNORE_WATERLOGGING), 96, 384, true);
    }

    // Does not automatically replace air blocks, primarily used for buried structures
    public static Function<StructureTemplatePool.Projection, AetherPoolElement> aetherPoolBuried(String id) {
        return pool -> new AetherPoolElement(Either.left(Identifier.fromNamespaceAndPath(AetherII.MODID, id)), EMPTY, pool, Optional.of(LiquidSettings.IGNORE_WATERLOGGING), 96, 384, false);
    }
    public static Function<StructureTemplatePool.Projection, AetherPoolElement> aetherPoolBuried(String id, Holder<StructureProcessorList> processor) {
        return pool -> new AetherPoolElement(Either.left(Identifier.fromNamespaceAndPath(AetherII.MODID, id)), processor, pool, Optional.of(LiquidSettings.IGNORE_WATERLOGGING), 96, 384, false);
    }

    // Can generate below Surface Level, primarily used for underground structures
    public static Function<StructureTemplatePool.Projection, AetherPoolElement> aetherPoolUnderground(String id) {
        return pool -> new AetherPoolElement(Either.left(Identifier.fromNamespaceAndPath(AetherII.MODID, id)), EMPTY, pool, Optional.of(LiquidSettings.IGNORE_WATERLOGGING), -32, 384, false);
    }
    public static Function<StructureTemplatePool.Projection, AetherPoolElement> aetherPoolUnderground(String id, Holder<StructureProcessorList> processor) {
        return pool -> new AetherPoolElement(Either.left(Identifier.fromNamespaceAndPath(AetherII.MODID, id)), processor, pool, Optional.of(LiquidSettings.IGNORE_WATERLOGGING), -32, 384, false);
    }

    // Does automatically replace air blocks and allows generation on a deep cave level, primarily used for cave structures
    public static Function<StructureTemplatePool.Projection, AetherPoolElement> aetherPoolCaves(String id) {
        return pool -> new AetherPoolElement(Either.left(Identifier.fromNamespaceAndPath(AetherII.MODID, id)), EMPTY, pool, Optional.of(LiquidSettings.IGNORE_WATERLOGGING), 24, 112, true);
    }
    public static Function<StructureTemplatePool.Projection, AetherPoolElement> aetherPoolCaves(String id, Holder<StructureProcessorList> processor) {
        return pool -> new AetherPoolElement(Either.left(Identifier.fromNamespaceAndPath(AetherII.MODID, id)), processor, pool, Optional.of(LiquidSettings.IGNORE_WATERLOGGING), 24, 112, true);
    }

    public static Function<StructureTemplatePool.Projection, AetherPoolElement> aetherPoolAquatic(String id) {
        return pool -> new AetherPoolElement(Either.left(Identifier.fromNamespaceAndPath(AetherII.MODID, id)), EMPTY, pool, Optional.of(LiquidSettings.APPLY_WATERLOGGING), -32, 384, true);
    }
    public static Function<StructureTemplatePool.Projection, AetherPoolElement> aetherPoolAquatic(String id, Holder<StructureProcessorList> processor) {
        return pool -> new AetherPoolElement(Either.left(Identifier.fromNamespaceAndPath(AetherII.MODID, id)), processor, pool, Optional.of(LiquidSettings.APPLY_WATERLOGGING), -32, 384, true);
    }

    public static Function<StructureTemplatePool.Projection, DebugPoolElement> debugPool(String id) {
        return pool -> new DebugPoolElement(Either.left(Identifier.fromNamespaceAndPath(AetherII.MODID, id)), EMPTY, pool, Optional.of(LiquidSettings.APPLY_WATERLOGGING));
    }
}