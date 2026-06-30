package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.blockentity.CopyBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RotshroomBlock extends BushBlock {
    protected static final VoxelShape SHAPE = Block.box(5, 0.0, 5, 11, 10.0, 11);

    public RotshroomBlock(Properties properties) {
        super(properties);
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.isSolidRender(level, pos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos posBelow = pos.below();
        BlockState stateBelow = level.getBlockState(posBelow);
        return stateBelow.is(BlockTags.MUSHROOM_GROW_BLOCK)
                || super.canSurvive(state, level, pos)
                || (level.getRawBrightness(pos, 0) < 13 && this.mayPlaceOn(stateBelow, level, posBelow))
                || ((level.getBlockEntity(posBelow) != null
                    && level.getBlockEntity(posBelow) instanceof CopyBlockEntity copyBlock
                    && copyBlock.getCopyState() != null
                    && copyBlock.getCopyState().is(BlockTags.MUSHROOM_GROW_BLOCK)));
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 vec3 = state.getOffset(level, pos);
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
    }
}
