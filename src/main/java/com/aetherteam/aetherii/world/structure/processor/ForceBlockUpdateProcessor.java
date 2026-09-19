package com.aetherteam.aetherii.world.structure.processor;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nullable;

public class ForceBlockUpdateProcessor extends StructureProcessor {
    public static final ForceBlockUpdateProcessor INSTANCE = new ForceBlockUpdateProcessor();

    public static final MapCodec<ForceBlockUpdateProcessor> CODEC = MapCodec.unit(ForceBlockUpdateProcessor.INSTANCE);

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos origin, BlockPos centerBottom, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo modifiedBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        if (!originalBlockInfo.state().canSurvive(level, originalBlockInfo.pos())) {
            return new StructureTemplate.StructureBlockInfo(modifiedBlockInfo.pos(), Blocks.AIR.defaultBlockState(), modifiedBlockInfo.nbt());
        }

        return super.process(level, origin, centerBottom, originalBlockInfo, modifiedBlockInfo, settings, template);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherIIStructureProcessorTypes.FORCE_BLOCK_UPDATE.get();
    }
}