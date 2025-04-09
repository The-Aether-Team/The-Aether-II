package com.aetherteam.aetherii.world.structure.processor;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public class NoiseProcessor extends StructureProcessor {
    public final DensityFunction noise;
    public static final MapCodec<NoiseProcessor> CODEC = DensityFunction.HOLDER_HELPER_CODEC.xmap(NoiseProcessor::new, (instance) -> instance.noise).fieldOf("value");

    public NoiseProcessor(DensityFunction noise) {
        this.noise = noise;
    }

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