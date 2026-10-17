package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.AetherTallGrassBlock;
import com.aetherteam.aetherii.block.natural.Snowable;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class AetherBlockFeature extends Feature<SimpleBlockConfiguration> {
    public AetherBlockFeature(Codec<SimpleBlockConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        SimpleBlockConfiguration simpleblockconfiguration = context.config();
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        BlockState state = simpleblockconfiguration.toPlace().getState(level, context.random(), pos);
        BlockState belowState = level.getBlockState(pos.below());
        if (state.getBlock() instanceof Snowable && state.hasProperty(BlockStateProperties.SNOWY)
                && (state.getBlock() == AetherIIBlocks.ARCTIC_SNOW.get() || (belowState.getBlock() == AetherIIBlocks.AETHER_GRASS_BLOCK.get() && belowState.getValue(GrassBlock.SNOWY)))) {
            state = state.setValue(BlockStateProperties.SNOWY, true);
        }
        if (state.getBlock() instanceof AetherTallGrassBlock && belowState.is(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK)) {
            state = state.setValue(AetherTallGrassBlock.TYPE, AetherTallGrassBlock.GrassType.ENCHANTED);
        }
        if (state.canSurvive(level, pos)) {
            if (!state.getValueOrElse(BlockStateProperties.SNOWY, false) && belowState.getBlock() == AetherIIBlocks.AETHER_GRASS_BLOCK.get() && belowState.getValue(GrassBlock.SNOWY)) {
                level.setBlock(pos.below(), belowState.setValue(GrassBlock.SNOWY, false), 2);
            }
            if (state.getBlock() instanceof DoublePlantBlock) {
                if (!level.isEmptyBlock(pos.above())) {
                    return false;
                }
                DoublePlantBlock.placeAt(level, state, pos, 2);
            } else {
                level.setBlock(pos, state, 2);
            }
            return true;
        } else {
            return false;
        }
    }
}
