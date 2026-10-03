package com.aetherteam.aetherii.world.structure.processor;

import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DensityFunctionDegradationProcessor extends StructureProcessor {
    private final Optional<BlockState> outputState;
    private final Optional<Double> lowerBound;
    private final Optional<Double> upperBound;
    public final DensityFunction density;

    public static final MapCodec<DensityFunctionDegradationProcessor> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockState.CODEC.optionalFieldOf("output_state").forGetter(codec -> codec.outputState),
            Codec.DOUBLE.optionalFieldOf("lower_bound").forGetter(codec -> codec.lowerBound),
            Codec.DOUBLE.optionalFieldOf("upper_bound").forGetter(codec -> codec.upperBound),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("density_function").forGetter(codec -> codec.density)
            ).apply(instance, DensityFunctionDegradationProcessor::new)
    );

    public DensityFunctionDegradationProcessor(Optional<BlockState> outputState, Optional<Double> upperBound, Optional<Double> lowerBound, DensityFunction density) {
        this.outputState = outputState;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.density = density;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos origin, BlockPos centerBottom, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo modifiedBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        if (level instanceof WorldGenLevel worldGenLevel) {

            DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(worldGenLevel.getSeed());
            this.density.mapAll(visitor);
            double noise = this.density.compute(new DensityFunction.SinglePointContext(modifiedBlockInfo.pos().getX(), modifiedBlockInfo.pos().getY(), modifiedBlockInfo.pos().getZ()));
            BlockState state = blockInfo.state();
            if ((this.upperBound.isPresent() && this.lowerBound.isPresent()) ? (noise < this.upperBound.get() && noise > this.lowerBound.get()) : (noise > 0)) {
                if (state != Blocks.AIR.defaultBlockState()) {
                    return new StructureTemplate.StructureBlockInfo(modifiedBlockInfo.pos(), this.outputState.orElseGet(Blocks.AIR::defaultBlockState), modifiedBlockInfo.nbt());
                }
            }
        }
        return super.process(level, origin, centerBottom, blockInfo, modifiedBlockInfo, settings, template);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherIIStructureProcessorTypes.DENSITY_FUNCTION_DEGRADATION.get();
    }
}