package com.aetherteam.aetherii.world.structure.type;

import com.aetherteam.aetherii.world.structure.piece.guardiantree.InfectedGuardianTreeBuilder;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.*;

public class InfectedGuardianTreeStructure extends Structure {
    public static final MapCodec<InfectedGuardianTreeStructure> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            settingsCodec(builder)
    ).apply(builder, InfectedGuardianTreeStructure::new));

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
        WorldgenRandom random = context.random();
        Rotation structureRotation = Rotation.getRandom(random);

        int startHeight = context.chunkGenerator().getFirstFreeHeight(context.chunkPos().getMiddleBlockX(), context.chunkPos().getMiddleBlockZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
        BlockPos startPos = chunkPos.getBlockAt(0, startHeight, 0);

        return Optional.of(new GenerationStub(startPos, builder -> this.generatePieces(builder, context, startPos, structureRotation)));
    }

    private void generatePieces(StructurePiecesBuilder builder, GenerationContext context, BlockPos startPos, Rotation structureRotation) {
        InfectedGuardianTreeBuilder graph = new InfectedGuardianTreeBuilder(context);
        graph.initializeDungeon(builder, startPos, structureRotation);
    }

    @Override
    public StructureType<?> type() {
        return AetherIIStructureTypes.INFECTED_GUARDIAN_TREE.get();
    }
}
