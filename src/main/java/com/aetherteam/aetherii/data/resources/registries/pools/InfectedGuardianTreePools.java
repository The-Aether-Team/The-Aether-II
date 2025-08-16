package com.aetherteam.aetherii.data.resources.registries.pools;

import com.aetherteam.aetherii.data.resources.registries.AetherIIProcessorLists;
import com.aetherteam.aetherii.data.resources.registries.highlands.HighlandsPlacedFeatures;
import com.aetherteam.aetherii.world.structure.pool.AetherIIPoolElementTypes;
import com.aetherteam.aetherii.world.structure.pool.AetherPoolElement;
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
    public static final ResourceKey<StructureTemplatePool> LAYOUT_LOBBIES_FLOOR_1 = AetherIIPools.createKey("infected_guardian_tree/layout/lobbies/floor_1");
    public static final ResourceKey<StructureTemplatePool> LAYOUT_LOBBIES_FLOOR_2 = AetherIIPools.createKey("infected_guardian_tree/layout/lobbies/floor_2");
    public static final ResourceKey<StructureTemplatePool> LAYOUT_LOBBIES_FLOOR_3 = AetherIIPools.createKey("infected_guardian_tree/layout/lobbies/floor_3");
    public static final ResourceKey<StructureTemplatePool> LAYOUT_ROOMS = AetherIIPools.createKey("infected_guardian_tree/layout/rooms");
    public static final ResourceKey<StructureTemplatePool> LAYOUT_EYE_ROOMS = AetherIIPools.createKey("infected_guardian_tree/layout/eye_rooms");
    public static final ResourceKey<StructureTemplatePool> LAYOUT_PATH_SMALL_NO_EYES = AetherIIPools.createKey("infected_guardian_tree/layout/path/small/no_eyes");
    public static final ResourceKey<StructureTemplatePool> LAYOUT_PATH_SMALL_1_EYE = AetherIIPools.createKey("infected_guardian_tree/layout/path/small/1_eye");
    public static final ResourceKey<StructureTemplatePool> LAYOUT_PATH_MEDIUM_1_EYE = AetherIIPools.createKey("infected_guardian_tree/layout/path/medium/1_eye");
    public static final ResourceKey<StructureTemplatePool> LAYOUT_PATH_MEDIUM_2_EYES = AetherIIPools.createKey("infected_guardian_tree/layout/path/medium/2_eyes");
    public static final ResourceKey<StructureTemplatePool> LAYOUT_PATH_LARGE_2_EYES = AetherIIPools.createKey("infected_guardian_tree/layout/path/large/2_eyes");
    public static final ResourceKey<StructureTemplatePool> LAYOUT_DEAD_END = AetherIIPools.createKey("infected_guardian_tree/layout/dead_end");

    public static final ResourceKey<StructureTemplatePool> STAIRCASES_FLOOR_1 = AetherIIPools.createKey("infected_guardian_tree/staircases/floor_1");
    public static final ResourceKey<StructureTemplatePool> STAIRCASES_FLOOR_2 = AetherIIPools.createKey("infected_guardian_tree/staircases/floor_2");
    public static final ResourceKey<StructureTemplatePool> STAIRCASES_FLOOR_3 = AetherIIPools.createKey("infected_guardian_tree/staircases/floor_3");
    public static final ResourceKey<StructureTemplatePool> STAIRCASES_BOSS = AetherIIPools.createKey("infected_guardian_tree/staircases_boss");
    public static final ResourceKey<StructureTemplatePool> LOBBIES_FLOOR_1 = AetherIIPools.createKey("infected_guardian_tree/lobbies/floor_1");
    public static final ResourceKey<StructureTemplatePool> LOBBIES_FLOOR_2 = AetherIIPools.createKey("infected_guardian_tree/lobbies/floor_2");
    public static final ResourceKey<StructureTemplatePool> LOBBIES_FLOOR_3 = AetherIIPools.createKey("infected_guardian_tree/lobbies/floor_3");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS = AetherIIPools.createKey("infected_guardian_tree/corridors");
    public static final ResourceKey<StructureTemplatePool> ROOMS = AetherIIPools.createKey("infected_guardian_tree/rooms");
    public static final ResourceKey<StructureTemplatePool> EYE_ROOMS = AetherIIPools.createKey("infected_guardian_tree/eye_rooms");
    public static final ResourceKey<StructureTemplatePool> BOSS_ROOM = AetherIIPools.createKey("infected_guardian_tree/boss_room");
    public static final ResourceKey<StructureTemplatePool> DEAD_ENDS = AetherIIPools.createKey("infected_guardian_tree/dead_ends");
    public static final ResourceKey<StructureTemplatePool> DIRECT_DEAD_ENDS = AetherIIPools.createKey("infected_guardian_tree/direct_dead_ends");

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

        context.register(LAYOUT_LOBBIES_FLOOR_1, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/layouts/lobbies/floor_1/lobby_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LAYOUT_LOBBIES_FLOOR_2, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/layouts/lobbies/floor_2/lobby_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LAYOUT_LOBBIES_FLOOR_3, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/layouts/lobbies/floor_3/lobby_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LAYOUT_ROOMS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/layouts/additional_rooms/room"), 3),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/layouts/additional_rooms/eye_room_mixed"), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/layouts/dead_ends/dead_end_mixed"), 4)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LAYOUT_EYE_ROOMS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/layouts/additional_rooms/eye_room"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LAYOUT_PATH_SMALL_NO_EYES, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/layouts/paths/small/no_eyes/path_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LAYOUT_PATH_SMALL_1_EYE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/layouts/paths/small/1_eye/path_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LAYOUT_PATH_MEDIUM_1_EYE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/layouts/paths/medium/1_eye/path_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LAYOUT_PATH_LARGE_2_EYES, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/layouts/paths/large/2_eyes/path_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LAYOUT_DEAD_END, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/layouts/dead_ends/dead_end"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(STAIRCASES_FLOOR_2, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/staircases/floor_2/staircase_01"), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/staircases/floor_2/staircase_02"), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/staircases/floor_2/staircase_03"), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/staircases/floor_2/staircase_04"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(STAIRCASES_FLOOR_3, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/staircases/floor_3/staircase_01"), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/staircases/floor_3/staircase_02"), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/staircases/floor_3/staircase_03"), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/staircases/floor_3/staircase_04"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(STAIRCASES_BOSS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/staircases/boss/staircase_01"), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/staircases/boss/staircase_02"), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/staircases/boss/staircase_03"), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/staircases/boss/staircase_04"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LOBBIES_FLOOR_1, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/lobbies/floor_1/lobby_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LOBBIES_FLOOR_2, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/lobbies/floor_2/lobby_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LOBBIES_FLOOR_3, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/lobbies/floor_3/lobby_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(ROOMS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/rooms/room_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(EYE_ROOMS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/eye_rooms/eye_room_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/corridor_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(DEAD_ENDS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/dead_ends/dead_end_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(DIRECT_DEAD_ENDS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/dead_ends/direct_dead_end_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

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