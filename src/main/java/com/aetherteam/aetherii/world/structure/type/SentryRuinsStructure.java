package com.aetherteam.aetherii.world.structure.type;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.world.structure.piece.AetherTemplateStructurePiece;
import com.aetherteam.aetherii.world.structure.piece.sentry.SentryRuinsBuilder;
import com.aetherteam.aetherii.world.structure.piece.sentry.SentryRuinsProcessorSettings;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.Optional;

public class SentryRuinsStructure extends Structure {
    public static final MapCodec<SentryRuinsStructure> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            settingsCodec(builder),
            Codec.INT.fieldOf("max_rooms").forGetter(o -> o.maxRooms),
            Codec.INT.fieldOf("above_bottom").forGetter(o -> o.aboveBottom),
            Codec.INT.fieldOf("below_top").forGetter(o -> o.belowTop),
            Codec.INT.fieldOf("surface_ruin_offset").forGetter(o -> o.surfaceRuinOffset),
            SentryRuinsProcessorSettings.CODEC.fieldOf("processor_settings").forGetter(o -> o.processors)
    ).apply(builder, SentryRuinsStructure::new));

    private final int maxRooms;
    private final int aboveBottom;
    private final int belowTop;
    private final int surfaceRuinOffset;
    private final SentryRuinsProcessorSettings processors;

    public SentryRuinsStructure(StructureSettings settings, int maxRooms, int aboveBottom, int belowTop, int surfaceRuinOffset, SentryRuinsProcessorSettings processors) {
        super(settings);
        this.maxRooms = maxRooms;
        this.aboveBottom = aboveBottom;
        this.belowTop = belowTop;
        this.surfaceRuinOffset = surfaceRuinOffset;
        this.processors = processors;
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        ChunkGenerator chunkGenerator = context.chunkGenerator();
        LevelHeightAccessor heightAccessor = context.heightAccessor();
        RandomState randomState = context.randomState();
        WorldgenRandom random = context.random();
        StructureTemplateManager templateManager = context.structureTemplateManager();
        // To make structure placement more reliable, we check the surrounding 8 chunks for suitable locations.
        RuinsOriginInfo originInfo = searchNearbyChunks(chunkPos, chunkGenerator, heightAccessor, randomState, random, templateManager, this.aboveBottom, this.belowTop);
        if (originInfo == null || originInfo.pos() == null || originInfo.pos().getY() <= heightAccessor.getMinBuildHeight() || originInfo.rotation() == null) {
            return Optional.empty();
        }
        return Optional.of(new GenerationStub(originInfo.pos(), builder -> this.generatePieces(builder, context, originInfo.pos(), originInfo.rotation())));
    }

    private void generatePieces(StructurePiecesBuilder builder, GenerationContext context, BlockPos startPos, Rotation rotation) {
        SentryRuinsBuilder graph = new SentryRuinsBuilder(context, this.maxRooms, this.processors);
        graph.initializeDungeon(startPos, rotation, context, builder, surfaceRuinOffset);
    }

    /**
     * Check the surrounding chunks for bronze dungeon placement.
     *
     * @param chunkPos        The {@link ChunkPos}.
     * @param height          The {@link MutableInt} for the height to check.
     * @param generator       The {@link ChunkGenerator} for generation.
     * @param heightAccessor  The {@link LevelHeightAccessor} to place in.
     * @param randomState     The {@link RandomState} for the structure.
     * @param templateManager The {@link StructureTemplateManager}.
     * @return A {@link ChunkPos} for placement.
     */
    private static RuinsOriginInfo searchNearbyChunks(ChunkPos chunkPos, ChunkGenerator generator, LevelHeightAccessor heightAccessor, RandomState randomState, WorldgenRandom random, StructureTemplateManager templateManager, int aboveBottom, int belowTop) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (x != 0 || z != 0) {
                    ChunkPos offset = new ChunkPos(chunkPos.x + x, chunkPos.z + z);
                    RuinsOriginInfo info = findStartingOrigin(generator, heightAccessor, offset, randomState, random, templateManager, aboveBottom, belowTop);
                    if (info.pos() != null && info.pos().getY() > heightAccessor.getMinBuildHeight()) {
                        return info;
                    }
                }
            }
        }
        return null;
    }

    /**
     * The bronze dungeon needs to generate as covered by land as possible.
     * Try to find a place where the land is taller than the boss room.
     *
     * @param generator       The {@link ChunkGenerator} for generation.
     * @param heightAccessor  The {@link LevelHeightAccessor} to place in.
     * @param chunkPos        The {@link ChunkPos}.
     * @param random          The {@link RandomSource} for the structure.
     * @param templateManager The {@link StructureTemplateManager}.
     * @return The starting height as an {@link Integer}.
     */
    private static RuinsOriginInfo findStartingOrigin(ChunkGenerator generator, LevelHeightAccessor heightAccessor, ChunkPos chunkPos, RandomState randomState, WorldgenRandom random, StructureTemplateManager templateManager, int aboveBottom, int belowTop) {
        StructureTemplate bossTemplate = templateManager.getOrCreate(new ResourceLocation(AetherII.MODID, "sentry_ruins/boss_room"));
        StructureTemplate tunnelTemplate = templateManager.getOrCreate(new ResourceLocation(AetherII.MODID, "sentry_ruins/square_tunnel"));
        StructureTemplate loungeTemplate = templateManager.getOrCreate(new ResourceLocation(AetherII.MODID, "sentry_ruins/rooms/lounge"));

        Vec3i bossSize = bossTemplate.getSize();
        Vec3i tunnelSize = tunnelTemplate.getSize();
        Vec3i loungeSize = loungeTemplate.getSize();

        BlockPos returnPos = null;
        Rotation returnRotation = null;

        for (Rotation rotation : Rotation.getShuffled(random)) {
            Direction direction = rotation.rotate(Direction.SOUTH);

            BlockPos initialPos = new BlockPos(chunkPos.getMinBlockX(), 0, chunkPos.getMinBlockZ());

            AetherTemplateStructurePiece.TransformInfo transformInfo = AetherTemplateStructurePiece.getTransformInfo(bossTemplate, rotation);
            BoundingBox bossBox = bossTemplate.getBoundingBox(initialPos, transformInfo.rotation(), transformInfo.pivot(), transformInfo.mirror());

            int height = heightAccessor.getMinBuildHeight() + aboveBottom;
            int maxHeight = heightAccessor.getMaxBuildHeight() - belowTop;

            int bossCheckY = checkCorners(bossBox, generator, heightAccessor, randomState, height, maxHeight);
            if (bossCheckY > heightAccessor.getMinBuildHeight()) {
                returnPos = initialPos.atY(bossCheckY - bossBox.getYSpan() - 1);
                returnRotation = rotation;
            }

            if (returnPos != null && returnRotation != null) {
                int offsetDistance = (bossSize.getZ() / 2) + tunnelSize.getZ() + (loungeSize.getZ() / 2);
                BlockPos neighborOffset = bossBox.getCenter().atY(returnPos.getY()).offset(direction.getNormal().multiply(offsetDistance));
                BoundingBox loungeBox = loungeTemplate.getBoundingBox(neighborOffset, Rotation.NONE, BlockPos.ZERO, Mirror.NONE);

                int loungeCheckY = checkCorners(loungeBox, generator, heightAccessor, randomState, returnPos.getY(), returnPos.getY() + loungeBox.getYSpan() + 2);
                if (loungeCheckY <= heightAccessor.getMinBuildHeight()) {
                    returnPos = null;
                    returnRotation = null;
                    continue;
                }

                break;
            }
        }

        return new RuinsOriginInfo(returnPos, returnRotation);
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
        return heightAccessor.getMinBuildHeight();
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
            if (column.getBlock(y).isAir() || column.getBlock(y).is(AetherIITags.Blocks.NON_SENTRY_RUINS_SPAWNABLE)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Override to prevent beardifier bounding box adjustment.
     *
     * @param box The original {@link BoundingBox}.
     * @return The new {@link BoundingBox}.
     */
    @Override
    public BoundingBox adjustBoundingBox(BoundingBox box) {
        return box;
    }

    @Override
    public StructureType<?> type() {
        return AetherIIStructureTypes.SENTRY_RUINS.get();
    }

    public record RuinsOriginInfo(BlockPos pos, Rotation rotation) {

    }
}
