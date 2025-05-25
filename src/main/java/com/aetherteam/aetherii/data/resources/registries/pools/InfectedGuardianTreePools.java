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
    public static final ResourceKey<StructureTemplatePool> TRUNK_BASE = AetherIIPools.createKey("infected_guardian_tree/trunk/base");
    public static final ResourceKey<StructureTemplatePool> TRUNK_ENTRANCE = AetherIIPools.createKey("infected_guardian_tree/trunk/entrance");
    public static final ResourceKey<StructureTemplatePool> TRUNK_WALLS = AetherIIPools.createKey("infected_guardian_tree/trunk/walls");
    public static final ResourceKey<StructureTemplatePool> TRUNK_BACK_WALLS = AetherIIPools.createKey("infected_guardian_tree/trunk/back_walls");
    public static final ResourceKey<StructureTemplatePool> TRUNK_BOTTOM = AetherIIPools.createKey("infected_guardian_tree/trunk/bottom");
    public static final ResourceKey<StructureTemplatePool> TRUNK_TOP = AetherIIPools.createKey("infected_guardian_tree/trunk/top");
    public static final ResourceKey<StructureTemplatePool> TRUNK_ROOTS = AetherIIPools.createKey("infected_guardian_tree/trunk/roots");
    public static final ResourceKey<StructureTemplatePool> TRUNK_BRANCHES = AetherIIPools.createKey("infected_guardian_tree/trunk/branches");
    public static final ResourceKey<StructureTemplatePool> TRUNK_TOP_BRANCHES = AetherIIPools.createKey("infected_guardian_tree/trunk/top_branches");

    public static final ResourceKey<StructureTemplatePool> MAIN_CORRIDORS_1 = AetherIIPools.createKey("infected_guardian_tree/corridors/main_corridors_1");
    public static final ResourceKey<StructureTemplatePool> MAIN_CORRIDORS_2 = AetherIIPools.createKey("infected_guardian_tree/corridors/main_corridors_2");
    public static final ResourceKey<StructureTemplatePool> MAIN_CORRIDORS_3 = AetherIIPools.createKey("infected_guardian_tree/corridors/main_corridors_3");
    public static final ResourceKey<StructureTemplatePool> MAIN_CORRIDORS_DOUBLE = AetherIIPools.createKey("infected_guardian_tree/corridors/main_corridors_double");
    public static final ResourceKey<StructureTemplatePool> MAIN_JOINTS = AetherIIPools.createKey("infected_guardian_tree/corridors/main_joints");
    public static final ResourceKey<StructureTemplatePool> MAIN_JOINTS_DOUBLE = AetherIIPools.createKey("infected_guardian_tree/corridors/main_joints_double");
    public static final ResourceKey<StructureTemplatePool> SIDE_CORRIDORS = AetherIIPools.createKey("infected_guardian_tree/corridors/side_corridors");
    public static final ResourceKey<StructureTemplatePool> SIDE_JOINTS = AetherIIPools.createKey("infected_guardian_tree/corridors/side_joints");
    public static final ResourceKey<StructureTemplatePool> DEAD_END_CORRIDOR = AetherIIPools.createKey("infected_guardian_tree/corridors/dead_end_corridor");
    public static final ResourceKey<StructureTemplatePool> DEAD_END_JOINT = AetherIIPools.createKey("infected_guardian_tree/corridors/dead_end_joint");
    public static final ResourceKey<StructureTemplatePool> FALLBACK_DEAD_END_CORRIDOR = AetherIIPools.createKey("infected_guardian_tree/corridors/fallback_dead_end_corridor");
    public static final ResourceKey<StructureTemplatePool> FALLBACK_DEAD_END_JOINT = AetherIIPools.createKey("infected_guardian_tree/corridors/fallback_dead_end_joint");
    public static final ResourceKey<StructureTemplatePool> ROOM_CORRIDORS = AetherIIPools.createKey("infected_guardian_tree/corridors/room_corridors");

    public static final ResourceKey<StructureTemplatePool> STAIRCASE_1 = AetherIIPools.createKey("infected_guardian_tree/center/staircase_1");
    public static final ResourceKey<StructureTemplatePool> STAIRCASE_2 = AetherIIPools.createKey("infected_guardian_tree/center/staircase_2");
    public static final ResourceKey<StructureTemplatePool> STAIRCASE_3 = AetherIIPools.createKey("infected_guardian_tree/center/staircase_3");
    public static final ResourceKey<StructureTemplatePool> STAIRCASE_BOSS = AetherIIPools.createKey("infected_guardian_tree/center/staircase_boss");
    public static final ResourceKey<StructureTemplatePool> LOBBIES_1 = AetherIIPools.createKey("infected_guardian_tree/center/lobbies_1");
    public static final ResourceKey<StructureTemplatePool> LOBBIES_2 = AetherIIPools.createKey("infected_guardian_tree/center/lobbies_2");
    public static final ResourceKey<StructureTemplatePool> LOBBIES_3 = AetherIIPools.createKey("infected_guardian_tree/center/lobbies_3");

    public static final ResourceKey<StructureTemplatePool> ROOMS = AetherIIPools.createKey("infected_guardian_tree/rooms/rooms");
    public static final ResourceKey<StructureTemplatePool> ROOMS_OR_JOINTS = AetherIIPools.createKey("infected_guardian_tree/rooms/rooms_or_joints");

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

        context.register(TRUNK_BASE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/base", processorTrunk), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(TRUNK_ENTRANCE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/walls/entrance_01", processorTrunk), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(TRUNK_WALLS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/walls/wall_01", processorTrunk), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(TRUNK_BACK_WALLS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/walls/back_wall_01", processorTrunk), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(TRUNK_TOP, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/top_01", processorTrunk), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/top_02", processorTrunk), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(TRUNK_BOTTOM, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/trunk/bottom", processorTrunk), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(TRUNK_ROOTS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/roots/root_01_a", processorRoots), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/roots/root_01_b", processorRoots), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/roots/root_02_a", processorRoots), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/roots/root_02_b", processorRoots), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/roots/root_03_a", processorRoots), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/roots/root_03_b", processorRoots), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/roots/root_04_a", processorRoots), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/roots/root_04_b", processorRoots), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/roots/root_05_a", processorRoots), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/roots/root_05_b", processorRoots), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(TRUNK_BRANCHES, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/branches/branch_01_a", processorTrunk), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/branches/branch_01_b", processorTrunk), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/branches/branch_02_a", processorTrunk), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/branches/branch_02_b", processorTrunk), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/branches/branch_03_a", processorTrunk), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/branches/branch_03_b", processorTrunk), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/branches/branch_04_a", processorTrunk), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/branches/branch_04_b", processorTrunk), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(TRUNK_TOP_BRANCHES, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/branches/top_branch_01_a", processorTrunk), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/branches/top_branch_01_b", processorTrunk), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/branches/top_branch_02_a", processorTrunk), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/branches/top_branch_02_b", processorTrunk), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(MAIN_CORRIDORS_1, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/main/corridor_01", processorDungeon), 3),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/main/corridor_02", processorDungeon), 2)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(MAIN_CORRIDORS_2, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/main/corridor_02", processorDungeon), 2),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/main/corridor_03", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(MAIN_CORRIDORS_3, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/main/corridor_03", processorDungeon), 2),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/main/corridor_04", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(MAIN_CORRIDORS_DOUBLE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/main/corridor_double", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(MAIN_JOINTS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/main/corner", processorDungeon), 2),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/main/t_crossing_01", processorDungeon), 3),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/main/t_crossing_02", processorDungeon), 3),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/main/t_crossing_03", processorDungeon), 3),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/main/staircase_intersection_01", processorDungeon), 2),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/main/staircase_intersection_02", processorDungeon), 2)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(MAIN_JOINTS_DOUBLE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/main/t_crossing_double", processorDungeon), 2),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/main/staircase_intersection_double", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(SIDE_CORRIDORS, new StructureTemplatePool(
                templatePools.getOrThrow(DEAD_END_CORRIDOR),
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/side/corridor_01", processorDungeon), 2),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/side/corridor_02", processorDungeon), 3)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(SIDE_JOINTS, new StructureTemplatePool(
                templatePools.getOrThrow(DEAD_END_JOINT),
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/side/corner", processorDungeon), 5),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/side/t_crossing", processorDungeon), 4),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/side/staircase_intersection", processorDungeon), 3),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/side/dead_end_joint", processorDungeon), 6),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/side/joint_room_01_a", processorDungeon), 3),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/side/joint_room_01_b", processorDungeon), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/side/joint_room_01_c", processorDungeon), 2)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(DEAD_END_CORRIDOR, new StructureTemplatePool(
                templatePools.getOrThrow(FALLBACK_DEAD_END_CORRIDOR),
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/side/dead_end_corridor", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(DEAD_END_JOINT, new StructureTemplatePool(
                templatePools.getOrThrow(FALLBACK_DEAD_END_JOINT),
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/side/dead_end_joint", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(FALLBACK_DEAD_END_CORRIDOR, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/side/fallback_dead_end_corridor", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(FALLBACK_DEAD_END_JOINT, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/side/fallback_dead_end_joint", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(ROOM_CORRIDORS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/room/corridor_01", processorDungeon), 3),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/room/corridor_02", processorDungeon), 2)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(STAIRCASE_1, new StructureTemplatePool(
                templatePools.getOrThrow(LOBBIES_1),
                ImmutableList.of(
                        Pair.of(AetherIIPools.dynamicStaircase("infected_guardian_tree/center/floor_1/staircase_01", "infected_guardian_tree/center/floor_1/lobby_01_a", processorDungeon, 96), 1),
                        Pair.of(AetherIIPools.dynamicStaircase("infected_guardian_tree/center/floor_1/staircase_01", "infected_guardian_tree/center/floor_1/lobby_01_b", processorDungeon, 96), 1),
                        Pair.of(AetherIIPools.dynamicStaircase("infected_guardian_tree/center/floor_1/staircase_01", "infected_guardian_tree/center/floor_1/lobby_01_c", processorDungeon, 96), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(STAIRCASE_2, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/center/floor_2/staircase_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(STAIRCASE_3, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/center/floor_3/staircase_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(STAIRCASE_BOSS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/center/boss/staircase_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LOBBIES_1, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/center/floor_1/lobby_01_a", processorDungeon), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/center/floor_1/lobby_01_b", processorDungeon), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/center/floor_1/lobby_01_c", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LOBBIES_2, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/center/floor_2/lobby_01_a", processorDungeon), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/center/floor_2/lobby_01_b", processorDungeon), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/center/floor_2/lobby_01_c", processorDungeon), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/center/floor_2/lobby_01_d", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LOBBIES_3, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/center/floor_3/lobby_01_a", processorDungeon), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/center/floor_3/lobby_01_b", processorDungeon), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/center/floor_3/lobby_01_c", processorDungeon), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/center/floor_3/lobby_01_d", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(ROOMS, new StructureTemplatePool(
                templatePools.getOrThrow(ROOM_CORRIDORS),
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/rooms/room_01", processorDungeon), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/rooms/room_02", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(ROOMS_OR_JOINTS, new StructureTemplatePool(
                templatePools.getOrThrow(ROOM_CORRIDORS),
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/rooms/room_01", processorDungeon), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/rooms/room_02", processorDungeon), 1)
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