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

    public static final ResourceKey<StructureTemplatePool> FLOOR_1_STAIRCASE = AetherIIPools.createKey("infected_guardian_tree/floor_1/staircase");
    public static final ResourceKey<StructureTemplatePool> FLOOR_1_LOBBIES = AetherIIPools.createKey("infected_guardian_tree/floor_1/lobbies");
    public static final ResourceKey<StructureTemplatePool> FLOOR_1_MAIN_CORRIDORS = AetherIIPools.createKey("infected_guardian_tree/floor_1/main/corridors");
    public static final ResourceKey<StructureTemplatePool> FLOOR_1_MAIN_JOINTS = AetherIIPools.createKey("infected_guardian_tree/floor_1/main/joints");
    public static final ResourceKey<StructureTemplatePool> FLOOR_1_SIDE_CORRIDORS = AetherIIPools.createKey("infected_guardian_tree/floor_1/side/corridors");

    public static final ResourceKey<StructureTemplatePool> FLOOR_2_STAIRCASE = AetherIIPools.createKey("infected_guardian_tree/floor_2/staircase");
    public static final ResourceKey<StructureTemplatePool> FLOOR_2_LOBBIES = AetherIIPools.createKey("infected_guardian_tree/floor_2/lobbies");
    public static final ResourceKey<StructureTemplatePool> FLOOR_2_MAIN_CORRIDORS = AetherIIPools.createKey("infected_guardian_tree/floor_2/main/corridors");
    public static final ResourceKey<StructureTemplatePool> FLOOR_2_MAIN_JOINTS = AetherIIPools.createKey("infected_guardian_tree/floor_2/main/joints");
    public static final ResourceKey<StructureTemplatePool> FLOOR_2_SIDE_CORRIDORS = AetherIIPools.createKey("infected_guardian_tree/floor_2/side/corridors");

    public static final ResourceKey<StructureTemplatePool> FLOOR_3_STAIRCASE = AetherIIPools.createKey("infected_guardian_tree/floor_3/staircase");
    public static final ResourceKey<StructureTemplatePool> FLOOR_3_LOBBIES = AetherIIPools.createKey("infected_guardian_tree/floor_3/lobbies");
    public static final ResourceKey<StructureTemplatePool> FLOOR_3_MAIN_CORRIDORS = AetherIIPools.createKey("infected_guardian_tree/floor_3/main/corridors");
    public static final ResourceKey<StructureTemplatePool> FLOOR_3_MAIN_JOINTS = AetherIIPools.createKey("infected_guardian_tree/floor_3/main/joints");
    public static final ResourceKey<StructureTemplatePool> FLOOR_3_SIDE_CORRIDORS = AetherIIPools.createKey("infected_guardian_tree/floor_3/side/corridors");

    public static final ResourceKey<StructureTemplatePool> ROOMS = AetherIIPools.createKey("infected_guardian_tree/rooms/eye_rooms");
    public static final ResourceKey<StructureTemplatePool> ROOMS_EYE = AetherIIPools.createKey("infected_guardian_tree/rooms/eye_rooms");
    public static final ResourceKey<StructureTemplatePool> ROOMS_EYE_OR_JOINTS = AetherIIPools.createKey("infected_guardian_tree/rooms/eye_rooms_or_joints");
    public static final ResourceKey<StructureTemplatePool> ROOMS_CORRIDORS = AetherIIPools.createKey("infected_guardian_tree/rooms/corridors");

    public static final ResourceKey<StructureTemplatePool> DECORATION_LARGE_SHELF_ROTSHROOM = AetherIIPools.createKey("infected_guardian_tree/decoration/large_shelf_rotshroom");
    public static final ResourceKey<StructureTemplatePool> DECORATION_LARGE_SHELF_ROTSHROOM_REDUCED = AetherIIPools.createKey("infected_guardian_tree/decoration/large_shelf_rotshroom_reduced");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processorDungeon = processors.getOrThrow(AetherIIProcessorLists.INFECTED_GUARDIAN_TREE);
        Holder<StructureProcessorList> processorTrunk = processors.getOrThrow(AetherIIProcessorLists.INFECTED_GUARDIAN_TREE_TRUNK);

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
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/top_01", processorTrunk), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(TRUNK_BOTTOM, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/bottom", processorTrunk, 0, 384), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(TRUNK_ROOTS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/roots/root_01_a", processorTrunk), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/roots/root_01_b", processorTrunk), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/roots/root_02_a", processorTrunk), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/roots/root_02_b", processorTrunk), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/roots/root_03_a", processorTrunk), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/roots/root_03_b", processorTrunk), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/roots/root_04_a", processorTrunk), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/roots/root_04_b", processorTrunk), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/roots/root_05_a", processorTrunk), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/trunk/roots/root_05_b", processorTrunk), 1)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
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

        context.register(FLOOR_1_STAIRCASE, new StructureTemplatePool(
                templatePools.getOrThrow(FLOOR_1_LOBBIES),
                ImmutableList.of(
                        Pair.of(AetherIIPools.dynamicStaircase("infected_guardian_tree/floor_1/staircase_01", "infected_guardian_tree/floor_1/lobby_01_a", processorDungeon, 88), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(FLOOR_1_LOBBIES, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_1/lobby_01_a", processorDungeon, 0, 180), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_1/lobby_01_b", processorDungeon, 0, 180), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_1/lobby_01_c", processorDungeon, 0, 180), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(FLOOR_1_MAIN_CORRIDORS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_1/main/corridor_01", processorDungeon, 0, 180), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(FLOOR_1_MAIN_JOINTS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_1/main/t_crossing_01", processorDungeon, 0, 180), 2),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_1/main/t_crossing_02", processorDungeon, 0, 180), 2),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_1/main/t_crossing_03", processorDungeon, 0, 180), 2),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_1/main/staircase_intersection_01", processorDungeon, 0, 180), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_1/main/staircase_intersection_02", processorDungeon, 0, 180), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(FLOOR_1_SIDE_CORRIDORS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_1/side/corridor_01", processorDungeon, 0, 180), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(FLOOR_2_STAIRCASE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_2/staircase_01", processorDungeon, 0, 180), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(FLOOR_2_LOBBIES, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_2/lobby_01_a", processorDungeon, 0, 180), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(FLOOR_2_MAIN_CORRIDORS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_2/main/corridor_01", processorDungeon, 0, 180), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(FLOOR_2_MAIN_JOINTS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_2/main/t_crossing_01", processorDungeon, 0, 180), 2),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_2/main/t_crossing_02", processorDungeon, 0, 180), 2),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_2/main/t_crossing_03", processorDungeon, 0, 180), 2),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_2/main/staircase_intersection_01", processorDungeon, 0, 180), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_2/main/staircase_intersection_02", processorDungeon, 0, 180), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(FLOOR_2_SIDE_CORRIDORS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_2/side/corridor_01", processorDungeon, 0, 180), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(FLOOR_3_STAIRCASE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_3/staircase_01", processorDungeon, 0, 180), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(FLOOR_3_LOBBIES, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_3/lobby_01_a", processorDungeon, 0, 180), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(FLOOR_3_MAIN_CORRIDORS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_3/main/corridor_01", processorDungeon, 0, 180), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(FLOOR_3_MAIN_JOINTS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_3/main/t_crossing_01", processorDungeon, 0, 180), 2),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_3/main/t_crossing_02", processorDungeon, 0, 180), 2),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_3/main/t_crossing_03", processorDungeon, 0, 180), 2),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_3/main/staircase_intersection_01", processorDungeon, 0, 180), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_3/main/staircase_intersection_02", processorDungeon, 0, 180), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(FLOOR_3_SIDE_CORRIDORS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/floor_3/side/corridor_01", processorDungeon, 0, 180), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(ROOMS_EYE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/rooms/eye_room_01", processorDungeon, 0, 180), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(ROOMS_EYE_OR_JOINTS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/rooms/eye_room_01", processorDungeon, 0, 180), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(ROOMS_CORRIDORS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("infected_guardian_tree/rooms/corridor_01", processorDungeon, 0, 180), 1)
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
                        Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HighlandsPlacedFeatures.LARGE_SHELF_ROTSHROOM)), 1),
                        Pair.of(StructurePoolElement.empty(), 12)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}