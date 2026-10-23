package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.AetherGrassBlock;
import com.aetherteam.aetherii.block.natural.AetherTallGrassBlock;
import com.aetherteam.aetherii.block.natural.Snowable;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
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
        BlockState placementState = simpleblockconfiguration.toPlace().getState(level, context.random(), pos);
        BlockState state = level.getBlockState(pos);
        BlockState belowState = level.getBlockState(pos.below());

        if (pos.getX() == 463 && pos.getY() == 205 && pos.getZ() == -489) {
            AetherII.LOGGER.info("1" + " " + state + " " + placementState);
        }

        if (placementState.getBlock() instanceof Snowable snowable && (state.getBlock() == AetherIIBlocks.ARCTIC_SNOW.get() || AetherGrassBlock.plantIsSnowed(state))) {
            if (placementState.is(AetherIITags.Blocks.NATURALLY_SNOWABLE_PLANT)) {
                placementState = snowable.setSnowy(placementState);
            } else {
                return false;
            }
        }
        if (placementState.getBlock() instanceof AetherTallGrassBlock && belowState.is(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK)) {
            placementState = placementState.setValue(AetherTallGrassBlock.TYPE, AetherTallGrassBlock.GrassType.ENCHANTED);
        }
        if (placementState.canSurvive(level, pos)) {
            if (!AetherGrassBlock.plantIsSnowed(placementState) && belowState.getBlock() == AetherIIBlocks.AETHER_GRASS_BLOCK.get() && belowState.getValue(GrassBlock.SNOWY)) {
                level.setBlock(pos.below(), belowState.setValue(GrassBlock.SNOWY, false), 2);
            }
            if (placementState.getBlock() instanceof DoublePlantBlock) {
                if (!level.isEmptyBlock(pos.above())) {
                    return false;
                }
                DoublePlantBlock.placeAt(level, placementState, pos, 2);
            } else {
                level.setBlock(pos, placementState, 2);
            }
            return true;
        } else {
            return false;
        }
    }
}
