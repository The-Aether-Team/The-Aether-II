package com.aetherteam.aetherii.world.structure.piece.sentry;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.util.random.WeightedList;
import com.aetherteam.aetherii.world.BlockLogicUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A directed graph used for assembling the Bronze Dungeon. This allows us to keep track of how far away a room is
 * from the starting point by counting along the path.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Directed_graph">https://en.wikipedia.org/wiki/Directed_graph</a>
 */
public class SentryRuinsBuilder {
    public static final Map<String, WeightedList.Builder<RoomProvider<?>>> ROOM_OPTIONS_BUILDER = Map.ofEntries(
            Map.entry("boss_room", new WeightedList.Builder<>()),
            Map.entry("chest_room", new WeightedList.Builder<>()),
            Map.entry("lobby", new WeightedList.Builder<>()),
            Map.entry("square_tunnel", new WeightedList.Builder<>()),
            Map.entry("staircase", new WeightedList.Builder<>()),
            Map.entry("surface_ruin", new WeightedList.Builder<>())
    );
    private static Map<String, WeightedList<RoomProvider<?>>> ROOM_OPTIONS;
    
    static {
        ROOM_OPTIONS_BUILDER.get("boss_room").add((manager, pos, rotation, processorList) -> new SentryRuinsBossRoom(manager, "boss_room", pos, rotation, processorList), 1);
        ROOM_OPTIONS_BUILDER.get("chest_room")
                .add((manager, pos, rotation, processorList) -> new SentryRuinsRoom(manager, "rooms/lounge", pos, rotation, processorList), 1)
                .add((manager, pos, rotation, processorList) -> new SentryRuinsRoom(manager, "rooms/material_deposit", pos, rotation, processorList), 1)
                .add((manager, pos, rotation, processorList) -> new SentryRuinsRoom(manager, "rooms/cold_storage", pos, rotation, processorList), 1)
                .add((manager, pos, rotation, processorList) -> new SentryRuinsRoom(manager, "rooms/testing", pos, rotation, processorList), 1)
                .add((manager, pos, rotation, processorList) -> new SentryRuinsRoom(manager, "rooms/workshop", pos, rotation, processorList), 1)
                .add((manager, pos, rotation, processorList) -> new SentryRuinsRoom(manager, "rooms/forge", pos, rotation, processorList), 1);
        ROOM_OPTIONS_BUILDER.get("lobby").add((manager, pos, rotation, processorList) -> new SentryRuinsRoom(manager, "lobby", pos, rotation, processorList), 1);
        ROOM_OPTIONS_BUILDER.get("square_tunnel").add((manager, pos, rotation, processorList) -> new SentryRuinsRoom(manager, "square_tunnel", pos, rotation, processorList), 1);
        ROOM_OPTIONS_BUILDER.get("staircase").add((manager, pos, rotation, processorList) -> new SentryRuinsRoom(manager, "staircase", pos, rotation, processorList), 1);
        ROOM_OPTIONS_BUILDER.get("surface_ruin")
                .add((manager, pos, rotation, processorList) -> new SentryRuinsRoom(manager, "surface_ruins/ruin_01", pos, rotation, processorList), 1)
                .add((manager, pos, rotation, processorList) -> new SentryRuinsRoom(manager, "surface_ruins/ruin_02", pos, rotation, processorList), 1)
                .add((manager, pos, rotation, processorList) -> new SentryRuinsRoom(manager, "surface_ruins/ruin_03", pos, rotation, processorList), 1);
    }

    private final Structure.GenerationContext context;
    private final StructureTemplateManager manager;
    private final RandomSource random;
    private final SentryRuinsProcessorSettings processors;

    private final int nodeWidth;
    private final int edgeWidth;
    private final int edgeLength;
    private final int maxSize;

    private final List<StructurePiece> nodes = new ArrayList<>();
    private final Map<StructurePiece, Map<Direction, Connection>> edges = new HashMap<>();

    public SentryRuinsBuilder(Structure.GenerationContext context, int maxSize, SentryRuinsProcessorSettings processors) {
        this.context = context;
        this.manager = context.structureTemplateManager();
        this.random = context.random();
        this.processors = processors;

        Vec3i nodeSize = context.structureTemplateManager().getOrCreate(new ResourceLocation(AetherII.MODID, "sentry_ruins/rooms/lounge")).getSize();
        this.nodeWidth = nodeSize.getX();

        Vec3i edgeSize = context.structureTemplateManager().getOrCreate(new ResourceLocation(AetherII.MODID, "sentry_ruins/square_tunnel")).getSize();
        this.edgeWidth = edgeSize.getX();
        this.edgeLength = edgeSize.getZ();

        this.maxSize = Math.max(3, maxSize);
    }

    public void initializeDungeon(BlockPos startPos, Rotation rotation, Structure.GenerationContext genContext, StructurePiecesBuilder builder, int surfaceRuinOffset) {
        ROOM_OPTIONS = ROOM_OPTIONS_BUILDER.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, (e) -> e.getValue().build()));

        SentryRuinsPiece bossRoom = this.chooseRoom("boss_room", startPos, rotation, this.processors.bossSettings());
        Direction direction = bossRoom.getOrientation();
        if (direction != null) {
            BlockPos pos = BlockLogicUtil.tunnelFromEvenSquareRoom(bossRoom.getBoundingBox(), direction, this.edgeWidth);
            SentryRuinsPiece hallway = this.chooseRoom("square_tunnel", pos, bossRoom.getRotation(), this.processors.roomSettings());
            pos = BlockLogicUtil.tunnelFromEvenSquareRoom(hallway.getBoundingBox(), direction, this.nodeWidth);
            SentryRuinsPiece defaultRoom = this.chooseRoom("chest_room", pos, hallway.getRotation(), this.processors.roomSettings());

            this.nodes.add(bossRoom);
            this.nodes.add(defaultRoom);
            new Connection(bossRoom, defaultRoom, hallway, direction);

            ChunkPos chunkPos = genContext.chunkPos();

            int targetRoomCount = this.maxSize - 1;
            int attempts = 0;
            while (this.nodes.size() < targetRoomCount && attempts < this.maxSize * 8) {
                if (this.propagateRooms(defaultRoom, chunkPos, false, 0)) {
                    attempts = 0;
                } else {
                    ++attempts;
                }
            }

            if (this.propagateRooms(defaultRoom, chunkPos, true, 0)) {
                this.buildSurfaceStaircase(genContext.heightAccessor(), genContext.chunkGenerator(), genContext.randomState(), surfaceRuinOffset);
            }

            this.populatePiecesBuilder(builder);
        }
    }

    /**
     * Recursively move through the graph of rooms to add new pieces.
     *
     * @param currentNode The current {@link StructurePiece} node to try to add.
     * @param chunkPos    The {@link ChunkPos} for the piece.
     * @param placeLobby  Whether to place a lobby or a chest room, as a {@link Boolean}.
     * @return Whether the new piece was successfully placed, as a {@link Boolean}.
     */
    private boolean propagateRooms(StructurePiece currentNode, ChunkPos chunkPos, boolean placeLobby, int iteration) {
        Rotation rotation = currentNode.getRotation();
        List<Rotation> rotations = new ArrayList<>(3);
        rotations.add(rotation.getRotated(Rotation.COUNTERCLOCKWISE_90));
        rotations.add(rotation);
        rotations.add(rotation.getRotated(Rotation.CLOCKWISE_90));
        String roomName = placeLobby ? "lobby" : "chest_room";

        // Attempt to generate a room in each direction.
        for (int i = 3; i > 0; i--) {
            rotation = rotations.remove(this.random.nextInt(i));
            Direction direction = rotation.rotate(Direction.SOUTH);
            if (this.hasConnection(currentNode, direction)) {
                if (propagateRooms(this.edges.get(currentNode).get(direction).end, chunkPos, placeLobby, iteration + 1)) {
                    return true;
                }
            } else {
                BlockPos pos = BlockLogicUtil.tunnelFromEvenSquareRoom(currentNode.getBoundingBox(), direction, this.edgeWidth);

                SentryRuinsPiece hallway = this.chooseRoom("square_tunnel", pos, rotation, this.processors.roomSettings());
                pos = BlockLogicUtil.tunnelFromEvenSquareRoom(hallway.getBoundingBox(), direction, this.nodeWidth);
                SentryRuinsPiece room = this.chooseRoom(roomName, pos, rotation, this.processors.roomSettings());
                StructurePiece collisionPiece = StructurePiece.findCollisionPiece(this.nodes, room.getBoundingBox());

                if (this.canPlaceRoom(chunkPos, room, placeLobby, iteration)) {
                    if (collisionPiece == null) {
                        new Connection(currentNode, room, hallway, direction);
                        this.nodes.add(room);
                        return true;
                    } else if (!(collisionPiece instanceof SentryRuinsBossRoom)) { // If there's a piece in the way, see if there's a connection already. If not, make one. Then continue the loop.
                        boolean flag = this.edges.computeIfAbsent(collisionPiece, piece -> new HashMap<>()).values().stream()
                                .map(Connection::endPiece).anyMatch(piece -> piece == currentNode);
                        if (!flag) {
                            new Connection(currentNode, room, hallway, direction);
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean canPlaceRoom(ChunkPos chunkPos, SentryRuinsPiece room, boolean placeLobby, int iteration) {
        if (placeLobby && iteration == this.nodes.size() - 2) {
            return true;
        }
        if (!this.isCloseToCenter(chunkPos, room.templatePosition())) {
            return false;
        }
        if (isCoveredAtPos(room.getBoundingBox(), this.context.chunkGenerator(), this.context.heightAccessor(), this.context.randomState())) {
            return true;
        }
        if (placeLobby) {
            return true;
        }
        return isMostlyCoveredAtPos(room.getBoundingBox(), this.context.chunkGenerator(), this.context.heightAccessor(), this.context.randomState());
    }

    @Nullable
    @SuppressWarnings("SameParameterValue")
    private StructurePiece seekLastRoomNode(int minWidth) {
        for (int i = this.nodes.size() - 1; i >= 0; i--) {
            StructurePiece piece = this.nodes.get(i);
            BoundingBox box = piece.getBoundingBox();
            if (box.getXSpan() > minWidth && box.getZSpan() > minWidth) {
                return piece;
            }
        }

        return null;
    }

    private void buildSurfaceStaircase(LevelHeightAccessor level, ChunkGenerator chunkGenerator, RandomState randomState, int surfaceRuinOffset) {
        final int shrink = 3;
        StructurePiece lobby = this.seekLastRoomNode(shrink * 2);
        if (lobby == null) return; // Not likely to happen ever, but just in case for wackiness

        BoundingBox lobbyBounds = lobby.getBoundingBox();
        BlockPos lobbyCenter = lobbyBounds.getCenter();
        Rotation lobbyRotation = lobby.getRotation();
        int topSurfaceY = chunkGenerator.getFirstOccupiedHeight(lobbyCenter.getX(), lobbyCenter.getZ(), Heightmap.Types.OCEAN_FLOOR_WG, level, randomState);

        for (int i = 0; i <= (topSurfaceY - lobbyBounds.maxY() + surfaceRuinOffset) / 6; i++) {
            SentryRuinsPiece staircase = this.chooseRoom("staircase", staircasePos(lobbyBounds, lobbyRotation, lobbyBounds.maxY() + 1 + i * 6, 0), lobbyRotation, this.processors.staircaseSettings());
            this.nodes.add(staircase);


        }
        for (int i = 0; i <= (topSurfaceY - lobbyBounds.maxY() + surfaceRuinOffset) / 6; i++) {
            if (i++ >= (topSurfaceY - lobbyBounds.maxY() + surfaceRuinOffset - 6) / 6) {
                SentryRuinsPiece surfaceRuin = this.chooseRoom("surface_ruin", staircasePos(lobbyBounds, lobbyRotation, lobbyBounds.maxY() + 1 + i * 6, 1), lobbyRotation, this.processors.bossSettings());
                this.nodes.add(surfaceRuin);
            }
        }
    }

    public BlockPos staircasePos(BoundingBox lobbyBounds, Rotation lobbyRotation, int y, int offset) {
        if (lobbyRotation == Rotation.NONE) {
            return new BlockPos(lobbyBounds.minX() + 9 - offset, y, lobbyBounds.minZ() + 6 - offset);
        }
        else if (lobbyRotation == Rotation.CLOCKWISE_90) {
            return new BlockPos(lobbyBounds.minX() + 9 + offset, y, lobbyBounds.minZ() + 9 - offset);
        }
        else if (lobbyRotation == Rotation.CLOCKWISE_180) {
            return new BlockPos(lobbyBounds.minX() + 16 + offset, y, lobbyBounds.minZ() + 9 + offset);
        }
        return new BlockPos(lobbyBounds.minX() + 6 - offset, y, lobbyBounds.minZ() + 16 + offset);
    }

    public SentryRuinsPiece chooseRoom(String name, BlockPos pos, Rotation rotation, Holder<StructureProcessorList> processors) {
        WeightedList<RoomProvider<?>> list = ROOM_OPTIONS.get(name);
        if (list != null) {
            Optional<RoomProvider<?>> option = list.getRandom(this.random);
            if (option.isPresent()) {
                return option.get().provide(this.manager, pos, rotation, processors);
            }
        }
        return new SentryRuinsRoom(this.manager, name, pos, rotation, processors);
    }

    /**
     * Adds all the pieces to the StructurePieceAccessor so that it can generate in the world.
     *
     * @param builder The {@link StructurePiecesBuilder} for the structure.
     */
    public void populatePiecesBuilder(StructurePiecesBuilder builder) {
        StructurePiece bossRoom = this.nodes.remove(0);
        this.nodes.forEach(builder::addPiece);
        this.edges.values().forEach(map -> map.values().forEach(connection -> builder.addPiece(connection.hallway)));
        // Add the tunnel at the end to make sure the tunnel doesn't dig into the boss room, since we have special doorway blocks.
        builder.addPiece(bossRoom);
    }

    /**
     * Checks if there is a hallway going in the given direction from the room.
     *
     * @param node      The {@link StructurePiece} node.
     * @param direction The {@link Direction} to check.
     * @return Whether there is a hallway, as a {@link Boolean}.
     */
    private boolean hasConnection(StructurePiece node, Direction direction) {
        Map<Direction, Connection> map = this.edges.get(node);
        return map != null && map.containsKey(direction);
    }

    /**
     * Checks whether the room position at the current chunk is less than three chunks away from the structure's starting chunk.
     *
     * @param chunkPos The starting {@link ChunkPos}.
     * @param pos      The {@link BlockPos} for the current room.
     * @return Whether the room position is close enough to the starting chunk, as a {@link Boolean}.
     */
    private boolean isCloseToCenter(ChunkPos chunkPos, BlockPos pos) {
        ChunkPos currentChunk = new ChunkPos(pos);
        return chunkPos.getChessboardDistance(currentChunk) <= 3;
    }

    /**
     * Checks whether the room is covered at all four corner columns.
     *
     * @param room The {@link BoundingBox} of the room.
     * @return Whether the room is covered, as a {@link Boolean}.
     */
    public static boolean isCoveredAtPos(BoundingBox room, ChunkGenerator chunkGenerator, LevelHeightAccessor heightAccessor, RandomState randomState) {
        int minX = room.minX() - 1;
        int minZ = room.minZ() - 1;
        int maxX = room.maxX() + 1;
        int maxZ = room.maxZ() + 1;

        NoiseColumn[] columns = {
                chunkGenerator.getBaseColumn(minX, minZ, heightAccessor, randomState),
                chunkGenerator.getBaseColumn(minX, maxZ, heightAccessor, randomState),
                chunkGenerator.getBaseColumn(maxX, minZ, heightAccessor, randomState),
                chunkGenerator.getBaseColumn(maxX, maxZ, heightAccessor, randomState)
        };

        return isSolidInColumns(columns, room.minY() - 1, room.maxY() + 1);
    }

    public static boolean isMostlyCoveredAtPos(BoundingBox room, ChunkGenerator chunkGenerator, LevelHeightAccessor heightAccessor, RandomState randomState) {
        int minX = room.minX() - 1;
        int minZ = room.minZ() - 1;
        int maxX = room.maxX() + 1;
        int maxZ = room.maxZ() + 1;

        NoiseColumn[] columns = {
                chunkGenerator.getBaseColumn(minX, minZ, heightAccessor, randomState),
                chunkGenerator.getBaseColumn(minX, maxZ, heightAccessor, randomState),
                chunkGenerator.getBaseColumn(maxX, minZ, heightAccessor, randomState),
                chunkGenerator.getBaseColumn(maxX, maxZ, heightAccessor, randomState)
        };

        int coveredCorners = 0;
        for (NoiseColumn column : columns) {
            if (isSolidInColumn(column, room.minY() - 1, room.maxY() + 1)) {
                coveredCorners++;
            }
        }
        return coveredCorners >= 3;
    }

    /**
     * Iterates through an array of noise columns and checks if any of them have air in the range specified.
     *
     * @param columns The {@link NoiseColumn NoiseColumn[]} array to check.
     * @param minY    The minimum y {@link Integer} for the range.
     * @param maxY    The maximum y {@link Integer} for the range.
     * @return If there is no air in the range, as a {@link Boolean}.
     */
    public static boolean isSolidInColumns(NoiseColumn[] columns, int minY, int maxY) {
        for (NoiseColumn column : columns) {
            if (!isSolidInColumn(column, minY, maxY)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSolidInColumn(NoiseColumn column, int minY, int maxY) {
        for (int y = minY; y <= maxY; ++y) {
            if (column.getBlock(y).isAir() || column.getBlock(y).is(AetherIITags.Blocks.NON_SENTRY_RUINS_SPAWNABLE)) {
                return false;
            }
        }
        return true;
    }

    @FunctionalInterface
    public interface RoomProvider<T extends SentryRuinsPiece> {
        T provide(StructureTemplateManager manager, BlockPos pos, Rotation rotation, Holder<StructureProcessorList> processors);
    }

    /**
     * An edge going in one direction. When iterating through the graph, you cannot go backward through these.
     */
    private class Connection {
        public final StructurePiece start;
        public final StructurePiece end;
        public final StructurePiece hallway;

        /**
         * Creates a new Connection and adds it to the map.
         *
         * @param start     The {@link StructurePiece} at the start.
         * @param end       The {@link StructurePiece} at the end.
         * @param hallway   The {@link StructurePiece} for the hallway connecting the rooms.
         * @param direction The {@link Direction} of the connection.
         */
        public Connection(StructurePiece start, StructurePiece end, StructurePiece hallway, Direction direction) {
            this.start = start;
            this.end = end;
            this.hallway = hallway;
            SentryRuinsBuilder.this.edges.computeIfAbsent(start, piece -> new HashMap<>()).put(direction, this);
        }

        public StructurePiece endPiece() {
            return this.end;
        }
    }
}
