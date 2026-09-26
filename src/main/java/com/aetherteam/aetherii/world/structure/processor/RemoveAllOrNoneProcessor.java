package com.aetherteam.aetherii.world.structure.processor;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.BottomedVineBlock;
import com.aetherteam.aetherii.block.natural.MossFlowersBlock;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nullable;

public class RemoveAllOrNoneProcessor extends StructureProcessor {
    public final BlockState state;
    public final double probability;
    public final int seedOffset;
    public final boolean buried;

    public static final MapCodec<RemoveAllOrNoneProcessor> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockState.CODEC.fieldOf("state").forGetter(codec -> codec.state),
            Codec.DOUBLE.fieldOf("probability").forGetter(codec -> codec.probability),
            Codec.INT.fieldOf("seed_offset").forGetter(codec -> codec.seedOffset),
            Codec.BOOL.fieldOf("buried").forGetter(codec -> codec.buried)
            ).apply(instance, RemoveAllOrNoneProcessor::new)
    );

    public RemoveAllOrNoneProcessor(BlockState state, double probability, int seedOffset, boolean buried) {
        this.state = state;
        this.probability = probability;
        this.seedOffset = seedOffset;
        this.buried = buried;
    }

    @SuppressWarnings("deprecation")
    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos origin, BlockPos centerBottom, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo modifiedBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        RandomSource random = RandomSource.create(Mth.getSeed(origin.above(this.seedOffset)));

        if (modifiedBlockInfo.state() == this.state && random.nextDouble() <= this.probability) {
            if (level.getBlockState(modifiedBlockInfo.pos()).isAir() || !this.buried) {
                return new StructureTemplate.StructureBlockInfo(modifiedBlockInfo.pos(), Blocks.AIR.defaultBlockState(), modifiedBlockInfo.nbt());
            }
        }
        return super.process(level, origin, centerBottom, originalBlockInfo, modifiedBlockInfo, settings, template);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherIIStructureProcessorTypes.REMOVE_ALL_OR_NONE.get();
    }
}