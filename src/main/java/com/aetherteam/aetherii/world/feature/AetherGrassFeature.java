package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.AetherTallGrassBlock;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class AetherGrassFeature extends Feature<SimpleBlockConfiguration> {
    public AetherGrassFeature(Codec<SimpleBlockConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        SimpleBlockConfiguration simpleblockconfiguration = context.config();
        WorldGenLevel level = context.level();
        BlockPos blockpos = context.origin();
        BlockState blockstate = simpleblockconfiguration.toPlace().getState(context.random(), blockpos);
        BlockState belowstate = level.getBlockState(blockpos.below());
        if (blockstate.getBlock() instanceof AetherTallGrassBlock && belowstate.is(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get())) {
            blockstate = blockstate.setValue(AetherTallGrassBlock.TYPE, AetherTallGrassBlock.GrassType.ENCHANTED);
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
