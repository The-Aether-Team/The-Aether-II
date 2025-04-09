package com.aetherteam.aetherii.world.structure.processor;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public class DensityFunctionProcessor extends StructureProcessor {
    private final BlockState targetState;
    private final BlockState resultState;
    public final DensityFunction density;

    public static final MapCodec<DensityFunctionProcessor> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockState.CODEC.fieldOf("target_state").forGetter(codec -> codec.targetState),
            BlockState.CODEC.fieldOf("result_state").forGetter(codec -> codec.resultState),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("density_function").forGetter(codec -> codec.density)
            ).apply(instance, DensityFunctionProcessor::new)
    );

    public DensityFunctionProcessor(BlockState targetState, BlockState resultState, DensityFunction density) {
        this.targetState = targetState;
        this.resultState = resultState;
        this.density = density;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos origin, BlockPos centerBottom, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo modifiedBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        double noise = this.density.compute(new DensityFunction.SinglePointContext(modifiedBlockInfo.pos().getX(), modifiedBlockInfo.pos().getY(), modifiedBlockInfo.pos().getZ()));

        if (noise > 0 && originalBlockInfo.state() == targetState) {
            return new StructureTemplate.StructureBlockInfo(modifiedBlockInfo.pos(), resultState, modifiedBlockInfo.nbt());
        }
        return super.process(level, origin, centerBottom, originalBlockInfo, modifiedBlockInfo, settings, template);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherIIStructureProcessorTypes.DENSITY_FUNCTION.get();
    }
}