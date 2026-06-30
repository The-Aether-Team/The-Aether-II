package com.aetherteam.aetherii.world.structure.piece;

import com.aetherteam.aetherii.AetherII;
import com.mojang.serialization.DynamicOps;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.function.Function;

/**
 * Extension of {@link TemplateStructurePiece} to make our lives easier when making custom structure pieces.
 */
public abstract class AetherTemplateStructurePiece extends TemplateStructurePiece {
    protected final Holder<StructureProcessorList> processors;
    public AetherTemplateStructurePiece(StructurePieceType type, StructureTemplateManager templateManager, ResourceLocation name, StructurePlaceSettings placeSettings, BlockPos templatePosition, Holder<StructureProcessorList> processors) {
        super(type, 0, templateManager, name, name.toString(), addProcessors(placeSettings, processors), templatePosition);
        this.setOrientation(this.getRotation().rotate(Direction.SOUTH));
        this.processors = processors;
    }

    public AetherTemplateStructurePiece(StructurePieceType type, RegistryAccess access, CompoundTag tag, StructureTemplateManager templateManager, Function<ResourceLocation, StructurePlaceSettings> settingsFactory) {
        super(type, tag, templateManager, settingsFactory.andThen(settings -> readSettings(tag, settings, access)));
        this.setOrientation(this.getRotation().rotate(Direction.SOUTH));
        this.processors = readProcessors(tag, access);
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        super.addAdditionalSaveData(context, tag);
        tag.putString("Rotation", this.placeSettings.getRotation().name());
        if (this.placeSettings.getRotationPivot() != BlockPos.ZERO) {
            tag.putLong("RotationPivot", this.placeSettings.getRotationPivot().asLong());
        }
        writeProcessors(tag, context.registryAccess(), this.processors);
    }

    private static StructurePlaceSettings readSettings(CompoundTag tag, StructurePlaceSettings settings, RegistryAccess access) {
        settings.setRotation(Rotation.valueOf(tag.getString("Rotation")));
        if (tag.contains("RotationPivot")) {
            settings.setRotationPivot(BlockPos.of(tag.getLong("RotationPivot")));
        }
        Holder<StructureProcessorList> processors = readProcessors(tag, access);
        addProcessors(settings, processors);
        return settings;
    }

    protected static Holder<StructureProcessorList> readProcessors(CompoundTag tag, RegistryAccess access) {
        DynamicOps<Tag> ops = RegistryOps.create(NbtOps.INSTANCE, access);
        return StructureProcessorType.LIST_CODEC
            .parse(ops, tag.get("Processors"))
            .resultOrPartial(AetherII.LOGGER::error)
            .orElseThrow(() -> new IllegalStateException("Invalid processor found"));
    }

    protected static void writeProcessors(CompoundTag tag, RegistryAccess access, Holder<StructureProcessorList> processors) {
        DynamicOps<Tag> ops = RegistryOps.create(NbtOps.INSTANCE, access);
        StructureProcessorType.LIST_CODEC
            .encodeStart(ops, processors)
            .resultOrPartial(AetherII.LOGGER::error)
            .ifPresent(entry -> tag.put("Processors", entry));
    }

    protected static StructurePlaceSettings addProcessors(StructurePlaceSettings settings, Holder<StructureProcessorList> processors) {
        processors.value().list().forEach(settings::addProcessor);
        return settings;
    }

    public static StructurePlaceSettings makeSettingsWithPivot(StructurePlaceSettings settings, StructureTemplateManager templateManager, ResourceLocation name, Rotation rotation) {
        StructureTemplate template = templateManager.getOrCreate(name);
        TransformInfo info = getTransformInfo(template, rotation);
        settings.setRotationPivot(info.pivot());
        settings.setRotation(info.rotation());
        settings.setMirror(info.mirror());
        return settings;
    }

    public static TransformInfo getTransformInfo(StructureTemplate template, Rotation rotation) {
        Vec3i size = template.getSize(rotation);
        int xOffset = ((size.getX()) >> 1);
        int zOffset = ((size.getZ()) >> 1);
        BlockPos pivot = new BlockPos(xOffset, 0, zOffset);
        return new TransformInfo(Mirror.NONE, rotation, pivot);
    }

    @Override
    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) { }

    public record TransformInfo(Mirror mirror, Rotation rotation, BlockPos pivot) { }
}
