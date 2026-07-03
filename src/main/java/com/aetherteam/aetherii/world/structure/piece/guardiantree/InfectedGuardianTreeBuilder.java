package com.aetherteam.aetherii.world.structure.piece.guardiantree;

import com.google.common.collect.Multimap;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import org.apache.commons.lang3.StringUtils;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InfectedGuardianTreeBuilder {
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

        public record NeighborInfo(List<Direction> empty, List<Direction> full) { }

        public record CellData(Vector2i offset, RoomCell cell) { }
    }
}
