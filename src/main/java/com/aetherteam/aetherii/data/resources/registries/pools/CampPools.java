package com.aetherteam.aetherii.data.resources.registries.pools;

import com.aetherteam.aetherii.data.resources.registries.AetherIIProcessorLists;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class CampPools {
    public static final ResourceKey<StructureTemplatePool> HIGHFIELDS_CENTER = AetherIIPools.createKey("camp/highfields/center");
    public static final ResourceKey<StructureTemplatePool> HIGHFIELDS_ADDITIONAL_PATHS = AetherIIPools.createKey("camp/highfields/additional_paths");
    public static final ResourceKey<StructureTemplatePool> HIGHFIELDS_TENTS = AetherIIPools.createKey("camp/highfields/tents");
    public static final ResourceKey<StructureTemplatePool> HIGHFIELDS_DECORATIONS = AetherIIPools.createKey("camp/highfields/decorations");
    public static final ResourceKey<StructureTemplatePool> HIGHFIELDS_STORAGE_AREAS = AetherIIPools.createKey("camp/highfields/storage_areas");

    public static final ResourceKey<StructureTemplatePool> MAGNETIC_CENTER = AetherIIPools.createKey("camp/magnetic/center");
    public static final ResourceKey<StructureTemplatePool> MAGNETIC_ADDITIONAL_PATHS = AetherIIPools.createKey("camp/magnetic/additional_paths");
    public static final ResourceKey<StructureTemplatePool> MAGNETIC_TENTS = AetherIIPools.createKey("camp/magnetic/tents");
    public static final ResourceKey<StructureTemplatePool> MAGNETIC_DECORATIONS = AetherIIPools.createKey("camp/magnetic/decorations");
    public static final ResourceKey<StructureTemplatePool> MAGNETIC_STORAGE_AREAS = AetherIIPools.createKey("camp/magnetic/storage_areas");

    public static final ResourceKey<StructureTemplatePool> ARCTIC_CENTER = AetherIIPools.createKey("camp/arctic/center");
    public static final ResourceKey<StructureTemplatePool> ARCTIC_ADDITIONAL_PATHS = AetherIIPools.createKey("camp/arctic/additional_paths");
    public static final ResourceKey<StructureTemplatePool> ARCTIC_TENTS = AetherIIPools.createKey("camp/arctic/tents");
    public static final ResourceKey<StructureTemplatePool> ARCTIC_DECORATIONS = AetherIIPools.createKey("camp/arctic/decorations");
    public static final ResourceKey<StructureTemplatePool> ARCTIC_STORAGE_AREAS = AetherIIPools.createKey("camp/arctic/storage_areas");

    public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processorCamp = processors.getOrThrow(AetherIIProcessorLists.CAMP);

        context.register(HIGHFIELDS_CENTER, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/center/small_center_01", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/center/small_center_02", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/center/medium_center_01", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/center/medium_center_02", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/center/large_center_01", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/center/large_center_02", processorCamp), 1)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
        );
        context.register(HIGHFIELDS_ADDITIONAL_PATHS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/additional_paths/path_01", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/additional_paths/path_02", processorCamp), 1)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
        );
        context.register(HIGHFIELDS_TENTS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/tents/small_tent", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/tents/tent", processorCamp), 2)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(HIGHFIELDS_DECORATIONS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("camp/common/decorations/blueberry_farm", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/common/decorations/orange_farm", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/common/decorations/valkyrie_sprout_farm", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/decorations/well", processorCamp), 2),
                        Pair.of(StructurePoolElement.empty(), 2)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(HIGHFIELDS_STORAGE_AREAS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("camp/common/storage_areas/chest", processorCamp), 2),
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/storage_areas/logs", processorCamp), 2),
                        Pair.of(StructurePoolElement.empty(), 3)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(MAGNETIC_CENTER, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("camp/magnetic/center/small_center_01", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/magnetic/center/small_center_02", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/magnetic/center/medium_center_01", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/magnetic/center/medium_center_02", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/magnetic/center/large_center_01", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/magnetic/center/large_center_02", processorCamp), 1)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
        );
        context.register(MAGNETIC_ADDITIONAL_PATHS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("camp/magnetic/additional_paths/path_01", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/magnetic/additional_paths/path_02", processorCamp), 1)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
        );
        context.register(MAGNETIC_TENTS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("camp/magnetic/tents/small_tent", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/magnetic/tents/tent", processorCamp), 2)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(MAGNETIC_DECORATIONS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("camp/common/decorations/blueberry_farm", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/common/decorations/orange_farm", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/common/decorations/valkyrie_sprout_farm", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/magnetic/decorations/well", processorCamp), 2),
                        Pair.of(StructurePoolElement.empty(), 2)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(MAGNETIC_STORAGE_AREAS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("camp/common/storage_areas/chest", processorCamp), 2),
                        Pair.of(AetherIIPools.aetherPool("camp/magnetic/storage_areas/logs", processorCamp), 2),
                        Pair.of(StructurePoolElement.empty(), 3)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(ARCTIC_CENTER, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("camp/arctic/center/small_center_01", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/arctic/center/small_center_02", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/arctic/center/medium_center_01", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/arctic/center/medium_center_02", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/arctic/center/large_center_01", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/arctic/center/large_center_02", processorCamp), 1)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
        );
        context.register(ARCTIC_ADDITIONAL_PATHS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("camp/arctic/additional_paths/path_01", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/arctic/additional_paths/path_02", processorCamp), 1)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
        );
        context.register(ARCTIC_TENTS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("camp/arctic/tents/small_tent", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/arctic/tents/tent", processorCamp), 2)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(ARCTIC_DECORATIONS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("camp/common/decorations/blueberry_farm", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/common/decorations/orange_farm", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/common/decorations/valkyrie_sprout_farm", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/arctic/decorations/well", processorCamp), 2),
                        Pair.of(StructurePoolElement.empty(), 2)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(ARCTIC_STORAGE_AREAS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("camp/common/storage_areas/chest", processorCamp), 2),
                        Pair.of(AetherIIPools.aetherPool("camp/arctic/storage_areas/logs", processorCamp), 2),
                        Pair.of(StructurePoolElement.empty(), 3)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}