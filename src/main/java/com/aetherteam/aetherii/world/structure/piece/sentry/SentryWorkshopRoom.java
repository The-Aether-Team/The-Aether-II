package com.aetherteam.aetherii.world.structure.piece.sentry;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.world.structure.piece.AetherIIStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Objects;

/**
 * A normal Bronze Dungeon room or hallway.
 */
public class SentryWorkshopRoom extends SentryWorkshopPiece {
    public SentryWorkshopRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation, Holder<StructureProcessorList> processors) {
        super(AetherIIStructurePieceTypes.SENTRY_WORKSHOP_ROOM.get(), manager, name, new StructurePlaceSettings().setRotation(rotation), pos, processors);
    }

    public SentryWorkshopRoom(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherIIStructurePieceTypes.SENTRY_WORKSHOP_ROOM.get(), context.registryAccess(), tag, context.structureTemplateManager(), resourceLocation -> new StructurePlaceSettings());
    }

    @Override
    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);

        if (random.nextInt(4) > 1) {
            ConfiguredFeature<?, ?> feature = Objects.requireNonNull(level.registryAccess().get(ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.parse(name))).orElse(null)).value();
            feature.place((WorldGenLevel) level, level.getLevel().getChunkSource().getGenerator(), random, pos);
        }
    }
}