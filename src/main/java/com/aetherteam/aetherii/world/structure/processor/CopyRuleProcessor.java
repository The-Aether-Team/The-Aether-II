package com.aetherteam.aetherii.world.structure.processor;

import com.aetherteam.aetherii.block.dungeon.CopyBlock;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class CopyRuleProcessor extends StructureProcessor {
    public static final MapCodec<CopyRuleProcessor> CODEC = ProcessorRule.CODEC.listOf().fieldOf("rules").xmap(CopyRuleProcessor::new, (processor) -> processor.rules);
    private final ImmutableList<ProcessorRule> rules;

    public CopyRuleProcessor(List<? extends ProcessorRule> rules) {
        this.rules = ImmutableList.copyOf(rules);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @Nullable StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos offset, BlockPos pos, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo relativeBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        RandomSource random = RandomSource.create(Mth.getSeed(blockInfo.pos()));
        BlockState state = blockInfo.state();
        if (state.getBlock() instanceof CopyBlock) {
            CompoundTag tag = blockInfo.nbt();
            if (tag != null) {
                Optional<BlockState> copyState = readBlockState(tag, "copy_state");
                if (copyState.isPresent()) {
                    for (ProcessorRule rule : this.rules) {
                        if (rule.test(copyState.get(), state, blockInfo.pos(), relativeBlockInfo.pos(), pos, random)) {
                            storeBlockState(tag, "copy_state", rule.getOutputState());
                        }
                    }
                }
            }
        }
        return super.process(level, offset, pos, blockInfo, relativeBlockInfo, settings, template);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherIIStructureProcessorTypes.COPY_RULE.get();
    }

    private static Optional<BlockState> readBlockState(CompoundTag tag, String key) {
        if (!tag.contains(key)) {
            return Optional.empty();
        }
        return BlockState.CODEC.parse(NbtOps.INSTANCE, tag.get(key)).result();
    }

    private static void storeBlockState(CompoundTag tag, String key, BlockState state) {
        BlockState.CODEC.encodeStart(NbtOps.INSTANCE, state).result().ifPresent(encoded -> tag.put(key, encoded));
    }
}
