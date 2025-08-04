package com.aetherteam.aetherii.block.dungeon;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.util.TriState;

public class RotshroomClusterBlock extends BushBlock {
    public static final MapCodec<RotshroomClusterBlock> CODEC = simpleCodec(RotshroomClusterBlock::new);
    protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 8.0, 14.0);

    public RotshroomClusterBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends RotshroomClusterBlock> codec() {
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
        Vec3 vec3 = state.getOffset(pos);
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
    }
}