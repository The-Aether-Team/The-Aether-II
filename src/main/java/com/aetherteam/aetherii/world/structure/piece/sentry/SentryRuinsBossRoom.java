package com.aetherteam.aetherii.world.structure.piece.sentry;


import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.blockentity.CopyBlockEntity;
import com.aetherteam.aetherii.world.structure.piece.AetherIIStructurePieceTypes;
import com.aetherteam.aetherii.world.structure.piece.AetherTemplateStructurePiece;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.function.Function;

/**
 * Starting piece for the Bronze Dungeon. Has the slider.
 */
public class SentryRuinsBossRoom extends SentryRuinsPiece {
    public SentryRuinsBossRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation, Holder<StructureProcessorList> processors) {
        this(AetherIIStructurePieceTypes.SENTRY_RUINS_BOSS_ROOM.get(), manager, name, AetherTemplateStructurePiece.makeSettingsWithPivot(makeSettings(), manager, SentryRuinsPiece.makeLocation(name), rotation), pos, processors);
    }

    public SentryRuinsBossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
        this(AetherIIStructurePieceTypes.SENTRY_RUINS_BOSS_ROOM.get(), context.registryAccess(), tag, context.structureTemplateManager(), resourceLocation -> SentryRuinsBossRoom.makeSettings());
    }

    public SentryRuinsBossRoom(StructurePieceType type, StructureTemplateManager manager, String name, StructurePlaceSettings settings, BlockPos pos, Holder<StructureProcessorList> processors) {
        this(type, manager, makeLocation(name), settings, pos, processors);
    }

    public SentryRuinsBossRoom(StructurePieceType type, StructureTemplateManager manager, ResourceLocation name, StructurePlaceSettings settings, BlockPos pos, Holder<StructureProcessorList> processors) {
        super(type, manager, name, settings, pos, processors);
    }

    public SentryRuinsBossRoom(StructurePieceType type, RegistryAccess access, CompoundTag tag, StructureTemplateManager manager, Function<ResourceLocation, StructurePlaceSettings> settingsFactory) {
        super(type, access, tag, manager, settingsFactory);
    }

    static StructurePlaceSettings makeSettings() {
        return new StructurePlaceSettings()
                .setFinalizeEntities(true);
    }

    @Override
    public void postProcess(WorldGenLevel level, StructureManager manager, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
        super.postProcess(level, manager, chunkGenerator, random, boundingBox, chunkPos, blockPos);
        for (StructureTemplate.StructureBlockInfo info : this.template.filterBlocks(this.templatePosition, this.placeSettings, AetherIIBlocks.LOCKED_BLOCK.get())) {
            if (level.getBlockEntity(info.pos()) instanceof CopyBlockEntity blockEntity) {
                if (this.getMirror() != Mirror.NONE) {
                    blockEntity.setCopyState(blockEntity.getCopyState().mirror(this.getMirror()));
                }
                if (this.getRotation() != Rotation.NONE) {
                    blockEntity.setCopyState(blockEntity.getCopyState().rotate(this.getRotation()));
                }
            }
        }
    }
}
