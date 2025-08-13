package com.aetherteam.aetherii.data.resources.registries.pools;

import com.aetherteam.aetherii.data.resources.registries.AetherIIProcessorLists;
import com.aetherteam.aetherii.data.resources.registries.highlands.HighlandsPlacedFeatures;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class InfectedGuardianTreePools {

    public static final ResourceKey<StructureTemplatePool> DECORATION_LARGE_SHELF_ROTSHROOM = AetherIIPools.createKey("infected_guardian_tree/decoration/large_shelf_rotshroom");
    public static final ResourceKey<StructureTemplatePool> DECORATION_LARGE_SHELF_ROTSHROOM_REDUCED = AetherIIPools.createKey("infected_guardian_tree/decoration/large_shelf_rotshroom_reduced");
    public static final ResourceKey<StructureTemplatePool> DECORATION_COARSE_AETHER_DIRT_PATCH = AetherIIPools.createKey("infected_guardian_tree/decoration/coarse_aether_dirt_patch");
    public static final ResourceKey<StructureTemplatePool> DECORATION_ROTSHROOM_PATCH = AetherIIPools.createKey("infected_guardian_tree/decoration/rotshroom_patch");
    public static final ResourceKey<StructureTemplatePool> DECORATION_UNDERGROWTH_PATCH = AetherIIPools.createKey("infected_guardian_tree/decoration/undergrowth_patch");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processorDungeon = processors.getOrThrow(AetherIIProcessorLists.INFECTED_GUARDIAN_TREE);
        Holder<StructureProcessorList> processorTrunk = processors.getOrThrow(AetherIIProcessorLists.INFECTED_GUARDIAN_TREE_TRUNK);
        Holder<StructureProcessorList> processorRoots = processors.getOrThrow(AetherIIProcessorLists.INFECTED_GUARDIAN_TREE_ROOTS);

        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        context.register(DECORATION_LARGE_SHELF_ROTSHROOM, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HighlandsPlacedFeatures.LARGE_SHELF_ROTSHROOM)), 1),
                        Pair.of(StructurePoolElement.empty(), 6)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(DECORATION_LARGE_SHELF_ROTSHROOM_REDUCED, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HighlandsPlacedFeatures.LARGE_SHELF_ROTSHROOM_UNDERGROUND)), 1),
                        Pair.of(StructurePoolElement.empty(), 12)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(DECORATION_ROTSHROOM_PATCH, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HighlandsPlacedFeatures.ROTSHROOM_PATCH)), 3),
                        Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HighlandsPlacedFeatures.INFECTED_PATCH)), 1),
                        Pair.of(StructurePoolElement.empty(), 9)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(DECORATION_COARSE_AETHER_DIRT_PATCH, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HighlandsPlacedFeatures.COARSE_AETHER_DIRT_DUNGEON)), 6),
                        Pair.of(StructurePoolElement.empty(), 4)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(DECORATION_UNDERGROWTH_PATCH, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HighlandsPlacedFeatures.UNDERGROWTH_PATCH)), 6),
                        Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HighlandsPlacedFeatures.ROTTEN_UNDERGROWTH_PATCH)), 2),
                        Pair.of(StructurePoolElement.empty(), 64)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}