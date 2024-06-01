package com.aetherteam.aetherii.world.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

// TODO: Port Jigsaw System from 24w14potato Snapshot and replace this with it [Density Checks]

public class AetherJigsawStructure extends Structure {

    public static final Codec<AetherJigsawStructure> CODEC = RecordCodecBuilder.<AetherJigsawStructure>mapCodec(instance ->
            instance.group(AetherJigsawStructure.settingsCodec(instance),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                    Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
                    HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                    Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
                    Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter),
                    Codec.intRange(-4096, 4096).fieldOf("above_bottom").forGetter(structure -> structure.aboveBottom),
                    Codec.list(PoolAliasBinding.CODEC).optionalFieldOf("pool_aliases", List.of()).forGetter(structure -> structure.poolAliases)
            ).apply(instance, AetherJigsawStructure::new)).codec();
    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int size;
    private final HeightProvider startHeight;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;
    private final int aboveBottom;
    private final List<PoolAliasBinding> poolAliases;

    public AetherJigsawStructure(StructureSettings config,
                                   Holder<StructureTemplatePool> startPool,
                                   Optional<ResourceLocation> startJigsawName,
                                   int size,
                                   HeightProvider startHeight,
                                   Optional<Heightmap.Types> projectStartToHeightmap,
                                   int maxDistanceFromCenter,
                                   int aboveBottom,
                                   List<PoolAliasBinding> poolAliases) {
        super(config);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.size = size;
        this.startHeight = startHeight;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
        this.aboveBottom = aboveBottom;
        this.poolAliases = poolAliases;
    }

    private boolean extraSpawningChecks(GenerationContext context) {
        ChunkPos chunkpos = context.chunkPos();
        return context.chunkGenerator().getFirstOccupiedHeight(
                chunkpos.getWorldPosition().getX(),
                chunkpos.getWorldPosition().getZ(),
                Heightmap.Types.WORLD_SURFACE_WG,
                context.heightAccessor(),
                context.randomState())
                > aboveBottom;
    }

    @Override
    public @NotNull Optional<GenerationStub> findGenerationPoint(@NotNull GenerationContext context) {
        if (!extraSpawningChecks(context)) {
            return Optional.empty();
        }
        int startY = startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
        ChunkPos chunkPos = context.chunkPos();
        BlockPos pos = new BlockPos(chunkPos.getMiddleBlockX(), startY, chunkPos.getMiddleBlockZ());

        return JigsawPlacement.addPieces(
                context,
                startPool,
                startJigsawName,
                size,
                pos,
                false,
                projectStartToHeightmap,
                maxDistanceFromCenter,
                PoolAliasLookup.create(poolAliases, pos, context.seed())
        );
    }

    @Override
    public @NotNull StructureType<?> type() {
        return AetherIIStructureTypes.AETHER_JIGSAW.get();
    }
}