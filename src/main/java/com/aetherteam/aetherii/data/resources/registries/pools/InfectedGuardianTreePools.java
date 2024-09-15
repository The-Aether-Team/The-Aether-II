package com.aetherteam.aetherii.data.resources.registries.pools;

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

public class InfectedGuardianTreePools {
    public static final ResourceKey<StructureTemplatePool> LOBBIES = AetherIIPools.createKey("dungeon/infected_guardian_tree/lobbies");
    public static final ResourceKey<StructureTemplatePool> ENTRANCE = AetherIIPools.createKey("dungeon/infected_guardian_tree/entrance");
    public static final ResourceKey<StructureTemplatePool> MAIN_PATH_FLOOR_1 = AetherIIPools.createKey("dungeon/infected_guardian_tree/main_path/floor_1");
    public static final ResourceKey<StructureTemplatePool> MAIN_PATH_FLOOR_2 = AetherIIPools.createKey("dungeon/infected_guardian_tree/main_path/floor_2");
    public static final ResourceKey<StructureTemplatePool> MAIN_PATH_FLOOR_3 = AetherIIPools.createKey("dungeon/infected_guardian_tree/main_path/floor_3");
    public static final ResourceKey<StructureTemplatePool> PATHS = AetherIIPools.createKey("dungeon/infected_guardian_tree/path");
    public static final ResourceKey<StructureTemplatePool> ROOMS = AetherIIPools.createKey("dungeon/infected_guardian_tree/rooms");
    public static final ResourceKey<StructureTemplatePool> ARENA = AetherIIPools.createKey("dungeon/infected_guardian_tree/arena");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        HolderGetter<PlacedFeature> placement = context.lookup(Registries.PLACED_FEATURE);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        //HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);

        context.register(LOBBIES, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("dungeon/infected_guardian_tree/lobbies/lobby_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(ENTRANCE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.feature(placement.getOrThrow(HighlandsPlacedFeatures.INFECTED_GUARDIAN_TREE_ENTRANCE)), 1)
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

        context.register(ARENA, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("dungeon/infected_guardian_tree/boss/arena"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}