package com.aetherteam.aetherii.block.dungeon;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.TriState;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RotshroomBlock extends BushBlock {

    public RotshroomBlock(Properties properties) {
        super(properties);
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
}