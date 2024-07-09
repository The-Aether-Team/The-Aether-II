package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;

public class BrettlPlantTipBlock extends GrowingPlantHeadBlock {
    public static final MapCodec<BrettlPlantTipBlock> CODEC = simpleCodec(BrettlPlantTipBlock::new);
    public static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 15.0, 12.0);

    @Override
    public MapCodec<BrettlPlantTipBlock> codec() {
        return CODEC;
    }

    public BrettlPlantTipBlock(Properties properties) {
        super(properties, Direction.UP, SHAPE, false, 0.1);
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
        return NetherVines.getBlocksToGrowWhenBonemealed(random);
    }

    @Override
    protected Block getBodyBlock() {
        return AetherIIBlocks.BRETTL_PLANT.get();
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return NetherVines.isValidGrowthState(state);
    }

    @Override
    public BlockState getStateForPlacement(LevelAccessor level) {
        return this.defaultBlockState().setValue(AGE, 0);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 2;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(AGE) < 2 && CommonHooks.canCropGrow(level, pos.relative(this.growthDirection), state, random.nextDouble() < 0.1)) {
            BlockPos blockpos = pos.relative(this.growthDirection);
            if (this.canGrowInto(level.getBlockState(blockpos))) {
                level.setBlockAndUpdate(blockpos, this.getGrowIntoState(state, level.random));
                CommonHooks.fireCropGrowPost(level, blockpos, level.getBlockState(blockpos));
            }
        }
    }

    @Override
    public BlockState getMaxAgeState(BlockState state) {
        return state.setValue(AGE, 2);
    }

    @Override
    public boolean isMaxAge(BlockState state) {
        return state.getValue(AGE) == 2;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos blockpos = pos.relative(this.growthDirection);
        int i = Math.min(state.getValue(AGE) + 1, 2);
        int j = this.getBlocksToGrowWhenBonemealed(random);

        for (int k = 0; k < j && this.canGrowInto(level.getBlockState(blockpos)); k++) {
            level.setBlockAndUpdate(blockpos, state.setValue(AGE, i));
            blockpos = blockpos.relative(this.growthDirection);
            i = Math.min(i + 1, 2);
        }
    }
}