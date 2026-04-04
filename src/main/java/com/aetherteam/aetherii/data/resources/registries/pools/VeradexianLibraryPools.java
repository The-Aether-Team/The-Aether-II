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
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class VeradexianLibraryPools {
    public static final ResourceKey<StructureTemplatePool> ENTRANCE_TEMPERATE = AetherIIPools.createKey("veradexian_library/temperate/entrances");
    public static final ResourceKey<StructureTemplatePool> LIBRARY = AetherIIPools.createKey("veradexian_library/common/libraries");
    public static final ResourceKey<StructureTemplatePool> LIBRARY_VAULT = AetherIIPools.createKey("veradexian_library/common/library_vaults");
    public static final ResourceKey<StructureTemplatePool> SECRET_ROOM = AetherIIPools.createKey("veradexian_library/common/secret_rooms");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processorRuins = processors.getOrThrow(AetherIIProcessorLists.VERADEXIAN_RUINS);
        Holder<StructureProcessorList> processorLibrary = processors.getOrThrow(AetherIIProcessorLists.VERADEXIAN_LIBRARY);

        context.register(ENTRANCE_TEMPERATE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_library/temperate/entrance_01", processorRuins), 1),
                        Pair.of(AetherIIPools.aetherPool("veradexian_library/temperate/entrance_02", processorRuins), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LIBRARY, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("veradexian_library/common/library_01", processorLibrary), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(LIBRARY_VAULT, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("veradexian_library/common/library_vault_01", processorLibrary), 1)
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