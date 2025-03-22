package com.aetherteam.aetherii.world.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class AetherJigsawStructure extends Structure {

    public static final MapCodec<AetherJigsawStructure> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(AetherJigsawStructure.settingsCodec(instance),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                    Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
                    HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                    Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
                    Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter),
                    Codec.intRange(-4096, 4096).fieldOf("discard_below_y").forGetter(structure -> structure.discardBelowY),
                    Codec.intRange(-4096, 4096).fieldOf("discard_above_y").forGetter(structure -> structure.discardAboveY),
                    Codec.BOOL.fieldOf("buried").forGetter(structure -> structure.buried),
                    Codec.list(PoolAliasBinding.CODEC).optionalFieldOf("pool_aliases", List.of()).forGetter(structure -> structure.poolAliases),
                    DimensionPadding.CODEC.optionalFieldOf("dimension_padding", DimensionPadding.ZERO).forGetter(structure -> structure.dimensionPadding),
                    LiquidSettings.CODEC.optionalFieldOf("liquid_settings", LiquidSettings.APPLY_WATERLOGGING).forGetter(structure -> structure.liquidSettings)
            ).apply(instance, AetherJigsawStructure::new));
    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int size;
    private final HeightProvider startHeight;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;
    private final int discardBelowY;
    private final int discardAboveY;
    private final boolean buried;
    private final List<PoolAliasBinding> poolAliases;
    private final DimensionPadding dimensionPadding;
    private final LiquidSettings liquidSettings;

    public AetherJigsawStructure(StructureSettings config, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int size, HeightProvider startHeight, Optional<Heightmap.Types> projectStartToHeightmap, int maxDistanceFromCenter, int discardBelowY, int discardAboveY, boolean buried, List<PoolAliasBinding> poolAliases, DimensionPadding dimensionPadding, LiquidSettings liquidSettings) {
        super(config);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.size = size;
        this.startHeight = startHeight;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
        this.discardBelowY = discardBelowY;
        this.discardAboveY = discardAboveY;
        this.buried = buried;
        this.poolAliases = poolAliases;
        this.dimensionPadding = dimensionPadding;
        this.liquidSettings = liquidSettings;
    }

    @Override
    public @NotNull Optional<GenerationStub> findGenerationPoint(@NotNull GenerationContext context) {
        ChunkGenerator generator = context.chunkGenerator();
        LevelHeightAccessor heightAccessor = context.heightAccessor();
        int startY = startHeight.sample(context.random(), new WorldGenerationContext(generator, heightAccessor));
        ChunkPos chunkPos = context.chunkPos();
        BlockPos pos = new BlockPos(chunkPos.getMiddleBlockX(), startY, chunkPos.getMiddleBlockZ());
        if (!this.checkHeight(context, discardBelowY, discardAboveY) || (checkCorners(generator, heightAccessor, chunkPos, context.randomState(), startY) && buried)) {
            return Optional.empty();
        }
        return JigsawPlacement.addPieces(
                context,
                startPool,
                startJigsawName,
                size,
                pos,
                false,
                projectStartToHeightmap,
                maxDistanceFromCenter,
                PoolAliasLookup.create(poolAliases, pos, context.seed()),
                this.dimensionPadding,
                this.liquidSettings
        );
    }

    public boolean checkHeight(Structure.GenerationContext context, int minY, int maxY) {
        ChunkPos chunkpos = context.chunkPos();
        int posTest = context.chunkGenerator().getFirstOccupiedHeight(chunkpos.getWorldPosition().getX(), chunkpos.getWorldPosition().getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
        return posTest > minY && posTest < maxY;
    }

    private static boolean checkCorners(ChunkGenerator generator, LevelHeightAccessor heightAccessor, ChunkPos chunkPos, RandomState random, int startY) {
        int minX = chunkPos.getMinBlockX() - 8;
        int minZ = chunkPos.getMinBlockZ() - 8;
        int maxX = chunkPos.getMaxBlockX() + 8;
        int maxZ = chunkPos.getMaxBlockZ() + 8;
        NoiseColumn[] columns = {
                generator.getBaseColumn(minX, minZ, heightAccessor, random),
                generator.getBaseColumn(minX, maxZ, heightAccessor, random),
                generator.getBaseColumn(maxX, minZ, heightAccessor, random),
                generator.getBaseColumn(maxX, maxZ, heightAccessor, random)
        };
        for (NoiseColumn column : columns) {
            if (!column.getBlock(startY - 20).isAir()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull StructureType<?> type() {
        return AetherIIStructureTypes.AETHER_JIGSAW.get();
    }
}