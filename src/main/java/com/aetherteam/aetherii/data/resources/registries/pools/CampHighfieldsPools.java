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
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class CampHighfieldsPools {
    public static final ResourceKey<StructureTemplatePool> CENTER = AetherIIPools.createKey("camp/highfields/center");
    public static final ResourceKey<StructureTemplatePool> ADDITIONAL_PATHS = AetherIIPools.createKey("camp/highfields/additional_paths");
    public static final ResourceKey<StructureTemplatePool> TENTS = AetherIIPools.createKey("camp/highfields/tents");
    public static final ResourceKey<StructureTemplatePool> DECORATIONS = AetherIIPools.createKey("camp/highfields/decorations");
    public static final ResourceKey<StructureTemplatePool> STORAGE_AREAS = AetherIIPools.createKey("camp/highfields/storage_areas");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processorCamp = processors.getOrThrow(AetherIIProcessorLists.CAMP_HIGHFIELDS);

        context.register(CENTER, new StructureTemplatePool(
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

        context.register(ADDITIONAL_PATHS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/additional_paths/path_01", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/additional_paths/path_02", processorCamp), 1)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
        );

        context.register(TENTS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/tents/small_tent", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/tents/tent", processorCamp), 2)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(DECORATIONS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/decorations/blueberry_farm", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/decorations/orange_farm", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/decorations/valkyrie_sprout_farm", processorCamp), 1),
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/decorations/well", processorCamp), 2),
                        Pair.of(StructurePoolElement.empty(), 2)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(STORAGE_AREAS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/storage_areas/storage_area_01", processorCamp), 2),
                        Pair.of(AetherIIPools.aetherPool("camp/highfields/storage_areas/storage_area_02", processorCamp), 2),
                        Pair.of(StructurePoolElement.empty(), 3)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}