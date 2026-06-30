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

public class IrradiatedRemnantsPools {
    public static final ResourceKey<StructureTemplatePool> IRRADIATED_BUNKER_REMNANTS = AetherIIPools.createKey("irradiated_remnants/bunker_remnants");
    public static final ResourceKey<StructureTemplatePool> BUNKERS = AetherIIPools.createKey("irradiated_remnants/bunkers");
    public static final ResourceKey<StructureTemplatePool> BUNKER_DECORATIONS = AetherIIPools.createKey("irradiated_remnants/bunker_decorations");
    public static final ResourceKey<StructureTemplatePool> IRRADIATED_SETTLEMENT_REMNANTS = AetherIIPools.createKey("irradiated_remnants/settlement_remnants");

    public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> fallback = templatePools.getOrThrow(Pools.EMPTY);

        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> processorBunker = processors.getOrThrow(AetherIIProcessorLists.IRRADIATED_BUNKER_EXTERIOR);

        context.register(IRRADIATED_BUNKER_REMNANTS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("irradiated_remnants/bunkers/bunker_start_01", processorBunker), 2),
                        Pair.of(AetherIIPools.aetherPool("irradiated_remnants/bunkers/bunker_start_02", processorBunker), 1)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
        );

        context.register(BUNKERS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPoolBuried("irradiated_remnants/bunkers/bunker_01"), 1),
                        Pair.of(AetherIIPools.aetherPoolBuried("irradiated_remnants/bunkers/research_bunker_01"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(BUNKER_DECORATIONS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("irradiated_remnants/bunkers/decorations/decoration_01", processorBunker), 2),
                        Pair.of(AetherIIPools.aetherPool("irradiated_remnants/bunkers/decorations/decoration_02", processorBunker), 2),
                        Pair.of(AetherIIPools.aetherPool("irradiated_remnants/bunkers/decorations/decoration_03", processorBunker), 2),
                        Pair.of(AetherIIPools.aetherPool("irradiated_remnants/bunkers/decorations/decoration_04", processorBunker), 2),
                        Pair.of(AetherIIPools.aetherPool("irradiated_remnants/bunkers/decorations/decoration_05", processorBunker), 2),
                        Pair.of(StructurePoolElement.empty(), 3)
                ),
                StructureTemplatePool.Projection.RIGID)
        );

        context.register(IRRADIATED_SETTLEMENT_REMNANTS, new StructureTemplatePool(
                fallback,
                ImmutableList.of(
                        Pair.of(AetherIIPools.aetherPool("irradiated_remnants/outpost"), 1),
                        Pair.of(AetherIIPools.aetherPool("irradiated_remnants/village"), 1)
                ),
                StructureTemplatePool.Projection.RIGID)
        );
    }
}