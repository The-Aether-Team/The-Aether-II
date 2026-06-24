package com.aetherteam.aetherii.world.structure.processor;

import com.aetherteam.aetherii.block.dungeon.CopyBlock;
import com.aetherteam.aetherii.data.resources.registries.AetherIIStructures;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.aetherteam.aetherii.world.structure.type.AetherJigsawStructure;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class DensityFunctionGradientProcessor extends DensityFunctionProcessor {
    private final BlockState inputState;
    private final BlockState outputState;
    public final DensityFunction density;
    public final boolean modifyCopyBlocks;
    public final int startY;
    public final int endYOffset;

    public static final MapCodec<DensityFunctionGradientProcessor> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockState.CODEC.fieldOf("input_state").forGetter(codec -> codec.inputState),
            BlockState.CODEC.fieldOf("output_state").forGetter(codec -> codec.outputState),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("density_function").forGetter(codec -> codec.density),
            Codec.BOOL.fieldOf("modify_copy_blocks").forGetter(codec -> codec.modifyCopyBlocks),
            Codec.INT.fieldOf("start_y").forGetter(codec -> codec.startY),
            Codec.INT.fieldOf("end_y_offset").forGetter(codec -> codec.endYOffset)
            ).apply(instance, DensityFunctionGradientProcessor::new)
    );

    public DensityFunctionGradientProcessor(BlockState inputState, BlockState outputState, DensityFunction density, boolean modifyCopyBlocks, int startY, int endYOffset) {
        super(inputState, outputState, density, modifyCopyBlocks);
        this.inputState = inputState;
        this.outputState = outputState;
        this.density = density;
        this.modifyCopyBlocks = modifyCopyBlocks;
        this.startY = startY;
        this.endYOffset = endYOffset;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos origin, BlockPos centerBottom, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo modifiedBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        if (level instanceof WorldGenLevel worldGenLevel) {

            DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(worldGenLevel.getSeed());
            density.mapAll(visitor);
            if (level instanceof ServerLevel serverLevel) {
                double noise = this.density.compute(new DensityFunction.SinglePointContext(modifiedBlockInfo.pos().getX(), modifiedBlockInfo.pos().getY(), modifiedBlockInfo.pos().getZ())) * Mth.clampedMap(modifiedBlockInfo.pos().getY(), startY(serverLevel, blockInfo.pos()), startY(serverLevel, blockInfo.pos()) + endYOffset, 0, 5.0);
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
        }
        return super.process(level, origin, centerBottom, blockInfo, modifiedBlockInfo, settings, template);
    }

    private int startY(ServerLevel level, BlockPos pos) {
        StructureStart structureStart = level.structureManager().getStructureWithPieceAt(pos, Objects.requireNonNull(level.structureManager().registryAccess().lookupOrThrow(Registries.STRUCTURE).getValue(AetherIIStructures.INFECTED_GUARDIAN_TREE)));

        if (structureStart.isValid()) {
            if (structureStart.getStructure().type() instanceof AetherJigsawStructure aetherJigsawStructure) {
                return aetherJigsawStructure.startY;
            }
        }
        return 0;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherIIStructureProcessorTypes.DENSITY_FUNCTION_GRADIENT.get();
    }
}