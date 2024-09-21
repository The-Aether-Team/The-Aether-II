package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.data.resources.builders.AetherIIStructureBuilders;
import com.aetherteam.aetherii.data.resources.registries.pools.CampHighfieldsPools;
import com.aetherteam.aetherii.data.resources.registries.pools.InfectedGuardianTreePools;
import com.aetherteam.aetherii.data.resources.registries.pools.OutpostPools;
import com.aetherteam.aetherii.world.structure.AetherJigsawStructure;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

import java.util.List;
import java.util.Optional;

public class AetherIIStructures {
    public static final ResourceKey<Structure> OUTPOST = createKey("outpost");
    public static final ResourceKey<Structure> CAMP_HIGHFIELDS = createKey("camp_highfields");
    public static final ResourceKey<Structure> INFECTED_GUARDIAN_TREE = createKey("infected_guardian_tree");

    private static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<Structure> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);

        context.register(OUTPOST, new AetherJigsawStructure(
                AetherIIStructureBuilders.structure(biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_OUTPOST), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN),
                templatePools.getOrThrow(OutpostPools.OUTPOST), Optional.empty(), 4, ConstantHeight.of(VerticalAnchor.absolute(0)), Optional.of(Heightmap.Types.WORLD_SURFACE_WG), 32, 128, 256, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));

        context.register(CAMP_HIGHFIELDS, new AetherJigsawStructure(
                AetherIIStructureBuilders.structure(biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_CAMP_HIGHFIELDS), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN),
                templatePools.getOrThrow(CampHighfieldsPools.CENTER), Optional.empty(), 4, ConstantHeight.of(VerticalAnchor.absolute(0)), Optional.of(Heightmap.Types.WORLD_SURFACE_WG), 32, 128, 256, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));

        context.register(INFECTED_GUARDIAN_TREE, new AetherJigsawStructure(
                AetherIIStructureBuilders.structure(biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_INFECTED_GUARDIAN_TREE), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE),
                templatePools.getOrThrow(InfectedGuardianTreePools.ENTRANCE), Optional.empty(), 12, ConstantHeight.of(VerticalAnchor.absolute(-16)), Optional.of(Heightmap.Types.WORLD_SURFACE_WG), 128, 128, 256, List.of(), DimensionPadding.ZERO, LiquidSettings.IGNORE_WATERLOGGING));
    }
}