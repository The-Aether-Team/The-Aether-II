package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.data.resources.builders.worldgen.AetherIIStructureBuilders;
import com.aetherteam.aetherii.data.resources.registries.pools.*;
import com.aetherteam.aetherii.world.structure.type.AetherJigsawStructure;
import com.aetherteam.aetherii.world.structure.piece.sentry.SentryRuinsProcessorSettings;
import com.aetherteam.aetherii.world.structure.type.SentryRuinsStructure;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;
import java.util.Optional;

public class AetherIIStructures {
    public static final ResourceKey<Structure> OUTPOST = createKey("outpost");
    public static final ResourceKey<Structure> CAMP_HIGHFIELDS = createKey("camp_highfields");
    public static final ResourceKey<Structure> CAMP_MAGNETIC = createKey("camp_magnetic");
    public static final ResourceKey<Structure> CAMP_ARCTIC = createKey("camp_arctic");
    public static final ResourceKey<Structure> WATCHTOWER = createKey("watchtower");
    public static final ResourceKey<Structure> ANIMAL_DEN = createKey("animal_den");
    public static final ResourceKey<Structure> VERADEXIAN_RUINS_TEMPERATE = createKey("veradexian_ruins_temperate");
    public static final ResourceKey<Structure> VERADEXIAN_RUINS_ARCTIC = createKey("veradexian_ruins_arctic");
    public static final ResourceKey<Structure> VERADEXIAN_LIBRARY_TEMPERATE = createKey("veradexian_library_temperate");
    public static final ResourceKey<Structure> VERADEXIAN_LIBRARY_ARCTIC = createKey("veradexian_library_arctic");
    public static final ResourceKey<Structure> VERADEXIAN_AQUEDUCT = createKey("veradexian_aqueduct");
    public static final ResourceKey<Structure> BREXALLEN_RUINS = createKey("brexallen_ruins");
    public static final ResourceKey<Structure> UNDERCLOUD_MINESHAFT = createKey("undercloud_mineshaft");
    public static final ResourceKey<Structure> ANCIENT_HENGE = createKey("ancient_henge");
    public static final ResourceKey<Structure> IRRADIATED_BUNKER_REMNANTS = createKey("irradiated_bunker_remnants");
    public static final ResourceKey<Structure> IRRADIATED_SETTLEMENT_REMNANTS = createKey("irradiated_settlement_remnants");
    public static final ResourceKey<Structure> SENTRY_RUINS = createKey("sentry_ruins");
    public static final ResourceKey<Structure> INFECTED_GUARDIAN_TREE = createKey("infected_guardian_tree");

    private static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<Structure> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);

        context.register(OUTPOST, new AetherJigsawStructure(
                AetherIIStructureBuilders.structure(biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_OUTPOST), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN),
                templatePools.getOrThrow(OutpostPools.OUTPOST), Optional.empty(), 3, ConstantHeight.of(VerticalAnchor.absolute(-1)), Optional.of(Heightmap.Types.WORLD_SURFACE_WG), new JigsawStructure.MaxDistance(32), 128, 256, false, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));

        context.register(CAMP_HIGHFIELDS, new AetherJigsawStructure(
                AetherIIStructureBuilders.structure(biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_CAMP_HIGHFIELDS), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN),
                templatePools.getOrThrow(CampPools.HIGHFIELDS_CENTER), Optional.empty(), 20, ConstantHeight.of(VerticalAnchor.absolute(0)), Optional.of(Heightmap.Types.WORLD_SURFACE_WG), new JigsawStructure.MaxDistance(32), 128, 256, false, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));
        context.register(CAMP_MAGNETIC, new AetherJigsawStructure(
                AetherIIStructureBuilders.structure(biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_CAMP_MAGNETIC), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN),
                templatePools.getOrThrow(CampPools.MAGNETIC_CENTER), Optional.empty(), 20, ConstantHeight.of(VerticalAnchor.absolute(0)), Optional.of(Heightmap.Types.WORLD_SURFACE_WG), new JigsawStructure.MaxDistance(32), 128, 256, false, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));
        context.register(CAMP_ARCTIC, new AetherJigsawStructure(
                AetherIIStructureBuilders.structure(biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_CAMP_ARCTIC), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN),
                templatePools.getOrThrow(CampPools.ARCTIC_CENTER), Optional.empty(), 20, ConstantHeight.of(VerticalAnchor.absolute(0)), Optional.of(Heightmap.Types.WORLD_SURFACE_WG), new JigsawStructure.MaxDistance(32), 128, 256, false, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));

        context.register(WATCHTOWER, new AetherJigsawStructure(
                AetherIIStructureBuilders.structure(biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_WATCHTOWER), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN),
                templatePools.getOrThrow(WatchtowerPools.WATCHTOWER), Optional.empty(), 3, ConstantHeight.of(VerticalAnchor.absolute(0)), Optional.of(Heightmap.Types.WORLD_SURFACE_WG), new JigsawStructure.MaxDistance(32), 128, 256, false, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));

        context.register(ANIMAL_DEN, new AetherJigsawStructure(
                AetherIIStructureBuilders.structure(biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_ANIMAL_DEN), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE),
                templatePools.getOrThrow(AnimalDenPools.ANIMAL_DEN), Optional.empty(), 3, ConstantHeight.of(VerticalAnchor.absolute(-2)), Optional.of(Heightmap.Types.WORLD_SURFACE_WG), new JigsawStructure.MaxDistance(10), 128, 256, false, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));

        context.register(VERADEXIAN_RUINS_TEMPERATE, new AetherJigsawStructure(
                AetherIIStructureBuilders.structure(biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_VERADEXIAN_RUINS_TEMPERATE), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN),
                templatePools.getOrThrow(VeradexianRuinPools.RUIN_CENTERS_TEMPERATE), Optional.empty(), 3, ConstantHeight.of(VerticalAnchor.absolute(0)), Optional.of(Heightmap.Types.WORLD_SURFACE_WG), new JigsawStructure.MaxDistance(32), 128, 256, false, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));
        context.register(VERADEXIAN_RUINS_ARCTIC, new AetherJigsawStructure(
                AetherIIStructureBuilders.structure(biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_VERADEXIAN_RUINS_ARCTIC), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN),
                templatePools.getOrThrow(VeradexianRuinPools.RUIN_CENTERS_ARCTIC), Optional.empty(), 3, ConstantHeight.of(VerticalAnchor.absolute(0)), Optional.of(Heightmap.Types.WORLD_SURFACE_WG), new JigsawStructure.MaxDistance(32), 128, 256, false, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));
        context.register(VERADEXIAN_LIBRARY_TEMPERATE, new AetherJigsawStructure(
                AetherIIStructureBuilders.structure(biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_VERADEXIAN_LIBRARY_TEMPERATE), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE),
                templatePools.getOrThrow(VeradexianLibraryPools.ENTRANCE_PEDESTAL_TEMPERATE), Optional.empty(), 10, ConstantHeight.of(VerticalAnchor.absolute(0)), Optional.of(Heightmap.Types.WORLD_SURFACE_WG), new JigsawStructure.MaxDistance(64), 128, 256, false, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));
        context.register(VERADEXIAN_LIBRARY_ARCTIC, new AetherJigsawStructure(
                AetherIIStructureBuilders.structure(biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_VERADEXIAN_LIBRARY_ARCTIC), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE),
                templatePools.getOrThrow(VeradexianLibraryPools.ENTRANCE_PEDESTAL_ARCTIC), Optional.empty(), 10, ConstantHeight.of(VerticalAnchor.absolute(0)), Optional.of(Heightmap.Types.WORLD_SURFACE_WG), new JigsawStructure.MaxDistance(64), 128, 320, false, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));
        context.register(VERADEXIAN_AQUEDUCT, new AetherJigsawStructure(
                AetherIIStructureBuilders.structure(biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_VERADEXIAN_AQUEDUCT), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE),
                templatePools.getOrThrow(VeradexianAqueductPools.AQUEDUCT_START), Optional.empty(), 5, ConstantHeight.of(VerticalAnchor.absolute(111)), Optional.empty(), new JigsawStructure.MaxDistance(80), 96, 256, false, List.of(), DimensionPadding.ZERO, LiquidSettings.APPLY_WATERLOGGING));

        context.register(BREXALLEN_RUINS, new AetherJigsawStructure(
                AetherIIStructureBuilders.structure(biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_BREXALLEN_RUINS), GenerationStep.Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.BEARD_THIN),
                templatePools.getOrThrow(BrexallenRuinPools.RUIN_CENTERS), Optional.empty(), 3, UniformHeight.of(VerticalAnchor.absolute(32), VerticalAnchor.absolute(80)), Optional.empty(), new JigsawStructure.MaxDistance(32), 24, 112, false, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));

        context.register(UNDERCLOUD_MINESHAFT, new AetherJigsawStructure(
                AetherIIStructureBuilders.structure(biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_UNDERCLOUD_MINESHAFT), GenerationStep.Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.NONE),
                templatePools.getOrThrow(UndercloudMineshaftPools.HUB), Optional.empty(), 12, UniformHeight.of(VerticalAnchor.absolute(32), VerticalAnchor.absolute(80)), Optional.empty(), new JigsawStructure.MaxDistance(64, 32), 24, 96, true, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));

        context.register(ANCIENT_HENGE, new AetherJigsawStructure(
                AetherIIStructureBuilders.structure(biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_ANCIENT_HENGE), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN),
                templatePools.getOrThrow(AncientHengePools.CENTER), Optional.empty(), 3, ConstantHeight.of(VerticalAnchor.absolute(0)), Optional.of(Heightmap.Types.WORLD_SURFACE_WG), new JigsawStructure.MaxDistance(32), 128, 256, false, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));

        context.register(IRRADIATED_BUNKER_REMNANTS, new AetherJigsawStructure(
                AetherIIStructureBuilders.structure(biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_IRRADIATED_REMNANTS), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN),
                templatePools.getOrThrow(IrradiatedRemnantsPools.IRRADIATED_BUNKER_REMNANTS), Optional.empty(), 3, ConstantHeight.of(VerticalAnchor.absolute(0)), Optional.of(Heightmap.Types.WORLD_SURFACE_WG), new JigsawStructure.MaxDistance(32), 128, 256, false, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));
        context.register(IRRADIATED_SETTLEMENT_REMNANTS, new AetherJigsawStructure(
                AetherIIStructureBuilders.structure(biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_IRRADIATED_REMNANTS), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN),
                templatePools.getOrThrow(IrradiatedRemnantsPools.IRRADIATED_SETTLEMENT_REMNANTS), Optional.empty(), 3, ConstantHeight.of(VerticalAnchor.absolute(0)), Optional.of(Heightmap.Types.WORLD_SURFACE_WG), new JigsawStructure.MaxDistance(32), 128, 256, false, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));

        context.register(SENTRY_RUINS, new SentryRuinsStructure(AetherIIStructureBuilders.structure(
                biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_SENTRY_RUINS),
                GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
                TerrainAdjustment.NONE),
                6, 64, 24, -6,
                new SentryRuinsProcessorSettings(
                        processors.getOrThrow(AetherIIProcessorLists.SENTRY_RUINS_ROOM),
                        processors.getOrThrow(AetherIIProcessorLists.SENTRY_RUINS_STAIRCASE),
                        processors.getOrThrow(AetherIIProcessorLists.SENTRY_RUINS_BOSS_ROOM))));

        context.register(INFECTED_GUARDIAN_TREE, new AetherJigsawStructure(
                AetherIIStructureBuilders.structure(biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_INFECTED_GUARDIAN_TREE), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE),
                templatePools.getOrThrow(InfectedGuardianTreePools.ENTRANCE), Optional.empty(), 13, ConstantHeight.of(VerticalAnchor.absolute(-3)), Optional.of(Heightmap.Types.WORLD_SURFACE_WG), new JigsawStructure.MaxDistance(112, 156), 0, 384, false, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING)); //placeholder values
    }
}