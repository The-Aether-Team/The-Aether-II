package com.aetherteam.aetherii.world.structure.piece.guardiantree;

import com.aetherteam.aetherii.world.structure.piece.AetherIIStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class InfectedGuardianTreeRoom extends InfectedGuardianTreePiece {
    public InfectedGuardianTreeRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation, Holder<StructureProcessorList> processors) {
        super(AetherIIStructurePieceTypes.INFECTED_GUARDIAN_TREE_ROOM.get(), manager, name, new StructurePlaceSettings().setRotation(rotation), pos, processors);
    }

    public InfectedGuardianTreeRoom(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherIIStructurePieceTypes.INFECTED_GUARDIAN_TREE_ROOM.get(), context.registryAccess(), tag, context.structureTemplateManager(), resourceLocation -> new StructurePlaceSettings());
    }
}
