package com.aetherteam.aetherii.world.structure.piece.guardiantree;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.world.structure.piece.AetherTemplateStructurePiece;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.function.Function;

public class InfectedGuardianTreePiece extends AetherTemplateStructurePiece {
    public InfectedGuardianTreePiece(StructurePieceType type, StructureTemplateManager manager, String name, StructurePlaceSettings settings, BlockPos pos, Holder<StructureProcessorList> processors) {
        this(type, manager, makeLocation(name), settings, pos, processors);
    }

    public InfectedGuardianTreePiece(StructurePieceType type, StructureTemplateManager manager, Identifier name, StructurePlaceSettings settings, BlockPos pos, Holder<StructureProcessorList> processors) {
        super(type, manager, name, settings, pos, processors);
    }

    public InfectedGuardianTreePiece(StructurePieceType type, RegistryAccess access, CompoundTag tag, StructureTemplateManager manager, Function<Identifier, StructurePlaceSettings> settingsFactory) {
        super(type, access, tag, manager, settingsFactory);
    }

    protected static Identifier makeLocation(String name) {
        return Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_guardian_tree/" + name);
    }
}
