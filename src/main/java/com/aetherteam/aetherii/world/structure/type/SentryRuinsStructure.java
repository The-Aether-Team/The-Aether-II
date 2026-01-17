package com.aetherteam.aetherii.world.structure.type;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.world.BlockLogicUtil;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.mutable.MutableInt;

import javax.annotation.Nullable;
import java.util.Optional;

public class SentryRuinsStructure extends Structure {
    public static final MapCodec<SentryRuinsStructure> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            settingsCodec(builder),
            Codec.INT.fieldOf("max_rooms").forGetter(o -> o.maxRooms),
            Codec.INT.fieldOf("above_bottom").forGetter(o -> o.aboveBottom),
            Codec.INT.fieldOf("below_top").forGetter(o -> o.belowTop),
            SentryRuinsProcessorSettings.CODEC.fieldOf("processor_settings").forGetter(o -> o.processors)
    ).apply(builder, SentryRuinsStructure::new));

    private final int maxRooms;
    private final int aboveBottom;
    private final int belowTop;
    private final SentryRuinsProcessorSettings processors;

    public SentryRuinsStructure(StructureSettings settings, int maxRooms, int aboveBottom, int belowTop, SentryRuinsProcessorSettings processors) {
        super(settings);
        this.maxRooms = maxRooms;
        this.aboveBottom = aboveBottom;
        this.belowTop = belowTop;
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
        if (originInfo == null || originInfo.height() <= heightAccessor.getMinY() || originInfo.rotation() == null) {
            return Optional.empty();
        }
        BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), originInfo.height(), chunkPos.getMinBlockZ());
        AetherII.LOGGER.info(String.valueOf(blockPos));
        return Optional.of(new GenerationStub(blockPos, builder -> this.generatePieces(builder, context, blockPos, originInfo.rotation())));
    }

    private void generatePieces(StructurePiecesBuilder builder, GenerationContext context, BlockPos startPos, Rotation rotation) {
        SentryRuinsBuilder graph = new SentryRuinsBuilder(context, this.maxRooms, this.processors);
        graph.initializeDungeon(startPos, rotation, context, builder);
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
                    RuinsOriginInfo info = SentryRuinsStructure.findStartingOrigin(generator, heightAccessor, offset, randomState, random, templateManager, aboveBottom, belowTop);
                    if (info.height() > heightAccessor.getMinY()) {
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
        StructureTemplate template = templateManager.getOrCreate(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "sentry_ruins/boss_room"));

        int returnHeight = heightAccessor.getMinY();
        Rotation returnRotation = null;

        for (Rotation rotation : Rotation.getShuffled(random)) {
            Vec3i roomSize = template.getSize(rotation);
            AetherTemplateStructurePiece.TransformInfo transformInfo = AetherTemplateStructurePiece.getTransformInfo(template, rotation);

            int height = heightAccessor.getMinY() + aboveBottom;
            int maxHeight = heightAccessor.getMaxY() - belowTop;
            int goalThickness = roomSize.getY() + 2;
            int currentThickness = 0;

            BlockPos initialPos = new BlockPos(chunkPos.getMinBlockX(), 0, chunkPos.getMinBlockZ());
            BlockPos transformPos = StructureTemplate.transform(initialPos, transformInfo.mirror(), transformInfo.rotation(), transformInfo.pivot());

            int minX = transformPos.getX() - 1;
            int minZ = transformPos.getZ() - 1;
            int maxX = transformPos.getX() + roomSize.getX() + 1;
            int maxZ = transformPos.getZ() + roomSize.getZ() + 1;

            NoiseColumn[] columns = {
                    generator.getBaseColumn(minX, minZ, heightAccessor, randomState),
                    generator.getBaseColumn(minX, maxZ, heightAccessor, randomState),
                    generator.getBaseColumn(maxX, minZ, heightAccessor, randomState),
                    generator.getBaseColumn(maxX, maxZ, heightAccessor, randomState)
            };

            for (int y = height; y <= maxHeight; y++) {
                if (checkEachCornerAtY(columns, y)) {
                    currentThickness++;
                    if (currentThickness >= goalThickness) {
                        returnHeight = y - roomSize.getY() - 1; //todo maybe y - currentthickness idk
                        returnRotation = rotation;
                        break;
                    }
                } else {
                    currentThickness = 0;
                }
            }
            if (returnHeight > heightAccessor.getMinY() && returnRotation != null) {
//                AetherII.LOGGER.info(boundingBox + " " + returnHeight + " " + transformInfo);
                break;
            }
        }

        return new RuinsOriginInfo(returnHeight, returnRotation);
    }

    public record RuinsOriginInfo(int height, Rotation rotation) {

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
}
