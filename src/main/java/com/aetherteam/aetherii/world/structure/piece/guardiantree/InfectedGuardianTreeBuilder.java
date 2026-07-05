package com.aetherteam.aetherii.world.structure.piece.guardiantree;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.registries.AetherIIProcessorLists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.apache.commons.lang3.StringUtils;
import org.joml.Vector2i;

import java.util.*;

public class InfectedGuardianTreeBuilder {
    private static final Vec3i ROOM_BOUNDS = new Vec3i(23, 24, 23);
    private static final Vec3i CORRIDOR_SEPARATION_BOUNDS = new Vec3i(11, 24, 11);

    private final StructureTemplateManager manager;
    private final HolderGetter<StructureProcessorList> processors; //todo
    private final RandomSource random;

    private final Multimap<String, Identifier> binaryMappedNormalRooms;
    private final Multimap<String, Identifier> binaryMappedChallengeRooms;
    private final Multimap<String, Identifier> mappedCorridors;

    public InfectedGuardianTreeBuilder(Structure.GenerationContext context) {
        this.manager = context.structureTemplateManager();
        this.processors = context.registryAccess().lookupOrThrow(Registries.PROCESSOR_LIST);
        this.random = context.random();

        Identifier normalRoomPrefix = Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/rooms/");
        Identifier challengeRoomPrefix = Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/challenge_rooms/");
        Identifier corridorPrefix = Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/corridors/");
        Identifier deadEndPrefix = Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/dead_ends/");

        List<Identifier> normalRooms = this.manager.listTemplates().filter((identifier) -> identifier.toString().startsWith(normalRoomPrefix.toString())).toList();
        this.binaryMappedNormalRooms = this.binaryMappedRooms(normalRooms);

        List<Identifier> challengeRooms = this.manager.listTemplates().filter((identifier) -> identifier.toString().startsWith(challengeRoomPrefix.toString())).toList();
        this.binaryMappedChallengeRooms = this.binaryMappedRooms(challengeRooms);

        List<Identifier> corridors = this.manager.listTemplates().filter((identifier) -> identifier.toString().startsWith(corridorPrefix.toString())).toList();
        this.mappedCorridors = Multimaps.newSetMultimap(new HashMap<>(), HashSet::new);

        for (Identifier corridor : corridors) {
            String[] roomPath = corridor.getPath().split("/");
            String roomName = roomPath[roomPath.length - 1].replace("-", "").substring(0, 2);
            this.mappedCorridors.put(roomName, corridor);
        }

//        List<Identifier> deadEnds = context.structureTemplateManager().listTemplates().filter((identifier) -> identifier.toString().startsWith(deadEndPrefix.toString())).toList();
    }

    public void initializeDungeon(StructurePiecesBuilder builder, BlockPos startPos, Rotation structureRotation) {


        //todo staircase pieces are incorrect and missing a connector bit at the top

        InfectedGuardianTreePiece entrancePiece = new InfectedGuardianTreeRoom(this.manager, "entrance", startPos.offset(1, 0, 1), structureRotation, this.processors.getOrThrow(AetherIIProcessorLists.SENTRY_RUINS_ROOM));
        builder.addPiece(entrancePiece);


        Vec3i staircase1Size = this.manager.getOrCreate(Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/staircases/floor_1")).getSize();
        startPos = startPos.below(staircase1Size.getY());
        InfectedGuardianTreePiece staircase1Piece = new InfectedGuardianTreeRoom(this.manager, "staircases/floor_1", startPos.offset(7, 0, 7), Rotation.COUNTERCLOCKWISE_90.getRotated(structureRotation), this.processors.getOrThrow(AetherIIProcessorLists.SENTRY_RUINS_ROOM));
        builder.addPiece(staircase1Piece);


        Vec3i lobby1Size = this.manager.getOrCreate(Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/lobbies/floor_1/lobby_01")).getSize();
        startPos = startPos.below(lobby1Size.getY() + 1);
        FloorGrid floor1 = new FloorGrid(2);
        floor1.planLayout(7, 3, this.random);
        this.initializeFloor(builder, structureRotation, floor1, startPos, "lobbies/floor_1/lobby_01", binaryMappedNormalRooms, binaryMappedChallengeRooms, mappedCorridors);


        Vec3i staircase2Size = this.manager.getOrCreate(Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/staircases/floor_2")).getSize();
        startPos = startPos.below(staircase2Size.getY() - 1);
        InfectedGuardianTreePiece staircase2Piece = new InfectedGuardianTreeRoom(this.manager, "staircases/floor_2", startPos.offset(7, 0, 7), Rotation.COUNTERCLOCKWISE_90.getRotated(structureRotation), this.processors.getOrThrow(AetherIIProcessorLists.SENTRY_RUINS_ROOM));
        builder.addPiece(staircase2Piece);


        Vec3i lobby2Size = this.manager.getOrCreate(Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/lobbies/floor_2/lobby_01")).getSize();
        startPos = startPos.below(lobby2Size.getY() + 1);
        FloorGrid floor2 = new FloorGrid(2);
        floor2.planLayout(9, 6, this.random);
        this.initializeFloor(builder, structureRotation, floor2, startPos, "lobbies/floor_2/lobby_01", binaryMappedNormalRooms, binaryMappedChallengeRooms, mappedCorridors);


        Vec3i staircaseBossSize = this.manager.getOrCreate(Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/staircases/boss")).getSize();
        startPos = startPos.below(staircaseBossSize.getY() - 1);
        InfectedGuardianTreePiece staircaseBossPiece = new InfectedGuardianTreeRoom(this.manager, "staircases/boss", startPos.offset(7, 0, 7), Rotation.COUNTERCLOCKWISE_90.getRotated(structureRotation), this.processors.getOrThrow(AetherIIProcessorLists.SENTRY_RUINS_ROOM));
        builder.addPiece(staircaseBossPiece);


        Vec3i bossRoomSize = this.manager.getOrCreate(Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/boss_room")).getSize(); //todo this doesnt rotate correctly; need a different rotation pivot
        startPos = startPos.below(bossRoomSize.getY());
        InfectedGuardianTreePiece bossRoomPiece = new InfectedGuardianTreeRoom(this.manager, "boss_room", startPos.offset(-2, 0, 1), structureRotation, this.processors.getOrThrow(AetherIIProcessorLists.SENTRY_RUINS_ROOM));
        builder.addPiece(bossRoomPiece);
    }

    public void initializeFloor(StructurePiecesBuilder builder, Rotation structureRotation, InfectedGuardianTreeBuilder.FloorGrid floor, BlockPos initialPos, String lobby, Multimap<String, Identifier> binaryMappedNormalRooms, Multimap<String, Identifier> binaryMappedChallengeRooms, Multimap<String, Identifier> mappedCorridors) {
        List<InfectedGuardianTreeBuilder.FloorGrid.CellData> cells = floor.getCellData();
        for (InfectedGuardianTreeBuilder.FloorGrid.CellData data : cells) {
            BlockPos offset = initialPos.offset(ROOM_BOUNDS.offset(CORRIDOR_SEPARATION_BOUNDS).multiply(data.offset().x(), 0, data.offset().y()));

            if (data.cell().type == InfectedGuardianTreeBuilder.RoomCell.Type.LOBBY) {
                offset = offset.above(1);
            }

            String roomName = switch (data.cell().type) {
                case LOBBY -> lobby;
                case REGULAR -> {
                    List<Identifier> validRooms = data.cell().findValidRooms(binaryMappedNormalRooms);
                    yield data.cell().selectRandomRoom(validRooms, this.random);
                }
                case CHALLENGE -> {
                    List<Identifier> validRooms = data.cell().findValidRooms(binaryMappedChallengeRooms);
                    yield data.cell().selectRandomRoom(validRooms, this.random);
                }
            };
            Rotation rotation = data.cell().setupRoom(roomName, floor, data.offset());

            if (data.cell().type == InfectedGuardianTreeBuilder.RoomCell.Type.LOBBY) {
                rotation = rotation.getRotated(structureRotation);
            }

            InfectedGuardianTreePiece piece = new InfectedGuardianTreeRoom(this.manager, roomName, offset.offset(1, 0, 1), rotation, this.processors.getOrThrow(AetherIIProcessorLists.SENTRY_RUINS_ROOM));
            builder.addPiece(piece);
        }

        BlockPos initialCenter = initialPos.offset(Mth.ceil(ROOM_BOUNDS.getX() / 2.0F), 0, Mth.ceil(ROOM_BOUNDS.getZ() / 2.0F));

        for (InfectedGuardianTreeBuilder.FloorGrid.CellData data : cells) {
            BlockPos offsetCenter = initialCenter.offset(ROOM_BOUNDS.offset(CORRIDOR_SEPARATION_BOUNDS).multiply(data.offset().x(), 0, data.offset().y()));

            for (Map.Entry<Direction, Character> entry : data.cell().connections.entrySet()) {
                Direction direction = entry.getKey();
                if (entry.getValue() != '0') {
                    Vector2i directionOffset = floor.getOffset(direction);
                    Vector2i trueOffset = floor.getCenter().add(data.offset()).add(directionOffset);
                    if (floor.isWithinBounds(trueOffset)) {
                        InfectedGuardianTreeBuilder.RoomCell adjacentCell = floor.getCell(trueOffset);
                        if (adjacentCell != null) {
                            Character oppositeConnection = adjacentCell.connections.get(direction.getOpposite());
                            String connectionLink = entry.getValue() + "" + oppositeConnection;
                            Collection<Identifier> corridorOptions = mappedCorridors.get(connectionLink);
                            if (!corridorOptions.isEmpty()) {
                                String corridorName = Util.getRandom(new ArrayList<>(corridorOptions), this.random).getPath().replace("infected_guardian_tree/", "");

                                Rotation rotation = switch (direction) {
                                    case NORTH -> Rotation.CLOCKWISE_90;
                                    case EAST -> Rotation.CLOCKWISE_180;
                                    case SOUTH -> Rotation.COUNTERCLOCKWISE_90;
                                    case WEST -> Rotation.NONE;
                                    default -> Rotation.NONE;
                                };
                                BlockPos corridorOffset = switch (rotation) {
                                    case NONE -> offsetCenter.offset(new Vec3i(-22, 0, -11));
                                    case CLOCKWISE_90 -> offsetCenter.offset(new Vec3i(-5, 0, -16));
                                    case CLOCKWISE_180 -> offsetCenter.offset(new Vec3i(12, 0, -11));
                                    case COUNTERCLOCKWISE_90 -> offsetCenter.offset(new Vec3i(-17, 0, 6));
                                };

                                InfectedGuardianTreePiece piece = new InfectedGuardianTreeCorridor(this.manager, corridorName, corridorOffset, rotation, this.processors.getOrThrow(AetherIIProcessorLists.SENTRY_RUINS_ROOM));
                                builder.addPiece(piece);

                                data.cell().connections.put(entry.getKey(), '0');
                                adjacentCell.connections.put(entry.getKey().getOpposite(), '0');
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

        public String selectRandomRoom(List<Identifier> validRooms, RandomSource random) {
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

        public void planLayout(int totalRooms, int challengeRooms, RandomSource random) {
            this.planBaseCells(totalRooms, random);
            this.planChallengeCells(challengeRooms, random);
            this.planConnections(random);
        }

        public void planBaseCells(int totalRooms, RandomSource random) {
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

        public void planChallengeCells(int challengeRooms, RandomSource random) {
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

        public void planConnections(RandomSource random) {
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

        public record NeighborInfo(List<Direction> empty, List<Direction> full) { }

        public record CellData(Vector2i offset, RoomCell cell) { }
    }
}
