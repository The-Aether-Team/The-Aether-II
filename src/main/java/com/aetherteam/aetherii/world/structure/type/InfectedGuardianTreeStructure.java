package com.aetherteam.aetherii.world.structure.type;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.registries.AetherIIProcessorLists;
import com.aetherteam.aetherii.world.structure.piece.guardiantree.InfectedGuardianTreeBuilder;
import com.aetherteam.aetherii.world.structure.piece.guardiantree.InfectedGuardianTreeCorridor;
import com.aetherteam.aetherii.world.structure.piece.guardiantree.InfectedGuardianTreePiece;
import com.aetherteam.aetherii.world.structure.piece.guardiantree.InfectedGuardianTreeRoom;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Rotation;
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
    private static final Vec3i CORRIDOR_SEPARATION_BOUNDS = new Vec3i(11, 24, 11);

    public InfectedGuardianTreeStructure(StructureSettings settings) {
        super(settings);
    }

//    @Override //todo do cover features here
//    public StructureStart generate(Holder<Structure> selected, ResourceKey<Level> dimension, RegistryAccess registryAccess, ChunkGenerator chunkGenerator, BiomeSource biomeSource, RandomState randomState, StructureTemplateManager structureTemplateManager, long seed, ChunkPos sourceChunkPos, int references, LevelHeightAccessor heightAccessor, Predicate<Holder<Biome>> validBiome) {
//        return super.generate(selected, dimension, registryAccess, chunkGenerator, biomeSource, randomState, structureTemplateManager, seed, sourceChunkPos, references, heightAccessor, validBiome);
//    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        BlockPos originPos = chunkPos.getWorldPosition(); //todo actual position selection

        return Optional.of(new GenerationStub(originPos, builder -> this.buildPieces(context, builder)));
    }

    public void buildPieces(GenerationContext context, StructurePiecesBuilder builder) {
        ChunkPos chunkPos = context.chunkPos();
        WorldgenRandom random = context.random();
        StructureTemplateManager templateManager = context.structureTemplateManager();
        HolderGetter<StructureProcessorList> processors = context.registryAccess().lookupOrThrow(Registries.PROCESSOR_LIST);

        BlockPos initialPos = chunkPos.getBlockAt(0, 150, 0).mutable();

        Identifier normalRoomPrefix = Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/rooms/");
        Identifier challengeRoomPrefix = Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/challenge_rooms/");
        Identifier corridorPrefix = Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/corridors/");
        Identifier deadEndPrefix = Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/dead_ends/");

        List<Identifier> normalRooms = templateManager.listTemplates().filter((identifier) -> identifier.toString().startsWith(normalRoomPrefix.toString())).toList();
        Multimap<String, Identifier> binaryMappedNormalRooms = this.binaryMappedRooms(normalRooms);

        List<Identifier> challengeRooms = templateManager.listTemplates().filter((identifier) -> identifier.toString().startsWith(challengeRoomPrefix.toString())).toList();
        Multimap<String, Identifier> binaryMappedChallengeRooms = this.binaryMappedRooms(challengeRooms);

        List<Identifier> corridors = templateManager.listTemplates().filter((identifier) -> identifier.toString().startsWith(corridorPrefix.toString())).toList();
        Multimap<String, Identifier> mappedCorridors = Multimaps.newSetMultimap(new HashMap<>(), HashSet::new);

        for (Identifier corridor : corridors) {
            String[] roomPath = corridor.getPath().split("/");
            String roomName = roomPath[roomPath.length - 1].replace("-", "").substring(0, 2);
            mappedCorridors.put(roomName, corridor);
        }

//        List<Identifier> deadEnds = context.structureTemplateManager().listTemplates().filter((identifier) -> identifier.toString().startsWith(deadEndPrefix.toString())).toList();


        //todo staircase pieces are incorrect and missing a connector bit at the top

        InfectedGuardianTreePiece entrancePiece = new InfectedGuardianTreeRoom(templateManager, "entrance", initialPos.offset(1, 0, 1), Rotation.NONE, processors.getOrThrow(AetherIIProcessorLists.SENTRY_RUINS_ROOM));
        builder.addPiece(entrancePiece);


        Vec3i staircase1Size = context.structureTemplateManager().getOrCreate(Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/staircases/floor_1")).getSize();
        initialPos = initialPos.below(staircase1Size.getY());
        InfectedGuardianTreePiece staircase1Piece = new InfectedGuardianTreeRoom(templateManager, "staircases/floor_1", initialPos.offset(7, 0, 7), Rotation.COUNTERCLOCKWISE_90, processors.getOrThrow(AetherIIProcessorLists.SENTRY_RUINS_ROOM));
        builder.addPiece(staircase1Piece);


        Vec3i lobby1Size = context.structureTemplateManager().getOrCreate(Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/lobbies/floor_1/lobby_01")).getSize();
        initialPos = initialPos.below(lobby1Size.getY() + 1);
        InfectedGuardianTreeBuilder.FloorGrid floor1 = new InfectedGuardianTreeBuilder.FloorGrid(2);
        floor1.planLayout(7, 3, random);
        this.buildFloor(builder, templateManager, processors, random, floor1, initialPos, "lobbies/floor_1/lobby_01", binaryMappedNormalRooms, binaryMappedChallengeRooms, mappedCorridors);


        Vec3i staircase2Size = context.structureTemplateManager().getOrCreate(Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/staircases/floor_2")).getSize();
        initialPos = initialPos.below(staircase2Size.getY() - 1);
        InfectedGuardianTreePiece staircase2Piece = new InfectedGuardianTreeRoom(templateManager, "staircases/floor_2", initialPos.offset(7, 0, 7), Rotation.COUNTERCLOCKWISE_90, processors.getOrThrow(AetherIIProcessorLists.SENTRY_RUINS_ROOM));
        builder.addPiece(staircase2Piece);


        Vec3i lobby2Size = context.structureTemplateManager().getOrCreate(Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/lobbies/floor_2/lobby_01")).getSize();
        initialPos = initialPos.below(lobby2Size.getY() + 1);
        InfectedGuardianTreeBuilder.FloorGrid floor2 = new InfectedGuardianTreeBuilder.FloorGrid(2);
        floor2.planLayout(9, 6, random);
        this.buildFloor(builder, templateManager, processors, random, floor2, initialPos, "lobbies/floor_2/lobby_01", binaryMappedNormalRooms, binaryMappedChallengeRooms, mappedCorridors);


        Vec3i staircaseBossSize = context.structureTemplateManager().getOrCreate(Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/staircases/boss")).getSize();
        initialPos = initialPos.below(staircaseBossSize.getY() - 1);
        InfectedGuardianTreePiece staircaseBossPiece = new InfectedGuardianTreeRoom(templateManager, "staircases/boss", initialPos.offset(7, 0, 7), Rotation.COUNTERCLOCKWISE_90, processors.getOrThrow(AetherIIProcessorLists.SENTRY_RUINS_ROOM));
        builder.addPiece(staircaseBossPiece);


        Vec3i bossRoomSize = context.structureTemplateManager().getOrCreate(Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/boss_room")).getSize();
        initialPos = initialPos.below(bossRoomSize.getY());
        InfectedGuardianTreePiece bossRoomPiece = new InfectedGuardianTreeRoom(templateManager, "boss_room", initialPos.offset(-2, 0, 1), Rotation.NONE, processors.getOrThrow(AetherIIProcessorLists.SENTRY_RUINS_ROOM));
        builder.addPiece(bossRoomPiece);
    }

    public void buildFloor(StructurePiecesBuilder builder, StructureTemplateManager manager, HolderGetter<StructureProcessorList> processors, WorldgenRandom random, InfectedGuardianTreeBuilder.FloorGrid floor, BlockPos initialPos, String lobby, Multimap<String, Identifier> binaryMappedNormalRooms, Multimap<String, Identifier> binaryMappedChallengeRooms, Multimap<String, Identifier> mappedCorridors) {
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
                    yield data.cell().selectRandomRoom(validRooms, random);
                }
                case CHALLENGE -> {
                    List<Identifier> validRooms = data.cell().findValidRooms(binaryMappedChallengeRooms);
                    yield data.cell().selectRandomRoom(validRooms, random);
                }
            };
            Rotation rotation = data.cell().setupRoom(roomName, floor, data.offset());

            InfectedGuardianTreePiece piece = new InfectedGuardianTreeRoom(manager, roomName, offset.offset(1, 0, 1), rotation, processors.getOrThrow(AetherIIProcessorLists.SENTRY_RUINS_ROOM));
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
                                String corridorName = Util.getRandom(new ArrayList<>(corridorOptions), random).getPath().replace("infected_guardian_tree/", "");

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

                                InfectedGuardianTreePiece piece = new InfectedGuardianTreeCorridor(manager, corridorName, corridorOffset, rotation, processors.getOrThrow(AetherIIProcessorLists.SENTRY_RUINS_ROOM));
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

    @Override
    public StructureType<?> type() {
        return AetherIIStructureTypes.INFECTED_GUARDIAN_TREE.get();
    }
}
