package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.AetherIIBlocks;
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

public class AetherFlowerFeature extends Feature<SimpleBlockConfiguration> {
    public AetherFlowerFeature(Codec<SimpleBlockConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        SimpleBlockConfiguration simpleblockconfiguration = context.config();
        WorldGenLevel level = context.level();
        BlockPos blockpos = context.origin();
        BlockState blockstate = simpleblockconfiguration.toPlace().getState(context.random(), blockpos);
        BlockState atstate = level.getBlockState(blockpos);
        BlockState belowstate = level.getBlockState(blockpos.below());
        if (blockstate.getBlock() instanceof Snowable && blockstate.hasProperty(BlockStateProperties.SNOWY)
                && (atstate.getBlock() == AetherIIBlocks.ARCTIC_SNOW.get() || (belowstate.getBlock() == AetherIIBlocks.AETHER_GRASS_BLOCK.get() && belowstate.getValue(GrassBlock.SNOWY)))) {
            blockstate = blockstate.setValue(BlockStateProperties.SNOWY, true);
        }
        if (blockstate.canSurvive(level, blockpos)) {
            if (blockstate.getBlock() instanceof DoublePlantBlock) {
                if (!level.isEmptyBlock(blockpos.above())) {
                    return false;
                }

                DoublePlantBlock.placeAt(level, blockstate, blockpos, 2);
            } else {
                level.setBlock(blockpos, blockstate, 2);
            }

            return true;
        } else {
            return false;
        }
    }
}