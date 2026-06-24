package com.aetherteam.aetherii.world.structure.type;

import com.aetherteam.aetherii.AetherIITags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class AetherJigsawStructure extends Structure {

    public static final MapCodec<AetherJigsawStructure> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(AetherJigsawStructure.settingsCodec(instance),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                    Identifier.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                    Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
                    HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                    Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
                    JigsawStructure.MaxDistance.CODEC.fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter),
                    Codec.intRange(-4096, 4096).fieldOf("discard_below_y").forGetter(structure -> structure.discardBelowY),
                    Codec.intRange(-4096, 4096).fieldOf("discard_above_y").forGetter(structure -> structure.discardAboveY),
                    Codec.BOOL.fieldOf("buried").forGetter(structure -> structure.buried),
                    Codec.list(PoolAliasBinding.CODEC).optionalFieldOf("pool_aliases", List.of()).forGetter(structure -> structure.poolAliases),
                    DimensionPadding.CODEC.optionalFieldOf("dimension_padding", DimensionPadding.ZERO).forGetter(structure -> structure.dimensionPadding),
                    LiquidSettings.CODEC.optionalFieldOf("liquid_settings", LiquidSettings.APPLY_WATERLOGGING).forGetter(structure -> structure.liquidSettings)
            ).apply(instance, AetherJigsawStructure::new));
    public final Holder<StructureTemplatePool> startPool;
    private final Optional<Identifier> startJigsawName;
    private final int size;
    private final HeightProvider startHeight;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final JigsawStructure.MaxDistance maxDistanceFromCenter;
    private final int discardBelowY;
    private final int discardAboveY;
    private final boolean buried;
    private final List<PoolAliasBinding> poolAliases;
    private final DimensionPadding dimensionPadding;
    private final LiquidSettings liquidSettings;
    public int startY;

    public AetherJigsawStructure(StructureSettings config,
                                 Holder<StructureTemplatePool> startPool,
                                 Optional<Identifier> startJigsawName,
                                 int size, HeightProvider startHeight,
                                 Optional<Heightmap.Types> projectStartToHeightmap,
                                 JigsawStructure.MaxDistance maxDistanceFromCenter,
                                 int discardBelowY,
                                 int discardAboveY,
                                 boolean buried,
                                 List<PoolAliasBinding> poolAliases,
                                 DimensionPadding dimensionPadding,
                                 LiquidSettings liquidSettings) {
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
        StructureTemplateManager templateManager = context.structureTemplateManager();
        this.startY = this.startHeight.sample(context.random(), new WorldGenerationContext(generator, heightAccessor));
        ChunkPos chunkPos = context.chunkPos();
        BlockPos pos = new BlockPos(chunkPos.getMiddleBlockX(), this.startY, chunkPos.getMiddleBlockZ());

        WorldgenRandom worldGenRandom = context.random();
        Rotation rotation = Rotation.getRandom(worldGenRandom);
        BoundingBox startPoolBounds = this.startPool.value().getRandomTemplate(context.random()).getBoundingBox(templateManager, pos, rotation);


        if (!this.buried) {
            if ((this.checkHeight(context, startPoolBounds.getCenter().getX(), startPoolBounds.getCenter().getZ(), this.discardBelowY, this.discardAboveY)
                    && this.checkHeight(context, startPoolBounds.minX(), startPoolBounds.minZ(), this.discardBelowY, this.discardAboveY)
                    && this.checkHeight(context, startPoolBounds.minX(), startPoolBounds.maxZ(), this.discardBelowY, this.discardAboveY)
                    && this.checkHeight(context, startPoolBounds.maxX(), startPoolBounds.minZ(), this.discardBelowY, this.discardAboveY)
                    && this.checkHeight(context, startPoolBounds.maxX(), startPoolBounds.maxZ(), this.discardBelowY, this.discardAboveY))
            ) {
                return JigsawPlacement.addPieces(
                        context,
                        this.startPool,
                        this.startJigsawName,
                        this.size,
                        pos,
                        false,
                        this.projectStartToHeightmap,
                        this.maxDistanceFromCenter,
                        PoolAliasLookup.create(this.poolAliases, pos, context.seed()),
                        this.dimensionPadding,
                        this.liquidSettings
                );
            }
        }
        if (this.buried) {
            if (checkCorners(startPoolBounds, generator, heightAccessor, context.randomState(), this.discardBelowY, this.discardAboveY) > this.discardBelowY
                    && checkCorners(startPoolBounds, generator, heightAccessor, context.randomState(), this.discardBelowY, this.discardAboveY) < this.discardAboveY) {
                return JigsawPlacement.addPieces(
                        context,
                        this.startPool,
                        this.startJigsawName,
                        this.size,
                        pos,
                        false,
                        this.projectStartToHeightmap,
                        this.maxDistanceFromCenter,
                        PoolAliasLookup.create(this.poolAliases, pos, context.seed()),
                        this.dimensionPadding,
                        this.liquidSettings
                );
            }
        }
        return Optional.empty();
    }

    public boolean checkHeight(Structure.GenerationContext context, int x, int z, int minY, int maxY) {
        int posTest = context.chunkGenerator().getFirstOccupiedHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
        return posTest > minY && posTest < maxY;
    }

    public static int checkCorners(BoundingBox boundingBox, ChunkGenerator generator, LevelHeightAccessor heightAccessor, RandomState randomState, int minHeight, int maxHeight) {
        int minX = boundingBox.minX() - 1;
        int minZ = boundingBox.minZ() - 1;
        int maxX = boundingBox.maxX() + 1;
        int maxZ = boundingBox.maxZ() + 1;

        int goalThickness = boundingBox.getYSpan() + 2;
        int currentThickness = 0;

        NoiseColumn[] columns = {
                generator.getBaseColumn(minX, minZ, heightAccessor, randomState),
                generator.getBaseColumn(minX, maxZ, heightAccessor, randomState),
                generator.getBaseColumn(maxX, minZ, heightAccessor, randomState),
                generator.getBaseColumn(maxX, maxZ, heightAccessor, randomState)
        };

        for (int y = maxHeight; y >= minHeight; y--) {
            if (checkEachCornerAtY(columns, y)) {
                currentThickness++;
                if (currentThickness >= goalThickness) {
                    return y;
                }
            } else {
                currentThickness = 0;
            }
        }
        return heightAccessor.getMinY();
    }

    /**
     * Checks for no air in a column.
     *
     * @param columns The {@link NoiseColumn NoiseColumn[]} array to check.
     * @param y       The given y-level {@link Integer} to check.
     * @return A {@link Boolean} if there was no air found at the given y-level. Returns false if there was air found.
     */
    private static boolean checkEachCornerAtY(NoiseColumn[] columns, int y) {
        for (NoiseColumn column : columns) {
            if (column.getBlock(y).isAir()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public @NotNull StructureType<?> type() {
        return AetherIIStructureTypes.AETHER_JIGSAW.get();
    }
}