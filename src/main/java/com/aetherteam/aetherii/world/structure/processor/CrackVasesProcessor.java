package com.aetherteam.aetherii.world.structure.processor;

import com.aetherteam.aetherii.block.furniture.VaseBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public class CrackVasesProcessor extends StructureProcessor {
    public static final CrackVasesProcessor INSTANCE = new CrackVasesProcessor();

    public static final MapCodec<CrackVasesProcessor> CODEC = MapCodec.unit(CrackVasesProcessor.INSTANCE);

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos origin, BlockPos centerBottom, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo modifiedBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        if (modifiedBlockInfo.state().getBlock() instanceof VaseBlock && modifiedBlockInfo.state().hasProperty(VaseBlock.CRACKED)) {
            return new StructureTemplate.StructureBlockInfo(modifiedBlockInfo.pos(), modifiedBlockInfo.state().setValue(VaseBlock.CRACKED, true), modifiedBlockInfo.nbt());
        }
        return super.process(level, origin, centerBottom, originalBlockInfo, modifiedBlockInfo, settings, template);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherIIStructureProcessorTypes.CRACK_VASES.get();
    }
}