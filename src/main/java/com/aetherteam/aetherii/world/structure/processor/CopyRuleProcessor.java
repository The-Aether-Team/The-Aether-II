package com.aetherteam.aetherii.world.structure.processor;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.dungeon.CopyBlock;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
        RandomSource random = RandomSource.create(Mth.getSeed(relativeBlockInfo.pos()));
        BlockState state = level.getBlockState(relativeBlockInfo.pos());

        if (state.getBlock() instanceof CopyBlock) {
//            AetherII.LOGGER.info("1");
            CompoundTag tag = relativeBlockInfo.nbt();
            if (tag != null) {
//                AetherII.LOGGER.info("2");
                Optional<BlockState> copyState = tag.read("copy_state", BlockState.CODEC);
                if (copyState.isPresent()) {
//                    AetherII.LOGGER.info("3");
                    for (ProcessorRule rule : this.rules) {
//                        AetherII.LOGGER.info("4");
                        if (rule.test(copyState.get(), state, blockInfo.pos(), relativeBlockInfo.pos(), pos, random)) {
//                            AetherII.LOGGER.info("5");
                            tag.store("copy_state", BlockState.CODEC, rule.getOutputState());
                            return new StructureTemplate.StructureBlockInfo(relativeBlockInfo.pos(), relativeBlockInfo.state(), tag);
                        }
                    }
                }
            }
        }
        return relativeBlockInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherIIStructureProcessorTypes.COPY_RULE.get();
    }
}
