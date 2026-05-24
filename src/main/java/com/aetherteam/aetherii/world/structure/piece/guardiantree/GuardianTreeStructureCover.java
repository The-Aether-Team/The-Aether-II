package com.aetherteam.aetherii.world.structure.piece.guardiantree;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDensityFunctions;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.aetherteam.aetherii.world.structure.piece.AetherIIStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

import java.util.HashSet;
import java.util.Set;

public class GuardianTreeStructureCover extends StructurePiece {

    public GuardianTreeStructureCover(BoundingBox boundingBox) {
        super(AetherIIStructurePieceTypes.GUARDIAN_TREE_STRUCTURE_COVER.get(), 0, boundingBox);
    }

    public GuardianTreeStructureCover(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherIIStructurePieceTypes.GUARDIAN_TREE_STRUCTURE_COVER.get(), tag);
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext structurePieceSerializationContext, CompoundTag compoundTag) {

    }

    @Override
    public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox chunkBB, ChunkPos chunkPos, BlockPos referencePos) {
        HolderGetter<DensityFunction> function = level.holderLookup(Registries.DENSITY_FUNCTION);
        BlockPos pos = this.getWorldPos(0, 0, 0);
        Set<BlockPos> positions = new HashSet<>();

        DensityFunction noise = AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.DUNGEONS_STRUCTURE_COVER);
        DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(level.getSeed());
        noise.mapAll(visitor);

        for (int i = 0; i > -60; --i) { //height
            placeStructureCover(i, level, pos, noise, positions, chunkBB);
        }
    }

    public void placeStructureCover(int i, WorldGenLevel level, BlockPos pos, DensityFunction noise, Set<BlockPos> positions, BoundingBox chunkBB) {
        this.placeDisk(level, new BlockPos(pos.getX(), pos.getY() + i, pos.getZ()), 60, positions);

        for (BlockPos position : positions) {
            double density = noise.compute(new DensityFunction.SinglePointContext(position.getX(), position.getY(), position.getZ()));
            if (position.getY() == pos.getY() + i) {
                double densitySmoothed = density - Mth.clamp(position.distToCenterSqr(pos.getX(), pos.getY() + i, pos.getZ()) * 0.001F - 60 * 0.05F, 0, 10);
                if (densitySmoothed > 0) {
                    if (position.getY() > 95) {
                        this.placeBlock(level, AetherIIBlocks.HOLYSTONE.get().defaultBlockState(), position.getX(), position.getY(), position.getZ(), chunkBB);
                    } else {
                        this.placeBlock(level, AetherIIBlocks.UNDERSHALE.get().defaultBlockState(), position.getX(), position.getY(), position.getZ(), chunkBB);
                    }
                }
            }
        }
    }

    public void placeDisk(WorldGenLevel level, BlockPos center, float radius, Set<BlockPos> positions) {
        float radiusSq = radius * radius;
        this.placeProvidedBlock(level, center, positions);
        for (int z = 0; z < radius; z++) {
            for (int x = 0; x < radius; x++) {
                if (x * x + z * z > radiusSq) continue;
                this.placeProvidedBlock(level, center.offset(x, 0, z), positions);
                this.placeProvidedBlock(level, center.offset(-x, 0, -z), positions);
                this.placeProvidedBlock(level, center.offset(-z, 0, x), positions);
                this.placeProvidedBlock(level, center.offset(z, 0, -x), positions);
            }
        }
    }

    public void placeProvidedBlock(WorldGenLevel level, BlockPos pos, Set<BlockPos> positions) {
        if ((level.getBlockState(pos).isAir() && level.getBlockState(pos).getBlock() != Blocks.VOID_AIR)
                || level.getBlockState(pos).getBlock() == Blocks.BARRIER //TODO DEBUG
        ) {
            positions.add(pos);
        }
    }

}