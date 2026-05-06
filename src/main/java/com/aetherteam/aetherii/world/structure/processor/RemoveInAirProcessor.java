package com.aetherteam.aetherii.world.structure.processor;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nullable;

public class RemoveInAirProcessor extends StructureProcessor {
    public static final RemoveInAirProcessor INSTANCE = new RemoveInAirProcessor();

    public static final MapCodec<RemoveInAirProcessor> CODEC = MapCodec.unit(RemoveInAirProcessor.INSTANCE);

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos origin, BlockPos centerBottom, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo modifiedBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        assert template != null;
       /* BoundingBox bounds = template.getBoundingBox(settings, modifiedBlockInfo.pos());
        if (level.getBlockState(bounds.getCenter().atY(bounds.minY())).is(Blocks.AIR)

                new BlockPos(bounds.minX(), bounds.minY(), bounds.minZ())).is(Blocks.AIR)
                && level.getBlockState(new BlockPos(bounds.maxX(), bounds.minY(), bounds.minZ())).is(Blocks.AIR)
                && level.getBlockState(new BlockPos(bounds.maxX(), bounds.maxX(), bounds.minZ())).is(Blocks.AIR)
                && level.getBlockState(new BlockPos(bounds.minX(), bounds.maxX(), bounds.minZ())).is(Blocks.AIR)
                && level.getBlockState(new BlockPos(bounds.minX(), bounds.minY(), bounds.maxZ())).is(Blocks.AIR)
                && level.getBlockState(new BlockPos(bounds.maxX(), bounds.minY(), bounds.maxZ())).is(Blocks.AIR)
                && level.getBlockState(new BlockPos(bounds.maxX(), bounds.maxX(), bounds.maxZ())).is(Blocks.AIR)
                && level.getBlockState(new BlockPos(bounds.minX(), bounds.maxX(), bounds.maxZ())).is(Blocks.AIR)


        ) {
            return new StructureTemplate.StructureBlockInfo(modifiedBlockInfo.pos(), Blocks.AIR.defaultBlockState(), modifiedBlockInfo.nbt());
        }

        */


        return super.process(level, origin, centerBottom, originalBlockInfo, modifiedBlockInfo, settings, template);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherIIStructureProcessorTypes.REMOVE_IN_AIR.get();
    }
}