package com.aetherteam.aetherii.data.resources.registries.pools;

import com.aetherteam.aetherii.data.resources.registries.AetherIIProcessorLists;
import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesPlacedFeatures;
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

    public static final ResourceKey<StructureTemplatePool> LAYOUT_ROOMS_A = AetherIIPools.createKey("infected_guardian_tree/layout/rooms/a");
    public static final ResourceKey<StructureTemplatePool> LAYOUT_ROOMS_B = AetherIIPools.createKey("infected_guardian_tree/layout/rooms/b");
    public static final ResourceKey<StructureTemplatePool> LAYOUT_ROOMS_C = AetherIIPools.createKey("infected_guardian_tree/layout/rooms/c");
    public static final ResourceKey<StructureTemplatePool> LAYOUT_ROOMS_D = AetherIIPools.createKey("infected_guardian_tree/layout/rooms/d");
    public static final ResourceKey<StructureTemplatePool> LAYOUT_ROOMS_E = AetherIIPools.createKey("infected_guardian_tree/layout/rooms/e");

    public static final ResourceKey<StructureTemplatePool> LAYOUT_CHALLENGE_ROOMS_A = AetherIIPools.createKey("infected_guardian_tree/layout/challenge_rooms/a");
    public static final ResourceKey<StructureTemplatePool> LAYOUT_CHALLENGE_ROOMS_B = AetherIIPools.createKey("infected_guardian_tree/layout/challenge_rooms/b");
    public static final ResourceKey<StructureTemplatePool> LAYOUT_CHALLENGE_ROOMS_C = AetherIIPools.createKey("infected_guardian_tree/layout/challenge_rooms/c");
    public static final ResourceKey<StructureTemplatePool> LAYOUT_CHALLENGE_ROOMS_D = AetherIIPools.createKey("infected_guardian_tree/layout/challenge_rooms/d");
    public static final ResourceKey<StructureTemplatePool> LAYOUT_CHALLENGE_ROOMS_E = AetherIIPools.createKey("infected_guardian_tree/layout/challenge_rooms/e");

    public static final ResourceKey<StructureTemplatePool> LAYOUT_PATH_SMALL_1_EYE = AetherIIPools.createKey("infected_guardian_tree/layout/path/small/1_eye");
    public static final ResourceKey<StructureTemplatePool> LAYOUT_PATH_MEDIUM_1_EYE = AetherIIPools.createKey("infected_guardian_tree/layout/path/medium/1_eye");
    public static final ResourceKey<StructureTemplatePool> LAYOUT_PATH_MEDIUM_2_EYES = AetherIIPools.createKey("infected_guardian_tree/layout/path/medium/2_eyes");
    public static final ResourceKey<StructureTemplatePool> LAYOUT_PATH_LARGE_2_EYES = AetherIIPools.createKey("infected_guardian_tree/layout/path/large/2_eyes");

    public static final ResourceKey<StructureTemplatePool> ENTRANCE = AetherIIPools.createKey("infected_guardian_tree/entrance");
    public static final ResourceKey<StructureTemplatePool> STAIRCASES_FLOOR_1 = AetherIIPools.createKey("infected_guardian_tree/staircases/floor_1");
    public static final ResourceKey<StructureTemplatePool> STAIRCASES_FLOOR_2 = AetherIIPools.createKey("infected_guardian_tree/staircases/floor_2");
    public static final ResourceKey<StructureTemplatePool> STAIRCASES_BOSS = AetherIIPools.createKey("infected_guardian_tree/staircases/boss");
    public static final ResourceKey<StructureTemplatePool> LOBBIES_FLOOR_1 = AetherIIPools.createKey("infected_guardian_tree/lobbies/floor_1");
    public static final ResourceKey<StructureTemplatePool> LOBBIES_FLOOR_2 = AetherIIPools.createKey("infected_guardian_tree/lobbies/floor_2");

    public static final ResourceKey<StructureTemplatePool> CORRIDORS_A_A = AetherIIPools.createKey("infected_guardian_tree/corridors/a-a");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_A_B = AetherIIPools.createKey("infected_guardian_tree/corridors/a-b");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_A_C = AetherIIPools.createKey("infected_guardian_tree/corridors/a-c");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_A_D = AetherIIPools.createKey("infected_guardian_tree/corridors/a-d");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_A_E = AetherIIPools.createKey("infected_guardian_tree/corridors/a-e");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_B_B = AetherIIPools.createKey("infected_guardian_tree/corridors/b-b");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_B_C = AetherIIPools.createKey("infected_guardian_tree/corridors/b-c");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_B_D = AetherIIPools.createKey("infected_guardian_tree/corridors/b-d");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_B_E = AetherIIPools.createKey("infected_guardian_tree/corridors/b-e");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_C_C = AetherIIPools.createKey("infected_guardian_tree/corridors/c-c");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_C_D = AetherIIPools.createKey("infected_guardian_tree/corridors/c-d");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_C_E = AetherIIPools.createKey("infected_guardian_tree/corridors/c-e");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_D_D = AetherIIPools.createKey("infected_guardian_tree/corridors/d-d");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_D_E = AetherIIPools.createKey("infected_guardian_tree/corridors/d-e");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_E_E = AetherIIPools.createKey("infected_guardian_tree/corridors/e-e");

    public static final ResourceKey<StructureTemplatePool> CORRIDORS_A_A_V = AetherIIPools.createKey("infected_guardian_tree/corridors/a-a_v");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_A_B_V = AetherIIPools.createKey("infected_guardian_tree/corridors/a-b_v");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_A_C_V = AetherIIPools.createKey("infected_guardian_tree/corridors/a-c_v");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_A_D_V = AetherIIPools.createKey("infected_guardian_tree/corridors/a-d_v");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_A_E_V = AetherIIPools.createKey("infected_guardian_tree/corridors/a-e_v");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_B_B_V = AetherIIPools.createKey("infected_guardian_tree/corridors/b-b_v");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_B_C_V = AetherIIPools.createKey("infected_guardian_tree/corridors/b-c_v");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_B_D_V = AetherIIPools.createKey("infected_guardian_tree/corridors/b-d_v");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_B_E_V = AetherIIPools.createKey("infected_guardian_tree/corridors/b-e_v");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_C_C_V = AetherIIPools.createKey("infected_guardian_tree/corridors/c-c_v");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_C_D_V = AetherIIPools.createKey("infected_guardian_tree/corridors/c-d_v");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_C_E_V = AetherIIPools.createKey("infected_guardian_tree/corridors/c-e_v");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_D_D_V = AetherIIPools.createKey("infected_guardian_tree/corridors/d-d_v");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_D_E_V = AetherIIPools.createKey("infected_guardian_tree/corridors/d-e_v");
    public static final ResourceKey<StructureTemplatePool> CORRIDORS_E_E_V = AetherIIPools.createKey("infected_guardian_tree/corridors/e-e_v");

    public static final ResourceKey<StructureTemplatePool> ROOMS_A = AetherIIPools.createKey("infected_guardian_tree/rooms/rooms_a");
    public static final ResourceKey<StructureTemplatePool> ROOMS_B = AetherIIPools.createKey("infected_guardian_tree/rooms/rooms_b");
    public static final ResourceKey<StructureTemplatePool> ROOMS_C = AetherIIPools.createKey("infected_guardian_tree/rooms/rooms_c");
    public static final ResourceKey<StructureTemplatePool> ROOMS_D = AetherIIPools.createKey("infected_guardian_tree/rooms/rooms_d");
    public static final ResourceKey<StructureTemplatePool> ROOMS_E = AetherIIPools.createKey("infected_guardian_tree/rooms/rooms_e");

    public static final ResourceKey<StructureTemplatePool> ROOMS_0_0_A_B = AetherIIPools.createKey("infected_guardian_tree/rooms/0-0-a-b");
    public static final ResourceKey<StructureTemplatePool> ROOMS_0_C_0_D = AetherIIPools.createKey("infected_guardian_tree/rooms/0-c-0-d");
    public static final ResourceKey<StructureTemplatePool> ROOMS_0_E_A_A = AetherIIPools.createKey("infected_guardian_tree/rooms/0-e-a-a");
    public static final ResourceKey<StructureTemplatePool> ROOMS_0_D_0_A = AetherIIPools.createKey("infected_guardian_tree/rooms/0-d-0-a");
    public static final ResourceKey<StructureTemplatePool> ROOMS_B_B_D_D = AetherIIPools.createKey("infected_guardian_tree/rooms/b-b-d-d");
    public static final ResourceKey<StructureTemplatePool> ROOMS_D_0_B_B = AetherIIPools.createKey("infected_guardian_tree/rooms/d-0-b-b");
    public static final ResourceKey<StructureTemplatePool> ROOMS_E_B_B_0 = AetherIIPools.createKey("infected_guardian_tree/rooms/e-b-b-0");
    public static final ResourceKey<StructureTemplatePool> ROOMS_E_C_D_A = AetherIIPools.createKey("infected_guardian_tree/rooms/e-c-d-a");

    public static final ResourceKey<StructureTemplatePool> CHALLENGE_ROOMS_A = AetherIIPools.createKey("infected_guardian_tree/challenge_rooms/rooms_a");
    public static final ResourceKey<StructureTemplatePool> CHALLENGE_ROOMS_B = AetherIIPools.createKey("infected_guardian_tree/challenge_rooms/rooms_b");
    public static final ResourceKey<StructureTemplatePool> CHALLENGE_ROOMS_C = AetherIIPools.createKey("infected_guardian_tree/challenge_rooms/rooms_c");
    public static final ResourceKey<StructureTemplatePool> CHALLENGE_ROOMS_D = AetherIIPools.createKey("infected_guardian_tree/challenge_rooms/rooms_d");
    public static final ResourceKey<StructureTemplatePool> CHALLENGE_ROOMS_E = AetherIIPools.createKey("infected_guardian_tree/challenge_rooms/rooms_e");

    public static final ResourceKey<StructureTemplatePool> CHALLENGE_ROOMS_B_0_B_D = AetherIIPools.createKey("infected_guardian_tree/challenge_rooms/b-0-b-d");
    public static final ResourceKey<StructureTemplatePool> CHALLENGE_ROOMS_B_B_C_A = AetherIIPools.createKey("infected_guardian_tree/challenge_rooms/b-b-c-a");
    public static final ResourceKey<StructureTemplatePool> CHALLENGE_ROOMS_C_B_B_C = AetherIIPools.createKey("infected_guardian_tree/challenge_rooms/c-b-b-c");
    public static final ResourceKey<StructureTemplatePool> CHALLENGE_ROOMS_D_B_0_C = AetherIIPools.createKey("infected_guardian_tree/challenge_rooms/d-b-0-c");
    public static final ResourceKey<StructureTemplatePool> CHALLENGE_ROOMS_E_B_0_E = AetherIIPools.createKey("infected_guardian_tree/challenge_rooms/e-b-0-e");

    public static final ResourceKey<StructureTemplatePool> BOSS_ROOM = AetherIIPools.createKey("infected_guardian_tree/boss_room");
    public static final ResourceKey<StructureTemplatePool> DEAD_ENDS = AetherIIPools.createKey("infected_guardian_tree/dead_ends");
    public static final ResourceKey<StructureTemplatePool> DEAD_END_FALLBACK = AetherIIPools.createKey("infected_guardian_tree/dead_end_fallback");

    public static final ResourceKey<StructureTemplatePool> DECORATION_LARGE_SHELF_ROTSHROOM = AetherIIPools.createKey("infected_guardian_tree/decoration/large_shelf_rotshroom");
    public static final ResourceKey<StructureTemplatePool> DECORATION_LARGE_SHELF_ROTSHROOM_REDUCED = AetherIIPools.createKey("infected_guardian_tree/decoration/large_shelf_rotshroom_reduced");
    public static final ResourceKey<StructureTemplatePool> DECORATION_COARSE_AETHER_DIRT_PATCH = AetherIIPools.createKey("infected_guardian_tree/decoration/coarse_aether_dirt_patch");
    public static final ResourceKey<StructureTemplatePool> DECORATION_ROTSHROOM_PATCH = AetherIIPools.createKey("infected_guardian_tree/decoration/rotshroom_patch");
    public static final ResourceKey<StructureTemplatePool> DECORATION_UNDERGROWTH_PATCH = AetherIIPools.createKey("infected_guardian_tree/decoration/undergrowth_patch");

    public static final ResourceKey<StructureTemplatePool> STRUCTURE_COVER_ENTRANCE = AetherIIPools.createKey("infected_guardian_tree/structure_cover/entrance");
    public static final ResourceKey<StructureTemplatePool> STRUCTURE_COVER_STAIRCASE = AetherIIPools.createKey("infected_guardian_tree/structure_cover/staircase");
    public static final ResourceKey<StructureTemplatePool> STRUCTURE_COVER_LOBBY = AetherIIPools.createKey("infected_guardian_tree/structure_cover/lobby");
    public static final ResourceKey<StructureTemplatePool> STRUCTURE_COVER_BOSS_ROOM = AetherIIPools.createKey("infected_guardian_tree/structure_cover/boss_room");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processorDungeon = processors.getOrThrow(AetherIIProcessorLists.INFECTED_GUARDIAN_TREE);
        Holder<StructureProcessorList> processorDebug = processors.getOrThrow(AetherIIProcessorLists.INFECTED_GUARDIAN_TREE_DEBUG);

        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        context.register(LAYOUT_LOBBIES_FLOOR_1, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/layouts/lobbies/floor_1/lobby_01", processorDebug), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/layouts/lobbies/floor_1/lobby_02", processorDebug), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LAYOUT_LOBBIES_FLOOR_2, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/layouts/lobbies/floor_2/lobby_01", processorDebug), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/layouts/lobbies/floor_2/lobby_02", processorDebug), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(LAYOUT_ROOMS_A, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        //Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_a-a"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_a-b"), 1),
                        //Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_a-c"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_a-d"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_a-e"), 1),

                        //Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_a-a"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_a-b"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_a-c"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_a-d"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_a-e"), 1),

                        Pair.of(StructurePoolElement.empty(), 8)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LAYOUT_ROOMS_B, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        //Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_b-a"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_b-b"), 1),
                        //Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_b-c"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_b-d"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_b-e"), 1),

                        //Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_b-a"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_b-b"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_b-c"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_b-d"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_b-e"), 1),

                        Pair.of(StructurePoolElement.empty(), 8)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LAYOUT_ROOMS_C, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        //Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_c-a"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_c-b"), 1),
                        //Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_c-c"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_c-d"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_c-e"), 1),

                        //Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_c-a"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_c-b"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_c-c"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_c-d"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_c-e"), 1),

                        Pair.of(StructurePoolElement.empty(), 8)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LAYOUT_ROOMS_D, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        //Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_d-a"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_d-b"), 1),
                        //Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_d-c"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_d-d"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_d-e"), 1),

                        //Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_d-a"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_d-b"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_d-c"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_d-d"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_d-e"), 1),

                        Pair.of(StructurePoolElement.empty(), 8)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LAYOUT_ROOMS_E, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        //Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_e-a"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_e-b"), 1),
                        //Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_e-c"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_e-d"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/room_e-e"), 1),

                        //Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_e-a"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_e-b"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_e-c"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_e-d"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_e-e"), 1),

                        Pair.of(StructurePoolElement.empty(), 8)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(LAYOUT_CHALLENGE_ROOMS_A, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                      //Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_a-a"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_a-b"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_a-c"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_a-d"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_a-e"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LAYOUT_CHALLENGE_ROOMS_B, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                     //Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_b-a"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_b-b"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_b-c"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_b-d"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_b-e"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LAYOUT_CHALLENGE_ROOMS_C, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                       //Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/layouts/additional_rooms/challenge_room_c-a", processorDebug), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_c-b"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_c-c"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_c-d"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_c-e"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LAYOUT_CHALLENGE_ROOMS_D, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        //Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_d-a"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_d-b"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_d-c"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_d-d"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_d-e"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LAYOUT_CHALLENGE_ROOMS_E, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        //Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_e-a"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_e-b"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_e-c"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_e-d"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/additional_rooms/challenge_room_e-e"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(LAYOUT_PATH_SMALL_1_EYE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/small/1_eye/path_01_a"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/small/1_eye/path_01_b"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/small/1_eye/path_01_c"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/small/1_eye/path_01_d"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/small/1_eye/path_01_e"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LAYOUT_PATH_MEDIUM_1_EYE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/medium/1_eye/path_01_a"), 3),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/medium/1_eye/path_01_b"), 3),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/medium/1_eye/path_01_c"), 3),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/medium/1_eye/path_01_d"), 3),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/medium/1_eye/path_01_e"), 3),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/medium/1_eye/path_02_a"), 2),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/medium/1_eye/path_02_b"), 2),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/medium/1_eye/path_02_c"), 2),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/medium/1_eye/path_02_d"), 2),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/medium/1_eye/path_02_e"), 2)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LAYOUT_PATH_MEDIUM_2_EYES, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/medium/2_eyes/path_01_a"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/medium/2_eyes/path_01_b"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/medium/2_eyes/path_01_c"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/medium/2_eyes/path_01_d"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/medium/2_eyes/path_01_e"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LAYOUT_PATH_LARGE_2_EYES, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/large/2_eyes/path_01_a"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/large/2_eyes/path_01_b"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/large/2_eyes/path_01_c"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/large/2_eyes/path_01_d"), 1),
                        Pair.of(AetherIIPools.debugPool("infected_guardian_tree/layouts/paths/large/2_eyes/path_01_e"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(ENTRANCE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/entrance"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(STAIRCASES_FLOOR_1, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/staircases/floor_1", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(STAIRCASES_FLOOR_2, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/staircases/floor_2", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(STAIRCASES_BOSS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/staircases/boss", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(LOBBIES_FLOOR_1, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/lobbies/floor_1/lobby_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LOBBIES_FLOOR_2, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/lobbies/floor_2/lobby_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(ROOMS_A, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        //Pair.of(AetherIIPools.aetherPoolRotatable("infected_guardian_tree/rooms/0-0-a-b_01", RotatablePoolElement.PoolRotation.ADD_180), 1),
                        //Pair.of(AetherIIPools.aetherPoolRotatable("infected_guardian_tree/rooms/0-e-a-a_01", RotatablePoolElement.PoolRotation.ADD_180), 1),
                        //Pair.of(AetherIIPools.aetherPoolRotatable("infected_guardian_tree/rooms/0-e-a-a_01", RotatablePoolElement.PoolRotation.ADD_270), 1),
                        //Pair.of(AetherIIPools.aetherPoolRotatable("infected_guardian_tree/rooms/e-c-d-a_01", RotatablePoolElement.PoolRotation.ADD_270), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(ROOMS_B, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/rooms/b-b-d-d_01", processorDungeon), 1)
                        //,
                        //Pair.of(AetherIIPools.aetherPoolRotatable("infected_guardian_tree/rooms/0-0-a-b_01", RotatablePoolElement.PoolRotation.ADD_270), 1),
                        //Pair.of(AetherIIPools.aetherPoolRotatable("infected_guardian_tree/rooms/b-b-d-d_01", RotatablePoolElement.PoolRotation.ADD_90), 1),
                        //Pair.of(AetherIIPools.aetherPoolRotatable("infected_guardian_tree/rooms/d-0-b-b_01", RotatablePoolElement.PoolRotation.ADD_180), 1),
                        //Pair.of(AetherIIPools.aetherPoolRotatable("infected_guardian_tree/rooms/d-0-b-b_01", RotatablePoolElement.PoolRotation.ADD_270), 1),
                        //Pair.of(AetherIIPools.aetherPoolRotatable("infected_guardian_tree/rooms/e-b-b-0_01", RotatablePoolElement.PoolRotation.ADD_90), 1),
                        //Pair.of(AetherIIPools.aetherPoolRotatable("infected_guardian_tree/rooms/e-b-b-0_01", RotatablePoolElement.PoolRotation.ADD_180), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(ROOMS_C, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        //Pair.of(AetherIIPools.aetherPoolRotatable("infected_guardian_tree/rooms/e-c-d-a_01", RotatablePoolElement.PoolRotation.ADD_90), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(ROOMS_D, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/rooms/d-0-b-b_01", processorDungeon), 1)
                        //,
                        //Pair.of(AetherIIPools.aetherPoolRotatable("infected_guardian_tree/rooms/b-b-d-d_01", RotatablePoolElement.PoolRotation.ADD_180), 1),
                        //Pair.of(AetherIIPools.aetherPoolRotatable("infected_guardian_tree/rooms/b-b-d-d_01", RotatablePoolElement.PoolRotation.ADD_270), 1),
                        //Pair.of(AetherIIPools.aetherPoolRotatable("infected_guardian_tree/rooms/e-c-d-a_01", RotatablePoolElement.PoolRotation.ADD_180), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(ROOMS_E, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/rooms/e-b-b-0_01", processorDungeon), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/rooms/e-c-d-a_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(ROOMS_0_0_A_B, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/rooms/0-0-a-b_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(ROOMS_0_C_0_D, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/rooms/0-c-0-d_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(ROOMS_0_E_A_A, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/rooms/0-e-a-a_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(ROOMS_0_D_0_A, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/rooms/0-d-0-a_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(ROOMS_B_B_D_D, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/rooms/b-b-d-d_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(ROOMS_D_0_B_B, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/rooms/d-0-b-b_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(ROOMS_E_B_B_0, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/rooms/e-b-b-0_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(ROOMS_E_C_D_A, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/rooms/e-c-d-a_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(CHALLENGE_ROOMS_A, new StructureTemplatePool(
                fallback,
                ImmutableList.of(

                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CHALLENGE_ROOMS_B, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/challenge_rooms/b-0-b-d_01", processorDungeon), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/challenge_rooms/b-b-c-a_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CHALLENGE_ROOMS_C, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/challenge_rooms/c-b-b-c_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CHALLENGE_ROOMS_D, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/challenge_rooms/d-b-0-c_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CHALLENGE_ROOMS_E, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/challenge_rooms/e-b-0-e_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(CHALLENGE_ROOMS_B_0_B_D, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/challenge_rooms/b-0-b-d_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CHALLENGE_ROOMS_B_B_C_A, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/challenge_rooms/b-b-c-a_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CHALLENGE_ROOMS_C_B_B_C, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/challenge_rooms/c-b-b-c_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CHALLENGE_ROOMS_D_B_0_C, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/challenge_rooms/d-b-0-c_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CHALLENGE_ROOMS_E_B_0_E, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/challenge_rooms/e-b-0-e_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );


        context.register(BOSS_ROOM, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/boss_room", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(CORRIDORS_A_A, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/a-a_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_A_B, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/a-b_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_A_C, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/a-c_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_A_D, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/a-d_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_A_E, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/a-e_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_B_B, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/b-b_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_B_C, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/b-c_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_B_D, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/b-d_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_B_E, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/b-e_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_C_C, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/c-c_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_C_D, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/c-d_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_C_E, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/c-e_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_D_D, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/d-d_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_D_E, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/d-e_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_E_E, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/e-e_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(CORRIDORS_A_A_V, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/a-a_v_01", processorDungeon), 2),
                        Pair.of(StructurePoolElement.empty(), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_A_B_V, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/a-b_v_01", processorDungeon), 2),
                        Pair.of(StructurePoolElement.empty(), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_A_C_V, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/a-c_v_01", processorDungeon), 2),
                        Pair.of(StructurePoolElement.empty(), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_A_D_V, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/a-d_v_01", processorDungeon), 2),
                        Pair.of(StructurePoolElement.empty(), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_A_E_V, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/a-e_v_01", processorDungeon), 2),
                        Pair.of(StructurePoolElement.empty(), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_B_B_V, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/b-b_v_01", processorDungeon), 2),
                        Pair.of(StructurePoolElement.empty(), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_B_C_V, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/b-c_v_01", processorDungeon), 2),
                        Pair.of(StructurePoolElement.empty(), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_B_D_V, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/b-d_v_01", processorDungeon), 2),
                        Pair.of(StructurePoolElement.empty(), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_B_E_V, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/b-e_v_01", processorDungeon), 2),
                        Pair.of(StructurePoolElement.empty(), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_C_C_V, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/c-c_v_01", processorDungeon), 2),
                        Pair.of(StructurePoolElement.empty(), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_C_D_V, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/c-d_v_01", processorDungeon), 2),
                        Pair.of(StructurePoolElement.empty(), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_C_E_V, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/c-e_v_01", processorDungeon), 2),
                        Pair.of(StructurePoolElement.empty(), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_D_D_V, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/d-d_v_01", processorDungeon), 2),
                        Pair.of(StructurePoolElement.empty(), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_D_E_V, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/d-e_v_01", processorDungeon), 2),
                        Pair.of(StructurePoolElement.empty(), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(CORRIDORS_E_E_V, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/corridors/e-e_v_01", processorDungeon), 2),
                        Pair.of(StructurePoolElement.empty(), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(DEAD_ENDS, new StructureTemplatePool(
                templatePools.getOrThrow(DEAD_END_FALLBACK),
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/dead_ends/dead_end_01", processorDungeon), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/dead_ends/dead_end_02", processorDungeon), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/dead_ends/dead_end_03", processorDungeon), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/dead_ends/dead_end_04", processorDungeon), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/dead_ends/dead_end_05", processorDungeon), 1),
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/dead_ends/dead_end_06", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(DEAD_END_FALLBACK, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolUnderground("infected_guardian_tree/dead_ends/dead_end_fallback", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(DECORATION_LARGE_SHELF_ROTSHROOM, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HolyIslesPlacedFeatures.LARGE_SHELF_ROTSHROOM)), 1),
                        Pair.of(StructurePoolElement.empty(), 4)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(DECORATION_LARGE_SHELF_ROTSHROOM_REDUCED, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HolyIslesPlacedFeatures.LARGE_SHELF_ROTSHROOM_UNDERGROUND)), 1),
                        Pair.of(StructurePoolElement.empty(), 8)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(DECORATION_ROTSHROOM_PATCH, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HolyIslesPlacedFeatures.ROTSHROOM_PATCH)), 1),
                        Pair.of(StructurePoolElement.empty(), 3)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(DECORATION_COARSE_AETHER_DIRT_PATCH, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HolyIslesPlacedFeatures.COARSE_AETHER_DIRT_DUNGEON)), 1),
                        Pair.of(StructurePoolElement.empty(), 3)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(DECORATION_UNDERGROWTH_PATCH, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HolyIslesPlacedFeatures.UNDERGROWTH_PATCH)), 1),
                        Pair.of(StructurePoolElement.empty(), 8)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(STRUCTURE_COVER_ENTRANCE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HolyIslesPlacedFeatures.INFECTED_GUARDIAN_TREE_ENTRANCE_COVER)), 1)),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(STRUCTURE_COVER_STAIRCASE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HolyIslesPlacedFeatures.INFECTED_GUARDIAN_TREE_STAIRCASE_COVER)), 1)),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(STRUCTURE_COVER_LOBBY, new StructureTemplatePool(
                fallback,
                ImmutableList.of(Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HolyIslesPlacedFeatures.INFECTED_GUARDIAN_TREE_LOBBY_COVER)), 1)),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(STRUCTURE_COVER_BOSS_ROOM, new StructureTemplatePool(
                fallback,
                ImmutableList.of(Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HolyIslesPlacedFeatures.INFECTED_GUARDIAN_TREE_BOSS_ROOM_COVER)), 1)),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}