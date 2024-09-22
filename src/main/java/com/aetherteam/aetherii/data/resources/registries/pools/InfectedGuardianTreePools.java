package com.aetherteam.aetherii.data.resources.registries.pools;

import com.aetherteam.aetherii.data.resources.registries.AetherIIProcessorLists;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class InfectedGuardianTreePools {
    public static final ResourceKey<StructureTemplatePool> ENTRANCE = AetherIIPools.createKey("dungeon/infected_guardian_tree/entrance");
    public static final ResourceKey<StructureTemplatePool> LOBBIES = AetherIIPools.createKey("dungeon/infected_guardian_tree/lobbies");

    public static final ResourceKey<StructureTemplatePool> MAIN_PATH_FLOOR_1 = AetherIIPools.createKey("dungeon/infected_guardian_tree/main_path/floor_1");
    public static final ResourceKey<StructureTemplatePool> MAIN_PATH_FLOOR_2 = AetherIIPools.createKey("dungeon/infected_guardian_tree/main_path/floor_2");
    public static final ResourceKey<StructureTemplatePool> MAIN_PATH_FLOOR_3 = AetherIIPools.createKey("dungeon/infected_guardian_tree/main_path/floor_3");

    public static final ResourceKey<StructureTemplatePool> PATHS = AetherIIPools.createKey("dungeon/infected_guardian_tree/paths");

    public static final ResourceKey<StructureTemplatePool> STAIRCASE_FLOOR_1_UPPER = AetherIIPools.createKey("dungeon/infected_guardian_tree/staircase/floor_1/upper");
    public static final ResourceKey<StructureTemplatePool> STAIRCASE_FLOOR_1_LOWER = AetherIIPools.createKey("dungeon/infected_guardian_tree/staircase/floor_1/lower");
    public static final ResourceKey<StructureTemplatePool> STAIRCASE_FLOOR_2_UPPER = AetherIIPools.createKey("dungeon/infected_guardian_tree/staircase/floor_2/upper");
    public static final ResourceKey<StructureTemplatePool> STAIRCASE_FLOOR_2_LOWER = AetherIIPools.createKey("dungeon/infected_guardian_tree/staircase/floor_2/lower");

    public static final ResourceKey<StructureTemplatePool> ROOMS = AetherIIPools.createKey("dungeon/infected_guardian_tree/rooms");

    public static final ResourceKey<StructureTemplatePool> ARENA = AetherIIPools.createKey("dungeon/infected_guardian_tree/arena");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        HolderGetter<PlacedFeature> placement = context.lookup(Registries.PLACED_FEATURE);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);
        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);

        Holder<StructureProcessorList> processorDungeon = processors.getOrThrow(AetherIIProcessorLists.INFECTED_GUARDIAN_TREE);

        context.register(ENTRANCE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("dungeon/infected_guardian_tree/entrance"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LOBBIES, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("dungeon/infected_guardian_tree/lobbies/lobby_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(MAIN_PATH_FLOOR_1, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("dungeon/infected_guardian_tree/main_path/floor_1/main_path_01"), 1),
                        Pair.of(AetherIIPools.aetherPool("dungeon/infected_guardian_tree/main_path/floor_1/main_path_02"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(MAIN_PATH_FLOOR_2, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("dungeon/infected_guardian_tree/main_path/floor_2/main_path_01"), 1),
                        Pair.of(AetherIIPools.aetherPool("dungeon/infected_guardian_tree/main_path/floor_2/main_path_02"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(MAIN_PATH_FLOOR_3, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("dungeon/infected_guardian_tree/main_path/floor_3/main_path_01"), 1),
                        Pair.of(AetherIIPools.aetherPool("dungeon/infected_guardian_tree/main_path/floor_3/main_path_02"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(PATHS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("dungeon/infected_guardian_tree/paths/path_01", processorDungeon), 2),
                        Pair.of(AetherIIPools.aetherPool("dungeon/infected_guardian_tree/paths/path_02", processorDungeon), 1),
                        Pair.of(AetherIIPools.aetherPool("dungeon/infected_guardian_tree/paths/path_03", processorDungeon), 3),
                        Pair.of(AetherIIPools.aetherPool("dungeon/infected_guardian_tree/paths/corner", processorDungeon), 2),
                        Pair.of(AetherIIPools.aetherPool("dungeon/infected_guardian_tree/paths/t_crossing", processorDungeon), 3)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(STAIRCASE_FLOOR_1_UPPER, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("dungeon/infected_guardian_tree/staircase/floor_1/upper_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(STAIRCASE_FLOOR_1_LOWER, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("dungeon/infected_guardian_tree/staircase/floor_1/lower_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(STAIRCASE_FLOOR_2_UPPER, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("dungeon/infected_guardian_tree/staircase/floor_2/upper_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(STAIRCASE_FLOOR_2_LOWER, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("dungeon/infected_guardian_tree/staircase/floor_2/lower_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(ROOMS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("dungeon/infected_guardian_tree/rooms/room_01", processorDungeon), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(ARENA, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("dungeon/infected_guardian_tree/boss/arena"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}