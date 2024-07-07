package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.neoforged.neoforge.common.util.TriState;

public class BrettlPlantBlock extends Block {
    public static final BooleanProperty BASE = AetherIIBlockStateProperties.BRETTL_BASE;
    public static final BooleanProperty GROWN = AetherIIBlockStateProperties.BRETTL_GROWN;


    public BrettlPlantBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState stateBelow = level.getBlockState(pos.below());
        if (stateBelow.is(this)) {
            return true;
        } else {
            TriState soilDecision = stateBelow.canSustainPlant(level, pos.below(), Direction.UP, stateBelow);
            if (!soilDecision.isDefault()) return soilDecision.isTrue();
            return stateBelow.is(BlockTags.DIRT) || stateBelow.is(BlockTags.SAND);
        }
    }
}