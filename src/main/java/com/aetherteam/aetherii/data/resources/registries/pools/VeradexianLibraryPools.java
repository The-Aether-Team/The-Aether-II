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
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class VeradexianLibraryPools {
    public static final ResourceKey<StructureTemplatePool> ENTRANCE_TEMPERATE = AetherIIPools.createKey("veradexian_library/temperate/entrances");
    public static final ResourceKey<StructureTemplatePool> ENTRANCE_ARCTIC = AetherIIPools.createKey("veradexian_library/arctic/entrances");
    public static final ResourceKey<StructureTemplatePool> LIBRARY_TEMPERATE = AetherIIPools.createKey("veradexian_library/temperate/libraries");
    public static final ResourceKey<StructureTemplatePool> LIBRARY_ARCTIC = AetherIIPools.createKey("veradexian_library/arctic/libraries");
    public static final ResourceKey<StructureTemplatePool> SECRET_ROOM = AetherIIPools.createKey("veradexian_library/common/secret_rooms");

    public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processorRuinsTemperate = processors.getOrThrow(AetherIIProcessorLists.VERADEXIAN_RUINS_TEMPERATE);
        Holder<StructureProcessorList> processorRuinsArctic = processors.getOrThrow(AetherIIProcessorLists.VERADEXIAN_RUINS_ARCTIC);
        Holder<StructureProcessorList> processorLibraryTemperate = processors.getOrThrow(AetherIIProcessorLists.VERADEXIAN_LIBRARY_TEMPERATE);
        Holder<StructureProcessorList> processorLibraryArctic = processors.getOrThrow(AetherIIProcessorLists.VERADEXIAN_LIBRARY_ARCTIC);

        context.register(ENTRANCE_TEMPERATE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_library/temperate/entrance_01", processorRuinsTemperate), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_library/temperate/entrance_02", processorRuinsTemperate), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(ENTRANCE_ARCTIC, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_library/arctic/entrance_01", processorRuinsArctic), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_library/arctic/entrance_02", processorRuinsArctic), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LIBRARY_TEMPERATE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("veradexian_library/common/library_01", processorLibraryTemperate), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LIBRARY_ARCTIC, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("veradexian_library/common/library_01", processorLibraryArctic), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(SECRET_ROOM, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("veradexian_library/common/secret_room_01"), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("veradexian_library/common/secret_room_02"), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("veradexian_library/common/secret_room_03"), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("veradexian_library/common/secret_room_04"), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("veradexian_library/common/secret_room_05"), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("veradexian_library/common/secret_room_06"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}