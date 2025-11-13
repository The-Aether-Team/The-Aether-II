package com.aetherteam.aetherii.world.structure.piece.sentry;


import com.aetherteam.aetherii.world.structure.piece.AetherIIStructurePieceTypes;
import com.aetherteam.aetherii.world.structure.piece.AetherTemplateStructurePiece;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.function.Function;

/**
 * Starting piece for the Bronze Dungeon. Has the slider.
 */
public class SentryWorkshopBossRoom extends SentryWorkshopPiece {
    public SentryWorkshopBossRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation, Holder<StructureProcessorList> processors) {
        this(AetherIIStructurePieceTypes.SENTRY_WORKSHOP_BOSS_ROOM.get(), manager, name, AetherTemplateStructurePiece.makeSettingsWithPivot(makeSettings(), manager, SentryWorkshopPiece.makeLocation(name), rotation), pos, processors);
    }

    public SentryWorkshopBossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
        this(AetherIIStructurePieceTypes.SENTRY_WORKSHOP_BOSS_ROOM.get(), context.registryAccess(), tag, context.structureTemplateManager(), resourceLocation -> SentryWorkshopBossRoom.makeSettings());
    }

    public SentryWorkshopBossRoom(StructurePieceType type, StructureTemplateManager manager, String name, StructurePlaceSettings settings, BlockPos pos, Holder<StructureProcessorList> processors) {
        this(type, manager, makeLocation(name), settings, pos, processors);
    }

    public SentryWorkshopBossRoom(StructurePieceType type, StructureTemplateManager manager, ResourceLocation name, StructurePlaceSettings settings, BlockPos pos, Holder<StructureProcessorList> processors) {
        super(type, manager, name, settings, pos, processors);
    }

    public SentryWorkshopBossRoom(StructurePieceType type, RegistryAccess access, CompoundTag tag, StructureTemplateManager manager, Function<ResourceLocation, StructurePlaceSettings> settingsFactory) {
        super(type, access, tag, manager, settingsFactory);
    }

    static StructurePlaceSettings makeSettings() {
        return new StructurePlaceSettings()
                .setFinalizeEntities(true);
    }

    @Override
    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {
        if (name.equals("Treasure Chest")) {
//            BlockPos chest = pos.below();
//            BlockEntity entity = level.getBlockEntity(chest);
//            if (entity instanceof RandomizableContainerBlockEntity container) {
//                container.setLootTable(AetherLoot.BRONZE_DUNGEON_REWARD, random.nextLong());
//            }
//            TreasureChestBlockEntity.setDungeonType(level, chest, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "bronze"));
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        }
    }
}
