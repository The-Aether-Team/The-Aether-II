package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.blockentity.CopyBlockEntity;
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
    protected static final VoxelShape SHAPE = Block.column(6.0, 0.0, 10.0);

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
        return stateBelow.is(BlockTags.OVERRIDES_MUSHROOM_LIGHT_REQUIREMENT)
                || (soilDecision.isDefault() ? level.getRawBrightness(pos, 0) < 13 && this.mayPlaceOn(stateBelow) : soilDecision.isTrue())
                || ((level.getBlockEntity(posBelow) != null
                    && level.getBlockEntity(posBelow) instanceof CopyBlockEntity copyBlock
                    && copyBlock.getCopyState() != null
                    && copyBlock.getCopyState().is(BlockTags.OVERRIDES_MUSHROOM_LIGHT_REQUIREMENT)));
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 vec3 = state.getOffset(pos);
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
    }
}