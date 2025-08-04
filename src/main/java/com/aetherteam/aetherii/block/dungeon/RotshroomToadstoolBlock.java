package com.aetherteam.aetherii.block.dungeon;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.util.TriState;

public class RotshroomToadstoolBlock extends BushBlock {
    public static final MapCodec<RotshroomToadstoolBlock> CODEC = simpleCodec(RotshroomToadstoolBlock::new);
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);

    public RotshroomToadstoolBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends RotshroomToadstoolBlock> codec() {
        return CODEC;
    }

    protected boolean mayPlaceOn(BlockState state) {
        return state.isSolidRender();
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos posBelow = pos.below();
        BlockState stateBelow = level.getBlockState(posBelow);
        TriState soilDecision = stateBelow.canSustainPlant(level, posBelow, Direction.UP, state);
        return stateBelow.is(BlockTags.MUSHROOM_GROW_BLOCK) || (soilDecision.isDefault() ? level.getRawBrightness(pos, 0) < 13 && this.mayPlaceOn(stateBelow) : soilDecision.isTrue());
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}