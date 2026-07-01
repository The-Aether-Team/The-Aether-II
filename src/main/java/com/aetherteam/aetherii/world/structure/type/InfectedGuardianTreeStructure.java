package com.aetherteam.aetherii.world.structure.type;

import com.aetherteam.aetherii.AetherII;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Util;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.joml.Vector2i;

import java.util.*;

public class InfectedGuardianTreeStructure extends Structure {
    public static final MapCodec<InfectedGuardianTreeStructure> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            settingsCodec(builder)
    ).apply(builder, InfectedGuardianTreeStructure::new));

    private static final Vec3i ROOM_BOUNDS = new Vec3i(23, 24, 23);
    private static final Vec3i CORRIDOR_BOUNDS = new Vec3i(23, 24, 11);

    public InfectedGuardianTreeStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        WorldgenRandom random = context.random();
        BlockPos originPos = chunkPos.getWorldPosition();

        FloorGrid floor1 = new FloorGrid(2);
        floor1.planLayout(7, 3, random);
        floor1.printGrid();

        FloorGrid floor2 = new FloorGrid(2);
        floor2.planLayout(9, 6, random);
        floor2.printGrid();

        FloorGrid floor3 = new FloorGrid(2);
        floor3.planLayout(12, 8, random);
        floor3.printGrid();

        return Optional.empty();
//        return Optional.of(new GenerationStub(originPos, builder -> this.generate(context, builder)));
    }

    public void generate(GenerationContext context, StructurePiecesBuilder builder) {
        ChunkPos chunkPos = context.chunkPos();
        ChunkGenerator chunkGenerator = context.chunkGenerator();
        LevelHeightAccessor heightAccessor = context.heightAccessor();
        RandomState randomState = context.randomState();
        WorldgenRandom random = context.random();
        StructureTemplateManager templateManager = context.structureTemplateManager();

        HolderGetter<StructureProcessorList> processors = context.registryAccess().lookupOrThrow(Registries.PROCESSOR_LIST);

        BlockPos initialPos = chunkPos.getBlockAt(0, 100, 0).mutable();



//        for (int x = -2; x <= 2; x++) {
//            for (int z = 0; z < 5; z++) {
//                InfectedGuardianTreePiece piece = new InfectedGuardianTreeRoom(templateManager, "room_boundary", initialPos.offset(ROOM_BOUNDS.multiply(x, 1, z)), Rotation.NONE, processors.getOrThrow(AetherIIProcessorLists.SENTRY_RUINS_ROOM));
//
//
//                builder.addPiece(piece);
//            }
//        }
    }

    @Override
    public StructureType<?> type() {
        return AetherIIStructureTypes.INFECTED_GUARDIAN_TREE.get();
    }

    public static class ConnectionSide { //todo this may not be necessary tbh
        ///  D
        /// ABC
        ///  E

        public boolean a;
        public boolean b;
        public boolean c;
        public boolean d;
        public boolean e;

        public ConnectionSide() {

        }
    }

    public static class RoomCell {
        public final Map<Direction, Boolean> connections = new HashMap<>();
        public Type type;

        public RoomCell(Type type) {
            this.type = type;
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                connections.put(direction, false);
            }
        }

        public void setType(Type type) {
            this.type = type;
        }

        public enum Type {
            REGULAR,
            CHALLENGE,
            LOBBY
        }
    }

    public static class FloorGrid {
        public final int radius;
        public final RoomCell[][] cells;

        public FloorGrid(int radius) {
            this.radius = radius;
            this.cells = new RoomCell[this.getDiameter()][this.getDiameter()];
            this.setCell(this.getCenter(), new RoomCell(RoomCell.Type.LOBBY));
        }

        public void planLayout(int totalRooms, int challengeRooms, WorldgenRandom random) {
            this.planBaseCells(totalRooms, random);
            this.planChallengeCells(challengeRooms, random);
            this.planConnections(random);
        }

        public void planBaseCells(int totalRooms, WorldgenRandom random) {
            while (totalRooms > 0) {
                Vector2i pointer = this.getCenter();
                while (pointer != null) {
                    NeighborInfo neighborInfo = this.getNeighborInfo(pointer);
                    List<Direction> empty = neighborInfo.empty;
                    List<Direction> full = neighborInfo.full;

                    if (random.nextBoolean()) {
                        if (!empty.isEmpty()) {
                            Direction direction = Util.getRandom(empty, random);
                            Vector2i offset = this.getOffset(direction);
                            offset.add(pointer);
                            this.setCell(offset, new RoomCell(RoomCell.Type.REGULAR));
                            totalRooms--;
                            pointer = null;
                        }
                    } else {
                        if (!full.isEmpty()) {
                            Direction direction = Util.getRandom(full, random);
                            Vector2i offset = this.getOffset(direction);
                            offset.add(pointer);
                            pointer = offset;
                        }
                    }
                }
            }
        }

        public void planChallengeCells(int challengeRooms, WorldgenRandom random) {
            while (challengeRooms > 0) {
                int step = 0;
                Vector2i pointer = this.getCenter();
                while (pointer != null) {
                    NeighborInfo neighborInfo = this.getNeighborInfo(pointer);
                    List<Direction> full = neighborInfo.full;

                    if (!full.isEmpty()) {
                        Direction direction = Util.getRandom(full, random);
                        Vector2i offset = this.getOffset(direction);
                        offset.add(pointer);
                        if (random.nextBoolean() || step < 3) {
                            pointer = offset;
                        } else {
                            RoomCell cell = this.getCell(offset);
                            if (cell.type == RoomCell.Type.REGULAR) {
                                cell.setType(RoomCell.Type.CHALLENGE);
                                challengeRooms--;
                                pointer = null;
                            }
                        }
                        step++;
                    }
                }
            }
        }

        public void planConnections(WorldgenRandom random) {
            for (int i = 0; i < this.getDiameter(); i++) {
                for (int j = 0; j < this.getDiameter(); j++) {
                    Vector2i position = new Vector2i(i, j);
                    RoomCell cell = this.getCell(position);
                    if (cell != null) {
                        NeighborInfo neighborInfo = this.getNeighborInfo(new Vector2i(i, j));
                        List<Direction> full = neighborInfo.full;

                        full.removeIf((direction) -> {
                            Vector2i offset = this.getOffset(direction);
                            offset.add(position);
                            RoomCell adjacentCell = this.getCell(offset);
                            return adjacentCell != null && adjacentCell.connections.get(direction.getOpposite());
                        });

                        int attemptLimit = 1;
                        if (random.nextFloat() < 0.25F) {
                            attemptLimit++;
                        }
                        for (int attempt = 0; attempt < attemptLimit; attempt++) {
                            if (!full.isEmpty()) {
                                Direction direction = Util.getRandom(full, random);
                                Vector2i offset = this.getOffset(direction);
                                offset.add(position);
                                RoomCell adjacentCell = this.getCell(offset);
                                cell.connections.put(direction, true);
                                adjacentCell.connections.put(direction.getOpposite(), true);
                                full.remove(direction);
                            }
                        }
                    }
                }
            }
        }

        public NeighborInfo getNeighborInfo(Vector2i pointer) {
            List<Direction> empty = new ArrayList<>();
            List<Direction> full = new ArrayList<>();

            for (Direction direction : Direction.Plane.HORIZONTAL) {
                Vector2i offset = this.getOffset(direction);
                offset.add(pointer);
                if (this.isWithinBounds(offset)) {
                    if (this.getCell(offset) == null) {
                        empty.add(direction);
                    } else {
                        full.add(direction);
                    }
                }
            }
            return new NeighborInfo(empty, full);
        }

        public RoomCell getCell(Vector2i pos) {
            return this.cells[pos.x][pos.y];
        }

        public void setCell(Vector2i pos, RoomCell cell) {
            this.cells[pos.x][pos.y] = cell;
        }

        public Vector2i getOffset(Direction direction) {
            return new Vector2i(direction.getStepX(), direction.getStepZ());
        }

        public Vector2i getCenter() {
            return new Vector2i(this.radius, this.radius);
        }

        public int getDiameter() {
            return (this.radius * 2) + 1;
        }

        public boolean isWithinBounds(Vector2i pos) {
            return pos.x >= 0 && pos.x < this.getDiameter() && pos.y >= 0 && pos.y < this.getDiameter();
        }


        public void printGrid() {
            AetherII.LOGGER.info("------");

            int size = (this.radius * 2 * 2) + 1;
            String[][] strings = new String[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    strings[i][j] = "⬛";
                }
            }

            for (int i = 0; i < this.getDiameter(); i++) {
                for (int j = 0; j < this.getDiameter(); j++) {
                    RoomCell cell = this.cells[i][j];
                    if (cell != null) {
                        strings[i * 2][j * 2] = switch (cell.type) {
                            case LOBBY -> "⬜";
                            case REGULAR -> "\uD83D\uDFE9";
                            case CHALLENGE -> "\uD83D\uDFE5";
                        };
                    }
                }
            }

            for (int i = 0; i < this.getDiameter(); i++) {
                for (int j = 0; j < this.getDiameter(); j++) {
                    Vector2i position = new Vector2i(i, j);
                    RoomCell cell = this.getCell(position);
                    if (cell != null) {
                        for (Map.Entry<Direction, Boolean> entry : cell.connections.entrySet()) {
                            if (entry.getValue()) {
                                Vector2i offset = this.getOffset(entry.getKey());
                                offset.add(position);
                                int newI = (offset.x * 2) - entry.getKey().getStepX();
                                int newJ = (offset.y * 2) - entry.getKey().getStepZ();
                                if (newI >= 0 && newI < size && newJ >= 0 && newJ < size) {
                                    strings[newI][newJ] = "\uD83D\uDFEA";
                                }
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < size; i++) {
                String string = "";
                for (int j = 0; j < size; j++) {
                    string = string.concat(strings[i][j]);
                }
                AetherII.LOGGER.info(string);
            }

            AetherII.LOGGER.info("------");
        }

        public record NeighborInfo(List<Direction> empty, List<Direction> full) { }
    }
}
