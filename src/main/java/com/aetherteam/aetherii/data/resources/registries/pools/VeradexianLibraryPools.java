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

public class VeradexianLibraryPools {
    public static final ResourceKey<StructureTemplatePool> ENTRANCE_PEDESTAL_TEMPERATE = AetherIIPools.createKey("veradexian_library/temperate/entrance_pedestal");
    public static final ResourceKey<StructureTemplatePool> ENTRANCE_PEDESTAL_ARCTIC = AetherIIPools.createKey("veradexian_library/arctic/entrance_pedestal");
    public static final ResourceKey<StructureTemplatePool> BASE_TEMPERATE = AetherIIPools.createKey("veradexian_library/temperate/base");
    public static final ResourceKey<StructureTemplatePool> BASE_ARCTIC = AetherIIPools.createKey("veradexian_library/arctic/base");
    public static final ResourceKey<StructureTemplatePool> TEMPLE_TEMPERATE = AetherIIPools.createKey("veradexian_library/temperate/temple");
    public static final ResourceKey<StructureTemplatePool> TEMPLE_ARCTIC = AetherIIPools.createKey("veradexian_library/arctic/temple");
    public static final ResourceKey<StructureTemplatePool> FLOOR_1_LIBRARY = AetherIIPools.createKey("veradexian_library/floor_1_library");
    public static final ResourceKey<StructureTemplatePool> FLOOR_2_LIBRARY = AetherIIPools.createKey("veradexian_library/floor_2_library");
    public static final ResourceKey<StructureTemplatePool> LIBRARY_VAULTS = AetherIIPools.createKey("veradexian_library/library_vaults");

    public static final ResourceKey<StructureTemplatePool> BRYALINN_MOSS_COVER = AetherIIPools.createKey("veradexian_library/decoration/bryalinn_moss_cover");
    public static final ResourceKey<StructureTemplatePool> AETHER_BUSH_PILE = AetherIIPools.createKey("veradexian_library/decoration/aether_bush_pile");
    public static final ResourceKey<StructureTemplatePool> COCKATRICE = AetherIIPools.createKey("veradexian_library/decoration/cockatrice");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processorLibrary = processors.getOrThrow(AetherIIProcessorLists.VERADEXIAN_LIBRARY_TEMPERATE);

        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        context.register(ENTRANCE_PEDESTAL_TEMPERATE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_library/temperate/entrance_pedestal"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(BASE_TEMPERATE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_library/temperate/base"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(TEMPLE_TEMPERATE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("veradexian_library/temperate/temple", processorLibrary), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(BRYALINN_MOSS_COVER, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HolyIslesPlacedFeatures.BRYALINN_MOSS_COVER_STRUCTURE)), 1),
                        Pair.of(StructurePoolElement.empty(), 6)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
        context.register(AETHER_BUSH_PILE, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.feature(placedFeatures.getOrThrow(HolyIslesPlacedFeatures.PILE_AETHER_BUSH)), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}