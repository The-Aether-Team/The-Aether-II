package com.aetherteam.aetherii.world.structure.processor;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.dungeon.CopyBlock;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DensityFunctionProcessor extends StructureProcessor {
    private final BlockState inputState;
    private final BlockState outputState;
    public final DensityFunction density;
    public final boolean modifyCopyBlocks;

    public static final MapCodec<DensityFunctionProcessor> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockState.CODEC.fieldOf("input_state").forGetter(codec -> codec.inputState),
            BlockState.CODEC.fieldOf("output_state").forGetter(codec -> codec.outputState),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("density_function").forGetter(codec -> codec.density),
            Codec.BOOL.fieldOf("modify_copy_blocks").forGetter(codec -> codec.modifyCopyBlocks)
            ).apply(instance, DensityFunctionProcessor::new)
    );

    public DensityFunctionProcessor(BlockState inputState, BlockState outputState, DensityFunction density, boolean modifyCopyBlocks) {
        this.inputState = inputState;
        this.outputState = outputState;
        this.density = density;
        this.modifyCopyBlocks = modifyCopyBlocks;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos origin, BlockPos centerBottom, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo modifiedBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        if (level instanceof WorldGenLevel worldGenLevel) {

            DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(worldGenLevel.getSeed());
            density.mapAll(visitor);
            double noise = this.density.compute(new DensityFunction.SinglePointContext(modifiedBlockInfo.pos().getX(), modifiedBlockInfo.pos().getY(), modifiedBlockInfo.pos().getZ()));
            BlockState state = blockInfo.state();

            if (noise > 0) {
                if (modifyCopyBlocks) {
                    if (state.getBlock() instanceof CopyBlock copyBlock) {
                        CompoundTag tag = blockInfo.nbt();
                        if (tag != null) {
                            Optional<BlockState> copyState = tag.read("copy_state", BlockState.CODEC);
                            if (copyState.isPresent() && copyState.equals(Optional.of(inputState))) {
                                modifiedBlockInfo.nbt().store("copy_state", BlockState.CODEC, outputState);
                                return new StructureTemplate.StructureBlockInfo(modifiedBlockInfo.pos(), copyBlock.defaultBlockState().setValue(CopyBlock.EMPTY, false), modifiedBlockInfo.nbt());
                            }
                        }
                    }
                }

                if (state == inputState) {
                    return new StructureTemplate.StructureBlockInfo(modifiedBlockInfo.pos(), outputState, modifiedBlockInfo.nbt());
                }
            }
        }
        return super.process(level, origin, centerBottom, blockInfo, modifiedBlockInfo, settings, template);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherIIStructureProcessorTypes.DENSITY_FUNCTION.get();
    }
}