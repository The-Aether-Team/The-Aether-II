package com.aetherteam.aetherii.world.structure.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nullable;

public class CappedGravityProcessor extends StructureProcessor {
    public static final MapCodec<CappedGravityProcessor> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Heightmap.Types.CODEC.fieldOf("heightmap").orElse(Heightmap.Types.WORLD_SURFACE_WG).forGetter(codec -> codec.heightmap),
            Codec.INT.fieldOf("offset").orElse(0).forGetter(codec -> codec.offset),
            Codec.INT.fieldOf("y_distance_below").forGetter(codec -> codec.yDistanceBelow),
            Codec.INT.fieldOf("y_distance_above").forGetter(codec -> codec.yDistanceAbove)
    ).apply(instance, CappedGravityProcessor::new));

    private final Heightmap.Types heightmap;
    private final int offset;
    private final int yDistanceBelow;
    private final int yDistanceAbove;

    public CappedGravityProcessor(Heightmap.Types heightmap, int offset, int yDistanceBelow, int yDistanceAbove) {
        this.heightmap = heightmap;
        this.offset = offset;
        this.yDistanceBelow = yDistanceBelow;
        this.yDistanceAbove = yDistanceAbove;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader level, BlockPos origin, BlockPos centerBottom, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo modifiedBlockInfo, StructurePlaceSettings settings) {
        Heightmap.Types heightmapTypes;
        if (level instanceof ServerLevel) {
            if (this.heightmap == Heightmap.Types.WORLD_SURFACE_WG) {
                heightmapTypes = Heightmap.Types.WORLD_SURFACE;
            } else if (this.heightmap == Heightmap.Types.OCEAN_FLOOR_WG) {
                heightmapTypes = Heightmap.Types.OCEAN_FLOOR;
            } else {
                heightmapTypes = this.heightmap;
            }
        } else {
            heightmapTypes = this.heightmap;
        }

        BlockPos pos = modifiedBlockInfo.pos();
        int i = level.getHeight(heightmapTypes, pos.getX(), pos.getZ()) + this.offset;
        int j = originalBlockInfo.pos().getY();
        if (i > origin.getY() - yDistanceBelow && i < origin.getY() + yDistanceAbove) {
            return new StructureTemplate.StructureBlockInfo(new BlockPos(pos.getX(), i + j, pos.getZ()), modifiedBlockInfo.state(), modifiedBlockInfo.nbt());
        }
        return null;
    }

    @Override
    protected StructureProcessorType<?> getType() {
            return AetherIIStructureProcessorTypes.GRAVITY_CAPPED.get();
    }
}