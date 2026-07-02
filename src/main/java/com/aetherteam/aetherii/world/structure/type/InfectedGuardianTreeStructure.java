package com.aetherteam.aetherii.world.structure.type;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.registries.AetherIIProcessorLists;
import com.aetherteam.aetherii.world.structure.piece.guardiantree.InfectedGuardianTreeCorridor;
import com.aetherteam.aetherii.world.structure.piece.guardiantree.InfectedGuardianTreePiece;
import com.aetherteam.aetherii.world.structure.piece.guardiantree.InfectedGuardianTreeRoom;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.apache.commons.lang3.StringUtils;
import org.joml.Vector2i;

import java.util.*;

public class InfectedGuardianTreeStructure extends Structure { //todo move lots of code to InfectedGuardianTreeBuilder
    public static final MapCodec<InfectedGuardianTreeStructure> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            settingsCodec(builder)
    ).apply(builder, InfectedGuardianTreeStructure::new));

    private static final Vec3i ROOM_BOUNDS = new Vec3i(23, 24, 23);
    private static final Vec3i CORRIDOR_SEPARATION_BOUNDS = new Vec3i(11, 24, 11);

    public InfectedGuardianTreeStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        WorldgenRandom random = context.random();
        BlockPos originPos = chunkPos.getWorldPosition();

//        return Optional.empty();
        return Optional.of(new GenerationStub(originPos, builder -> this.generate(context, builder)));
    }

    //todo: entrance, staircases, boss room
    public void generate(GenerationContext context, StructurePiecesBuilder builder) {
        ChunkPos chunkPos = context.chunkPos();
        ChunkGenerator chunkGenerator = context.chunkGenerator();
        LevelHeightAccessor heightAccessor = context.heightAccessor();
        RandomState randomState = context.randomState();
        WorldgenRandom random = context.random();
        StructureTemplateManager templateManager = context.structureTemplateManager();
        HolderGetter<StructureProcessorList> processors = context.registryAccess().lookupOrThrow(Registries.PROCESSOR_LIST);

        BlockPos initialPos = chunkPos.getBlockAt(0, 150, 0).mutable();



        Identifier normalRoomPrefix = Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/rooms/");
        Identifier challengeRoomPrefix = Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/challenge_rooms/");
        Identifier corridorPrefix = Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/corridors/");
        Identifier deadEndPrefix = Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/dead_ends/");

        List<Identifier> normalRooms = context.structureTemplateManager().listTemplates().filter((identifier) -> identifier.toString().startsWith(normalRoomPrefix.toString())).toList();
        Multimap<String, Identifier> binaryMappedNormalRooms = this.binaryMappedRooms(normalRooms);

        List<Identifier> challengeRooms = context.structureTemplateManager().listTemplates().filter((identifier) -> identifier.toString().startsWith(challengeRoomPrefix.toString())).toList();
        Multimap<String, Identifier> binaryMappedChallengeRooms = this.binaryMappedRooms(challengeRooms);

        List<Identifier> corridors = context.structureTemplateManager().listTemplates().filter((identifier) -> identifier.toString().startsWith(corridorPrefix.toString())).toList();
        Multimap<String, Identifier> mappedCorridors = Multimaps.newSetMultimap(new HashMap<>(), HashSet::new);

        for (Identifier corridor : corridors) {
            String[] roomPath = corridor.getPath().split("/");
            String roomName = roomPath[roomPath.length - 1].replace("-", "").substring(0, 2);
            String flippedName = roomName.charAt(1) + "" + roomName.charAt(0);
            mappedCorridors.put(roomName, corridor);
            mappedCorridors.put(flippedName, corridor);
        }

//        List<Identifier> deadEnds = context.structureTemplateManager().listTemplates().filter((identifier) -> identifier.toString().startsWith(deadEndPrefix.toString())).toList();








        FloorGrid floor1 = new FloorGrid(2);
        floor1.planLayout(7, 3, random);
        this.generateFloor(builder, templateManager, processors, random, floor1, initialPos, "lobbies/floor_1/lobby_01", binaryMappedNormalRooms, binaryMappedChallengeRooms, mappedCorridors);

        initialPos = initialPos.below(ROOM_BOUNDS.getY());

        FloorGrid floor2 = new FloorGrid(2);
        floor2.planLayout(9, 6, random);
        this.generateFloor(builder, templateManager, processors, random, floor2, initialPos, "lobbies/floor_2/lobby_01", binaryMappedNormalRooms, binaryMappedChallengeRooms, mappedCorridors);
    }

    public void generateFloor(StructurePiecesBuilder builder, StructureTemplateManager manager, HolderGetter<StructureProcessorList> processors, WorldgenRandom random, FloorGrid floor, BlockPos initialPos, String lobby, Multimap<String, Identifier> binaryMappedNormalRooms, Multimap<String, Identifier> binaryMappedChallengeRooms, Multimap<String, Identifier> mappedCorridors) {

        floor.printGrid();

        List<FloorGrid.CellData> cells = floor.getCellData();
        for (FloorGrid.CellData data : cells) {
            BlockPos offset = initialPos.offset(ROOM_BOUNDS.offset(CORRIDOR_SEPARATION_BOUNDS).multiply(data.offset().x(), 1, data.offset().y()));

            if (data.cell().type == RoomCell.Type.LOBBY) {
                offset = offset.above(1);
            }

            String roomName = switch (data.cell().type) {
                case LOBBY -> lobby;
                case REGULAR -> {
                    List<Identifier> validRooms = data.cell().findValidRooms(binaryMappedNormalRooms);
                    yield data.cell().selectRandomRoom(validRooms, random);
                }
                case CHALLENGE -> {
                    List<Identifier> validRooms = data.cell().findValidRooms(binaryMappedChallengeRooms);
                    yield data.cell.selectRandomRoom(validRooms, random);
                }
            };
            Rotation rotation = data.cell().setupRoom(roomName, floor, data.offset());

            InfectedGuardianTreePiece piece = new InfectedGuardianTreeRoom(manager, roomName, offset.offset(1, 1, 1), rotation, processors.getOrThrow(AetherIIProcessorLists.SENTRY_RUINS_ROOM));
            builder.addPiece(piece);
        }

        for (FloorGrid.CellData data : cells) {
//            BlockPos roomPos = initialPos.offset(ROOM_BOUNDS.offset(CORRIDOR_SEPARATION_BOUNDS).multiply(data.offset().x(), 1, data.offset().y()));
//
//            BlockPos corridorPos = roomPos.subtract(CORRIDOR_SEPARATION_BOUNDS.multiply(data.offset().x(), 0, data.offset().y()));

//            AetherII.LOGGER.info(corridorPos.toString());

            for (Map.Entry<Direction, Character> entry : data.cell.connections.entrySet()) {
                if (entry.getValue() != '0') {
                    Vector2i directionOffset = floor.getOffset(entry.getKey());
                    Vector2i trueOffset = floor.getCenter().add(data.offset()).add(directionOffset);
                    if (floor.isWithinBounds(trueOffset)) {
                        RoomCell adjacentCell = floor.getCell(trueOffset);
                        if (adjacentCell != null) {
                            Character oppositeConnection = adjacentCell.connections.get(entry.getKey().getOpposite());
                            String connectionLink = entry.getValue() + "" + oppositeConnection;
                            Collection<Identifier> corridorOptions = mappedCorridors.get(connectionLink);
                            if (!corridorOptions.isEmpty()) {
                                String corridorName = Util.getRandom(new ArrayList<>(corridorOptions), random).getPath().replace("infected_guardian_tree/", "");

//                                //todo rotation AND OFFset
//
//
//                                Rotation rotation = switch (entry.getKey()) {
//                                    case NORTH -> Rotation.COUNTERCLOCKWISE_90;
//                                    case SOUTH -> Rotation.CLOCKWISE_90;
//                                    case WEST -> Rotation.CLOCKWISE_180;
//                                    default -> Rotation.NONE;
//                                };
//
//
//
//                                InfectedGuardianTreePiece piece = new InfectedGuardianTreeCorridor(manager, corridorName, corridorPos, rotation, processors.getOrThrow(AetherIIProcessorLists.SENTRY_RUINS_ROOM));
//                                builder.addPiece(piece);
//
//
//                                //todo remove the connections when finished;
//                                data.cell.connections.put(entry.getKey(), '0');
//                                adjacentCell.connections.put(entry.getKey().getOpposite(), '0');
                            }
                        }
                    }
                }
            }
        }
    }

    public Multimap<String, Identifier> binaryMappedRooms(List<Identifier> rooms) {
        Multimap<String, Identifier> binaryMappedRooms = Multimaps.newSetMultimap(new HashMap<>(), HashSet::new);
        for (Identifier normalRoom : rooms) {
            String[] roomPath = normalRoom.getPath().split("/");
            String roomBinary = roomPath[roomPath.length - 1].replace("-", "").substring(0, 4).replaceAll("[a-z]", "1");
            binaryMappedRooms.put(roomBinary, normalRoom);
        }
        return binaryMappedRooms;
    }

    @Override
    public StructureType<?> type() {
        return AetherIIStructureTypes.INFECTED_GUARDIAN_TREE.get();
    }

    public static class RoomCell {
        public static final Direction[] CONNECTION_ORDER = { Direction.WEST, Direction.NORTH, Direction.EAST, Direction.SOUTH };
        public final Map<Direction, Character> connections = new LinkedHashMap<>();
        public Type type;

        public RoomCell(Type type) {
            this.type = type;
            for (Direction direction : CONNECTION_ORDER) {
                connections.put(direction, null);
            }
        }

        public void setType(Type type) {
            this.type = type;
        }

        public String getConnectionBinary() {
            String binary = "";
            for (Direction direction : CONNECTION_ORDER) {
                binary = binary.concat(this.connections.get(direction) != null ? "1" : "0");
            }
            return binary;
        }

        public List<Identifier> findValidRooms(Multimap<String, Identifier> binarySelection) {
            List<Identifier> validRooms = new ArrayList<>();
            String checkBinary = this.getConnectionBinary();
            int checkBinaryConnections = StringUtils.countMatches(checkBinary, "1");
            for (Map.Entry<String, Identifier> entry : binarySelection.entries()) {
                int entryConnections = StringUtils.countMatches(entry.getKey(), "1");
                if (checkBinary.concat(checkBinary).contains(entry.getKey()) || entryConnections > checkBinaryConnections) {
                    validRooms.add(entry.getValue());
                }
            }
            return validRooms;
        }

        public String selectRandomRoom(List<Identifier> validRooms, WorldgenRandom random) {
            return Util.getRandom(validRooms, random).getPath().replace("infected_guardian_tree/", "");
        }

        public Rotation setupRoom(String name, FloorGrid grid, Vector2i offset) {
            Rotation rotation = Rotation.NONE;
            if (this.type == Type.LOBBY) {
                for (int i = 0; i < 4; i++) {
                    Direction direction = CONNECTION_ORDER[i];
                    this.connections.put(direction, 'b');
                }
            } else {
                String[] roomPath = name.split("/");
                String roomName = roomPath[roomPath.length - 1].replace("-", "").substring(0, 4);

                String requiredDirections = "";
                for (Direction direction : CONNECTION_ORDER) {
                    if (this.connections.get(direction) != null) {
                        Vector2i directionOffset = grid.getOffset(direction);
                        Vector2i adjacentCellPosition = grid.getCenter().add(offset).add(directionOffset);
                        if (grid.isWithinBounds(adjacentCellPosition)) {
                            RoomCell adjacentCell = grid.getCell(adjacentCellPosition);
                            if (adjacentCell != null && adjacentCell.connections.get(direction.getOpposite()) != null) {
                                requiredDirections = requiredDirections.concat("1");
                                continue;
                            }
                        }
                    }
                    requiredDirections = requiredDirections.concat("0");
                }

                for (int i = 0; i < 4; i++) {
                    String roomBinary = roomName.replaceAll("[a-z]", "1");
                    if ((Integer.parseInt(roomBinary, 2) & Integer.parseInt(requiredDirections, 2)) == Integer.parseInt(requiredDirections, 2)) {
                        break;
                    }
                    roomName = roomName.charAt(roomName.length() - 1) + roomName.substring(0, roomName.length() - 1);
                    rotation = rotation.getRotated(Rotation.CLOCKWISE_90);
                }

                for (int i = 0; i < 4; i++) {
                    Direction direction = CONNECTION_ORDER[i];
                    char connection = roomName.charAt(i);
                    this.connections.put(direction, connection);
                }
            }
            return rotation;
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
                            return adjacentCell != null && adjacentCell.connections.get(direction.getOpposite()) != null;
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
                                cell.connections.put(direction, '1');
                                adjacentCell.connections.put(direction.getOpposite(), '1');
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

        public List<CellData> getCellData() {
            List<CellData> cells = new ArrayList<>();
            for (int i = 0; i < this.getDiameter(); i++) {
                for (int j = 0; j < this.getDiameter(); j++) {
                    Vector2i position = new Vector2i(i, j);
                    RoomCell cell = this.getCell(position);
                    if (cell != null) {
                        Vector2i offset = position.sub(this.getCenter());
                        cells.add(new CellData(offset, cell));
                    }
                }
            }
            return cells;
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
                        for (Map.Entry<Direction, Character> entry : cell.connections.entrySet()) {
                            if (entry.getValue() != null && entry.getValue() != '0') {
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

        public record CellData(Vector2i offset, RoomCell cell) { }
    }
}
