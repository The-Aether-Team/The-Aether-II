package com.aetherteam.aetherii.world.structure.processor;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

/**
 * This processor sets the {@link AetherIIBlockStateProperties#REINFORCED} property to true for blocks that have it.
 */
public class ReinforceBlocksProcessor extends StructureProcessor {
    public static final ReinforceBlocksProcessor INSTANCE = new ReinforceBlocksProcessor();

    public static final MapCodec<ReinforceBlocksProcessor> CODEC = MapCodec.unit(ReinforceBlocksProcessor.INSTANCE);

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos origin, BlockPos centerBottom, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo modifiedBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        if (modifiedBlockInfo.state().hasProperty(AetherIIBlockStateProperties.REINFORCED)) {
            return new StructureTemplate.StructureBlockInfo(modifiedBlockInfo.pos(), modifiedBlockInfo.state().setValue(AetherIIBlockStateProperties.REINFORCED, true), modifiedBlockInfo.nbt());
        }
        return super.process(level, origin, centerBottom, originalBlockInfo, modifiedBlockInfo, settings, template);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherIIStructureProcessors.REINFORCE_BLOCKS.get();
    }
}